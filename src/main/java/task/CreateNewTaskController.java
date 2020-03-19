package task;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class CreateNewTaskController{


	@FXML
	private TableView<Task> tableView;
	@FXML
	private TableColumn<Task, String> colDes;
	@FXML
	private TableColumn<Task, String> colDetdes;
	@FXML
	private TableColumn<Task, String> colCont;
	@FXML
	private TableColumn<Task, String> colCate;
	@FXML
	private TableColumn<Task, String> colAtt;
	@FXML
	private TableColumn<Task, LocalDate> colDate;
	@FXML
	private Button addTask;

	@FXML
	private TextField taskField;
	@FXML
	private TextField taskDetailField;
	@FXML
	private TextField contributorsField;
	@FXML
	private TextField categoryField;
	@FXML
	private TextField colAttField;
	@FXML
	private DatePicker dateField;

	/**
	 * This method adds new Task
	 * 
	 */

	@FXML
	public void buttonAdd(ActionEvent event) throws IOException {

		
		Task task2 = new Task();
		task2.setTaskDescription(taskField.getText());
		task2.setDetailsDescription(taskDetailField.getText());
		task2.setContributors(contributorsField.getText());
		task2.setCategory(categoryField.getText());
		// task2.setAttachment(colAttField.getText());
		task2.setDate(dateField.getValue());
		//taskField.clear();
		//taskDetailField.clear();
		//contributorsField.clear();
		//categoryField.clear();
		//colAttField.clear();
		//dateField).clear();

		
		 /*Task newTask = new Task(taskField.getText(), taskDetailField.getText(),
				 contributorsField.getText(), categoryField.getText(), dateField.getValue());
		 

		tableView.getItems().add(newTask);
		*/

		FXMLLoader loader = new FXMLLoader(getClass().getResource("MainWindow.fxml"));
		Parent root = loader.load();
		MainWindowController controller = loader.<MainWindowController>getController();
		controller.setData(taskField.getText(), taskDetailField.getText().toString(), contributorsField.getText().toString(), categoryField.getText().toString(),dateField.getValue());
		
		Parent parent = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
		Scene scene = new Scene(root);//parent
		Stage windowStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		windowStage.setScene(scene);
		windowStage.show();
	}
	
	
	
	
	@FXML
	public void back(ActionEvent event) throws IOException {

		Parent parent = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
		Scene scene = new Scene(parent);
		Stage windowStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		windowStage.setScene(scene);
		windowStage.show();

	}
	

}