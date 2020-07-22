package filter.view;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;
import java.util.function.Predicate;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import task.Task;
import task.Taskmanager;


/**
 * @author Noorullah 
 * This class creates the graphs for weeks
 */
public class WeekChartBuilder extends Application {

    private FilteredList<Task> filteredData;
    private ObservableList<Task> toFilterTasks;
    private Taskmanager taskList;
    private ObservableList<Predicate<Task>> filters = FXCollections.observableArrayList();
    private String filterStartDate = "";
    private String filterEndDate = "";

	@FXML
	private TableView<Task> taskView;
	private final CategoryAxis xAxis = new CategoryAxis();
	private final NumberAxis yAxis = new NumberAxis();
	private final StackedBarChart<String, Number> barChart = new StackedBarChart<>(xAxis, yAxis);
	private final XYChart.Series<String, Number> stack1 = new XYChart.Series<>();
	private final XYChart.Series<String, Number> stack2 = new XYChart.Series<>();
	private int openTaskCount;
	private int closeTaskCount;
	private String toogleGroupValue = "";
	
	/**
	 * @param filteredData,toogleGroupValue,startDate,endDate
	 * Initializing filteredData, startDate, endDate
	 */
	public WeekChartBuilder(FilteredList<Task> filteredData, String toogleGroupValue, String startDate, String endDate) {
		this.filteredData = new FilteredList<>(filteredData);
		this.filterStartDate = startDate;
		this.filterEndDate = endDate;
		taskList = Taskmanager.getInstance();
		this.toogleGroupValue = toogleGroupValue;
	}
	/**
	 * @param stage (window) contains all the objects of a JavaFx application.
	 * This method generates graphs based on criteria selected by the user. 
	 * The graphs are displayed weekly.
	 */
	@Override
	public void start(Stage stage) {
		stage.setTitle("Taskmanagement Chart");

		barChart.setTitle("Tasks");

		xAxis.setLabel("Weeks");
		
		yAxis.setLabel("Tasks");
		stack1.setName("Open Tasks");

		xAxis.setCategories(FXCollections.<String>observableArrayList(Arrays.asList("1", "2", "3", "4",
				"5", "6", "7", "8", "9", "10", "11", "12","13", "14", "15", "16",
				"17", "18", "19", "20", "21", "22", "23", "24","25", "26", "27", "28",
				"29", "30", "31", "32", "33", "34", "35", "36","37", "38", "39", "40",
				"41", "42", "43", "44", "45", "46", "47", "48","49", "50", "51", "52")));

		stack2.setName("Closed Tasks");
		int sWeek = 0;
		int eWeek = 0;
		if(filterStartDate != null && !("").equals(filterStartDate) && filterEndDate!= null && !("").equals(filterEndDate)){
			LocalDate sDate = LocalDate.parse(filterStartDate);
			TemporalField woyS = WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear(); 
		     sWeek = sDate.get(woyS);
		    LocalDate eDate = LocalDate.parse(filterEndDate);
		    TemporalField woyE = WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear(); 
		     eWeek = eDate.get(woyE);
		    showDataOnChart(sWeek, eWeek);
		}else if(filterStartDate != null && !("").equals(filterStartDate) && (filterEndDate == null || ("").equals(filterEndDate))) {
			LocalDate sDate = LocalDate.parse(filterStartDate);
			TemporalField woyS = WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear(); 
		     sWeek = sDate.get(woyS);
		     showDataOnChart(sWeek, 52);
		}else if(filterEndDate != null && !("").equals(filterEndDate) && (filterStartDate == null || ("").equals(filterStartDate))) {
			LocalDate eDate = LocalDate.parse(filterEndDate);
		    TemporalField woyE = WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear(); 
		     eWeek = eDate.get(woyE);
		     showDataOnChart(1, eWeek);
		}else {
			
			showDataOnChart(1, 52);
		}

		Scene scene = new Scene(barChart, 1200, 600);
		barChart.getData().addAll(stack1, stack2);
		stage.setScene(scene);
		stage.show();
	}
	/**
	 * @param countStart,countEnd 
	 * This method shows all open and closed tasks on chart. 
	 */
	public void showDataOnChart(int countStart, int countEnd) {
		for(int count = countStart; count <= countEnd; count++) {
			getStartAndEndDateOfWeek(count);
			stack1.getData().add(new XYChart.Data<String, Number>(count+"", openTaskCount));
			stack2.getData().add(new XYChart.Data<String, Number>(count+"", closeTaskCount));
		}
	}
	
	public void initdata(FilteredList<Task> data) {
		this.filteredData = new FilteredList<Task>(data);
	}
	/**
	 * @param week 
	 * This method determines the start and end of a week. 
	 */
	public void getStartAndEndDateOfWeek(int week) {
		int year = 2020;
		int day = 2;
		int dayE = 7;
		Calendar calendar = Calendar.getInstance();
		calendar.setWeekDate(year, week, day);
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		String formattedStart = format1.format(calendar.getTime());
		Calendar calendare = Calendar.getInstance();
		calendare.setWeekDate(year, week, dayE);
		String formattedEnd = format1.format(calendare.getTime());
		if(toogleGroupValue != null && !("").equals(toogleGroupValue)) {
			if(("closed").equalsIgnoreCase(toogleGroupValue)) {
				getClosetaskCount(formattedStart, formattedEnd);
			}else if(("open").equalsIgnoreCase(toogleGroupValue)) {
				getOpentaskCount(formattedStart, formattedEnd);
			}
		}else {
			getOpentaskCount(formattedStart, formattedEnd);
			getClosetaskCount(formattedStart, formattedEnd);
		}
	}
	
	public void getOpentaskCount(String startDate, String endDate) {
		openTaskCount = 0;
			LocalDate valStart = LocalDate.parse(startDate);
			LocalDate valEnd = LocalDate.parse(endDate);
			filteredData.predicateProperty()
			.bind(Bindings.createObjectBinding(() -> filters.stream().reduce(x -> true, Predicate::and), filters));
				filters.add(FilterBuilder.date_filter(valStart, valEnd));
				filters.add(FilterBuilder.status("open"));
				openTaskCount = filteredData.size();
				initData1();
				filters.clear();
	}
	/**
	 * @param startDate,endDate
	 * This method counts all closed between tasks a period of time.
	 */
	public void getClosetaskCount(String startDate, String endDate) {
		closeTaskCount = 0;
			
			LocalDate valStart = LocalDate.parse(startDate);
			LocalDate valEnd = LocalDate.parse(endDate);
			filteredData.predicateProperty()
			.bind(Bindings.createObjectBinding(() -> filters.stream().reduce(x -> true, Predicate::and), filters));
				filters.add(FilterBuilder.date_filter(valStart, valEnd));
				filters.add(FilterBuilder.status("close"));
				closeTaskCount = filteredData.size();
				initData1();
				filters.clear();
	}
	
	public void initData1() {
		this.toFilterTasks = this.taskList.getTasks();
		this.filteredData = new FilteredList<>(toFilterTasks, prad -> true);
	}
	
}