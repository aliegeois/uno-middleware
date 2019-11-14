package fr.univnantes.state;

import java.rmi.RemoteException;

import fr.univnantes.IRemoteClient;

class ContestState extends State {
	@Override
	void contest(Game game, IRemoteClient contestedClient) {
		try { game.server.contest(game.client, contestedClient); } catch(RemoteException e) {}
		game.setState(new WaitingState());
	}

	@Override
	void doNotContest(Game game) {
		try { game.server.doNotContest(game.client); } catch(RemoteException e) {}
		game.setState(new WaitingState());
	}
}