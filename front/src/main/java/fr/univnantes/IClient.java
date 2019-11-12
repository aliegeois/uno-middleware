package fr.univnantes;

import java.rmi.Remote;
import java.rmi.RemoteException;

import fr.univnantes.cards.ACard;
import fr.univnantes.cards.Color;
import fr.univnantes.state.StateException;

public interface IClient extends Remote {
	public void startGame(int nbPlayers, ACard[] initialCards, ACard pileCard) throws RemoteException, StateException;
	public void yourTurn() throws RemoteException, StateException;
	public void draw(ACard[] cards) throws RemoteException, StateException;
	public void aboutToDrawFourCards() throws RemoteException, StateException;
	public void winContest() throws RemoteException, StateException;
	public void loseContest(ACard[] cards) throws RemoteException, StateException;
	public void getContested() throws RemoteException, StateException;
	public void getSkipped() throws RemoteException, StateException;
	public void getPlusTwoed() throws RemoteException, StateException;
	public void cardPlayedBySomeoneElse(IClient client, ACard card) throws RemoteException, StateException;

	public void setReady(boolean ready) throws StateException;
	public void contest() throws StateException;
	public void doNotContest() throws StateException;
	public void playStandardCard(ACard card) throws StateException;
	public void playWildCard(ACard card, Color color) throws StateException;

	public String getName() throws Exception;
}