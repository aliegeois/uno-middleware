package fr.univnantes.state;

import java.rmi.RemoteException;

import fr.univnantes.cards.*;

class CounterSkipState extends State {
	@Override
	void counterSkip(Game game, ACard card) {
		try {
			game.server.counterSkip(game.client, card);
		} catch(RemoteException e) {}
		game.setState(new WaitingState());
	}
}