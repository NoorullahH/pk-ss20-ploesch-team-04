package filter.view;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import category.Category;
import contributor.Contributor;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
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
import task.Task;
import task.Taskmanager;
import taskmanager.view.CategoryController;
import taskmanager.view.ContributorController;
import weekday.Months;

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
	private Button chart;
	@FXML
	private Button resetFilters;
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
	private RadioButton closed = new RadioButton();
	@FXML
	private RadioButton yes = new RadioButton();
	@FXML
	private RadioButton no = new RadioButton();
	@FXML
	private ToggleGroup attachment = new ToggleGroup();
	@FXML
	private ToggleGroup task_done = new ToggleGroup();
	private Taskmanager taskList;
	private FilteredList<Task> filteredData;
	private ObservableList<Predicate<Task>> filters = FXCollections.observableArrayList();
	private static final Logger LOGGER = Logger.getLogger(FilterController.class.getName());
	private static final String STYLE = "-fx-font-size: 15 ;";
	@FXML
	private ComboBox cmbMonth;
	private ObservableList<Months> monthList = FXCollections.observableArrayList(Months.JANUARY, Months.FEBRUARY,
			Months.MARCH, Months.APRIL, Months.MAY, Months.JUNE, Months.JULY, Months.AUGUST, Months.SEPTEMBER,
			Months.OCTOBER, Months.NOVEMBER, Months.DECEMBER);

	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		addMonthsInCombo();

		// initialize taskList
		taskList = Taskmanager.getInstance();
		// set up the columns in the table
		descriptionColumn.setCellValueFactory(new PropertyValueFactory<Task, String>("taskDescription"));
		detailedDescriptionColumn
				.setCellValueFactory(new PropertyValueFactory<Task, String>("detailedTaskDescription"));
		contributorsColumn.setCellValueFactory(new PropertyValueFactory<Task, String>("contributors"));
		categoriesColumn.setCellValueFactory(new PropertyValueFactory<Task, String>("categories"));
		dueDateColumn.setCellValueFactory(new PropertyValueFactory<Task, LocalDate>("dueDate"));
		attachmentsColumn.setCellValueFactory(new PropertyValueFactory<Task, List<String>>("attachments"));
		descriptionColumn.setStyle(STYLE);
		detailedDescriptionColumn.setStyle(STYLE);
		contributorsColumn.setStyle(STYLE);
		categoriesColumn.setStyle(STYLE);
		dueDateColumn.setStyle(STYLE);
		attachmentsColumn.setStyle(STYLE);
		doneCheckBoxColumn.setStyle(STYLE);
		doneCheckBoxColumn.setCellValueFactory(new PropertyValueFactory<Task, Boolean>("done"));
		doneCheckBoxColumn.setCellFactory(CheckBoxTableCell.forTableColumn(doneCheckBoxColumn));
		doneCheckBoxColumn.setEditable(true);

		// Initialize ContributorListView
		FXMLLoader loaderCon = new FXMLLoader(
				getClass().getClassLoader().getResource("taskmanager/view/ContributorWindow.fxml"));

		try {
			loaderCon.load();
			ContributorController conController = loaderCon.<ContributorController>getController();
			contributorList.setItems(conController.getContributorList());
			contributorList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Exception occured (Contributors)", e);
		}

		contributorList.setStyle("-fx-font-size: 16 ;");

		/**
		 * @author Noorullah
		 * Initialize CategoryListView
		 */ 
		FXMLLoader loaderCat = new FXMLLoader(
				getClass().getClassLoader().getResource("taskmanager/view/CategoryWindow.fxml"));
		try {
			loaderCat.load();
			CategoryController catController = loaderCat.<CategoryController>getController();
			categoryList.setItems(catController.getCategoryList());
			categoryList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Exception occured (Categories)", e);
		}

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

		// load Data
		this.initData();

		taskView.setItems(this.filteredData);

	}

	/**
	 * Adding months In Month Combobox
	 */ 
	private void addMonthsInCombo() {
		for (int count = 0; count < monthList.size(); count++) {
			Months month = monthList.get(count);
			cmbMonth.getItems().add(month);
		}
	}

	/**
	 * 
	 */
	public void initData() {
		ObservableList<Task> toFilterTasks = this.taskList.getTasks();
		this.filteredData = new FilteredList<>(toFilterTasks, prad -> true);
	}

	/**
	 * @param event
	 * @throws IOException
	 */
	@FXML
	private void submit(ActionEvent event){
		// get selected Kategorien
		ObservableList<String> newCat = categoryList.getSelectionModel().getSelectedItems();
		ObservableList<Category> newCatList = FXCollections.observableArrayList();

		for (String s : newCat) {
			newCatList.add(new Category(s));
		}
		System.out.println(newCat.toString());

		// get selected Contributors
		ObservableList<String> newCon = contributorList.getSelectionModel().getSelectedItems();
		ObservableList<Contributor> newConList = FXCollections.observableArrayList();

		for (String s : newCon) {
			newConList.add(new Contributor(s));
		}
		System.out.println(newCon.toString());
		System.out.println(this.from.getValue());
		System.out.println(this.until.getValue());
		System.out.println(this.taskDescriptionField.getText());
		System.out.println(this.detailedTaskDescriptionField.getText());

		filteredData.predicateProperty()
				.bind(Bindings.createObjectBinding(() -> filters.stream().reduce(x -> true, Predicate::and), filters));
		filters.add(FilterBuilder.description(this.taskDescriptionField.getText()));
		filters.add(FilterBuilder.detail_desc(this.detailedTaskDescriptionField.getText()));
		filters.add(FilterBuilder.date_filter(this.from.getValue(), this.until.getValue()));
		filters.add(FilterBuilder.category(newCatList));
		filters.add(FilterBuilder.contributes(newConList));
		if (attachment.getSelectedToggle() != null) {
			RadioButton selectedRadioButton = (RadioButton) this.attachment.getSelectedToggle();
			String toogleGroupValue = selectedRadioButton.getText();
			filters.add(FilterBuilder.attachment(toogleGroupValue));
		}
		if (task_done.getSelectedToggle() != null) {
			RadioButton selectedRadioButton2 = (RadioButton) this.task_done.getSelectedToggle();
			String toogleGroupValue2 = selectedRadioButton2.getText();
			filters.add(FilterBuilder.status(toogleGroupValue2));
		}

	}

	/**
	 * @param event
	 * This method stores user input and passes 
	 * this information to chartBuilder class where graphs are created.
	 */ 
	@FXML
	private void chart(ActionEvent event) {
		// Get selected month from combobox
		Months month = (Months) cmbMonth.getSelectionModel().getSelectedItem();

		DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		// Format From date from Local date to String
		String startdate = "";
		if (this.from.getValue() != null) {
			startdate = (this.from.getValue()).format(formatter1);
		}

		// Format Until date from Local date to String
		String endDate = "";
		if (this.until.getValue() != null) {
			endDate = (this.until.getValue()).format(formatter1);
		}

		String toogleGroupValue = "";
		if (task_done.getSelectedToggle() != null) {
			RadioButton selectedRadioButton = (RadioButton) this.task_done.getSelectedToggle();
			toogleGroupValue = selectedRadioButton.getText();
		}

		// Calling chartBuilder Class Constructor with param
		ChartBuilder newChart = new ChartBuilder(month, filteredData, startdate, endDate, toogleGroupValue);
		Stage stage = new Stage();
		newChart.start(stage);
	}

	/**
	 * @param event
	 * This method stores user input including open or closed tasks and passes 
	 * this information to chartBuilder class where graphs are created.
	 */ 
	@FXML
	public void openWeekChart(ActionEvent event) {
		String toogleGroupValue = "";
		if (task_done.getSelectedToggle() != null) {
			RadioButton selectedRadioButton = (RadioButton) this.task_done.getSelectedToggle();
			toogleGroupValue = selectedRadioButton.getText();
		}
		DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		// Format From date from Local date to String
		String startdate = "";
		if (this.from.getValue() != null) {
			startdate = (this.from.getValue()).format(formatter2);
		}

		// Format Until date from Local date to String
		String endDate = "";
		if (this.until.getValue() != null) {
			endDate = (this.until.getValue()).format(formatter2);
		}
		// Calling WeekChartBuilder Class Constructor with param
		WeekChartBuilder newChart = new WeekChartBuilder(filteredData, toogleGroupValue, startdate, endDate);
		Stage stage = new Stage();
		newChart.start(stage);
	}

	/**
	 * @param event
	 * @throws IOException
	 * @noorullah
	 */
	@FXML
	private void backtoMain(ActionEvent event) throws IOException {
		Parent parent = FXMLLoader.load(getClass().getClassLoader().getResource("taskmanager/view/MainWindow.fxml"));
		Scene scene = new Scene(parent);
		Stage windowStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		windowStage.setScene(scene);
		windowStage.show();
	}

	@FXML
	private void removeAllFilters(ActionEvent event) {
		filters.clear();
		this.cmbMonth.getItems().clear();
		addMonthsInCombo();
		contributorList.getSelectionModel().clearSelection();
		categoryList.getSelectionModel().clearSelection();
		attachment.selectToggle(null);
		task_done.selectToggle(null);
		cmbMonth.setPromptText("Month");
		this.from.setValue(null);
		this.until.setValue(null);
	}

	/**
	 * This method sets start and end dates of a month. 
	 */
	@FXML
	public void performActionOnMonthCombo() {
		String year = "2020";
		if (cmbMonth.getSelectionModel().getSelectedItem() != null) {
			Months month = (Months) cmbMonth.getSelectionModel().getSelectedItem();
			if (("JANUARY").equalsIgnoreCase(month.toString())) {
				LocalDate valStart1 = LocalDate.parse(year + "-01-01");
				this.from.setValue(valStart1);
				LocalDate valEnd1 = LocalDate.parse(year + "-01-31");
				this.until.setValue(valEnd1);
				
			} else if (("FEBRUARY").equalsIgnoreCase(month.toString())) {
				LocalDate valStart2 = LocalDate.parse(year + "-02-01");
				this.from.setValue(valStart2);
				LocalDate valEnd2 = LocalDate.parse(year + "-02-28");
				this.until.setValue(valEnd2);
				
			} else if (("MARCH").equalsIgnoreCase(month.toString())) {
				LocalDate valStart3 = LocalDate.parse(year + "-03-01");
				this.from.setValue(valStart3);
				LocalDate valEnd3 = LocalDate.parse(year + "-03-31");
				this.until.setValue(valEnd3);
				
			} else if (("APRIL").equalsIgnoreCase(month.toString())) {
				LocalDate valStart4 = LocalDate.parse(year + "-04-01");
				this.from.setValue(valStart4);
				LocalDate valEnd4 = LocalDate.parse(year + "-04-30");
				this.until.setValue(valEnd4);
				
			} else if (("MAY").equalsIgnoreCase(month.toString())) {
				LocalDate valStart5 = LocalDate.parse(year + "-05-01");
				this.from.setValue(valStart5);
				LocalDate valEnd5 = LocalDate.parse(year + "-05-31");
				this.until.setValue(valEnd5);
				
			} else if (("JUNE").equalsIgnoreCase(month.toString())) {
				LocalDate valStart6 = LocalDate.parse(year + "-06-01");
				this.from.setValue(valStart6);
				LocalDate valEnd6 = LocalDate.parse(year + "-06-30");
				this.until.setValue(valEnd6);
				
			} else if (("JULY").equalsIgnoreCase(month.toString())) {
				LocalDate valStart7 = LocalDate.parse(year + "-07-01");
				this.from.setValue(valStart7);
				LocalDate valEnd7 = LocalDate.parse(year + "-07-31");
				this.until.setValue(valEnd7);
				
			} else if (("AUGUST").equalsIgnoreCase(month.toString())) {
				LocalDate valStart8 = LocalDate.parse(year + "-08-01");
				this.from.setValue(valStart8);
				LocalDate valEnd8 = LocalDate.parse(year + "-08-31");
				this.until.setValue(valEnd8);
				
			} else if (("SEPTEMBER").equalsIgnoreCase(month.toString())) {
				LocalDate valStart9 = LocalDate.parse(year + "-09-01");
				this.from.setValue(valStart9);
				LocalDate valEnd9 = LocalDate.parse(year + "-09-30");
				this.until.setValue(valEnd9);
				
			} else if (("OCTOBER").equalsIgnoreCase(month.toString())) {
				LocalDate valStart10 = LocalDate.parse(year + "-10-01");
				this.from.setValue(valStart10);
				LocalDate valEnd10 = LocalDate.parse(year + "-10-31");
				this.until.setValue(valEnd10);
				
			} else if (("NOVEMBER").equalsIgnoreCase(month.toString())) {
				LocalDate valStart11 = LocalDate.parse(year + "-11-01");
				this.from.setValue(valStart11);
				LocalDate valEnd11 = LocalDate.parse(year + "-11-30");
				this.until.setValue(valEnd11);
				
			} else if (("DECEMBER").equalsIgnoreCase(month.toString())) {
				LocalDate valStart12 = LocalDate.parse(year + "-12-01");
				this.from.setValue(valStart12);
				LocalDate valEnd12 = LocalDate.parse(year + "-12-31");
				this.until.setValue(valEnd12);
			}
		}
	}

}
