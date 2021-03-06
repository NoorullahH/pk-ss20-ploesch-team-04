package taskmanager.view;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Class which controls the start page of the application
 * manages the login of the user and the loading of the tasks
 * @author Mara
 */
public class StartWindowController {
	
	private boolean fileChosen;
	private static final String LINKTOMAIN = "MainWindow.fxml";
	
	/**
	 * loads the main page and initializes the tasks
	 * if no file is selected, the default file is selected
	 * @param event
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	@FXML
	private void logIn(ActionEvent event) throws IOException, ParserConfigurationException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(LINKTOMAIN));
		loader.load();
		MainWindowController controller = loader.<MainWindowController>getController();
		
		if(!fileChosen) {
			String xmlFile = "./tasks.xml";
			File file = new File(xmlFile);
			controller.taskList.readXML(file);
		}
	
    	Parent parent = FXMLLoader.load(getClass().getResource(LINKTOMAIN));
    	Scene scene = new Scene (parent);
    	Stage windowStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    	windowStage.setX(300);
    	windowStage.setY(20);
    	windowStage.setScene(scene);
    	windowStage.show();
    }
	
	/**
	 * this method reads a XML document
	 * @param event
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	@FXML
	private void importXML(ActionEvent event) throws IOException, ParserConfigurationException {
		Node source = (Node) event.getSource();
    	Stage window = (Stage) source.getScene().getWindow();
		
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Rescource File");
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
		fileChooser.getExtensionFilters().add(extFilter);
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource(LINKTOMAIN));
		loader.load();
		MainWindowController controller = loader.<MainWindowController>getController();
		
		File file = fileChooser.showSaveDialog(window);
		
		if (file != null) {
            controller.taskList.readXML(file);
            fileChosen = true;
        }
	}	
}
