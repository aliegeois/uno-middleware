package fr.univnantes;

import fr.univnantes.cards.ACard;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class GUI extends Application implements IGUI {
	private Stage stage;

	@Override
	public void start(Stage stage) throws Exception {
		GUI gui = this;

		this.stage = stage;

		TextField tfName = new TextField();

		Button bJoin = new Button("Join");
		bJoin.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					new Client(tfName.getText(), gui);
				} catch(Exception e) {}
			}
		});
		GridPane gp = new GridPane();
		gp.setPadding(new Insets(10, 10, 10, 10));
		gp.setVgap(5);
		gp.setHgap(5);
		gp.setAlignment(Pos.CENTER);

		gp.add(tfName, 0, 0);
		gp.add(bJoin, 0, 1);

		Scene accueil = new Scene(gp);

		stage.setTitle("Le prochain jeu du futur de la mort qui tue!");
		stage.setScene(accueil);
		stage.show();
	}

	@Override
	public void joinLobby() {
		GridPane gp = new GridPane();


		Scene lobby = new Scene(gp);

		stage.setScene(lobby);
	}

	@Override
	public void leaveLobby() {

	}

	@Override
	public void startGame(ACard[] initialCards) {

	}

	@Override
	public void yourTurn() {
		
	}

	@Override
	public void playStandardCard(ACard card) {
		
	}

	@Override
	public void playWildCard(ACard card) {
		
	}

	@Override
	public void draw(ACard[] cards) {
		
	}


	@Override
	public void aboutToDrawFourCards() {
		
	}

	@Override
	public void contest() {
		
	}

	@Override
	public void doNotContest() {
		
	}

	@Override
	public void winContest() {
		
	}

	@Override
	public void loseContest(ACard[] cards) {
		
	}

	@Override
	public void getContested() {
		
	}


	@Override
	public void getSkipped() {
		
	}

	@Override
	public void counterSkip(ACard card) {
		
	}

	@Override
	public void getPlusTwoed() {
		
	}

	@Override
	public void counterPlusTwo(ACard card) {
		
	}


	@Override
	public void cardPlayedBySomeoneElse(IClient client, ACard card) {
		
	}


	@Override
	public void replay() {
		
	}

	@Override
	public void quit() {
		
	}


	public static void main(String[] args) {
		launch(args);
	}
}