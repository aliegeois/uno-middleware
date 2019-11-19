package fr.univnantes.state;

import java.rmi.RemoteException;

import fr.univnantes.cards.ACard;
import fr.univnantes.cards.Color;

class PlayState extends State {
	@Override 
	void playStandardCard(Game game, ACard card) {
		try {
			game.playStandardCard(card);
		} catch(StateException e) {}
		game.setState(new WaitingState());
	}

	@Override
	void playPlusFourCard(Game game, ACard card, Color color) {
		card.color = color;
		try {
			game.server.playCard(game.client, card);
		} catch(RemoteException e) {}
		game.setState(new WillGetContestedState());
	}

	@Override
	void playWildCard(Game game, ACard card, Color color) {
		card.color = color;
		try {
			game.server.playCard(game.client, card);
		} catch(RemoteException e) {}
		game.setState(new WaitingState());
	}

	/*@Override
	void draw(Game game, List<ACard> cards) {
		game.cards.addAll(cards);
		// Ne pas changer d'état
	}*/
}