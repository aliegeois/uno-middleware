package fr.univnantes.state;

import java.rmi.RemoteException;

class WantToContestState implements State {
	@Override
	public void contest(Game game) {
		game.setState(new WaitingForContestResultState(true));
		try {
			game.server.contest(game.client.name);
		} catch(RemoteException e) {
			e.printStackTrace();
		}
		/*try {
			List<ACard> cardsToDraw = game.server.contest(game.client.name);
			if(cardsToDraw.size() > 0) {
				game.setState(new WaitingState());
				game.cards.addAll(cardsToDraw);
				game.client.ui.draw(cardsToDraw, false);
			} else {
				game.setState(new PlayingState());
			}
		} catch(RemoteException e) {}*/
	}

	@Override
	public void doNotContest(Game game) {
		game.setState(new WaitingForContestResultState(false));
		try {
			game.server.doNotContest(game.client.name);
		} catch(RemoteException e) {
			e.printStackTrace();
		}
		/*try {
			List<ACard> cardsToDraw = game.server.doNotContest(game.client.name);
			game.cards.addAll(cardsToDraw);
			game.client.ui.draw(cardsToDraw, false);
		} catch(RemoteException e) {}*/
	}
}