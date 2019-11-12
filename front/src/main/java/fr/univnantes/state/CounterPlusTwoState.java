package fr.univnantes.state;

import fr.univnantes.cards.*;

class CounterPlusTwoState extends State {
	@Override
	void counterPlusTwo(Game game, ACard card) {
		game.setState(new WaitingState());
	}
}