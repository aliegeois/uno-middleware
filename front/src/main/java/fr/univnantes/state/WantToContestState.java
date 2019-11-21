package fr.univnantes.state;

import java.rmi.RemoteException;
import java.util.List;

import fr.univnantes.cards.ACard;

class WantToContestState implements State {
	@Override
	public void contest(Game game) {
		game.setState(new WaitingState());
		try {
			List<ACard> cardsToDraw = game.server.contest(game.client.name);
			if(cardsToDraw.size() > 0) {
				game.cards.addAll(cardsToDraw);
				game.client.ui.draw(cardsToDraw);
			}
		} catch(RemoteException e) {}
	}

	@Override
	public void doNotContest(Game game) {
		game.setState(new WaitingState());
		try {
			game.cards.addAll(game.server.doNotContest(game.client.name));
		} catch(RemoteException e) {}
	}
}