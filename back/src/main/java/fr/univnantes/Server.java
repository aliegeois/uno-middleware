package fr.univnantes;

import java.util.Set;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
<<<<<<< HEAD
import java.util.Map;
=======
import java.util.List;
import java.util.ArrayList;
>>>>>>> ec069c3e8e4193e4fe1b93d27cfc7579bad7a577
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

	public Server() throws RemoteException {
		super();
	}

	public void InitDeck(){

		deck.add(new NumberCard(0, COLOR.Rouge));
		deck.add(new NumberCard(0, COLOR.Bleu));
		deck.add(new NumberCard(0, COLOR.Vert));
		deck.add(new NumberCard(0, COLOR.Jaune));

		for(int j=1; i<3; i++){
			for (int i=1; i<10; i++){
				deck.add(new NumberCard(i, COLOR.Rouge));
				deck.add(new NumberCard(i, COLOR.Vert));
				deck.add(new NumberCard(i, COLOR.Bleu));
				deck.add(new NumberCard(i, COLOR.Jaune));

			}

			deck.add(new EffectCard(EFFET.SKip, COLOR.Rouge));
			deck.add(new EffectCard(EFFET.SKip, COLOR.Bleu));
			deck.add(new EffectCard(EFFET.SKip, COLOR.Jaune));
			deck.add(new EffectCard(EFFET.SKip, COLOR.Vert));

			deck.add(new EffectCard(EFFET.Reverse, COLOR.Rouge));
			deck.add(new EffectCard(EFFET.Reverse, COLOR.Bleu));
			deck.add(new EffectCard(EFFET.Reverse, COLOR.Jaune));
			deck.add(new EffectCard(EFFET.Reverse, COLOR.Vert));

			deck.add(new EffectCard(EFFET.Plus2, COLOR.Rouge));
			deck.add(new EffectCard(EFFET.Plus2, COLOR.Bleu));
			deck.add(new EffectCard(EFFET.Plus2, COLOR.Jaune));
			deck.add(new EffectCard(EFFET.Plus2, COLOR.Vert));

			for (int i=1; i<3; i++) {
				deck.add(new WildCard(WILDEFFECT.ChangeColor));
				deck.add(new WildCard(WILDEFFECT.Plus4));
			}
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

	public void setReady(IClient c, boolean r) throws RemoteException {
		ready.put(c, r);
		if(ready.entrySet().stream().filter(e -> e.getValue()).count() == waiting.size()) {
			start();
		}
	}

	private void start() {

	}

	public static void main(String[] args) {
		try {
			Server obj = new Server();

			LocateRegistry.createRegistry(1099);
			Naming.bind("rmi://localhost:1099/Uno", obj);

			System.out.println("Serveur ready");
		} catch(Exception e) {
			System.err.println("Exception: " + e.toString());
			e.printStackTrace();
		}
	}
}