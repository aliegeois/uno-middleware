package fr.univnantes;

import java.util.Set;

import fr.univnantes.cards.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collections;

public class Server extends UnicastRemoteObject implements IServer {
	private static final long serialVersionUID = 4419803375268480151L;

	private boolean started = false;
	private Set<IClient> waiting = new HashSet<>();
	private Map<IClient, Boolean> ready = new HashMap<>();

	private List<Card> deck = new ArrayList<Card>();

	public Server() throws Exception {
		super();

		LocateRegistry.createRegistry(1099);
		Naming.bind("rmi://localhost:1099/Uno", this);
	}

	public void initDeck() {
		for(Color color : Color.values()) {
			deck.add(new NumberCard(0, color));

			for(int i = 1; i <= 2; i++) {
				for(int j = 1; j <= 9; j++)
					deck.add(new NumberCard(j, color));
				
				deck.add(new EffectCard(Effect.Skip, color));
				deck.add(new EffectCard(Effect.Reverse, color));
				deck.add(new EffectCard(Effect.Plus2, color));
			}
		}

		for(int j = 1; j <= 4; j++) {
			deck.add(new EffectCard(Effect.ChangeColor, null));
			deck.add(new EffectCard(Effect.Plus4, null));
		}

		Collections.shuffle(deck);
	}

	public String test(String s) throws RemoteException {
		System.out.println("Client dit: " + s);
		return s;
	}

	public boolean join(IClient c) throws RemoteException {
		if(started)
			return false;
		
		waiting.add(c);
		return true;
	}

	public void setReady(IClient client, boolean isReady) throws RemoteException {
		ready.put(client, isReady);
		if(ready.values().stream().filter(e -> e).count() == waiting.size())
			start();
	}

	private void start() {

	}

	public static void main(String[] args) {
		try {
			new Server();
		} catch(Exception e) {
			System.err.println("Exception: " + e.toString());
			e.printStackTrace();
		}
	}
}