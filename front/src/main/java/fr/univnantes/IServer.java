package fr.univnantes;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import fr.univnantes.cards.ACard;

public interface IServer extends Remote {
	public boolean join(IRemoteClient client) throws RemoteException;
	public void setReady(String client, boolean ready) throws RemoteException;

	public List<ACard> contest(String contestingClient) throws RemoteException;
	public List<ACard> doNotContest(String client) throws RemoteException;
	public void counterPlusTwo(String client, ACard card, int quantity) throws RemoteException;
	public void doNotCounterPlusTwo(String client, int quantity) throws RemoteException;
	public void counterSkip(String client, ACard card) throws RemoteException;
	public void playCard(String client, ACard card) throws RemoteException;
}