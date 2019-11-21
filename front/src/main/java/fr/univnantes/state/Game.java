package fr.univnantes.state;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.univnantes.Client;
import fr.univnantes.IServer;
import fr.univnantes.cards.ACard;

public class Game {
	final Client client;
	final IServer server;

	State state;
	public final List<ACard> cards = new ArrayList<>(); // Liste de nos cartes
	ACard topCard;
	
	Map<String, Integer[]> opponentsCards = new HashMap<>();
	// Integer[] opponentsCards; // Nombre de cartes de chaque autre joueur
	int nbPlayers;
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

	public void startGame(List<String> players, List<ACard> initialCards, ACard topCard) throws StateException {
		state.startGame(this, players, initialCards, topCard);
	}

	public void yourTurn() throws StateException {
		state.yourTurn(this);
	}

	public void playStandardCard(ACard card) throws StateException {
		state.playStandardCard(this, card);
	}

	public void playPlusFourCard(ACard card) throws StateException {
		state.playPlusFourCard(this, card);
	}

	public void playWildCard(ACard card) throws StateException {
		state.playWildCard(this, card);
	}

	public void draw(List<ACard> cards) throws StateException {
		state.draw(this, cards);
	}
	

	public void aboutToDrawFourCards() throws StateException {
		state.aboutToDrawFourCards(this);
	}

	public void contest() throws StateException {
		state.contest(this);
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

	public void getPlusTwoed(int cardsStacked) throws StateException {
		state.getPlusTwoed(this, cardsStacked);
	}

	public void counterPlusTwo(ACard card) throws StateException {
		state.counterPlusTwo(this, card);
	}


	public void cardPlayedBySomeoneElse(String otherClient, ACard card) throws StateException {
		state.cardPlayedBySomeoneElse(this, otherClient, card);
	}

	public void replay() throws StateException {
		state.replay(this);
	}

	public void quit() throws StateException {
		state.quit(this);
	}


	public ACard getTopCard() {
		return topCard;
	}
}