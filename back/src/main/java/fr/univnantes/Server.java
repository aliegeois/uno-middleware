package fr.univnantes;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

public class Server extends UnicastRemoteObject implements IServer {
	private static final long serialVersionUID = 4419803375268480151L;

	private Set<IClient> waiting = new HashSet<>();

	public Server() throws RemoteException {
		super();
	}



	public String parler(String phrase) throws RemoteException {
		return phrase;
	}

	public static void main(String[] args) {
		try {
			Serveur obj = new Server();

			LocateRegistry.createRegistry(1099);
			Naming.bind("rmi://localhost:1099/Hello", obj);

			System.out.println("Serveur ready");
		} catch(Exception e) {
			System.err.println("Exception: " + e.toString());
			e.printStackTrace();
		}
	}
}