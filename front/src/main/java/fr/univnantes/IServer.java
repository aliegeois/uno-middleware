package fr.univnantes;

import java.rmi.Remote;
import java.rmi.RemoteException;

import fr.univnantes.cards.ACard;
import fr.univnantes.cards.Color;

public interface IServer extends Remote {
	public String test(String s) throws RemoteException;
	public boolean join(IRemoteClient client) throws RemoteException;
	public void setReady(IRemoteClient client, boolean ready) throws RemoteException;

	public void contest(IRemoteClient client) throws RemoteException;
	public void doNotContest(IRemoteClient client) throws RemoteException;
	public void counterPlusTwo(IRemoteClient client, ACard card) throws RemoteException;
	public void counterSkip(IRemoteClient client, ACard card) throws RemoteException;
	public void playStandardCard(IRemoteClient client, ACard card) throws RemoteException;
	public void playWildCard(IRemoteClient client, ACard card, Color color) throws RemoteException;
}