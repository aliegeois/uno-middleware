package fr.univnantes.state;

import java.rmi.RemoteException;
import java.util.List;

import fr.univnantes.IRemoteClient;
import fr.univnantes.cards.ACard;

class WillGetContestedState extends State {
	@Override
	void contest(Game game, IRemoteClient contestedClient) {
		try {
			List<ACard> cardsToDraw = game.server.contest(game.client, contestedClient);
			if(cardsToDraw.size() != 0)
				game.cards.addAll(cardsToDraw);
		} catch(RemoteException e) {}
		game.setState(new WaitingState());
	}

	@Override
	void doNotContest(Game game, IRemoteClient contestedClient) {
		try {
			game.cards.addAll(game.server.doNotContest(game.client, contestedClient));
		} catch(RemoteException e) {}
		game.setState(new WaitingState());
	}
}