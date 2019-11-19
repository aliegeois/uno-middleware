package fr.univnantes.state;

import java.rmi.RemoteException;
import java.util.List;

import fr.univnantes.cards.ACard;

class LobbyState extends State {
	@Override
	void leaveLobby(Game game) {
		game.setState(new InitialState());
	}

	@Override
	void setReady(Game game, boolean ready) {
		try {
			game.server.setReady(game.client, ready);
		} catch(RemoteException e) {}
	}

	@Override
	void startGame(Game game, int nbPlayers, List<ACard> initialCards, ACard pileCard) {
		System.out.println("LobbyState.startGame");
		game.nbPlayers = nbPlayers;
		game.cards.addAll(initialCards);
		
		game.setState(new WaitingState());
		System.out.println("end start");
	}
}