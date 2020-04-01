package taskmanagerView;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import category.Category;
import contributor.Contributor;
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
import task.Task;

public class MainWindowController implements Initializable {

	ObservableList<Task> taskList = FXCollections.observableArrayList();

	@FXML
	private TableView<Task> tableView;
	@FXML
	private TableColumn<Task, String> colDes;
	@FXML
	private TableColumn<Task, String> colDetdes;
	@FXML
	private TableColumn<Task, LinkedList<Contributor>> colCont;
	@FXML
	private TableColumn<Task, LocalDate> colDate;
	@FXML
	private TableColumn<Task, LinkedList<Category>> colCate;
	@FXML
	private TableColumn<Task, String> colAtt;
	@FXML
	private Button addTask;

	
	public void setData(Task t) {
		taskList.add(t);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//set up the columns in the table
		colDes.setCellValueFactory(new PropertyValueFactory<Task, String>("taskDescription"));
		colDetdes.setCellValueFactory(new PropertyValueFactory<Task, String>("detailedTaskDescription"));
		colCont.setCellValueFactory(new PropertyValueFactory<Task, LinkedList<Contributor>>("contributors"));
		colCate.setCellValueFactory(new PropertyValueFactory<Task, LinkedList<Category>>("categories"));
		colDate.setCellValueFactory(new PropertyValueFactory<Task, LocalDate>("dueDate"));
		
		//load Data
		tableView.setItems(getTaskList());
	}
	
	//This method will return an observableList of the Tasks
	public ObservableList<Task> getTaskList() {
		return taskList;
	}

	@FXML
	private void addNewTask(ActionEvent event) throws IOException {
		Parent parent = FXMLLoader.load(getClass().getResource("CreateNewTask2.fxml"));
		Scene scene = new Scene(parent);
		Stage windowStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		windowStage.setScene(scene);
		windowStage.show();

	}

}
