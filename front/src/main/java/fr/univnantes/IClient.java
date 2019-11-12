package fr.univnantes;

import java.rmi.Remote;

import fr.univnantes.cards.ACard;

public interface IClient extends Remote {
	public void startGame(int nbPlayers, ACard[] initialCards, ACard pileCard) throws Exception;
	public void yourTurn() throws Exception;
	public void draw(ACard[] cards) throws Exception;
	public void aboutToDrawFourCards() throws Exception;
	public void winContest() throws Exception;
	public void loseContest(ACard[] cards) throws Exception;
	public void getContested() throws Exception;
	public void getSkipped() throws Exception;
	public void getPlusTwoed() throws Exception;
	public void cardPlayedBySomeoneElse(ACard card) throws Exception;

	public String getName() throws Exception;
}