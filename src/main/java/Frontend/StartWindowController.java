package Frontend;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StartWindowController {

	@FXML
	private void createNewTask(ActionEvent event) throws IOException {
	
    	Parent parent = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
    	Scene scene = new Scene (parent);
    	Stage windowStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    	windowStage.setScene(scene);
    	windowStage.show();
    }
	
	
	
	
	
	
	

}
