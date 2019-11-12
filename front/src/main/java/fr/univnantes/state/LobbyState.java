package fr.univnantes.state;

import java.rmi.RemoteException;
import java.util.Collections;

import fr.univnantes.cards.ACard;

class LobbyState extends State {
	@Override
	void leaveLobby(Game game) {
		game.setState(new InitialState());
	}

	void setReady(Game game, boolean ready) {
		try { game.server.setReady(game.client, ready); } catch(RemoteException e) {}
	}

	@Override
	void startGame(Game game, int nbPlayers, ACard[] initialCards, ACard pileCard) {
		game.nbPlayers = nbPlayers;
		Collections.addAll(game.cards, initialCards);
		game.setState(new WaitingState());
	}
}