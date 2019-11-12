package fr.univnantes;

import fr.univnantes.cards.ACard;

public interface IGUI {
	public void joinLobby();
	public void leaveLobby();
	public void startGame(ACard[] initialCards);

	public void yourTurn();
	public void playStandardCard(ACard card);
	public void playWildCard(ACard card);
	public void draw(ACard[] cards);

	public void aboutToDrawFourCards();
	public void contest();
	public void doNotContest();
	public void winContest();
	public void loseContest(ACard[] cards);
	public void getContested();

	public void getSkipped();
	public void counterSkip(ACard card);
	public void getPlusTwoed();
	public void counterPlusTwo(ACard card);

	public void cardPlayedBySomeoneElse(IClient client, ACard card);

	public void replay();
	public void quit();
}