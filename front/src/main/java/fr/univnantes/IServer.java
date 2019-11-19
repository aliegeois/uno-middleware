package fr.univnantes;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import fr.univnantes.cards.ACard;

public interface IServer extends Remote {
	public boolean join(IRemoteClient client) throws RemoteException;
	public void setReady(IRemoteClient client, boolean ready) throws RemoteException;

	public List<ACard> contest(IRemoteClient contestingClient, IRemoteClient contestedClient) throws RemoteException;
	public List<ACard> doNotContest(IRemoteClient clientlient) throws RemoteException;
	public void counterPlusTwo(IRemoteClient client, ACard card, int quantity) throws RemoteException;
	public void doNotCounterPlusTwo(IRemoteClient client, int quantity) throws RemoteException;
	public void counterSkip(IRemoteClient client, ACard card) throws RemoteException;
	public void playCard(IRemoteClient client, ACard card) throws RemoteException;
}