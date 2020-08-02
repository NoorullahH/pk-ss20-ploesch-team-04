package taskmanager.view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import contributor.Contributor;
import contributor.Contributormanager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *  Class which controls the contributor page of the application
 * @author Mara
 */
public class ContributorController implements Initializable{

	private Contributormanager contributors;
	
	@FXML
	private TextField contributorNameField;
	@FXML
	private Button addContributorButton;
	@FXML
	private ListView<String> contributorList = new ListView<>();
	@FXML
	private Button backButton;
	@FXML
	private Button deleteContributorButton;
	
	/**
	 * this method initializes the categories
	 * @param location
	 * @param resources
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		contributors = Contributormanager.getInstance();
		contributorList.setItems(getContributorList());
		contributorList.setStyle("-fx-font-size: 15 ;");
	}
	
	/**
	 * this method will return an observableList of the contributors
	 * @return ObservableList contributors
	 */
	public ObservableList<String> getContributorList() {
		ObservableList<String> conList = FXCollections.observableArrayList();
		ObservableList<Contributor> dummy = contributors.getContributors();
		for(Contributor c:dummy) {
			conList.add(c.getPerson());
		}
		return conList;
	}
	
	/**
	 * this method adds a new contributer to the contributors
	 * @param event
	 */
	@FXML
	public void addContributor(ActionEvent event) {
		contributors.addContributor(contributorNameField.getText());
		contributorList.setItems(getContributorList());
		contributorNameField.setText("");
	}
	
	/**
	 * this method deletes a contributor for the contributors
	 * @param event
	 */
	@FXML 
	public void deleteContributor(ActionEvent event) {
		contributors.removeContributor(contributorList.getSelectionModel().getSelectedItem());
		contributorList.setItems(getContributorList());
	}
	
	/**
	 * method to load MainWindow
	 * @param event
	 * @throws IOException
	 */
	@FXML
	public void backtoMain(Event event) throws IOException {
		Parent parent = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
		Scene scene = new Scene(parent);
		Stage windowStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		windowStage.setScene(scene);
		windowStage.show();
	}
}
