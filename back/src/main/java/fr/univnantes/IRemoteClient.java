package fr.univnantes;

import java.rmi.RemoteException;
import java.util.List;

import fr.univnantes.cards.ACard;
import fr.univnantes.cards.Color;

public interface IRemoteClient {
	public void startGame(int nbPlayers, List<ACard> initialCards, ACard pileCard) throws RemoteException;
	public void yourTurn() throws RemoteException;
	public void draw(List<ACard> cards) throws RemoteException;
	public void aboutToDrawFourCards() throws RemoteException;
	public void winContest() throws RemoteException;
	public void loseContest(List<ACard> cards) throws RemoteException;
	public void getContested() throws RemoteException;
	public void getSkipped() throws RemoteException;
	public void getPlusTwoed(int quantity) throws RemoteException;
	public void cardPlayedBySomeoneElse(IRemoteClient client, ACard card) throws RemoteException;

	public void setCards(List<ACard> cards) throws RemoteException;
	public List<ACard> getCards() throws RemoteException;
	public String getName() throws RemoteException;
	public boolean isPlaying() throws RemoteException;

	public void setReady(boolean ready) throws RemoteException;
	public void contest() throws RemoteException;
	public void doNotContest() throws RemoteException;
	public void playStandardCard(ACard card) throws RemoteException;
	public void playWildCard(ACard card, Color color) throws RemoteException;
}