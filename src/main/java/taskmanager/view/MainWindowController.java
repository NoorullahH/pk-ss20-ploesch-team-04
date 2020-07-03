package taskmanagerView;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import com.dropbox.core.DbxException;
import com.dropbox.core.v2.files.UploadErrorException;

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
import javafx.util.Callback;
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
	private TableColumn<Task, Void> deleteButton;
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
		
		addDeleteButtonToTable();
		
		descriptionColumn.setStyle("-fx-font-size: 12 ;");
		detailedDescriptionColumn.setStyle("-fx-font-size: 12 ;");
		contributorsColumn.setStyle("-fx-font-size: 12 ;");
		categoriesColumn.setStyle("-fx-font-size: 12 ;");
		dueDateColumn.setStyle("-fx-font-size: 12 ;");
		attachmentsColumn.setStyle("-fx-font-size: 12 ;");
		doneCheckBoxColumn.setStyle("-fx-font-size: 12 ;");
		
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
		Parent parent = FXMLLoader.load(getClass().getResource("Task.fxml"));
		Scene scene = new Scene(parent);
		Stage windowStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		windowStage.setScene(scene);
		windowStage.show();
	}
	
	
	private void addDeleteButtonToTable() {
		
		Callback<TableColumn<Task, Void>, TableCell<Task, Void>> cellFactory = new Callback<TableColumn<Task, Void>, TableCell<Task, Void>>() {
            @Override
            public TableCell<Task, Void> call(final TableColumn<Task, Void> param) {
                final TableCell<Task, Void> cell = new TableCell<Task, Void>() {

                    private final Button btn = new Button("delete");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Task task = getTableView().getItems().get(getIndex());
                            taskList.removeTask(task);
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        deleteButton.setCellFactory(cellFactory);
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
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Task.fxml"));
		Parent root = loader.load();
		TaskController controller = loader.<TaskController>getController();
		controller.initializeTask(taskView.getSelectionModel().getSelectedItem());
		
		Parent parent = FXMLLoader.load(getClass().getResource("Task.fxml"));
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
		for(int i = 0;  i < size; i++) {
			Task t = taskList.getTasks().get(i);
			if(t.isRecurrent()) {
				taskList.handleRecurrentTasks(t);
			}
		}
	}
	
	public void editData(int taskNumber, String taskDes, String detailedTaskDes, LocalDate dueDate, ObservableList<Contributor> contributors, ObservableList<Category> categories, ObservableList<Subtask> subtasks, ObservableList<String> attachments, boolean recurrent, boolean monthly, boolean weekly,  Weekday weekday, int monthday, int numberOfRepetitions, LocalDate repetitionDate) {
		ObservableList<Task> tasks = taskList.getTasks();
		for(Task t:tasks) {
			System.out.println("Task:"+t.getTaskNumber());
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
				System.out.println("Change Task"+"Tasknummer:"+t.getTaskNumber());
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
	
	
	@FXML
	private void saveToCsv(ActionEvent event){
		FileChooser fileChooser = new FileChooser();
		//FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
		//fileChooser.getExtensionFilters().add(extFilter);
		
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
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
		}
	}
	
		
	
	@FXML
	private void filterTasks (ActionEvent event) throws IOException {
		Parent parent = FXMLLoader.load(getClass().getClassLoader().getResource("FilterView/FilterWindow.fxml"));
		Scene scene = new Scene(parent);
		Stage windowStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		windowStage.setScene(scene);
		windowStage.show();
	}
	@FXML
	private void dropboxUpload (ActionEvent event) throws IOException, UploadErrorException, DbxException {
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
	private void importDropboxWriter (ActionEvent event) throws IOException, UploadErrorException, DbxException {

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