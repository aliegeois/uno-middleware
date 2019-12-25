package fr.univnantes.state;

import java.util.List;

import fr.univnantes.cards.ACard;

class WillGetContestedState implements State {
	@Override
	public void winContest(Game game) {
		game.setState(new WaitingState());
		game.client.ui.winContest(false);
	}

	@Override
	public void loseContest(Game game, List<ACard> cards) {
		game.setState(new WaitingState());
		game.cards.addAll(cards);
		game.client.ui.loseContest(cards, false);
	}
}