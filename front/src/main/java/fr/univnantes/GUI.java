package fr.univnantes;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class GUI extends Application {
	private Client client;

	@Override
	public void start(Stage stage) throws Exception {
		TextField tf = new TextField();

		Button b = new Button("Join");
		b.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					client = new Client(tf.getText());
				} catch(Exception e) {}
			}
		});
		GridPane gp = new GridPane();
		gp.setPadding(new Insets(10, 10, 10, 10));
		gp.setVgap(5);
		gp.setHgap(5);
		gp.setAlignment(Pos.CENTER);

		gp.add(tf, 0, 0);
		gp.add(b, 0, 1);

		Scene scene = new Scene(gp);

		stage.setTitle("Le prochain jeu du futur de la mort qui tue!");
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}