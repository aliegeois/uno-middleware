package fr.univnantes.state;

import java.util.ArrayList;
import java.util.Collection;

import fr.univnantes.IClient;
import fr.univnantes.IServer;
import fr.univnantes.cards.*;

public class Game {
	final IClient client;
	final IServer server;

	State state;
	Collection<ACard> cards = new ArrayList<>(); // Liste de nos cartes
	Integer[] opponentsCards; // Nombre de cartes de chaque autre joueur
	int nbPlayers;
	ACard pileCard; // La carte en haut de la pile
	int initialNumberOfCards;

	public Game(IClient client, IServer server) {
		this.client = client;
		this.server = server;

		state = new InitialState();
	}

	public Collection<ACard> getCards() {
		return cards;
	}

	void addCards(Collection<ACard> cards) {
		this.cards.addAll(cards);
	}


	void setState(State newState) {
		state = newState;
	}

	public void joinLobby() throws StateException {
		state.joinLobby(this);
	}

	public void leaveLobby() throws StateException {
		state.leaveLobby(this);
	}

	public void startGame(int nbPlayers, ACard[] initialCards, ACard pileCard) throws StateException {
		state.startGame(this, nbPlayers, initialCards, pileCard);
	}

	public void yourTurn() throws StateException {
		state.yourTurn(this);
	}

	public void playStandardCard(ACard card) throws StateException {
		state.playStandardCard(this, card);
	}

	public void playWildCard(ACard card, Color color) throws StateException {
		state.playWildCard(this, card, color);
	}

	public void draw(ACard[] cards) throws StateException {
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

	public void loseContest(ACard[] cards) throws StateException {
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

	public void getPlusTwoed() throws StateException {
		state.getPlusTwoed(this);
	}

	public void counterPlusTwo(ACard card) throws StateException {
		state.counterPlusTwo(this, card);
	}


	public void cardPlayedBySomeoneElse(ACard card) throws StateException {
		state.cardPlayedBySomeoneElse(this, card);
	}

	public void replay() throws StateException {
		state.replay(this);
	}

	public void quit() throws StateException {
		state.quit(this);
	}
}