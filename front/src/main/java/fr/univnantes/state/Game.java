package fr.univnantes.state;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.univnantes.Client;
import fr.univnantes.IRemoteClient;
import fr.univnantes.IServer;
import fr.univnantes.cards.ACard;
import fr.univnantes.cards.Color;

public class Game {
	final Client client;
	final IServer server;

	State state;
	List<ACard> cards = new ArrayList<>(); // Liste de nos cartes
	
	Map<String, Integer[]> opponentsCards = new HashMap<>();
	// Integer[] opponentsCards; // Nombre de cartes de chaque autre joueur
	int nbPlayers;
	ACard pileCard; // La carte en haut de la pile
	int initialNumberOfCards;

	public Game(Client client, IServer server) {
		this.client = client;
		this.server = server;

		state = new LobbyState();
	}

	public List<ACard> getCards() {
		return cards;
	}


	void setState(State newState) {
		state = newState;
	}

	public void setReady(boolean ready) throws StateException {
		state.setReady(this, ready);
	}

	public void joinLobby(String name) throws StateException {
		state.joinLobby(this, name);
	}

	public void leaveLobby() throws StateException {
		state.leaveLobby(this);
	}

	public void startGame(int nbPlayers, List<ACard> initialCards, ACard pileCard) throws StateException {
		System.out.println("Game.startGame");
		state.startGame(this, nbPlayers, initialCards, pileCard);
	}

	public void yourTurn() throws StateException {
		state.yourTurn(this);
	}

	public void playStandardCard(ACard card) throws StateException {
		state.playStandardCard(this, card);
	}

	public void playPlusFourCard(ACard card, Color color) throws StateException {
		state.playPlusFourCard(this, card, color);
	}

	public void playWildCard(ACard card, Color color) throws StateException {
		state.playWildCard(this, card, color);
	}

	public void draw(List<ACard> cards) throws StateException {
		state.draw(this, cards);
	}
	

	public void aboutToDrawFourCards() throws StateException {
		state.aboutToDrawFourCards(this);
	}

	public void contest(IRemoteClient contestedClient) throws StateException {
		state.contest(this, contestedClient);
	}

	public void doNotContest() throws StateException {
		state.doNotContest(this);
	}

	public void winContest() throws StateException {
		state.winContest(this);
	}

	public void loseContest(List<ACard> cards) throws StateException {
		state.loseContest(this, cards);
	}

	public void getContested() throws StateException {
		state.getContested(this);
	}


	public void getSkipped() throws StateException {
		state.getSkipped(this);
	}

	public void counterSkip(ACard card) throws StateException {
		state.counterSkip(this, card);
	}

	public void getPlusTwoed(int quantity) throws StateException {
		state.getPlusTwoed(this, quantity);
	}

	public void counterPlusTwo(ACard card, int quantity) throws StateException {
		state.counterPlusTwo(this, card, quantity);
	}


	public void cardPlayedBySomeoneElse(IRemoteClient client, ACard card) throws StateException {
		state.cardPlayedBySomeoneElse(this, client, card);
	}

	public void replay() throws StateException {
		state.replay(this);
	}

	public void quit() throws StateException {
		state.quit(this);
	}
}