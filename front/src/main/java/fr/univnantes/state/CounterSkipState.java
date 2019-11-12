package fr.univnantes.state;

import fr.univnantes.cards.*;

class CounterSkipState extends State {
	@Override
	void counterSkip(Game game, ACard card) {
		game.setState(new WaitingState());
	}
}