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
	public void contest(IRemoteClient client) throws RemoteException {

	}

	@Override
	public void doNotContest(IRemoteClient client) throws RemoteException {

	}

	@Override
	public void counterPlusTwo(IRemoteClient client, ACard card) {
		

	}

	@Override
	public void counterSkip(IRemoteClient client, ACard card) throws RemoteException {

	}

	@Override
	public void playStandardCard(IRemoteClient client, ACard card) throws RemoteException {
		

	}

	@Override
	public void playWildCard(IRemoteClient client, ACard card, Color color) throws RemoteException {
		

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