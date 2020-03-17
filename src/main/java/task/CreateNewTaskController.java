package task;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class CreateNewTaskController {

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
	private TextField colDesField;
	@FXML
	private TextField colDetdesField;
	@FXML
	private TextField colContField;
	@FXML
	private TextField colCateField;
	@FXML
	private TextField colAttField;
	@FXML
	private DatePicker colDateField;

	/**
	 * This method adds new Task
	 * 
	 */

	@FXML
	public void buttonAdd(ActionEvent event) throws IOException {

		Task newTask = new Task(colDesField.getText(), colDetdesField.getText(), colContField.getText(),
				colCateField.getText(), null, colDateField.getValue());

		tableView.getItems().add(newTask);

	}

	@FXML
	public void back(ActionEvent event) throws IOException {

		Parent parent = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
		Scene scene = new Scene(parent);
		Stage windowStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		windowStage.setScene(scene);
		windowStage.show();

	}

	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		colDes.setCellValueFactory(new PropertyValueFactory<Task, String>("taskDescription"));
		colDetdes.setCellValueFactory(new PropertyValueFactory<Task, String>("detailsDescription"));
		colCont.setCellValueFactory(new PropertyValueFactory<Task, String>("contributors"));
		colCate.setCellValueFactory(new PropertyValueFactory<Task, String>("category"));
		colAtt.setCellValueFactory(new PropertyValueFactory<Task, String>("attachment"));
		colDate.setCellValueFactory(new PropertyValueFactory<Task, LocalDate>("date"));

	}

}