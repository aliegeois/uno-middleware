package fr.univnantes.state;

import fr.univnantes.card.*;

class WaitingState extends State {
	@Override
	void yourTurn(StateContext context) {
		context.setState(new PlayState());
	}

	@Override
	void draw(StateContext context, ACard[] cards) {

	}

	@Override
	void aboutToDrawFourCards(StateContext context) {
		context.setState(new ContestState());
	}

	@Override
	void winContest(StateContext context) {

	}

	@Override
	void loseContest(StateContext context, ACard[] cards) {

	}

	@Override
	void getContested(StateContext context) {

	}

	@Override
	void getSkipped(StateContext context) {

	}

	@Override
	void getPlusTwoed(StateContext context) {

	}

	@Override
	void cardPlayedBySomeoneElse(StateContext context, ACard card) {

	}
}