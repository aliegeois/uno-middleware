package fr.univnantes.state;

import java.rmi.RemoteException;

class ContestState extends State {
	@Override
	void contest(Game game) {
		try { game.server.contest(game.client, true); } catch(RemoteException e) {}
		game.setState(new WaitingState());
	}

	@Override
	void doNotContest(Game game) {
		game.setState(new WaitingState());
	}
}