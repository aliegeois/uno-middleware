package fr.univnantes;

import fr.univnantes.cards.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

public class Server extends UnicastRemoteObject implements IServer {
	private static final long serialVersionUID = 4419803375268480151L;

	private boolean started = false;
	private Map<IRemoteClient, Boolean> ready = new HashMap<>();

	private List<ACard> deck = new ArrayList<ACard>();
	private List<IRemoteClient> players = new ArrayList<>();
	private ACard pileCard;
	private boolean clockwise = true;

	public Server() throws Exception {
		super();

		LocateRegistry.createRegistry(1099);
		Naming.bind("rmi://localhost:1099/Uno", this);
	}

	/**
	 * Initialise le deck
	 */
	public void initDeck() {
		for(Color color : Color.values()) {
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
			deck.add(new EffectCard(Effect.Wild, null));
			deck.add(new EffectCard(Effect.PlusFour, null));
		}

		Collections.shuffle(deck);
	}

	public String test(String s) throws RemoteException {
		System.out.println("Client dit: " + s);
		return s;
	}

	public boolean join(IRemoteClient c) throws RemoteException {
		if(started)
			return false;
		
		System.out.println(c.getName());
		
		ready.put(c,false);
		return true;
	}

	public void setReady(IRemoteClient client, boolean isReady) throws RemoteException {
		ready.put(client, isReady);
		if(isReady && ready.size() > 2 && ready.size() < 15 && ready.values().stream().filter(e -> e).count() == ready.size()) {
			start();
		}
	}

	private void start() {
		initDeck();
		players = new ArrayList<>(ready.keySet());
		List<List<ACard>> cards = new ArrayList<>(); 

		Iterator<ACard> iter = deck.iterator();

		for(int i = 0; i < players.size(); i++) {
			int cardsDistributed = 0;
			while(iter.hasNext() && cardsDistributed < 6) {
				ACard current = iter.next();
				iter.remove();
				cards.get(i).add(current);
				cardsDistributed++;
			}
		}

		this.pileCard = deck.get(0);
		deck.remove(0);

		for(int i = 0; i < players.size(); i++) {
			try {
				players.get(i).setCards(cards.get(i));
				players.get(i).startGame(players.size(), cards.get(i), pileCard);
			} catch(RemoteException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void contest(IRemoteClient contestingClient, IRemoteClient contestedClient) throws RemoteException {
		long playableCards = contestedClient.getCards().stream()
		.filter(card -> card instanceof EffectCard ? (((EffectCard)card).effect != Effect.Wild) && (((EffectCard)card).effect != Effect.PlusFour) : true) // On récupères toutes les cartes sauf wild et plusfour
		.filter(card -> {
			boolean condition = card.color == pileCard.color;
			if(card instanceof NumberCard) {
				condition |= ((NumberCard)card).value == ((NumberCard)pileCard).value;
			} else if(card instanceof EffectCard) { // Selon les règles, pas besoin de vérifer les wilds
				condition |= ((EffectCard)card).effect == ((EffectCard)pileCard).effect;
			}
			return condition; // On récupère toutes les cartes qui sont possiblement jouables
		}).count();
		if(playableCards == 0) { // contest perdu car l'autre joueur ne pouvait en effet rien jouer
			// Choisir les 4 cartes à piocher
			// Dire à contestingClient qu'il pioche ces cartes
		} else { // contest gagné car l'autre joueur aurait pu poser une autre carte
			contestingClient.winContest();
			// Choisir les cartes à piocher pour contestedClient
			// Les lui faire piocher;
		}
	}

	@Override
	public void doNotContest(IRemoteClient client) throws RemoteException {
		// Définir liste des cartes à piocher
		// Faire piocher au client 4 cartes
		nextClient(client).yourTurn();
	}

	@Override
	public void counterPlusTwo(IRemoteClient client, ACard card, int quantity) throws RemoteException {
		sendCardPlayed(client, card);
		nextClient(client).getPlusTwoed(quantity);
	}

	@Override
	public void doNotCounterPlusTwo(IRemoteClient client, int quantity) throws RemoteException {
		//Faire piocher 2*quantity cartes		
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
					nextClient(client).getPlusTwoed(1);
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
		pileCard = card;

		players.stream().filter(player -> player != client).forEach(player -> {
			try { player.cardPlayedBySomeoneElse(client, card); } catch(RemoteException e) {}
		});
	}

	public static void main(String[] args) {
		try {
			new Server();
			System.out.println("Server started");
		} catch(Exception e) {
			System.err.println("Exception: " + e.toString());
			e.printStackTrace();
		}
	}
}