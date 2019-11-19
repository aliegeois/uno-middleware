package fr.univnantes;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import fr.univnantes.cards.ACard;
import fr.univnantes.cards.Color;
import fr.univnantes.state.Game;
import fr.univnantes.state.StateException;

public class Client extends UnicastRemoteObject implements ILocalClient, IRemoteClient {
	private static final long serialVersionUID = 2501464824644144715L;

	public final String name;
	public final IUserInterface ui;
	final Game game;
	final IServer server;
	// private Collection<ICard> cards;

	private boolean palying = false;

	public Client(String name, IUserInterface ui) throws RemoteException {
		super();

		this.name = name;
		this.ui = ui;

		IServer s = null;

		try {
			s = (IServer)Naming.lookup("rmi://localhost:1099/Uno");
			s.join((IRemoteClient)this);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		server = s;
		this.game = new Game(this, server);
	}

	@Override
	public void startGame(int nbPlayers, List<ACard> initialCards, ACard pileCard) throws RemoteException {
		try {
			game.startGame(nbPlayers, initialCards, pileCard);
			ui.startGame(initialCards);
		} catch(StateException e) {}
	}

	@Override
	public void yourTurn() throws RemoteException {
		try {
			game.yourTurn();
		} catch(StateException e) {}
		ui.yourTurn();
	}

	@Override
	public void draw(List<ACard> cards) throws RemoteException {
		try {
			game.draw(cards);
		} catch(StateException e) {}
		ui.draw(cards);
	}

	@Override
	public void aboutToDrawFourCards() throws RemoteException {
		try {
			game.aboutToDrawFourCards();
		} catch(StateException e) {}
		ui.aboutToDrawFourCards();
	}

	@Override
	public void winContest() throws RemoteException {
		try {
			game.winContest();
		} catch(StateException e) {}
		ui.winContest();
	}

	@Override
	public void loseContest(List<ACard> cards) throws RemoteException {
		try {
			game.loseContest(cards);
		} catch(StateException e) {}
		ui.loseContest(cards);
	}

	@Override
	public void getContested() throws RemoteException {
		try {
			game.getContested();
		} catch(StateException e) {}
		ui.getContested();
	}

	@Override
	public void getSkipped() throws RemoteException {
		try {
			game.getSkipped();
		} catch(StateException e) {}
		ui.getSkipped();
	}

	@Override
	public void getPlusTwoed(int quantity) throws RemoteException {
		try {
			game.getPlusTwoed(quantity);
		} catch(StateException e) {}
		ui.getPlusTwoed();
	}

	@Override
	public void cardPlayedBySomeoneElse(IRemoteClient client, ACard card) throws RemoteException {
		try {
			game.cardPlayedBySomeoneElse(client, card);
		} catch(StateException e) {}
		ui.cardPlayedBySomeoneElse(client, card);
	}

	@Override
	public void setReady(boolean ready) {
		try {
			game.setReady(ready);
		} catch(StateException e) {}
	}

	@Override
	public void contest(IRemoteClient contestedClient) {
		try {
			game.contest(contestedClient);
		} catch(StateException e) {}
	}

	@Override
	public void doNotContest() {
		try {
			game.doNotContest();
		} catch(StateException e) {}
	}
	
	@Override
	public void playStandardCard(ACard card) {
		try {
			game.playStandardCard(card);
		} catch(StateException e) {}
	}

	@Override
	public void playPlusFourCard(ACard card, Color color) {
		try {
			game.playPlusFourCard(card, color);
		} catch(StateException e) {}
	}
	
	@Override
	public void playWildCard(ACard card, Color color) {
		try {
			game.playWildCard(card, color);
		} catch(StateException e) {}
	}

	@Override
	public String getName() throws RemoteException {
		return name;
	}

	@Override
	public void setCards(List<ACard> cards) throws RemoteException {
		// set cards
		
	}

	@Override
	public List<ACard> getCards() throws RemoteException {
		return game.getCards();
	}

	@Override
	public boolean isPlaying() throws RemoteException {
		return palying;
	}
}