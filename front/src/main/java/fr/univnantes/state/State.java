package fr.univnantes.state;

import fr.univnantes.cards.*;

class State {
	void joinLobby(Game game) throws StateException {throw new StateException();}
	void leaveLobby(Game game) throws StateException {throw new StateException();}
	void startGame(Game game, int nbPlayers, ACard[] initialCards, ACard pileCard) throws StateException {throw new StateException();}

	void yourTurn(Game game) throws StateException {throw new StateException();}
	void playStandardCard(Game game, ACard card) throws StateException {throw new StateException();} // numbers, skip, reverse and +2
	void playWildCard(Game game, ACard card, Color color) throws StateException {throw new StateException();} // Wild and wild+4
	void draw(Game game, ACard[] cards) throws StateException {throw new StateException();} // Stays in the same state

	void aboutToDrawFourCards(Game game) throws StateException {throw new StateException();} // You're about to draw 4 cards and you now have the choice to contest
	void contest(Game game) throws StateException {throw new StateException();} // You think that the +4 was wrongfully played so you contest the move
	void doNotContest(Game game) throws StateException {throw new StateException();} // The +4 may have been right so you do nothing
	void winContest(Game game) throws StateException {throw new StateException();} // When the mofo did had a card of the right color so he draws 4 cards
	void loseContest(Game game, ACard[] cards) throws StateException {throw new StateException();} // Cards to draw after loosing the contest (6)
	void getContested(Game game) throws StateException {throw new StateException();} // When you wrongfully put a +4 and the next player contested it so you draw 4 cards

	void getSkipped(Game game) throws StateException {throw new StateException();}
	void counterSkip(Game game, ACard card) throws StateException {throw new StateException();}
	void getPlusTwoed(Game game) throws StateException {throw new StateException();}
	void counterPlusTwo(Game game, ACard card) throws StateException {throw new StateException();}

	void cardPlayedBySomeoneElse(Game game, ACard card) throws StateException {throw new StateException();}

	void replay(Game game) throws StateException {throw new StateException();}
	void quit(Game game) throws StateException { throw new StateException();}
}