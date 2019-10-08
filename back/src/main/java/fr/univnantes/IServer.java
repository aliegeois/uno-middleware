package fr.univnantes;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IServer extends Remote {
	public String test(String s) throws RemoteException;
	public boolean join(IClient c) throws RemoteException;
	public void setReady(IClient c, boolean r) throws RemoteException;
}