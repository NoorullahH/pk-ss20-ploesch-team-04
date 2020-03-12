package Frontend;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	
	private Stage primaryStage;
	
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		startWindow();
	}
	
	private void startWindow() {
	
		
		try {
			FXMLLoader loader = new FXMLLoader(Main.class.getResource("startWindow.fxml"));
			AnchorPane pane = loader.load();
			StartWindowController startWindowController = loader.getController();
			
			
			Scene scene= new Scene(pane);
			primaryStage.setScene(scene);
			primaryStage.show();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		primaryStage.setTitle("Task Mangement");
		
	}

	public static void main(String[] args) {
		launch(args);
	}
}
