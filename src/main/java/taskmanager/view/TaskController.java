package taskmanager.view;

import contributor.Contributor;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.scene.control.TableRow;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import category.Category;
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
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TextArea;
import weekday.Weekday;
import javafx.scene.control.ComboBox;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class TaskController implements Initializable{
		
	private ObservableList<Integer> monthdayList = FXCollections.observableArrayList(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31);
	
	private ObservableList<Weekday> weekdayList = FXCollections.observableArrayList(Weekday.MONDAY,Weekday.TUESDAY,Weekday.WEDNESDAY,Weekday.THURSDAY,Weekday.FRIDAY,Weekday.SATURDAY,Weekday.SUNDAY);
	
	private ObservableList<Subtask> subtaskItems = FXCollections.observableArrayList();
	
	private ObservableList<String> attachmentItems = FXCollections.observableArrayList();
	
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
	private ComboBox<Weekday> weekday;
	@FXML
	private ComboBox<Integer> monthday;
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
	private TableView<String> attachmentView;
	@FXML
	private TableColumn<String, String> attachmentNameColumn;
	@FXML
	private TableColumn<String, Boolean> deleteButtonAttachment;
	@FXML
	private TextField subtaskField;
	@FXML
	private Button addSubtaskButton;
	@FXML
	private TableView<Subtask> subtaskView;
	@FXML
	private TableColumn<Subtask, String> subtaskNameColumn;
	@FXML
	private TableColumn<Subtask, Boolean> doneCheckBoxColumn;
	@FXML
	private TableColumn<Subtask, Boolean> deleteButtonSubtask;
	@FXML
	private Button addTaskButton;
	@FXML
	private Button editTaskButton;
	@FXML
	private Button backButton;
	
	private static final String LINKTOINFO = "InfoWindow.fxml";
	private static final String LINKTOMAIN = "MainWindow.fxml";
	
	private static final Logger LOGGER = Logger.getLogger(TaskController.class.getName());
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {		
		
		timesOfRepititionsField.textProperty().addListener((observable, oldValue, newValue) -> {
		    if (Pattern.matches( "\\d*", newValue )) return;
		    timesOfRepititionsField.setText(newValue.replaceAll("[^\\d]", ""));
		});
		
		monthday.setItems(monthdayList);
		weekday.setItems(weekdayList);
		
		//Initialize ContributorListView
		FXMLLoader loaderCon = new FXMLLoader(getClass().getResource("ContributorWindow.fxml"));
		try {
			loaderCon.load();
			ContributorController conController = loaderCon.<ContributorController>getController();
			contributorList.setItems(conController.getContributorList());
			contributorList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Exception occured (Contributors)", e);
		}
		
		//Initialize CategoryListView
		FXMLLoader loaderCat = new FXMLLoader(getClass().getResource("CategoryWindow.fxml"));
		try {
			loaderCat.load();
			CategoryController catController = loaderCat.<CategoryController>getController();
			categoryList.setItems(catController.getCategoryList());
			categoryList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Exception occured (Categories)", e);
		}
	
		//Initialize AttachmentListView
		attachmentView.setItems(attachmentItems);
		attachmentNameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()));
		
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
		
		
		deleteButtonSubtask.setCellFactory(para -> new ButtonCell());
		
		deleteButtonAttachment.setCellFactory(para -> new ButtonCellAttachment());
			
		editTaskButton.setVisible(false);
	}
	
	
	private class ButtonCell extends TableCell<Subtask, Boolean>{
		private final Button delete = new Button("delete");
		
		ButtonCell(){
			
			delete.setOnAction(e -> {subtaskItems.remove(ButtonCell.this.getTableView().getItems().get(ButtonCell.this.getIndex()));subtaskView.setItems(subtaskItems);});

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
	
	
	private class ButtonCellAttachment extends TableCell<String, Boolean>{
		private final Button delete = new Button("delete");
		
		ButtonCellAttachment(){
			
			delete.setOnAction(e -> {attachmentItems.remove(ButtonCellAttachment.this.getTableView().getItems().get(ButtonCellAttachment.this.getIndex()));attachmentView.setItems(attachmentItems);});

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

	//Ensures that it is not possible to select monthly and weekly at the same time
	@FXML
	private void handleMonthlyBox() {
		if(monthlyBox.isSelected()) {
			weeklyBox.setSelected(false);
			weekday.setValue(null);
			recurrentBox.setSelected(true);
		}
	}
	
	//Ensures that it is not possible to select monthly and weekly at the same time
	@FXML
	private void handleWeeklyBox() {
		if(weeklyBox.isSelected()) {
			monthlyBox.setSelected(false);
			monthday.setValue(null);
			recurrentBox.setSelected(true);
		}
	}
	
	//Ensures that it is not possible to select monthday and weeekday at the same time
	@FXML
	private void handleMonthday(){
		Integer mday = monthday.getValue();
		if(monthday.getValue()!=null) {
			weeklyBox.setSelected(false);
			monthlyBox.setSelected(true);
			handleMonthlyBox();
			monthday.setValue(mday);
		}
	}
	
	//Ensures that it is not possible to select monthday and weeekday at the same time
	@FXML
	private void handleWeekday(){
		Weekday wday = weekday.getValue();
		if(weekday.getValue()!=null) {
			monthlyBox.setSelected(false);
			weeklyBox.setSelected(true);
			handleWeeklyBox();
			weekday.setValue(wday);
		}
	}
	
	//Ensures that it is not possible to select monthly and weekly at the same time
	@FXML
	private void handleRepetitionDateField() {
		if(repetitionDateField.getValue()!=null) {
			timesOfRepititionsField.setText("");
		}
	}
	
	//Add Attachment
	@FXML
	public void addAttachment(ActionEvent event) {
		if(attachmentField.getText()!= null && attachmentField.getText().length()>8) {
			if("https://".equals(attachmentField.getText().substring(0, 8))) {
				attachmentItems.add(attachmentField.getText());
				attachmentView.setItems(attachmentItems);
				attachmentField.setText("");
			}
		}else {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(LINKTOINFO));
			Parent root;
			try {
				root = loader.load();
				InfoWindowController controller = loader.<InfoWindowController>getController();
				controller.setInfoText("Invalid Attachment!");
				
				Stage newstage = new Stage();
				newstage.setTitle("Info");
				newstage.setScene(new Scene(root));
				newstage.showAndWait();
			} catch (IOException e) {
				LOGGER.log(Level.SEVERE, "Exception occured (Add Attachment)", e);
			}
		}	
	}
	
	//Add Subtask to SubtaskItems
		@FXML
		public void addSubtask(ActionEvent event) {
			if(subtaskField.getText()!= null && !subtaskField.getText().isEmpty()) {
				subtaskItems.add(new Subtask(subtaskField.getText()));
				subtaskView.setItems(subtaskItems);
				subtaskField.setText("");
			}else {
				FXMLLoader loader = new FXMLLoader(getClass().getResource(LINKTOINFO));
				Parent root;
				try {
					root = loader.load();
					InfoWindowController controller = loader.<InfoWindowController>getController();
					controller.setInfoText("Subtask name must be specified!");
					
					Stage newstage = new Stage();
					newstage.setTitle("Info");
					newstage.setScene(new Scene(root));
					newstage.showAndWait();
				} catch (IOException e) {
					LOGGER.log(Level.SEVERE, "Exception occured (Add Subtask)", e);
				}
			}
			
		}
	
	//This Method adds a new Task
	@FXML
	public void addTask(ActionEvent event) throws IOException {
		
		if(!checkEntries()) {
			return;
		}
		Task taskNew = null;
		
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
			if((weeklyBox.isSelected()) && (!("".equals(weekday.getValue().toString())))) {
				taskNew = new Task(taskDescriptionField.getText(), detailedTaskDescriptionField.getText(), dueDateField.getValue(), newConList, newCatList, subtaskItems, attachmentView.getItems(), (Weekday) weekday.getValue(), times, repetitionDateField.getValue());
			//monatlich
			}else if((monthlyBox.isSelected()) && (!("".equals(monthday.getValue().toString())))) {
				taskNew = new Task(taskDescriptionField.getText(), detailedTaskDescriptionField.getText(), dueDateField.getValue(), newConList, newCatList, subtaskItems, attachmentView.getItems(), (int) monthday.getValue(), times, repetitionDateField.getValue());
			}else {
				taskNew = new Task(taskDescriptionField.getText(), detailedTaskDescriptionField.getText(), dueDateField.getValue(), newConList, newCatList, subtaskItems,attachmentView.getItems());
			}
		}else {
			taskNew = new Task(taskDescriptionField.getText(), detailedTaskDescriptionField.getText(), dueDateField.getValue(), newConList, newCatList, subtaskItems,attachmentView.getItems());
		}
		
		taskNew.setCreationDate(LocalDate.of(2020, 1, 1)); //Änderen

		FXMLLoader loader = new FXMLLoader(getClass().getResource(LINKTOMAIN));
		Parent root = loader.load();
		MainWindowController controller = loader.<MainWindowController>getController();
		controller.setData(taskNew);
		
		FXMLLoader.load(getClass().getResource(LINKTOMAIN));
		Scene scene = new Scene(root);//parent
		Stage windowStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		windowStage.setScene(scene);
		windowStage.show();
	}
	
	private boolean checkEntries() {
		
		if(taskDescriptionField.getText().isEmpty()) {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(LINKTOINFO));
			Parent root;
			try {
				root = loader.load();
				InfoWindowController controller = loader.<InfoWindowController>getController();
				controller.setInfoText("Task Description must be specified!");
				
				Stage newstage = new Stage();
				newstage.setTitle("Info");
				newstage.setScene(new Scene(root));
				newstage.showAndWait();
				return false;
			} catch (IOException e) {
				LOGGER.log(Level.SEVERE, "Exception occured (Info Screen)", e);
			}
		}
		
		if(dueDateField.getValue() == null) {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(LINKTOINFO));
			Parent root;
			try {
				root = loader.load();
				InfoWindowController controller = loader.<InfoWindowController>getController();
				controller.setInfoText("Due date must be specified!");
				
				Stage newstage = new Stage();
				newstage.setTitle("Info");
				newstage.setScene(new Scene(root));
				newstage.showAndWait();
				return false;
			} catch (IOException e) {
				LOGGER.log(Level.SEVERE, "Exception occured (Info Screen)", e);
			}	
		}
		return true;
	}
	
	public void initializeTask(Task task) {
		
		taskNumber = task.getTaskNumber();
	
		taskDescriptionField.setText(task.getTaskDescription());
		detailedTaskDescriptionField.setText(task.getDetailedTaskDescription());
		dueDateField.setValue(task.getDueDate());
		recurrentBox.setSelected(task.isRecurrent());
		monthlyBox.setSelected(task.isMonthly());
		weeklyBox.setSelected(task.isWeekly());
		weekday.getSelectionModel().select(task.getWeekday());
		if(task.getMonthday() != 0) {
			monthday.setValue(task.getMonthday());
		}
		
		timesOfRepititionsField.setText(""+task.getNumberOfRepetitions()+"");
		repetitionDateField.setValue(task.getRepetitionDate());
		
		//Initialize AttachmentListView
		attachmentItems.setAll(task.getAttachments());
	
		ObservableList<Contributor> conList = task.getContributorsList();
		for (Contributor c:conList) {
			contributorList.getSelectionModel().select(c.getPerson());
		}
		
		ObservableList<Category> catList = task.getCategoriesList();
		for (Category c:catList) {
			categoryList.getSelectionModel().select(c.getCategory());
		}
		
		//Initialize SubtasksListView
		subtaskItems = task.getSubtasks();
		subtaskView.setItems(subtaskItems);
		
		addTaskButton.setVisible(false);
		editTaskButton.setVisible(true);
	}
	
	@FXML
	private void editTask(ActionEvent event) throws IOException {
		
		if(!checkEntries()) {
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
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource(LINKTOMAIN));
		Parent root = loader.load();
		MainWindowController controller = loader.<MainWindowController>getController();
		
		if(recurrentBox.isSelected()) {
			if((weeklyBox.isSelected()) && (!("".equals(weekday.getValue().toString())))) {
				controller.editData(taskNumber, taskDescriptionField.getText(), detailedTaskDescriptionField.getText(), dueDateField.getValue(), newConList, newCatList, subtaskItems, attachmentView.getItems(), true, false, true, (Weekday) weekday.getValue(), 0, times, repetitionDateField.getValue());
			}else if((monthlyBox.isSelected()) && (!("".equals(monthday.getValue().toString())))) {
				controller.editData(taskNumber, taskDescriptionField.getText(), detailedTaskDescriptionField.getText(), dueDateField.getValue(), newConList, newCatList, subtaskItems, attachmentView.getItems(), true, true, false, null, (int) monthday.getValue(), times, repetitionDateField.getValue());
			}else {
				controller.editData(taskNumber, taskDescriptionField.getText(), detailedTaskDescriptionField.getText(), dueDateField.getValue(), newConList, newCatList, subtaskItems,attachmentView.getItems(), false, false, false, null, 0,0, null);
			}
		}else {
			controller.editData(taskNumber, taskDescriptionField.getText(), detailedTaskDescriptionField.getText(), dueDateField.getValue(), newConList, newCatList, subtaskItems,attachmentView.getItems(), false, false, false, null, 0,0, null);
		}
		
		FXMLLoader.load(getClass().getResource(LINKTOMAIN));
		Scene scene = new Scene(root);//parent
		Stage windowStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		windowStage.setScene(scene);
		windowStage.show();
	}
	
	//Navigate back to MainWindow
	@FXML
	public void backtoMain(ActionEvent event) throws IOException {
		Parent parent = FXMLLoader.load(getClass().getResource(LINKTOMAIN));
		Scene scene = new Scene(parent);
		Stage windowStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		windowStage.setScene(scene);
		windowStage.show();
	}
}