package fr.univnantes;

import java.rmi.Remote;
import java.rmi.RemoteException;

import fr.univnantes.cards.ACard;
import fr.univnantes.cards.Color;

public interface IServer extends Remote {
	public String test(String s) throws RemoteException;
	public boolean join(IClient client) throws RemoteException;
	public void setReady(IClient client, boolean ready) throws RemoteException;

	public void contest(IClient client) throws RemoteException;
	public void doNotContest(IClient client) throws RemoteException;
	public void counterPlusTwo(IClient client, ACard card) throws RemoteException;
	public void counterSkip(IClient client, ACard card) throws RemoteException;
	public void playStandardCard(IClient client, ACard card) throws RemoteException;
	public void playWildCard(IClient client, ACard card, Color color) throws RemoteException;
}