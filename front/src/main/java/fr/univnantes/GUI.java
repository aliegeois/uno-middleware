package fr.univnantes;

import java.util.List;

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
				} catch(Exception e) {
					e.printStackTrace();
				}
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

	public void joinLobby() {
		GridPane gp = new GridPane();


		Scene lobby = new Scene(gp);

		stage.setScene(lobby);
	}

	public void leaveLobby() {

	}

	@Override
	public void startGame(List<ACard> initialCards) {

	}

	@Override
	public void yourTurn() {
		System.out.println("Début de votre tour");
	}

	public void playStandardCard(ACard card) {
		System.out.println("Vous jouez la carte: " + card);
	}

	public void playWildCard(ACard card) {
		
	}

	@Override
	public void draw(List<ACard> cards) {
		
	}


	@Override
	public void aboutToDrawFourCards() {
		
	}

	public void contest() {
		
	}

	public void doNotContest() {
		
	}

	@Override
	public void winContest() {
		
	}

	@Override
	public void loseContest(List<ACard> cards) {
		
	}

	@Override
	public void getContested() {
		
	}


	@Override
	public void getSkipped() {
		
	}

	public void counterSkip(ACard card) {
		
	}

	@Override
	public void getPlusTwoed() {
		
	}

	public void counterPlusTwo(ACard card) {
		
	}


	@Override
	public void cardPlayedBySomeoneElse(IRemoteClient client, ACard card) {
		
	}


	public void replay() {
		
	}

	public void quit() {
		
	}


	public static void main(String[] args) {
		launch(args);
	}
}