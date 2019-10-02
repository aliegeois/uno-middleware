package fr.univnantes.state;

import fr.univnantes.card.*;

public class StateContext {
	private State state;



	public StateContext() {
		state = new InitialState();
	}

	void setState(State newState) {
		state = newState;
	}


	void joinLobby() throws StateException {
		state.joinLobby(this);
	}

	void leaveLobby() throws StateException {
		state.leaveLobby(this);
	}

	void startGame() throws StateException {
		state.startGame(this);
	}

	void yourTurn() throws StateException {
		state.yourTurn(this);
	}

	void playStandardCard(ACard card) throws StateException {
		state.playStandardCard(this, card);
	}

	void playWildCard(ACard card, Color color) throws StateException {
		state.playWildCard(this, card, color);
	}

	void draw(ACard[] card) throws StateException {
		state.draw(this, card);
	}
	

	void aboutToDrawFourCards() throws StateException {
		state.aboutToDrawFourCards(this);
	}

	void contest() throws StateException {
		state.contest(this);
	}

	void doNotContest() throws StateException {
		state.doNotContest(this);
	}

	void winContest() throws StateException {
		state.winContest(this);
	}

	void loseContest(ACard[] cards) throws StateException {
		state.loseContest(this, cards);
	}

	void getContested() throws StateException {
		state.getContested(this);
	}


	void getSkipped() throws StateException {
		state.getSkipped(this);
	}
	void counterSkip(ACard card) throws StateException {
		state.counterSkip(this, card);
	}

	void getPlusTwoed() throws StateException {
		state.getPlusTwoed(this);
	}

	void counterPlusTwo(ACard card) throws StateException {
		state.counterPlusTwo(this, card);
	}


	void cardPlayedBySomeoneElse(ACard card) throws StateException {
		state.cardPlayedBySomeoneElse(this, card);
	}

	void replay() throws StateException {
		state.replay(this);
	}
	void quit() throws StateException {
		state.quit(this);
	}
}