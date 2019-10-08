package fr.univnantes;

import java.util.Set;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

public class Server extends UnicastRemoteObject implements IServer {
	private static final long serialVersionUID = 4419803375268480151L;

	private boolean started = false;
	private Set<IClient> waiting = new HashSet<>();
	private Map<IClient, Boolean> ready = new HashMap<>();

	public Server() throws RemoteException {
		super();
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