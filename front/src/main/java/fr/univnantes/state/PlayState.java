package fr.univnantes.state;

import java.rmi.RemoteException;
import java.util.List;

import fr.univnantes.cards.*;

class PlayState extends State {
	@Override 
	void playStandardCard(Game game, ACard card) {
		try { game.playStandardCard(card); } catch(StateException e) {}
		game.setState(new WaitingState());
	}

	@Override
	void playWildCard(Game game, ACard card, Color color) {
		card.color = color;
		try { game.server.playCard(game.client, card); } catch(RemoteException e) {}
		game.setState(new WaitingState());
	}

	@Override
	void draw(Game game, List<ACard> cards) {
		try { game.draw(cards); } catch(StateException e) {}
		// Ne pas changer d'état
	}
}