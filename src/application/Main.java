package application;

/**
 * Main method that starts the GUI
 * @author Grant Gapinski
 * @author Bailey Middendorf
 * @version 5/10/18
 */

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			// Creates new View
			BorderPane root = new View();
			// Sets default window size
			Scene scene = new Scene(root,900,800);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	// Starts the GUI
	public static void main(String[] args) {
		launch(args);
	}
}
