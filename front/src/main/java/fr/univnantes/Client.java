package fr.univnantes;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Client extends UnicastRemoteObject implements IClient {
	public Client() throws RemoteException {

	}

	public static void main(String[] args) {
		IServer stub = (IServer)Naming.lookup("rmi://localhost:1099/Uno");

		
	}
}