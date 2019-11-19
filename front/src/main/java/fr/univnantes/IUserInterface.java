package fr.univnantes;

import java.util.List;

import fr.univnantes.cards.ACard;

public interface IUserInterface {
	public void startGame(int nbPlayers, List<ACard> initialCards, ACard pileCard);

	public void yourTurn();
	public void draw(List<ACard> cards);

	public void aboutToDrawFourCards();
	public void winContest();
	public void loseContest(List<ACard> cards);
	public void getContested();

	public void getSkipped();
	public void getPlusTwoed(int nbCards);

	public void cardPlayedBySomeoneElse(IRemoteClient client, ACard card);
}