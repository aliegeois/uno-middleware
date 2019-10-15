package fr.univnantes.state;

import fr.univnantes.cards.*;

public class StateContext {
	private State state;

	public StateContext() {
		state = new InitialState();
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

	public void startGame() throws StateException {
		state.startGame(this);
	}

	public void yourTurn() throws StateException {
		state.yourTurn(this);
	}

	public void playStandardCard(ICard card) throws StateException {
		state.playStandardCard(this, card);
	}

	public void playWildCard(ICard card, Color color) throws StateException {
		state.playWildCard(this, card, color);
	}

	public void draw(ICard[] cards) throws StateException {
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

	public void loseContest(ICard[] cards) throws StateException {
		state.loseContest(this, cards);
	}

	public void getContested() throws StateException {
		state.getContested(this);
	}


	public void getSkipped() throws StateException {
		state.getSkipped(this);
	}

	public void counterSkip(ICard card) throws StateException {
		state.counterSkip(this, card);
	}

	public void getPlusTwoed() throws StateException {
		state.getPlusTwoed(this);
	}

	public void counterPlusTwo(ICard card) throws StateException {
		state.counterPlusTwo(this, card);
	}


	public void cardPlayedBySomeoneElse(ICard card) throws StateException {
		state.cardPlayedBySomeoneElse(this, card);
	}

	public void replay() throws StateException {
		state.replay(this);
	}

	public void quit() throws StateException {
		state.quit(this);
	}
}