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

	private final String name;
	private final IGUI gui;
	private final Game game;
	private final IServer server;
	// private Collection<ICard> cards;

	private boolean palying = false;

	public Client(String name, IGUI gui) throws RemoteException {
		super();

		this.name = name;
		this.gui = gui;

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
			gui.startGame(initialCards);
		} catch(StateException e) {
			
		}
		
	}

	@Override
	public void yourTurn() throws RemoteException {
		try {
			game.yourTurn();
		} catch(StateException e) {}
		gui.yourTurn();
	}

	@Override
	public void draw(List<ACard> cards) throws RemoteException {
		try {
			game.draw(cards);
		} catch(StateException e) {}
		gui.draw(cards);
	}

	@Override
	public void aboutToDrawFourCards() throws RemoteException {
		try {
			game.aboutToDrawFourCards();
		} catch(StateException e) {}
		gui.aboutToDrawFourCards();
	}

	@Override
	public void winContest() throws RemoteException {
		try {
			game.winContest();
		} catch(StateException e) {}
		gui.winContest();
	}

	@Override
	public void loseContest(List<ACard> cards) throws RemoteException {
		try {
			game.loseContest(cards);
		} catch(StateException e) {}
		gui.loseContest(cards);
	}

	@Override
	public void getContested() throws RemoteException {
		try {
			game.getContested();
		} catch(StateException e) {}
		gui.getContested();
	}

	@Override
	public void getSkipped() throws RemoteException {
		try {
			game.getSkipped();
		} catch(StateException e) {}
		gui.getSkipped();
	}

	@Override
	public void getPlusTwoed() throws RemoteException {
		try {
			game.getPlusTwoed();
		} catch(StateException e) {}
		gui.getPlusTwoed();
	}

	@Override
	public void cardPlayedBySomeoneElse(IRemoteClient client, ACard card) throws RemoteException {
		try {
			game.cardPlayedBySomeoneElse(client, card);
		} catch(StateException e) {}
		gui.cardPlayedBySomeoneElse(client, card);
	}

	@Override
	public void setReady(boolean ready) {
		try {
			game.setReady(ready);
		} catch(StateException e) {}
	}

	@Override
	public void contest() {
		try {
			game.contest();
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
		
	}
	
	@Override
	public void playWildCard(ACard card, Color color) {

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