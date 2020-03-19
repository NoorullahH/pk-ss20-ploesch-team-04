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

public class MainWindowController implements Initializable {

	ObservableList<Task> task = FXCollections.observableArrayList();

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
	/*
	 * @FXML public void buttonAdd() {
	 * 
	 * Task task2 = new Task(); task2.setTaskDescription(colDesField.getText());
	 * task2.setDetailsDescription(colDetdesField.getText());
	 * task2.setContributors(colContField.getText());
	 * task2.setCategory(colCateField.getText()); //
	 * task2.setAttachment(colAttField.getText());
	 * task2.setDate(colDateField.getValue()); colDesField.clear();
	 * colDetdesField.clear(); colContField.clear(); colCateField.clear();
	 * colAttField.clear(); ((List<Task>) colDateField).clear();
	 * 
	 * 
	 * /*Task newTask = new Task(colDes.getText(), colDetdes.getText(),
	 * colCont.getText(), colCate.getText(), colAtt.getText(), null);
	 * 
	 */
	/*
	 * tableView.getItems().add(task2);
	 * 
	 * }
	 */
	/*
	 * @FXML public void back(ActionEvent event) throws IOException {
	 * 
	 * Parent parent = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
	 * Scene scene = new Scene(parent); Stage windowStage = (Stage) ((Node)
	 * event.getSource()).getScene().getWindow(); windowStage.setScene(scene);
	 * windowStage.show();
	 * 
	 * }
	 * 
	 */

	ObservableList<Task> getTaskList() {
		// ObservableList<Task> task = FXCollections.observableArrayList();
		// task.add(new Task("Test", "Test", "Test", "Test", null));
		// task.add(new Task("Test", "Test", "Test", null, null));
		return task;

	}

	public void setData(String data, String x, String g, String m, LocalDate p) {
		task.add(new Task(data, x, g, m, p));

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		colDes.setCellValueFactory(new PropertyValueFactory<Task, String>("taskDescription"));
		colDetdes.setCellValueFactory(new PropertyValueFactory<Task, String>("detailsDescription"));
		colCont.setCellValueFactory(new PropertyValueFactory<Task, String>("contributors"));
		colCate.setCellValueFactory(new PropertyValueFactory<Task, String>("category"));
		// colAtt.setCellValueFactory(new PropertyValueFactory<Task,
		// String>("attachment"));
		colDate.setCellValueFactory(new PropertyValueFactory<Task, LocalDate>("date"));
		tableView.setItems(getTaskList());

	}

	@FXML
	private void addNewUser(ActionEvent event) throws IOException {
		Parent parent = FXMLLoader.load(getClass().getResource("CreateNewTask.fxml"));
		Scene scene = new Scene(parent);
		Stage windowStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		windowStage.setScene(scene);
		windowStage.show();

	}

}
