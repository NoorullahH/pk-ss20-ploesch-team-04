package taskmanagerView;

import contributor.Contributor;
import javafx.beans.value.ChangeListener;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.scene.control.TableRow;

import javafx.beans.property.BooleanProperty;
import category.Category;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.stage.Stage;
import task.Subtask;
import task.Task;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TextArea;
import weekday.Weekday;
import javafx.scene.control.ComboBox;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;

public class CreateNewTaskController2 implements Initializable{
		
	ObservableList<Integer> monthdayList = FXCollections.observableArrayList(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31);
	
	ObservableList<Weekday> weekdayList = FXCollections.observableArrayList(Weekday.MONDAY,Weekday.TUESDAY,Weekday.WEDNESDAY,Weekday.THURSDAY,Weekday.FRIDAY,Weekday.SATURDAY,Weekday.SUNDAY);
	
	ObservableList<Subtask> subtaskItems = FXCollections.observableArrayList();
	
	ObservableList<String> attachmentItems = FXCollections.observableArrayList();
		
	@FXML
	private TextField taskDescriptionField;
	@FXML
	private TextArea detailedTaskDescriptionField;
	@FXML
	private DatePicker dueDateField;
	@FXML
	private CheckBox recurrentBox;
	@FXML
	private CheckBox monthlyBox;
	@FXML
	private CheckBox weeklyBox;
	@FXML
	private ComboBox weekday;
	@FXML
	private ComboBox monthday;
	@FXML
	private TextField timesOfRepititionsField;
	@FXML
	private DatePicker repetitionDateField;
	@FXML
	private ListView<String> contributorList = new ListView<>();
	@FXML
	private ListView<String> categoryList = new ListView<>();
	@FXML
	private TextField attachmentField;
	@FXML
	private Button addAttachmentButton;
	@FXML
	private Button deleteAttachmentButton;
	@FXML
	private ListView<String> attachmentsList = new ListView<>();
	@FXML
	private TextField subtaskField;
	@FXML
	private Button addSubtaskButton;
	@FXML
	private Button deleteSubtaskButton;
	@FXML
	private TableView<Subtask> subtaskView;
	@FXML
	private TableColumn<Subtask, String> subtaskNameColumn;
	@FXML
	private TableColumn<Subtask, Boolean> doneCheckBoxColumn;
	@FXML
	private Button addTaskButton;
	@FXML
	private Button backButton;
	@FXML
	private Label infoTextField;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {		
		
		//Number
		timesOfRepititionsField.textProperty().addListener(new ChangeListener<String>() {
		    @Override 
		    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		        if (newValue.matches("\\d*")) {
		            int value = Integer.parseInt(newValue);
		            repetitionDateField.setValue(null);
		        } else {
		        	timesOfRepititionsField.setText(oldValue);
		        	repetitionDateField.setValue(null);
		        }
		    }
		});
		
		monthday.setItems(monthdayList);
		weekday.setItems(weekdayList);
		
		//Initialize ContributorListView
		FXMLLoader loaderCon = new FXMLLoader(getClass().getResource("ContributorWindow.fxml"));
		try {
			Parent rootCon = loaderCon.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ContributorController conController = loaderCon.<ContributorController>getController();
		contributorList.setItems(conController.getContributorList());
		contributorList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
		//Initialize CategoryListView
		FXMLLoader loaderCat = new FXMLLoader(getClass().getResource("CategoryWindow.fxml"));
		try {
			Parent rootCat = loaderCat.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		CategoryController catController = loaderCat.<CategoryController>getController();
		categoryList.setItems(catController.getCategoryList());
		categoryList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
	
		//Initialize AttachmentListView
		attachmentsList.setItems(attachmentItems);
		
		//Initialize SubtasksListView
		subtaskView.setItems(subtaskItems);
		subtaskView.setEditable(true);
		
		subtaskView.setRowFactory(row -> new TableRow<Subtask>(){
			@Override
		    public void updateItem(Subtask item, boolean empty){
				if (item == null || empty) {
					setStyle("");
				} else {
					if (item.isDone()) {
						setStyle("-fx-background-color: lightgreen;");
					}else {
						setStyle("");
					}
				}
			}
		});
		
		subtaskNameColumn.setCellValueFactory(new PropertyValueFactory<Subtask, String>("subtask"));
		doneCheckBoxColumn.setCellValueFactory(new PropertyValueFactory<Subtask, Boolean>("done"));
		doneCheckBoxColumn.setCellFactory(CheckBoxTableCell.forTableColumn(doneCheckBoxColumn));
		doneCheckBoxColumn.setEditable(true);
		
		doneCheckBoxColumn.setCellValueFactory(cellData -> {
			Subtask cellValue = cellData.getValue();
			BooleanProperty property = cellValue.getDone();
			property.addListener((observable, oldValue, newValue) -> cellValue.setDone(newValue));
			return property;
		});
			
		infoTextField.setStyle("-fx-text-inner-color: red;");
	}

	//Ensures that it is not possible to select monthly and weekly at the same time
	@FXML
	private void handleMonthlyBox() {
		if(monthlyBox.isSelected()) {
			weeklyBox.setSelected(false);
			weekday.setValue("");
			recurrentBox.setSelected(true);
		}
	}
	
	//Ensures that it is not possible to select monthly and weekly at the same time
	@FXML
	private void handleWeeklyBox() {
		if(weeklyBox.isSelected()) {
			monthlyBox.setSelected(false);
			monthday.setValue("");
			recurrentBox.setSelected(true);
		}
	}
	
	//Ensures that it is not possible to select monthday and weeekday at the same time
	@FXML
	private void handleMonthday(){
		Object o = monthday.getValue();
		if(monthday.getValue()!=null) {
			weeklyBox.setSelected(false);
			monthlyBox.setSelected(true);
			handleMonthlyBox();
			monthday.setValue(o);
		}
	}
	
	//Ensures that it is not possible to select monthday and weeekday at the same time
	@FXML
	private void handleWeekday(){
		Object o = weekday.getValue();
		if(weekday.getValue()!=null) {
			monthlyBox.setSelected(false);
			weeklyBox.setSelected(true);
			handleWeeklyBox();
			weekday.setValue(o);
		}
	}
	
	//Ensures that it is not possible to select monthly and weekly at the same time
	@FXML
	private void handleRepetitionDateField() {
		if(repetitionDateField.getValue()!=null) {
			timesOfRepititionsField.setText("");
		}
	}
	
	
	//This Method adds a new Task
	@FXML
	public void addTask(ActionEvent event) throws IOException {
		
		Task taskNew = null;
		
		if(taskDescriptionField.getText().isEmpty()) {
			infoTextField.setText("Name of the task must be specified!");
			return;
		}
		
		if(dueDateField.getValue() == null) {
			infoTextField.setText("Due date must be specified!");
			return;
		}
		
		//Set selected Contributors
		ObservableList<String> newCon = contributorList.getSelectionModel().getSelectedItems();
		ObservableList<Contributor> newConList = FXCollections.observableArrayList();
		
		for(String s:newCon) {
			newConList.add(new Contributor(s));
		}
		
		//Kategorien
		ObservableList<String> newCat = categoryList.getSelectionModel().getSelectedItems();
		ObservableList<Category> newCatList = FXCollections.observableArrayList();

		for(String s:newCat) {
			newCatList.add(new Category(s));
		}
		
		//Anzahl der Wiederholungen
		int times = 0;
		if(!timesOfRepititionsField.getText().isEmpty()) {
			times = Integer.parseInt(timesOfRepititionsField.getText());
		}
		
		//wiederkehrende Task?
		if(recurrentBox.isSelected()) {
			//wöchentlich
			if((weeklyBox.isSelected()) && (!(weekday.getValue().toString().equals("")))) {
				taskNew = new Task(taskDescriptionField.getText(), detailedTaskDescriptionField.getText(), dueDateField.getValue(), newConList, newCatList, subtaskItems, attachmentsList.getItems(), (Weekday) weekday.getValue(), times, repetitionDateField.getValue());
			//monatlich
			}else if((monthlyBox.isSelected()) && (!(monthday.getValue().equals("")))) {
				taskNew = new Task(taskDescriptionField.getText(), detailedTaskDescriptionField.getText(), dueDateField.getValue(), newConList, newCatList, subtaskItems, attachmentsList.getItems(), (int) monthday.getValue(), times, repetitionDateField.getValue());
			}else {
				taskNew = new Task(taskDescriptionField.getText(), detailedTaskDescriptionField.getText(), dueDateField.getValue(), newConList, newCatList, subtaskItems,attachmentsList.getItems());
			}
		}else {
			taskNew = new Task(taskDescriptionField.getText(), detailedTaskDescriptionField.getText(), dueDateField.getValue(), newConList, newCatList, subtaskItems,attachmentsList.getItems());
		}
		
		taskNew.setCreationDate(LocalDate.of(2020, 01, 01)); //Änderen

		FXMLLoader loader = new FXMLLoader(getClass().getResource("MainWindow.fxml"));
		Parent root = loader.load();
		MainWindowController controller = loader.<MainWindowController>getController();
		controller.setData(taskNew);
		
		Parent parent = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
		Scene scene = new Scene(root);//parent
		Stage windowStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		windowStage.setScene(scene);
		windowStage.show();
	}
	

	//Add Subtask to SubtaskItems
	@FXML
	public void addSubtask(ActionEvent event) {
		subtaskItems.add(new Subtask(subtaskField.getText()));
		subtaskView.setItems(subtaskItems);
	}
	
	//Delete SubtaskItem
	@FXML 
	public void deleteSubtask(ActionEvent event) {
		subtaskItems.remove(subtaskView.getSelectionModel().getSelectedItem());
		subtaskView.setItems(subtaskItems);
	}
	
	//Add Attachment
	@FXML
	public void addAttachment(ActionEvent event) {
		attachmentItems.add(attachmentField.getText());
		attachmentsList.setItems(attachmentItems);
	}
	
	//Delete Attachment
	@FXML 
	public void deleteAttachment(ActionEvent event) {
		attachmentItems.remove(attachmentsList.getSelectionModel().getSelectedItem());
		attachmentsList.setItems(attachmentItems);
	}
		
	//Navigate back to MainWindow
	@FXML
	public void backtoMain(ActionEvent event) throws IOException {

		Parent parent = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
		Scene scene = new Scene(parent);
		Stage windowStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		windowStage.setScene(scene);
		windowStage.show();
	}
	
}
