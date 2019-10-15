package fr.univnantes;

import java.rmi.Naming;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import fr.univnantes.cards.ICard;
import fr.univnantes.state.StateContext;
import javafx.application.Application;

public class Client extends UnicastRemoteObject implements IClient {
	private static final long serialVersionUID = 2501464824644144715L;

	private String name;
	private StateContext context = new StateContext();
	private Collection<ICard> cards;

	public Client(String name) throws Exception {
		this.name = name;

		IServer server = (IServer)Naming.lookup("rmi://localhost:1099/Uno");
		server.join(this);
	}

	@Override
	public void startGame(ICard[] initialCards) throws Exception {
		context.startGame();
		cards = Arrays.asList(initialCards);
	}

	@Override
	public void yourTurn() throws Exception {
		context.yourTurn();
	}

	@Override
	public void draw(ICard[] cards) throws Exception {
		context.draw(cards);
	}

	@Override
	public void aboutToDrawFourCards() throws Exception {
		context.aboutToDrawFourCards();
	}

	@Override
	public void winContest() throws Exception {
		context.winContest();
	}

	@Override
	public void loseContest(ICard[] cards) throws Exception {
		context.loseContest(cards);
	}

	@Override
	public void getContested() throws Exception {
		context.getContested();
	}

	@Override
	public void getSkipped() throws Exception {
		context.getSkipped();
	}

	@Override
	public void getPlusTwoed() throws Exception {
		context.getPlusTwoed();
	}

	@Override
	public void cardPlayedBySomeoneElse(ICard card) throws Exception {
		context.cardPlayedBySomeoneElse(card);
	}

	public String getName() throws Exception {
		return name;
	}

	/*public static void main(String[] args) {
		try {
			new Client(args[0]);
			new HelloFX();
			Application.launch();
		} catch(Exception e) {
			System.err.println("Exception: " + e.toString());
			e.printStackTrace();
		}
	}*/
}