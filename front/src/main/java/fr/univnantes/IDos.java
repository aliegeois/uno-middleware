package fr.univnantes;

import java.rmi.Remote;
import java.rmi.RemoteException;

interface IDos extends Remote {
	public String parler(String phrase) throws RemoteException;
}