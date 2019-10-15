package fr.univnantes;

import java.rmi.Naming;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collection;

import fr.univnantes.card.ACard;
import fr.univnantes.state.StateContext;
import javafx.application.Application;

public class Client extends UnicastRemoteObject implements IClient {
	private static final long serialVersionUID = 2501464824644144715L;

	private String name;
	private StateContext context = new StateContext();
	private Collection<ACard> cards = new ArrayList<>();

	public Client(String name) throws Exception {
		this.name = name;

		IServer server = (IServer)Naming.lookup("rmi://localhost:1099/Uno");
		server.join(this);
	}

	public void startGame(ACard[] initialCards) throws Exception {
		context.startGame();
	}

	public void yourTurn() throws Exception {
		context.yourTurn();
	}

	public void draw(ACard[] cards) throws Exception {
		context.draw(cards);
	}

	public void aboutToDrawFourCards() throws Exception {
		context.aboutToDrawFourCards();
	}

	public void winContest() throws Exception {
		context.winContest();
	}

	public void loseContest(ACard[] cards) throws Exception {
		context.loseContest(cards);
	}

	public void getContested() throws Exception {
		context.getContested();
	}

	public void getSkipped() throws Exception {
		context.getSkipped();
	}

	public void getPlusTwoed() throws Exception {
		context.getPlusTwoed();
	}

	public void cardPlayedBySomeoneElse(ACard card) throws Exception {
		context.cardPlayedBySomeoneElse(card);
	}

	public static void main(String[] args) {
		try {
			new Client(args[0]);
			new HelloFX();
			Application.launch();
		} catch(Exception e) {
			System.err.println("Exception: " + e.toString());
			e.printStackTrace();
		}
	}
}