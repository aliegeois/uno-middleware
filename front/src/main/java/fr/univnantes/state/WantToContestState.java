package fr.univnantes.state;

import java.util.List;

import fr.univnantes.cards.ACard;

class WantToContestState extends State {
	@Override
	void winContest(Game game) {
		game.setState(new WaitingState());
	}

	@Override
	void loseContest(Game game, List<ACard> cards) {
		game.cards.addAll(cards);
		game.setState(new WaitingState());
	}
}