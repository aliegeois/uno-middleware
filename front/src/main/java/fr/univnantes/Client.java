package fr.univnantes;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import javafx.application.Application;

public class Client extends UnicastRemoteObject implements IClient {
	private static final long serialVersionUID = 2501464824644144715L;

	private String name;

	public Client(String name) throws RemoteException {
		this.name = name;
	}

	public static void main(String[] args) {
		try {
			IServer server = (IServer)Naming.lookup("rmi://localhost:1099/Uno");
			if(args.length > 0) {
				Client c = new Client(args[0]);
				server.join(c);
			}

			new HelloFX();
			Application.launch();
		} catch(Exception e) {
			System.err.println("Exception: " + e.toString());
			e.printStackTrace();
		}
	}
}