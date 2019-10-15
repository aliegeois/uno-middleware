package fr.univnantes;

import java.rmi.Remote;

import fr.univnantes.cards.ICard;

public interface IClient extends Remote {
	public void startGame(ICard[] initialCards) throws Exception;
	public void yourTurn() throws Exception;
	public void draw(ICard[] cards) throws Exception;
	public void aboutToDrawFourCards() throws Exception;
	public void winContest() throws Exception;
	public void loseContest(ICard[] cards) throws Exception;
	public void getContested() throws Exception;
	public void getSkipped() throws Exception;
	public void getPlusTwoed() throws Exception;
	public void cardPlayedBySomeoneElse(ICard card) throws Exception;

	public String getName() throws Exception;
}