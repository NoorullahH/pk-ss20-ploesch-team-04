package FilterView;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

import category.Category;
import contributor.Contributor;
import javafx.beans.property.BooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import task.Subtask;
import task.Task;
import task.Taskmanager;
import taskmanagerView.CategoryController;
import taskmanagerView.ContributorController;

//to do class
/**
 * @author Dino
 *
 */
public class FilterController implements Initializable {
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
    private Button submit;
    @FXML
    private Button csv;
    @FXML
    private TextField taskDescriptionField;
    @FXML
    private TextField detailedTaskDescriptionField;
    @FXML
    private DatePicker from;
    @FXML
    private DatePicker until;
    @FXML
    private ListView<String> contributorList = new ListView<>();
    @FXML
    private ListView<String> categoryList = new ListView<>();
    @FXML
    private Button backButton;

    @FXML
    private RadioButton open = new RadioButton();
    @FXML
    private RadioButton closed= new RadioButton();
    @FXML
    private RadioButton yes= new RadioButton();
    @FXML
    private RadioButton no= new RadioButton();
    @FXML
    ToggleGroup attachment = new ToggleGroup(); //I called it right in SceneBuilder.
    @FXML
    ToggleGroup task_done= new ToggleGroup(); //I called it right in SceneBuilder.
    Taskmanager taskList;
    ObservableList<Task> toFilterTasks;
    FilteredList<Task> filteredData;

   
    /**
     *
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub
        //initialize taskList
        taskList = Taskmanager.getInstance();
        //set up the columns in the table
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<Task, String>("taskDescription"));
        detailedDescriptionColumn.setCellValueFactory(new PropertyValueFactory<Task, String>("detailedTaskDescription"));
        contributorsColumn.setCellValueFactory(new PropertyValueFactory<Task, String>("contributors"));
        categoriesColumn.setCellValueFactory(new PropertyValueFactory<Task, String>("categories"));
        dueDateColumn.setCellValueFactory(new PropertyValueFactory<Task, LocalDate>("dueDate"));
        attachmentsColumn.setCellValueFactory(new PropertyValueFactory<Task, List<String>>("attachments"));
        descriptionColumn.setStyle("-fx-font-size: 15 ;");
        detailedDescriptionColumn.setStyle("-fx-font-size: 15 ;");
        contributorsColumn.setStyle("-fx-font-size: 15 ;");
        categoriesColumn.setStyle("-fx-font-size: 15 ;");
        dueDateColumn.setStyle("-fx-font-size: 15 ;");
        attachmentsColumn.setStyle("-fx-font-size: 15 ;");
        doneCheckBoxColumn.setStyle("-fx-font-size: 15 ;");
        doneCheckBoxColumn.setCellValueFactory(new PropertyValueFactory<Task, Boolean>("done"));
        doneCheckBoxColumn.setCellFactory(CheckBoxTableCell.forTableColumn(doneCheckBoxColumn));
        doneCheckBoxColumn.setEditable(true);

    	//Initialize ContributorListView
		FXMLLoader loaderCon = new FXMLLoader(getClass().getClassLoader().getResource("taskmanagerView/ContributorWindow.fxml"));
		try {
			Parent rootCon = loaderCon.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ContributorController conController = loaderCon.<ContributorController>getController();
		contributorList.setItems(conController.getContributorList());
		contributorList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		contributorList.setStyle("-fx-font-size: 16 ;");
		
		//Initialize CategoryListView
		FXMLLoader loaderCat = new FXMLLoader(getClass().getClassLoader().getResource("taskmanagerView/CategoryWindow.fxml"));
		try {
			Parent rootCat = loaderCat.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		CategoryController catController = loaderCat.<CategoryController>getController();
		categoryList.setItems(catController.getCategoryList());
		categoryList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		categoryList.setStyle("-fx-font-size: 16 ;");

        taskView.setEditable(true);

        taskView.setRowFactory(row -> new TableRow<Task>() {
            @Override
            public void updateItem(Task item, boolean empty) {
                if (item == null || empty) {
                    setStyle("");
                } else {
                    if (!item.isDone()) {
                        setStyle("");
                    } else if (item.isDone()) {
                        setStyle("-fx-background-color: lightgreen;");
                    } else if (item.getDueDate() != null) {
                        if ((!item.isDone()) && item.getDueDate().isBefore(LocalDate.now())) {
                            setStyle("-fx-background-color: red;");
                        }
                    } else {
                        setStyle("");
                    }
                }
            }
        });


        //load Data
        this.initData();

        taskView.setItems(this.filteredData);

    }
    //https://stackoverflow.com/questions/50708559/filtering-observablelist-with-checkbox-in-javafx

    /**
     * 
     */
    public void initData() {
        this.toFilterTasks = this.taskList.getTasks();
        this.filteredData = new FilteredList<Task>(toFilterTasks, prad -> true);
    }

    /**
     * @param event
     * @throws IOException
     */
    @FXML
    private void submit(ActionEvent event) throws IOException {

        System.out.println("find" + this.filteredData.size());
		if (attachment.getSelectedToggle() != null) {
			RadioButton selectedRadioButton = (RadioButton) this.attachment.getSelectedToggle();
			String toogleGroupValue = selectedRadioButton.getText();		
			System.out.println(toogleGroupValue);

		}
		if (task_done.getSelectedToggle() != null) {
			RadioButton selectedRadioButton2 = (RadioButton) this.task_done.getSelectedToggle();
			String toogleGroupValue2 = selectedRadioButton2.getText();
			System.out.println(toogleGroupValue2);
		}
		//get selected Kategorien
		ObservableList<String> newCat = categoryList.getSelectionModel().getSelectedItems();
		ObservableList<Category> newCatList = FXCollections.observableArrayList();

		for(String s:newCat) {
			newCatList.add(new Category(s));
		}
		System.out.println(newCat.toString());
		//get selected Contributors
		ObservableList<String> newCon = contributorList.getSelectionModel().getSelectedItems();
		ObservableList<Contributor> newConList = FXCollections.observableArrayList();
		
		for(String s:newCon) {
			newConList.add(new Contributor(s));
		}
		System.out.println(newCon.toString());

		System.out.println(this.from.getValue());
		System.out.println(this.until.getValue());
		System.out.println(this.taskDescriptionField.getText());
		System.out.println(this.detailedTaskDescriptionField.getText());

		}


    /**
     * @param event
     * @throws IOException
     * @noorullah
     */
    @FXML
    private void backtoMain(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getClassLoader().getResource("taskmanagerView/MainWindow.fxml"));
        Scene scene = new Scene(parent);
        Stage windowStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        windowStage.setScene(scene);
        windowStage.show();
    }


}
