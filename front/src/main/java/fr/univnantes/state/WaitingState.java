package fr.univnantes.state;

import fr.univnantes.cards.*;

class WaitingState extends State {
	@Override
	void yourTurn(Game game) {
		game.setState(new PlayState());
	}

	@Override
	void draw(Game game, ACard[] cards) {

	}

	@Override
	void aboutToDrawFourCards(Game game) {
		game.setState(new ContestState());
	}

	@Override
	void winContest(Game game) {

	}

	@Override
	void loseContest(Game game, ACard[] cards) {

	}

	@Override
	void getContested(Game game) {

	}

	@Override
	void getSkipped(Game game) {

	}

	@Override
	void getPlusTwoed(Game game) {

	}

	@Override
	void cardPlayedBySomeoneElse(Game game, ACard card) {

	}
}