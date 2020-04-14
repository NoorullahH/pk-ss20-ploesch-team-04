package taskmanagerView;

import java.io.File;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class StartWindowController {
	
	private boolean fileChosen = false;

	@FXML
	private void logIn(ActionEvent event) throws IOException {
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("MainWindow.fxml"));
		Parent root = loader.load();
		MainWindowController controller = loader.<MainWindowController>getController();
		
		//if no file was selected
		if(!fileChosen) {
			String xmlFile = "./tasks.xml";
			File file = new File(xmlFile);
			controller.taskList.readXML(file);
		}
	
    	Parent parent = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
    	Scene scene = new Scene (parent);
    	Stage windowStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    	windowStage.setScene(scene);
    	windowStage.show();
    }
	
	@FXML
	private void importXML(ActionEvent event) throws IOException {
		Node source = (Node) event.getSource();
    	Stage window = (Stage) source.getScene().getWindow();
		
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Rescource File");
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
		fileChooser.getExtensionFilters().add(extFilter);
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("MainWindow.fxml"));
		Parent root = loader.load();
		MainWindowController controller = loader.<MainWindowController>getController();
		
		File file = fileChooser.showSaveDialog(window);
		
		if (file != null) {
            controller.taskList.readXML(file);
            fileChosen = true;
        }
	}	
}
