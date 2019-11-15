package fr.univnantes.state;

import java.rmi.RemoteException;

import fr.univnantes.cards.*;

class CounterPlusTwoState extends State {
	@Override
	void counterPlusTwo(Game game, ACard card, int quantity) {
		try { game.server.counterPlusTwo(game.client, card, quantity + 1); } catch(RemoteException e) {}
		game.setState(new WaitingState());
	}

	@Override
	void doNotCounterPlusTwo(Game game, int quantity) throws StateException {
		try {game.server.doNotCounterPlusTwo(game.client, quantity);} catch (RemoteException e) {}
		game.setState(new WaitingState());
	}
}