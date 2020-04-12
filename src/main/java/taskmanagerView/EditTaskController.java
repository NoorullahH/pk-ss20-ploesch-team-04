package taskmanagerView;

import java.io.IOException;
import category.Category;
import contributor.Contributor;
import javafx.beans.property.BooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import task.Subtask;
import task.Task;
import weekday.Weekday;

public class EditTaskController {
	
	ObservableList<Integer> monthdayList = FXCollections.observableArrayList(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31);
	
	ObservableList<Weekday> weekdayList = FXCollections.observableArrayList(Weekday.MONDAY,Weekday.TUESDAY,Weekday.WEDNESDAY,Weekday.THURSDAY,Weekday.FRIDAY,Weekday.SATURDAY,Weekday.SUNDAY);
	
	ObservableList<Subtask> subtaskItems = FXCollections.observableArrayList();
	
	ObservableList<String> attachmentItems = FXCollections.observableArrayList();
	
	private int taskNumber;
	
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
	private Button editTaskButton;
	@FXML
	private Button backButton;
	@FXML
	private Label infoTextField;
	
	
	@FXML
	public void initializeTask(Task t) {
		taskNumber = t.getTaskNumber();
		
		monthday.setItems(monthdayList);
		weekday.setItems(weekdayList);
	
		taskDescriptionField.setText(t.getTaskDescription());
		detailedTaskDescriptionField.setText(t.getDetailedTaskDescription());
		dueDateField.setValue(t.getDueDate());
		recurrentBox.setSelected(t.isRecurrent());
		monthlyBox.setSelected(t.isMonthly());
		weeklyBox.setSelected(t.isWeekly());
		weekday.getSelectionModel().select(t.getWeekday());
		if(t.getMonthday() != 0) {
			monthday.setValue(t.getMonthday());
		}
		
		timesOfRepititionsField.textProperty().addListener(new ChangeListener<String>() {
		    @Override 
		    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		        if (newValue.matches("\\d*")) {
		            int value = Integer.parseInt(newValue);
		        } else {
		        	timesOfRepititionsField.setText(oldValue);
		        }
		    }
		});
		
		timesOfRepititionsField.setText(""+t.getNumberOfRepetitions()+"");
		
		//Initialize AttachmentListView
		attachmentItems.setAll(t.getAttachments());
		attachmentsList.setItems(attachmentItems);
		
	
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
		
		ObservableList<Contributor> conList = t.getContributors();
		for (Contributor c:conList) {
			contributorList.getSelectionModel().select(c.getPerson());
		}
		
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
		
		ObservableList<Category> catList = t.getCategories();
		for (Category c:catList) {
			categoryList.getSelectionModel().select(c.getCategory());
		}
		
		//Initialize SubtasksListView
		subtaskItems = t.getSubtasks();
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
	
	//Add Subtask to SubtaskItems
	@FXML
	public void addSubtask(ActionEvent event) {
		subtaskItems.add(new Subtask(subtaskField.getText()));
		subtaskView.setItems(subtaskItems);
	}
		
	@FXML 
	public void deleteSubtask(ActionEvent event) {
		subtaskItems.remove(subtaskView.getSelectionModel().getSelectedItem());
		subtaskView.setItems(subtaskItems);
	}
			
	//Add Subtask to SubtaskItems
	@FXML
	public void addAttachment(ActionEvent event) {
		attachmentItems.add(attachmentField.getText());
		attachmentsList.setItems(attachmentItems);
	}
	
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
	
	
	@FXML
	private void editTask(ActionEvent event) throws IOException {
		
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
				
		ObservableList<String> newCat = categoryList.getSelectionModel().getSelectedItems();
		ObservableList<Category> newCatList = FXCollections.observableArrayList();

		for(String s:newCat) {
			newCatList.add(new Category(s));
		}
		
		int times = 0;
		if(!timesOfRepititionsField.getText().isEmpty()) {
			times = Integer.parseInt(timesOfRepititionsField.getText());
		}
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("MainWindow.fxml"));
		Parent root = loader.load();
		MainWindowController controller = loader.<MainWindowController>getController();
		
		if(recurrentBox.isSelected()) {
			if((weeklyBox.isSelected()) && (!(weekday.getValue().toString().equals("")))) {
				controller.editData(taskNumber, taskDescriptionField.getText(), detailedTaskDescriptionField.getText(), dueDateField.getValue(), newConList, newCatList, subtaskItems, attachmentsList.getItems(), true, false, true, (Weekday) weekday.getValue(), 0, times);
			}else if((monthlyBox.isSelected()) && (!(monthday.getValue().equals("")))) {
				controller.editData(taskNumber, taskDescriptionField.getText(), detailedTaskDescriptionField.getText(), dueDateField.getValue(), newConList, newCatList, subtaskItems, attachmentsList.getItems(), true, true, false, null, (int) monthday.getValue(), times);
			}else {
				controller.editData(taskNumber, taskDescriptionField.getText(), detailedTaskDescriptionField.getText(), dueDateField.getValue(), newConList, newCatList, subtaskItems,attachmentsList.getItems(), false, false, false, null, 0,0);
			}
		}else {
			controller.editData(taskNumber, taskDescriptionField.getText(), detailedTaskDescriptionField.getText(), dueDateField.getValue(), newConList, newCatList, subtaskItems,attachmentsList.getItems(), false, false, false, null, 0,0);
		}
		
		Parent parent = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
		Scene scene = new Scene(root);//parent
		Stage windowStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		windowStage.setScene(scene);
		windowStage.show();
		
	}
	
}
