package fr.univnantes.client.state;

import java.rmi.RemoteException;

import fr.univnantes.card.ACard;

class CounterSkipState implements State {
	@Override
	public void counterSkip(Game game, ACard card) {
		game.cards.remove(card);
		game.topCard = card;
		game.setState(new WaitingState());
		try {
			game.server.counterSkip(game.client.name, card);
		} catch(RemoteException e) {}
	}

	// public void doNotCounterSkip(Game game) throws StateException {
	// 	try {
	// 		game.server.doNotCounterSkip(game.client.name);
	// 	} catch (RemoteException e) {}
	// 	game.setState(new WaitingState());
	// }
}