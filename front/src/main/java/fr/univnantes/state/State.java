package fr.univnantes.state;

import java.util.List;

import fr.univnantes.cards.ACard;

interface State {
	default void setReady(Game game, boolean ready) throws StateException {
		throw new StateException(this, LobbyState.class, "setReady");
	}
	default void joinLobby(Game game, String name) throws StateException {
		throw new StateException();
	}
	default void leaveLobby(Game game) throws StateException {
		throw new StateException();
	}
	default void startGame(Game game, List<String> clients, List<ACard> initialCards, ACard pileCard) throws StateException {
		throw new StateException(this, LobbyState.class, "startGame");
	}

	default void yourTurn(Game game) throws StateException {
		throw new StateException(this, WaitingState.class, "yourTurn");
	}
	/*default void playStandardCard(Game game, ACard card) throws StateException { // numbers, skip, reverse and +2
		throw new StateException(this, PlayingState.class, "playStandardCard");
	}
	default void playPlusFourCard(Game game, ACard card) throws StateException {
		throw new StateException(this, PlayingState.class, "playPlusFourCard");
	}
	default void playWildCard(Game game, ACard card) throws StateException { // Wild and wild+4
		throw new StateException(this, PlayingState.class, "playWildCard");
	}*/
	default void playCard(Game game, ACard card) throws StateException { // Wild and wild+4
		throw new StateException(this, PlayingState.class, "playWildCard");
	}
	default void draw(Game game, List<ACard> cards) throws StateException { // Stays in the same state
		throw new StateException(this, WaitingState.class, "draw");
	}

	default void aboutToDrawFourCards(Game game) throws StateException { // You're about to draw 4 cards and you now have the choice to contest
		throw new StateException(this, WaitingState.class, "aboutToDrawFourCards");
	}
	default void contest(Game game) throws StateException { // You think that the +4 was wrongfully played so you contest the move
		throw new StateException(this, WantToContestState.class, "contest");
	}
	default void doNotContest(Game game) throws StateException { // The +4 may have been right so you do nothing
		throw new StateException(this, WantToContestState.class, "doNotContest");
	}
	default void winContest(Game game) throws StateException { // When the mofo did had a card of the right color so he draws 4 cards
		throw new StateException(this, WillGetContestedState.class, "winContest");
	}
	default void loseContest(Game game, List<ACard> cards) throws StateException { // Cards to draw after loosing the contest (6)
		throw new StateException(this, WillGetContestedState.class, "loseContest");
	}
	
	default void getContested(Game game) throws StateException { // When you wrongfully put a +4 and the next player contested it so you draw 4 cards
		throw new StateException(this, WaitingState.class, "getContested");
	}

	default void getSkipped(Game game) throws StateException {
		throw new StateException(this, WaitingState.class, "getSkipped");
	}
	default void counterSkip(Game game, ACard card) throws StateException {
		throw new StateException(this, CounterSkipState.class, "counterSkip");
	}
	default void getPlusTwoed(Game game, int cardsStacked) throws StateException {
		throw new StateException(this, WaitingState.class, "getPlusTwoed");
	}
	default void counterPlusTwo(Game game, ACard card) throws StateException {
		throw new StateException(this, CounterPlusTwoState.class, "counterPlusTwo");
	}

	default void cardPlayedBySomeoneElse(Game game, String otherPlayer, ACard card) throws StateException {
		throw new StateException(this, WaitingState.class, "cardPlayedBySomeoneElse");
	}

	default void replay(Game game) throws StateException {
		throw new StateException();
	}
	default void quit(Game game) throws StateException {
		throw new StateException();
	}
}