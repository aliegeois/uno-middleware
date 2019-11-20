package fr.univnantes;

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

import fr.univnantes.cards.ACard;
import fr.univnantes.cards.Color;
import fr.univnantes.cards.Effect;
import fr.univnantes.cards.EffectCard;
import fr.univnantes.cards.NumberCard;

public class Server extends UnicastRemoteObject implements IServer {
	private static final long serialVersionUID = 4419803375268480151L;

	private boolean started = false;
	private Map<IRemoteClient, Boolean> ready = new HashMap<>();

	private List<ACard> deck = new ArrayList<ACard>();
	private Stack<ACard> playedCards = new Stack<ACard>();
	private List<IRemoteClient> players = new ArrayList<>();
	//private ACard pileCard;
	private boolean clockwise = true;

	public Server() throws Exception {
		super();

		LocateRegistry.createRegistry(1099);
		Naming.bind("rmi://localhost:1099/Uno", this);
	}

	/**
	 * Initialise le deck
	 */
	private void initDeck() {
		Color[] usableColors = { Color.Red, Color.Blue, Color.Green, Color.Yellow };
		for(Color color : usableColors) {
			deck.add(new NumberCard(0, color));

			for(int i = 1; i <= 2; i++) {
				for(int j = 1; j <= 9; j++)
					deck.add(new NumberCard(j, color));
				
				deck.add(new EffectCard(Effect.Skip, color));
				deck.add(new EffectCard(Effect.Reverse, color));
				deck.add(new EffectCard(Effect.PlusTwo, color));
			}
		}

		for(int j = 1; j <= 4; j++) {
			deck.add(new EffectCard(Effect.Wild, Color.Wild));
			deck.add(new EffectCard(Effect.PlusFour, Color.Wild));
		}

		Collections.shuffle(deck);
	}

	@Override
	public boolean join(IRemoteClient client) throws RemoteException {
		if(started)
			return false;
		
		ready.put(client, false);

		System.out.println(client.getName() + " has joined (" + ready.values().stream().filter(e -> e).count() + " / " + ready.size() + ")");

		return true;
	}

	@Override
	public void setReady(IRemoteClient client, boolean isReady) throws RemoteException {
		ready.put(client, isReady);
		System.out.println(client.getName() + " is now" + (isReady ? " " : " not ") + "ready (" + ready.values().stream().filter(e -> e).count() + " / " + ready.size() + ")");
		if(isReady && ready.size() > 1 && ready.size() < 15 && ready.values().stream().filter(e -> e).count() == ready.size()) {
			System.out.println("Starting the game");
			start();
		}
	}

	private void start() {
		initDeck();
		players = new ArrayList<>(ready.keySet());
		List<List<ACard>> cards = new ArrayList<>();

		Iterator<ACard> iter = deck.iterator();

		for(int i = 0; i < players.size(); i++) {
			List<ACard> current = new ArrayList<>();
			int cardsDistributed = 0;
			while(iter.hasNext() && cardsDistributed < 6) {
				current.add(iter.next());
				iter.remove();
				cardsDistributed++;
			}
			cards.add(current);
		}

		playedCards.push(deck.remove(0));

		for(int i = 0; i < players.size(); i++) {
			try {
				players.get(i).startGame(players.size(), cards.get(i), playedCards.peek());
			} catch(RemoteException e) {
				e.printStackTrace();
			}
		}

		try {
			players.get(0).yourTurn();
		} catch(RemoteException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<ACard> contest(IRemoteClient contestingClient, IRemoteClient contestedClient) throws RemoteException {
		long playableCards = contestedClient.getCards().stream()
		.filter(card -> card instanceof EffectCard ? (((EffectCard)card).effect != Effect.Wild) && (((EffectCard)card).effect != Effect.PlusFour) : true) // On récupères toutes les cartes sauf wild et plusfour
		.filter(card -> {
			boolean condition = card.color == playedCards.peek().color;
			if(card instanceof NumberCard) {
				condition |= ((NumberCard)card).value == ((NumberCard)playedCards.peek()).value;
			} else if(card instanceof EffectCard) { // Selon les règles, pas besoin de vérifer les wilds
				condition |= ((EffectCard)card).effect == ((EffectCard)playedCards.peek()).effect;
			}
			return condition; // On récupère toutes les cartes qui sont possiblement jouables
		}).count();

		if(playableCards == 0) { // contest perdu car l'autre joueur ne pouvait en effet rien jouer
			contestedClient.winContest();
			return cardsToDraw(6);
		} else { // contest gagné car l'autre joueur aurait pu poser une autre carte
			contestedClient.loseContest(cardsToDraw(4));
			return new ArrayList<>();
		}
	}

	@Override
	public List<ACard> doNotContest(IRemoteClient client) throws RemoteException {
		// client.draw(cardsToDraw(4));
		nextClient(client).yourTurn();
		return cardsToDraw(4);
	}

	@Override
	public void counterPlusTwo(IRemoteClient client, ACard card, int quantity) throws RemoteException {
		sendCardPlayed(client, card);
		nextClient(client).getPlusTwoed(quantity);
	}

	@Override
	public void doNotCounterPlusTwo(IRemoteClient client, int quantity) throws RemoteException {
		client.draw(cardsToDraw(2 * quantity));
	}

	@Override
	public void counterSkip(IRemoteClient client, ACard card) throws RemoteException {
		sendCardPlayed(client, card);
		nextClient(nextClient(client)).yourTurn();
	}

	@Override
	public void playCard(IRemoteClient client, ACard card) throws RemoteException {
		sendCardPlayed(client, card);

		if(client.getCards().size() == 0) {
			// Dire à tout le monde qu'il a gagné
		}

		if(card instanceof EffectCard) {
			switch(((EffectCard)card).effect) {
				case Skip:
					nextClient(client).getSkipped();
					break;
				
				case PlusTwo:
					if(nextClient(client).getCards().stream().filter(ncard -> ncard instanceof EffectCard && ((EffectCard)ncard).effect == Effect.PlusTwo).count() > 0) {
						nextClient(client).getPlusTwoed(1);
					} else {
						nextClient(client).draw(cardsToDraw(2));
					}
					
					break;
				
				case Reverse:
					clockwise = !clockwise;
					nextClient(client).yourTurn();
					break;

				case PlusFour:
					nextClient(client).aboutToDrawFourCards();
					break;

				default:
			}
		} else if(card instanceof NumberCard) {
			nextClient(client).yourTurn();
		}
	}

	private IRemoteClient nextClient(IRemoteClient client) {
		return players.get((players.indexOf(client) + (clockwise ? 1 : -1) + players.size()) % players.size());
	}

	private void sendCardPlayed(IRemoteClient client, ACard card) {
		playedCards.push(card);

		players.stream().filter(player -> player != client).forEach(player -> {
			try { player.cardPlayedBySomeoneElse(client, card); } catch(RemoteException e) {}
		});
	}

	private List<ACard> cardsToDraw(int nbCards) {
		ArrayList<ACard> cardsDrawn = new ArrayList<>();
		int firstStep = Math.min(nbCards, deck.size());

		for(int i = 0; i < firstStep; i++)
			cardsDrawn.add(deck.get(deck.size()));

		if(nbCards != firstStep) {
			ACard lastCard = playedCards.pop();
			deck.addAll(playedCards);
			playedCards.clear();
			playedCards.push(lastCard);
			
			for(int i = 0; i < nbCards - firstStep; i++)
				cardsDrawn.add(deck.get(deck.size()));
		}

		return cardsDrawn;
	}

	public static void main(String[] args) {
		try {
			new Server();
			System.out.println("Server started");
		} catch(Exception e) {
			System.err.println(e.toString());
			e.printStackTrace();
		}
	}
}