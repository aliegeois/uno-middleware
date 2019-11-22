package fr.univnantes.client.state;

import java.rmi.RemoteException;

import fr.univnantes.card.ACard;
import fr.univnantes.card.Effect;
import fr.univnantes.card.EffectCard;

class PlayingState implements State {
	@Override
	public void playCard(Game game, ACard card) {
		game.cards.remove(card);
		game.topCard = card;
		if(card instanceof EffectCard && ((EffectCard)card).effect == Effect.PlusFour)
			game.setState(new WillGetContestedState());
		else
			game.setState(new WaitingState());
		try {
			game.server.playCard(game.client.name, card);
		} catch(RemoteException e) {}
	}
}