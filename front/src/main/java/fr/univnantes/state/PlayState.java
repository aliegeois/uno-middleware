package fr.univnantes.state;

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
		try { game.playWildCard(card, color); } catch(StateException e) {}
		game.setState(new WaitingState());
	}

	@Override
	void draw(Game game, List<ACard> cards) {
		try { game.draw(cards); } catch(StateException e) {}
		// Ne pas changer d'état
	}
}