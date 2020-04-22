package taskmanagerView;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import org.xml.sax.*;
import org.w3c.dom.*;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.beans.XMLEncoder;
import java.beans.XMLDecoder;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

import java.util.Comparator;

import category.Category;
import category.Categorymanager;
import contributor.Contributor;
import javafx.beans.property.BooleanProperty;
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
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeTableColumn.CellDataFeatures;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import task.Task;
import task.Subtask;
import task.Taskmanager;
import weekday.Weekday;

public class MainWindowController implements Initializable {
	
	Taskmanager taskList;

	@FXML
	private TableView<Task> taskView;
	@FXML
	private TableColumn<Task, String> descriptionColumn;
	@FXML
	private TableColumn<Task, String> detailedDescriptionColumn;
	@FXML
	private TableColumn<Task, List<Contributor>> contributorsColumn;
	@FXML
	private TableColumn<Task, LocalDate> dueDateColumn;
	@FXML
	private TableColumn<Task, List<Category>> categoriesColumn;
	@FXML
	private TableColumn<Task, List<String>> attachmentsColumn;
	@FXML
	private TableColumn<Task, Boolean> doneCheckBoxColumn;
	@FXML
	private Button addTask;
	@FXML
	private Button editCategories;
	@FXML
	private Button editContributors;
	@FXML
	private Button editTask;
	@FXML
	private Button deleteTask;
	@FXML
	private Button xmlExport;
	@FXML
	private Button updateTask;

	//this method adds a new Task to the List
	public void setData(Task t) {
		taskList.addTask(t);
	//	recurrentTaskList.addRecurrentTask(t);
	}
	
	//this method initializes the main page
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//initialize taskList
		taskList = taskList.getInstance();
		
		//set up the columns in the table
		descriptionColumn.setCellValueFactory(new PropertyValueFactory<Task, String>("taskDescription"));
		detailedDescriptionColumn.setCellValueFactory(new PropertyValueFactory<Task, String>("detailedTaskDescription"));
		contributorsColumn.setCellValueFactory(new PropertyValueFactory<Task, List<Contributor>>("contributors"));
		categoriesColumn.setCellValueFactory(new PropertyValueFactory<Task, List<Category>>("categories"));
		dueDateColumn.setCellValueFactory(new PropertyValueFactory<Task, LocalDate>("dueDate"));
		attachmentsColumn.setCellValueFactory(new PropertyValueFactory<Task, List<String>>("attachments"));
		

		
		descriptionColumn.setStyle("-fx-font-size: 15 ;");
		detailedDescriptionColumn.setStyle("-fx-font-size: 15 ;");
		contributorsColumn.setStyle("-fx-font-size: 15 ;");
		categoriesColumn.setStyle("-fx-font-size: 15 ;");
		dueDateColumn.setStyle("-fx-font-size: 15 ;");
		attachmentsColumn.setStyle("-fx-font-size: 15 ;");
		
		doneCheckBoxColumn.setCellValueFactory(new PropertyValueFactory<Task, Boolean>("done"));
		doneCheckBoxColumn.setCellFactory(CheckBoxTableCell.forTableColumn(doneCheckBoxColumn));
		doneCheckBoxColumn.setEditable(true);
		
		doneCheckBoxColumn.setCellValueFactory(cellData -> {
			Task cellValue = cellData.getValue();
			BooleanProperty property = cellValue.getDone();
			boolean subtasksDone = true;
			for(Subtask t:cellValue.getSubtasks()) {
				if(!t.isDone()) {
					subtasksDone = false;
				}
			}
			if(subtasksDone) {
				property.addListener((observable, oldValue, newValue) -> cellValue.setDone(newValue));
			}else {
				cellValue.setDone(false);
				property = cellValue.getDone();
			}
			return property;	
		});
		
		taskView.setEditable(true);
		
		taskView.setRowFactory(row -> new TableRow<Task>(){
			@Override
		    public void updateItem(Task item, boolean empty){
				if (item == null || empty) {
					setStyle("");
				} else {
					if (item.isDone()) {
						setStyle("-fx-background-color: lightgreen;");
					}else if(item.getDueDate()!=null) {
						if((!item.isDone()) && item.getDueDate().isBefore(LocalDate.now())) {
							setStyle("-fx-background-color: red;");
						}
					}else {
						setStyle("");
					}
				}
			}
		});
        
		//load Data
		taskView.setItems(getTaskList());
	}
	
	//This method will return an observableList of the Tasks
	public ObservableList<Task> getTaskList() {
		
		// Sortieren nach der Highscoreliste
		Comparator<Task> dateComparator = new Comparator<Task>() {
	       	public int compare(Task orig, Task vgl) {
	       		if(orig.getDueDate()!=null && vgl.getDueDate()!=null) {
	       			if (orig.getDueDate().isAfter(vgl.getDueDate())) {
	       				return -1;
	       			}else if (orig.getDueDate().isBefore(vgl.getDueDate())) {
	       				return 1;
	       			}else {
	       				if(orig.getTaskNumber()>vgl.getTaskNumber()) {
	       					return -1;
	       				}else {
	       					return 1;
	       				}
	       			}
	       		}else {
	       			if(orig.getTaskNumber()>vgl.getTaskNumber()) {
	       				return -1;
	       			}else {
	       				return 1;
	       			}
	       		}
	       	}
		};		
		
		ObservableList<Task> tasks = taskList.getTasks();
		tasks.sort(dateComparator);
		return tasks;
	}

	@FXML
	private void addNewTask(ActionEvent event) throws IOException {
		Parent parent = FXMLLoader.load(getClass().getResource("CreateNewTask2.fxml"));
		Scene scene = new Scene(parent);
		Stage windowStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		windowStage.setScene(scene);
		windowStage.show();
	}
	
	@FXML
	private void editCategories(ActionEvent event) throws IOException {
		Parent parent = FXMLLoader.load(getClass().getResource("CategoryWindow.fxml"));
		Scene scene = new Scene(parent);
		Stage windowStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		windowStage.setScene(scene);
		windowStage.show();
	}
	
	@FXML
	private void editContributors(ActionEvent event) throws IOException {
		Parent parent = FXMLLoader.load(getClass().getResource("ContributorWindow.fxml"));
		Scene scene = new Scene(parent);
		Stage windowStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		windowStage.setScene(scene);
		windowStage.show();
	}
	
	@FXML
	private void editTask(ActionEvent event) throws IOException {		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("EditTaskWindow.fxml"));
		Parent root = loader.load();
		EditTaskController controller = loader.<EditTaskController>getController();
		controller.initializeTask(taskView.getSelectionModel().getSelectedItem());
		
		Parent parent = FXMLLoader.load(getClass().getResource("EditTaskWindow.fxml"));
		Scene scene = new Scene(root);//parent
		Stage windowStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		windowStage.setScene(scene);
		windowStage.show();
	}
	
	@FXML
	private void deleteTask(ActionEvent event) {
		taskList.removeTask(taskView.getSelectionModel().getSelectedItem());
	}
	
	public void deleteCategoryFromTask(String category) {
		taskList.removeCategoryFromTasks(category);
	}
	
	@FXML
	private void handleTasks(ActionEvent event) {
		int size = taskList.getSize();
		for(int i = 0;  i < size; i++) {
			Task t = taskList.getTasks().get(i);
			if(t.isRecurrent()) {
				taskList.handleRecurrentTasks(t);
			}
		}
	}
	
	public void editData(int taskNumber, String taskDes, String detailedTaskDes, LocalDate dueDate, ObservableList<Contributor> contributors, ObservableList<Category> categories, ObservableList<Subtask> subtasks, ObservableList<String> attachments, boolean recurrent, boolean monthly, boolean weekly,  Weekday weekday, int monthday, int numberOfRepetitions, LocalDate repetitionDate) {
		for(Task t:taskList.getTasks()) {
			if(t.getTaskNumber()==taskNumber) {
				t.setTaskDescription(taskDes);
				t.setDetailedTaskDescription(detailedTaskDes);
				t.setDueDate(dueDate);
				t.setContributors(contributors);
				t.setCategories(categories);
				t.setSubtasks(subtasks);
				t.setAttachments(attachments);
				t.setRecurrent(recurrent);
				t.setWeekly(weekly);
				t.setMonthly(monthly);
				t.setWeekday(weekday);
				t.setMonthday(monthday);
				t.setNumberOfRepetitions(numberOfRepetitions);
				t.setRepetitionDate(repetitionDate);
			}
		}
	}
	
	
	@FXML
	private void saveToXML(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
		fileChooser.getExtensionFilters().add(extFilter);
		
		Node source = (Node) event.getSource();
    	Stage window = (Stage) source.getScene().getWindow();

		File file = fileChooser.showSaveDialog(window);

		if (file != null) {
			// Make sure it has the correct extension
            if (!file.getPath().endsWith(".xml")) {
                file = new File(file.getPath() + ".xml");
            }
			taskList.saveToXML(file);
		}else {
			String xmlFile = "./tasks.xml";
			File fileDefault = new File(xmlFile);
			taskList.saveToXML(fileDefault);
		}
	}	

}