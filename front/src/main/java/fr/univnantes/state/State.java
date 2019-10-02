package fr.univnantes.state;

import fr.univnantes.card.*;

class State {
	void joinLobby(StateContext context) throws StateException {throw new StateException();}
	void leaveLobby(StateContext context) throws StateException {throw new StateException();}
	void startGame(StateContext context) throws StateException {throw new StateException();}

	void yourTurn(StateContext context) throws StateException {throw new StateException();}
	void playStandardCard(StateContext context, ACard card) throws StateException {throw new StateException();} // numbers, skip, reverse and +2
	void playWildCard(StateContext context, ACard card, Color color) throws StateException {throw new StateException();} // Wild and wild+4
	void draw(StateContext context, ACard[] card) throws StateException {throw new StateException();} // Stays in the same state

	void aboutToDrawFourCards(StateContext context) throws StateException {throw new StateException();} // You're about to draw 4 cards and you now have the choice to contest
	void contest(StateContext context) throws StateException {throw new StateException();} // You think that the +4 was wrongfully played so you contest the move
	void doNotContest(StateContext context) throws StateException {throw new StateException();} // The +4 may have been right so you do nothing
	void winContest(StateContext context) throws StateException {throw new StateException();} // When the mofo did had a card of the right color so he draws 4 cards
	void loseContest(StateContext context, ACard[] cards) throws StateException {throw new StateException();} // Cards to draw after loosing the contest (6)
	void getContested(StateContext context) throws StateException {throw new StateException();} // When you wrongfully put a +4 and the next player contested it so you draw 4 cards

	void getSkipped(StateContext context) throws StateException {throw new StateException();}
	void counterSkip(StateContext context, ACard card) throws StateException {throw new StateException();}
	void getPlusTwoed(StateContext context) throws StateException {throw new StateException();}
	void counterPlusTwo(StateContext context, ACard card) throws StateException {throw new StateException();}

	void cardPlayedBySomeoneElse(StateContext context, ACard card) throws StateException {throw new StateException();}

	void replay(StateContext context) throws StateException {throw new StateException();}
	void quit(StateContext context) throws StateException { throw new StateException();}
}