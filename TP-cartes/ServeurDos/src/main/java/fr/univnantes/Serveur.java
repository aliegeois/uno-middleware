package fr.univnantes;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

public class Serveur extends UnicastRemoteObject implements IDos {
	private static final long serialVersionUID = 4419803375268480151L;

	public Serveur() throws RemoteException {
		super();
	}

	public String parler(String phrase) throws RemoteException {
		return phrase;
	}

	public static void main(String[] args) {
		try {
			Serveur obj = new Serveur();

			LocateRegistry.createRegistry(1099);
			Naming.bind("rmi://localhost:1099/Hello", obj);

			System.out.println("Serveur ready");
		} catch(Exception e) {
			System.err.println("Exception: " + e.toString());
			e.printStackTrace();
		}
	}
}