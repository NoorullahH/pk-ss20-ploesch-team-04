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
		
    	//Node source = (Node) event.getSource();
    	//Stage window = (Stage) source.getScene().getWindow();
    	
    	Parent parent = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
    	Scene scene = new Scene (parent);
    	Stage windowStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    	
    	//FXMLLoader loader = new FXMLLoader(getClass().getResource("mainWindow.fxml"));
		//Parent root = loader.load();
    	//Scene hs = new Scene(root);
    	
    	windowStage.setScene(scene);
    	windowStage.show();
    }
	
	
	
	
	
	
	

}
