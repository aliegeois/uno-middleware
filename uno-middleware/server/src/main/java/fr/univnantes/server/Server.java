package fr.univnantes.server;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.function.Predicate;

import fr.univnantes.remote.*;
import fr.univnantes.card.*;

public class Server extends UnicastRemoteObject implements IServer {
	private static final long serialVersionUID = 4419803375268480151L;

	private static Predicate<? super ACard> isSkip() { return card -> card instanceof EffectCard && ((EffectCard)card).effect == Effect.Skip; }
	private static Predicate<? super ACard> isPlusTwo() { return card -> card instanceof EffectCard && ((EffectCard)card).effect == Effect.PlusTwo; }

	private class LocalClient {
		public final IRemoteClient client;
		public final String name;
		public final int index;
		public boolean ready = false;
		public final List<ACard> cards = new ArrayList<>();

		LocalClient(IRemoteClient client, String name, int index) {
			this.client = client;
			this.name = name;
			this.index = index;
		}

		void yourTurn() throws RemoteException {
			System.out.println("Debut du tour de " + name + ", sa main: " + ACard.asText(cards, false));
			while(cards.stream().noneMatch(c -> c.canBePlayedOn(playedCards.peek()))) {
				List<ACard> cardsDrawn = cardsToDraw(1);
				System.out.println("Aucune carte jouable, " + name + " pioche " + cardsDrawn.get(0));
				cards.addAll(cardsDrawn);
				System.out.println("Main de " + name + " : " + ACard.asText(cards, false));
				client.draw(cardsDrawn, true);
			}
			client.yourTurn();
		}

		void playCard(ACard card) {
			playedCards.push(card);
			System.out.println(name + " joue " + card);
			cards.removeIf(c -> c.id == card.id);
			System.out.println("Sa main : " + ACard.asText(cards, false));
			
			players.values().stream()
			.filter(lc -> lc.index != index)
			.forEach(lc -> {
				try {
					lc.client.cardPlayedBySomeoneElse(name, card);
				} catch(RemoteException e) {
					e.printStackTrace();
				}
			});

			if(cards.size() == 0) {
				players.values().forEach(lc -> {
					try {
						lc.client.endGame(name);
					} catch(RemoteException e) {
						e.printStackTrace();
					}
				});
				System.exit(0);
			}
		}
	}

	private boolean started = false;
	private Map<String, LocalClient> players = new HashMap<>();
	private List<String> playersOrder = new ArrayList<>();

	private List<ACard> deck = new ArrayList<ACard>();
	private Stack<ACard> playedCards = new Stack<ACard>();
	private boolean clockwise = true;

	public Server() throws RemoteException {
		super();

		LocateRegistry.createRegistry(1099);
		try {
			Naming.bind("rmi://localhost:1099/Uno", this);
		} catch(AlreadyBoundException e) {
			e.printStackTrace();
		} catch(MalformedURLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean join(IRemoteClient client) throws RemoteException {
		if(started)
			return false;
		
		String name = client.getName();
		players.put(name, new LocalClient(client, name, players.size()));
		playersOrder.add(name);

		System.out.println(client.getName() + " has joined (" + players.values().stream().filter(e -> e.ready).count() + " / " + players.size() + ")");

		return true;
	}

	@Override
	public void setReady(String client, boolean isReady) throws RemoteException {
		players.get(client).ready = isReady;
		System.out.println(client + " is now" + (isReady ? " " : " not ") + "ready (" + players.values().stream().filter(e -> e.ready).count() + " / " + players.size() + ")");
		if(isReady && players.size() > 1 && players.size() < 15 && players.values().stream().noneMatch(e -> !e.ready)) {
			System.out.println("Starting the game");
			start();
		}
	}

	private void start() {
		Color[] usableColors = { Color.Red, Color.Blue, Color.Green, Color.Yellow };
		for(Color color : usableColors) {
			deck.add(new NumberCard(color, 0));

			for(int i = 1; i <= 2; i++) {
				for(int j = 1; j <= 9; j++)
					deck.add(new NumberCard(color, j));
				
				deck.add(new EffectCard(color, Effect.Skip));
				deck.add(new EffectCard(color, Effect.Reverse));
				deck.add(new EffectCard(color, Effect.PlusTwo));
			}
		}

		for(int j = 1; j <= 4; j++) {
			deck.add(new EffectCard(Color.Wild, Effect.Wild));
			deck.add(new EffectCard(Color.Wild, Effect.PlusFour));
		}

		Collections.shuffle(deck);
		
		Iterator<ACard> iter = deck.iterator();

		for(LocalClient lc : players.values()) {
			List<ACard> current = new ArrayList<>();
			int cardsDistributed = 0;
			while(iter.hasNext() && cardsDistributed < 6) {
				current.add(iter.next());
				iter.remove();
				cardsDistributed++;
			}
			lc.cards.addAll(current);
		}

		playedCards.push(deck.remove(0));

		for(LocalClient lc : players.values()) {
			try {
				lc.client.startGame(playersOrder, lc.cards, playedCards.peek());
			} catch(RemoteException e) {
				e.printStackTrace();
			}
		}

		try {
			players.get(playersOrder.get(0)).yourTurn();
		} catch(RemoteException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<ACard> contest(String contestingClient) throws RemoteException {
		LocalClient contestedClient = previousClient(contestingClient);

		long nbPlayableCards = contestedClient.cards.stream()
		.filter(card -> card instanceof EffectCard ? (((EffectCard)card).effect != Effect.Wild) && (((EffectCard)card).effect != Effect.PlusFour) : true) // On récupères toutes les cartes sauf wild et plusfour
		.filter(card -> {
			return card.canBePlayedOn(playedCards.peek());
		}).count();

		if(nbPlayableCards == 0) { // contest perdu car l'autre joueur ne pouvait en effet rien jouer
			contestedClient.client.winContest();
			List<ACard> cardsDrawn = cardsToDraw(6);
			players.get(contestingClient).cards.addAll(cardsDrawn);
			nextClient(contestingClient).yourTurn();
			return cardsDrawn;
		} else { // contest gagné car l'autre joueur aurait pu poser une autre carte
			List<ACard> cardsDrawn = cardsToDraw(4);
			contestedClient.cards.addAll(cardsDrawn);
			contestedClient.client.loseContest(cardsDrawn);
			nextClient(contestingClient).yourTurn();
			return new ArrayList<>();
		}
	}

	@Override
	public List<ACard> doNotContest(String client) throws RemoteException {
		previousClient(client).client.winContest();
		nextClient(client).yourTurn();
		List<ACard> cardsDrawn = cardsToDraw(4);
		players.get(client).cards.addAll(cardsDrawn);
		return cardsDrawn;
	}

	@Override
	public void counterPlusTwo(String client, ACard card, int nbCardsStacked) throws RemoteException {
		players.get(client).playCard(card);
		/*.cards.remove(card);
		sendCardPlayed(client, card);*/
		if(nextClient(client).cards.stream().anyMatch(isPlusTwo())) {
			nextClient(client).client.getPlusTwoed(nbCardsStacked);
		} else {
			List<ACard> cardsDrawn = cardsToDraw(2 *nbCardsStacked);
			nextClient(client).cards.addAll(cardsDrawn);
			nextClient(client).client.draw(cardsDrawn, false);
			nextClient(nextClient(client).name).yourTurn();
		}
	}

	@Override
	public void counterSkip(String client, ACard card) throws RemoteException {
		players.get(client).playCard(card);
		/*.cards.remove(card);
		sendCardPlayed(client, card);*/
		if(nextClient(client).cards.stream().anyMatch(isSkip())) {
			nextClient(client).client.getSkipped();
		} else {
			nextClient(nextClient(client).name).yourTurn();
		}
	}

	@Override
	public void playCard(String client, ACard card) throws RemoteException {
		players.get(client).playCard(card);
		/*.cards.remove(card);
		sendCardPlayed(client, card);*/

		// if(client.getCards().size() == 0) {
		// 	// Dire à tout le monde qu'il a gagné
		// }

		if(card instanceof EffectCard) {
			switch(((EffectCard)card).effect) {
				case Skip:
					if(nextClient(client).cards.stream().anyMatch(isSkip()))
						nextClient(client).client.getSkipped();
					else
						nextClient(nextClient(client).name).yourTurn();
					break;
				
				case PlusTwo:
					if(nextClient(client).cards.stream().anyMatch(isPlusTwo())) {
						nextClient(client).client.getPlusTwoed(1);
					} else {
						List<ACard> cardsDrawn = cardsToDraw(2);
						nextClient(client).cards.addAll(cardsDrawn);
						nextClient(client).client.draw(cardsDrawn, false);
						nextClient(nextClient(client).name).yourTurn();
					}
					break;
				
				case Reverse:
					clockwise = !clockwise;
					nextClient(client).yourTurn();
					break;
				
				case Wild:
					nextClient(client).yourTurn();
					break;

				case PlusFour:
					nextClient(client).client.aboutToDrawFourCards();
					break;
			}
		} else if(card instanceof NumberCard) {
			nextClient(client).yourTurn();
		}
	}

	private LocalClient previousClient(String client) {
		return players.get(playersOrder.get((players.get(client).index + (clockwise ? -1 : 1) + playersOrder.size()) % playersOrder.size()));
	}

	private LocalClient nextClient(String client) {
		return players.get(playersOrder.get((players.get(client).index + (clockwise ? 1 : -1) + playersOrder.size()) % playersOrder.size()));
	}

	/**
	 * Quand un client joue une carte, annonce aux autres la carte qui vient d'être jouée
	 * @param client Le client qui vient de jouer
	 * @param card La carte qui vient d'être jouée
	 * @throws RemoteException
	 */
	/*private void sendCardPlayed(String client, ACard card) throws RemoteException {
		playedCards.push(card);

		LocalClient currentClient = players.get(client);

		players.values().stream()
		.filter(lc -> lc.index != currentClient.index)
		.forEach(lc -> {
			try {
				lc.client.cardPlayedBySomeoneElse(currentClient.name, card);
			} catch(RemoteException e) {
				e.printStackTrace();
			}
		});
	}*/

	/**
	 * Pioche autant de cartes que demandé et shuffle la pile de cartes si besoin
	 * @param nbCards Nombre de cartes à piocher
	 * @return Liste des cartes piochées
	 */
	private List<ACard> cardsToDraw(int nbCards) {
		List<ACard> cardsDrawn = new ArrayList<>();
		int firstStep = Math.min(nbCards, deck.size());

		for(int i = 0; i < firstStep; i++)
			cardsDrawn.add(deck.remove(0));

		if(nbCards != firstStep) {
			ACard lastCard = playedCards.pop();
			deck.addAll(playedCards);
			playedCards.clear();
			playedCards.push(lastCard);
			
			for(int i = 0; i < nbCards - firstStep; i++)
				cardsDrawn.add(deck.remove(0));
		}

		return cardsDrawn;
	}


	public static void main(String[] args) {
		try {
			System.out.println("Starting server...");
			new Thread() {
				@Override
				public void run() {
					try {
						new Server();
						System.out.println("Server started!");
					} catch(RemoteException e) {
						e.printStackTrace();
					}
				}
			};
			
		} catch(Exception e) {
			System.err.println(e.toString());
			e.printStackTrace();
		}
	}
}