package fr.univnantes;

import java.rmi.Naming;
import java.rmi.server.UnicastRemoteObject;

import fr.univnantes.cards.ACard;
import fr.univnantes.state.Game;

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
	public void startGame(int nbPlayers, ACard[] initialCards, ACard pileCard) throws Exception {
		game.startGame(nbPlayers, initialCards, pileCard);
		gui.startGame(initialCards);
	}

	@Override
	public void yourTurn() throws Exception {
		game.yourTurn();
		gui.yourTurn();
	}

	@Override
	public void draw(ACard[] cards) throws Exception {
		game.draw(cards);
		gui.draw(cards);
	}

	@Override
	public void aboutToDrawFourCards() throws Exception {
		game.aboutToDrawFourCards();
		gui.aboutToDrawFourCards();
	}

	@Override
	public void winContest() throws Exception {
		game.winContest();
		gui.winContest();
	}

	@Override
	public void loseContest(ACard[] cards) throws Exception {
		game.loseContest(cards);
		gui.loseContest(cards);
	}

	@Override
	public void getContested() throws Exception {
		game.getContested();
		gui.getContested();
	}

	@Override
	public void getSkipped() throws Exception {
		game.getSkipped();
		gui.getSkipped();
	}

	@Override
	public void getPlusTwoed() throws Exception {
		game.getPlusTwoed();
		gui.getPlusTwoed();
	}

	@Override
	public void cardPlayedBySomeoneElse(ACard card) throws Exception {
		game.cardPlayedBySomeoneElse(card);
		gui.cardPlayedBySomeoneElse(card);
	}

	public String getName() throws Exception {
		return name;
	}
}