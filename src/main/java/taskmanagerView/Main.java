package taskmanagerView;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {


	public void start(Stage primaryStage) throws Exception {

		Parent parent = FXMLLoader.load(getClass().getResource("StartWindow.fxml"));
		Scene scene = new Scene(parent);
		primaryStage.setScene(scene);
		primaryStage.show();

		primaryStage.setTitle("Task Mangement");

	}

	public static void main(String[] args) {
		launch(args);
	}
}
