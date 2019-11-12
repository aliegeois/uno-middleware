package fr.univnantes.state;

import fr.univnantes.IClient;
import fr.univnantes.cards.*;

class WaitingState extends State {
	@Override
	void yourTurn(Game game) {
		game.setState(new PlayState());
	}

	@Override
	void draw(Game game, ACard[] cards) {
		// Rester dans le même état
	}

	@Override
	void aboutToDrawFourCards(Game game) {
		game.setState(new ContestState());
	}

	@Override
	void winContest(Game game) {
		game.setState(new WaitingState());
	}

	@Override
	void loseContest(Game game, ACard[] cards) {
		game.setState(new WaitingState());
	}

	@Override
	void getContested(Game game) {
		game.setState(new ContestState());
	}

	@Override
	void getSkipped(Game game) {
		game.setState(new CounterSkipState());
	}

	@Override
	void getPlusTwoed(Game game) {
		game.setState(new CounterPlusTwoState());
	}

	@Override
	void cardPlayedBySomeoneElse(Game game, IClient client, ACard card) {

	}
}