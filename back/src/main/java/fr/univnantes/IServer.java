package fr.univnantes;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IServer extends Remote {
	public String test(String s) throws RemoteException;
}