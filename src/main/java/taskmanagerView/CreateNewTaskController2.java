package task;

import contributor.Contributor;

import java.io.IOException;
import java.time.LocalDate;
import java.util.LinkedList;

import category.Category;
import category.Categorymanager;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import weekday.Weekday;
import javafx.scene.control.ComboBox;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.SelectionMode;

public class CreateNewTaskController2 {
		
	ObservableList<Integer> monthdayList = FXCollections.observableArrayList(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31);
	
	ObservableList<String> weekdayList = FXCollections.observableArrayList(Weekday.MONDAY.toString(),Weekday.TUESDAY.toString(),Weekday.WEDNESDAY.toString(),Weekday.THURSDAY.toString(),Weekday.FRIDAY.toString(),Weekday.SATURDAY.toString(),Weekday.SUNDAY.toString());
	
	ObservableList<String> conItems = FXCollections.observableArrayList("Mara","Noorullah","Dino");
	
	ObservableList<String> catItems = FXCollections.observableArrayList("Software Engineering","Datenmodellierung","Englisch","Deutsch");
		
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
	private Button addTask;

	
	
	@FXML
	private void initialize() throws Exception{
		monthday.setItems(monthdayList);
		weekday.setItems(weekdayList);
		
		//Initialize ContributorListView
		contributorList.setItems(conItems);
		contributorList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
		//Initialize CategoryListView
		categoryList.setItems(catItems);
		categoryList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
	}
	
	//Ensures that it is not possible to select monthly and weekly at the same time
	@FXML
	private void handleMonthlyBox() {
		if(monthlyBox.isSelected()) {
			weeklyBox.setSelected(false);
		}
	}
	
	//Ensures that it is not possible to select monthly and weekly at the same time
	@FXML
	private void handleWeeklyBox() {
		if(weeklyBox.isSelected()) {
			monthlyBox.setSelected(false);
		}
	}
	
	//This Method adds a new Task
	@FXML
	public void buttonAdd(ActionEvent event) throws IOException {
		
		Task taskNew = new Task();
		taskNew.setTaskDescription(taskDescriptionField.getText());
		taskNew.setDetailedTaskDescription(detailedTaskDescriptionField.getText());
		taskNew.setDueDate(dueDateField.getValue());
		
		//checks if a Task is recurrent
		if(recurrentBox.isSelected()) {
			taskNew.setRecurrent(recurrentBox.isSelected());
			taskNew.setMonthly(monthlyBox.isSelected());
			taskNew.setMonthday((int) monthday.getSelectionModel().getSelectedItem());
			taskNew.setWeekly(weeklyBox.isSelected());
			taskNew.setWeekday((Weekday) weekday.getSelectionModel().getSelectedItem());
		}
				
		//Set selected Contributors
		ObservableList<String> newCon = contributorList.getSelectionModel().getSelectedItems();
		LinkedList<Contributor> newConList = new LinkedList<>();
		
		for(String s:newCon) {
			newConList.add(new Contributor(s));
		}
		
		taskNew.setContributors(newConList);
		
		//Set selected Categories
		ObservableList<String> newCat = categoryList.getSelectionModel().getSelectedItems();
		LinkedList<Category> newCatList = new LinkedList<>();
		
		for(String s:newCat) {
			newCatList.add(new Category(s));
		}
		
		taskNew.setCategories(newCatList);
		

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
	
	@FXML
	public void back(ActionEvent event) throws IOException {

		Parent parent = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
		Scene scene = new Scene(parent);
		Stage windowStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		windowStage.setScene(scene);
		windowStage.show();
	}
	
}
