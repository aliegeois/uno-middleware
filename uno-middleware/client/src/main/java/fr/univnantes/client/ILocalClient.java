package fr.univnantes.client;

import java.util.List;

import fr.univnantes.card.ACard;

public interface ILocalClient {
	public void setReady(boolean ready);
	public void contest();
	public void doNotContest();
	public void counterPlusTwo(ACard card);
	public void counterSkip(ACard card);
	/*public void playStandardCard(ACard card);
	public void playPlusFourCard(ACard card);
	public void playWildCard(ACard card);*/
	public void playCard(ACard card);

	public List<String> getPlayers();
	public List<ACard> getCards();
	public ACard getTopCard();
}