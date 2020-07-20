package taskmanager.view;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.dropbox.core.DbxException;

import javafx.stage.FileChooser;
import java.util.Comparator;

import category.Category;
import contributor.Contributor;
import dropbox.DropboxApi;
import javafx.beans.property.BooleanProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import task.Task;
import task.Subtask;
import task.Taskmanager;
import weekday.Weekday;

public class MainWindowController implements Initializable {
	
	protected Taskmanager taskList;

	@FXML
	private TableView<Task> taskView;
	@FXML
	private TableColumn<Task, String> descriptionColumn;
	@FXML
	private TableColumn<Task, String> detailedDescriptionColumn;
	@FXML
	private TableColumn<Task, String> contributorsColumn;
	@FXML
	private TableColumn<Task, LocalDate> dueDateColumn;
	@FXML
	private TableColumn<Task, String> categoriesColumn;
	@FXML
	private TableColumn<Task, List<String>> attachmentsColumn;
	@FXML
	private TableColumn<Task, Boolean> doneCheckBoxColumn;
	@FXML
	private TableColumn<Task, Boolean> deleteButton;
	@FXML
	private Button addTask;
	@FXML
	private Button editCategories;
	@FXML
	private Button editContributors;
	@FXML
	private Button editTask;
	@FXML
	private Button xmlExport;
	@FXML
	private Button updateTask;
	@FXML
	private Button importDropbox;
	@FXML
	private Button exportDropbox;
	//dino
	@FXML
	private Button filterTask;
	
	private static final String LINKTOTASK = "Task.fxml";
	
	private static final Logger LOGGER = Logger.getLogger(MainWindowController.class.getName());

	//this method adds a new Task to the List
	public void setData(Task t) {
		taskList.addTask(t);
	}
	
	//this method initializes the main page
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//initialize taskList
		taskList = Taskmanager.getInstance();
		
		//set up the columns in the table
		descriptionColumn.setCellValueFactory(new PropertyValueFactory<Task, String>("taskDescription"));
		detailedDescriptionColumn.setCellValueFactory(new PropertyValueFactory<Task, String>("detailedTaskDescription"));
		contributorsColumn.setCellValueFactory(new PropertyValueFactory<Task, String>("contributors"));
		categoriesColumn.setCellValueFactory(new PropertyValueFactory<Task, String>("categories"));
		dueDateColumn.setCellValueFactory(new PropertyValueFactory<Task, LocalDate>("dueDate"));
		attachmentsColumn.setCellValueFactory(new PropertyValueFactory<Task, List<String>>("attachments"));
		
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
		
		deleteButton.setCellFactory(para -> new ButtonCell());
		
		taskView.setEditable(true);
		
		taskView.setRowFactory(row -> new TableRow<Task>(){
			@Override
		    public void updateItem(Task item, boolean empty){
				if (item == null || empty) {
					setStyle("");
				} else {
					if (!item.isDone()) {
						setStyle("");
					}else if(item.isDone()) {
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
	
	private class ButtonCell extends TableCell<Task, Boolean>{
		private final Button delete = new Button("delete");
		
		ButtonCell(){
			
			delete.setOnAction(e -> {taskList.removeTask(ButtonCell.this.getTableView().getItems().get(ButtonCell.this.getIndex()));taskView.setItems(taskList.getTasks());});

		}
		
		@Override
		protected void updateItem(Boolean t, boolean empty) {
			super.updateItem(t, empty);
            if(!empty){
                setGraphic(delete);
            }else {
            	setGraphic(null);
            }
		}
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
		Parent parent = FXMLLoader.load(getClass().getResource(LINKTOTASK));
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
		FXMLLoader loader = new FXMLLoader(getClass().getResource(LINKTOTASK));
		Parent root = loader.load();
		TaskController controller = loader.<TaskController>getController();
		controller.initializeTask(taskView.getSelectionModel().getSelectedItem());
		
		FXMLLoader.load(getClass().getResource(LINKTOTASK));
		Scene scene = new Scene(root);//parent
		Stage windowStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		windowStage.setScene(scene);
		windowStage.show();
	}
	
	
	public void deleteCategoryFromTask(String category) {
		taskList.removeCategoryFromTasks(category);
	}
	
	@FXML
	private void handleTasks(ActionEvent event) {
		int size = Taskmanager.getSize();
		System.out.println("Recurrent Tasks handel: "+size);
		for(Task t:taskList.getTasks()) {
			if(t.isRecurrent()) {
				taskList.handleRecurrentTasks(t);
				System.out.println(t.getTaskNumber()+" is recurrent");
			}
		}
		taskView.setItems(taskList.getTasks());
	}
	
	public void editData(int taskNumber, String taskDes, String detailedTaskDes, LocalDate dueDate, ObservableList<Contributor> contributors, ObservableList<Category> categories, ObservableList<Subtask> subtasks, ObservableList<String> attachments, boolean recurrent, boolean monthly, boolean weekly,  Weekday weekday, int monthday, int numberOfRepetitions, LocalDate repetitionDate) {
		ObservableList<Task> tasks = taskList.getTasks();
		for(Task t:tasks) {
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
	private void saveToXML(ActionEvent event){
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
	
	
	@FXML
	private void saveToCsv(ActionEvent event){
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(
		        new FileChooser.ExtensionFilter("Comma-Separated Values (CSV)", "*.csv"));
		
		Node source = (Node) event.getSource();
    	Stage window = (Stage) source.getScene().getWindow();

		File file = fileChooser.showSaveDialog(window);

		if (file != null) {
			
			file = new File(file.getPath());
			
				try {
					taskList.saveToCsv(file);
				} catch (IOException e) {
					LOGGER.log(Level.SEVERE, "Exception occured (Save To CSV)", e);
				}
			
		}
	}
	
	@FXML
	private void filterTasks(ActionEvent event) throws IOException {
		Parent parent = FXMLLoader.load(getClass().getClassLoader().getResource("filter/view/FilterWindow.fxml"));
		Scene scene = new Scene(parent);
		Stage windowStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		windowStage.setScene(scene);
		windowStage.show();
	}
	
	@FXML
	private void dropboxUpload (ActionEvent event) throws IOException, DbxException {
		DropboxApi dropboxuploader= new DropboxApi();
		dropboxuploader.uploadFile("tasks.xml");
		FXMLLoader loader = new FXMLLoader(getClass().getResource("InfoWindow.fxml"));
		Parent root = loader.load();
		InfoWindowController controller = loader.<InfoWindowController>getController();
		controller.setInfoText("file uploaded to dropbox");
		
		Stage newstage = new Stage();
		newstage.setTitle("Info");
		newstage.setScene(new Scene(root));
		newstage.showAndWait();
	}
	
	@FXML
	private void importDropboxWriter(ActionEvent event) throws IOException, DbxException {

		DropboxApi dropboxdownloader= new DropboxApi();
		dropboxdownloader.downloadFile("tasks.xml");
		FXMLLoader loader = new FXMLLoader(getClass().getResource("InfoWindow.fxml"));
		Parent root = loader.load();
		InfoWindowController controller = loader.<InfoWindowController>getController();
		controller.setInfoText("file downloaded from dropbox");
		
		Stage newstage = new Stage();
		newstage.setTitle("Info");
		newstage.setScene(new Scene(root));
		newstage.showAndWait();
	}

}