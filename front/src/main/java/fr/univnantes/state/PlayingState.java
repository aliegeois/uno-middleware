package fr.univnantes.state;

import java.rmi.RemoteException;

import fr.univnantes.cards.ACard;

class PlayingState implements State {
	@Override 
	public void playStandardCard(Game game, ACard card) {
		game.cards.remove(card);
		game.setState(new WaitingState());
		try {
			game.server.playCard(game.client.name, card);
		} catch(RemoteException e) {}
	}

	@Override
	public void playPlusFourCard(Game game, ACard card) {
		game.cards.remove(card);
		game.setState(new WillGetContestedState());
		try {
			game.server.playCard(game.client.name, card);
		} catch(RemoteException e) {}
	}

	@Override
	public void playWildCard(Game game, ACard card) {
		game.cards.remove(card);
		game.setState(new WaitingState());
		try {
			game.server.playCard(game.client.name, card);
		} catch(RemoteException e) {}
	}

	/*@Override
	void draw(Game game, List<ACard> cards) {
		game.cards.addAll(cards);
		// Ne pas changer d'état
	}*/
}