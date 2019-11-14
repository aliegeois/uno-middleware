package fr.univnantes;

import fr.univnantes.cards.ACard;
import fr.univnantes.cards.Color;

public interface ILocalClient {
	public void setReady(boolean ready);
	public void contest(IRemoteClient contestedClient);
	public void doNotContest();
	public void playStandardCard(ACard card);
	public void playWildCard(ACard card, Color color);
}