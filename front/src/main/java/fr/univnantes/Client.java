package fr.univnantes;

import java.rmi.Naming;
import java.rmi.server.UnicastRemoteObject;

import fr.univnantes.cards.ACard;
import fr.univnantes.cards.Color;
import fr.univnantes.state.Game;
import fr.univnantes.state.StateException;

public class Client extends UnicastRemoteObject implements IClient {
	private static final long serialVersionUID = 2501464824644144715L;

	private final String name;
	private final IGUI gui;
	private final Game game;
	private final IServer server;
	// private Collection<ICard> cards;

	public Client(String name, IGUI gui) throws Exception {
		this.name = name;
		this.gui = gui;

		server = (IServer)Naming.lookup("rmi://localhost:1099/Uno");
		server.join(this);

		this.game = new Game(this, server);
	}

	@Override
	public void startGame(int nbPlayers, ACard[] initialCards, ACard pileCard) throws StateException {
		game.startGame(nbPlayers, initialCards, pileCard);
		gui.startGame(initialCards);
	}

	@Override
	public void yourTurn() throws StateException {
		game.yourTurn();
		gui.yourTurn();
	}

	@Override
	public void draw(ACard[] cards) throws StateException {
		game.draw(cards);
		gui.draw(cards);
	}

	@Override
	public void aboutToDrawFourCards() throws StateException {
		game.aboutToDrawFourCards();
		gui.aboutToDrawFourCards();
	}

	@Override
	public void winContest() throws StateException {
		game.winContest();
		gui.winContest();
	}

	@Override
	public void loseContest(ACard[] cards) throws StateException {
		game.loseContest(cards);
		gui.loseContest(cards);
	}

	@Override
	public void getContested() throws StateException {
		game.getContested();
		gui.getContested();
	}

	@Override
	public void getSkipped() throws StateException {
		game.getSkipped();
		gui.getSkipped();
	}

	@Override
	public void getPlusTwoed() throws StateException {
		game.getPlusTwoed();
		gui.getPlusTwoed();
	}

	@Override
	public void cardPlayedBySomeoneElse(IClient client, ACard card) throws StateException {
		game.cardPlayedBySomeoneElse(client, card);
		gui.cardPlayedBySomeoneElse(client, card);
	}

	@Override
	public void setReady(boolean ready) throws StateException {
		game.setReady(ready);
	}

	@Override
	public void contest() throws StateException {
		game.contest();
	}

	@Override
	public void doNotContest() throws StateException {
		game.doNotContest();
	}
	
	@Override
	public void playStandardCard(ACard card) throws StateException {

	}
	
	@Override
	public void playWildCard(ACard card, Color color) throws StateException {

	}

	public String getName() throws Exception {
		return name;
	}
}