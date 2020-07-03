package FilterView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
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
import weekday.Months;

public class WeekChartBuilder extends Application {

    private FilteredList<Task> filteredData;
    private ObservableList<Task> toFilterTasks;
    private Taskmanager taskList;
    private ObservableList<Predicate<Task>> filters = FXCollections.observableArrayList();
    private Months month;
    private String filterStartDate = "";
    private String filterEndDate = "";

	@FXML
	private TableView<Task> taskView;
	FilterController filterC = new FilterController();
	Task taskChart = new Task();

	private final CategoryAxis xAxis = new CategoryAxis();
	private final NumberAxis yAxis = new NumberAxis();
	private final StackedBarChart<String, Number> barChart = new StackedBarChart<String, Number>(xAxis, yAxis);
	private final XYChart.Series<String, Number> stack1 = new XYChart.Series<String, Number>();
	private final XYChart.Series<String, Number> stack2 = new XYChart.Series<String, Number>();
	private int openTaskCount = 0;
	private int closeTaskCount = 0;
	private String toogleGroupValue = "";
	
	//Initializing filteredData, startDate, endDate
	public WeekChartBuilder(FilteredList<Task> filteredData, String toogleGroupValue, String startDate, String endDate) {
		this.filteredData = new FilteredList<Task>(filteredData);
		this.filterStartDate = startDate;
		this.filterEndDate = endDate;
		taskList = Taskmanager.getInstance();
		this.toogleGroupValue = toogleGroupValue;
	}
	
	@Override
	public void start(Stage stage) {
		stage.setTitle("Taskmanagement Chart");

		barChart.setTitle("Tasks");

		xAxis.setLabel("Weeks");
		
		yAxis.setLabel("Value");
		stack1.setName("Open Tasks");

		xAxis.setCategories(FXCollections.<String>observableArrayList(Arrays.asList("1", "2", "3", "4",
				"5", "6", "7", "8", "9", "10", "11", "12","13", "14", "15", "16",
				"17", "18", "19", "20", "21", "22", "23", "24","25", "26", "27", "28",
				"29", "30", "31", "32", "33", "34", "35", "36","37", "38", "39", "40",
				"41", "42", "43", "44", "45", "46", "47", "48","49", "50", "51", "52")));

		stack2.setName("Closed Tasks");
		int sWeek = 0;
		int eWeek = 0;
		if(filterStartDate != null && !filterStartDate.equals("") && filterEndDate!= null && !filterEndDate.equals("")){
			LocalDate sDate = LocalDate.parse(filterStartDate);
			TemporalField woyS = WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear(); 
		     sWeek = sDate.get(woyS);
//		    System.out.println("sWeek:::::::::::: "+sWeek);
		    
		    LocalDate eDate = LocalDate.parse(filterEndDate);
		    TemporalField woyE = WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear(); 
		     eWeek = eDate.get(woyE);
//		    System.out.println("eWeek:::::::::::: "+eWeek);
		    showDataOnChart(sWeek, eWeek);
		}else if(filterStartDate != null && !filterStartDate.equals("") && (filterEndDate == null || filterEndDate.equals(""))) {
			LocalDate sDate = LocalDate.parse(filterStartDate);
			TemporalField woyS = WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear(); 
		     sWeek = sDate.get(woyS);
		     showDataOnChart(sWeek, 52);
		}else if(filterEndDate != null && !filterEndDate.equals("") && (filterStartDate == null || filterEndDate.equals(""))) {
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
//        System.out.println(this.filteredData.size());
	
	}
	
	public void showDataOnChart(int countStart, int countEnd) {
		for(int count = countStart; count <= countEnd; count++) {
			getStartAndEndDateOfWeek(count);
//			System.out.println("week::::::::::: "+count);
//			System.out.println("openTaskCount::::::::::: "+openTaskCount);
//			System.out.println("closeTaskCount::::::::::: "+closeTaskCount);
			stack1.getData().add(new XYChart.Data<String, Number>(count+"", openTaskCount));
			stack2.getData().add(new XYChart.Data<String, Number>(count+"", closeTaskCount));
		}
	}
	
	public void initdata(FilteredList<Task> data) {
		this.filteredData = new FilteredList<Task>(data);
	}
	
	public void getStartAndEndDateOfWeek(int week) {
		int year = 2020;
		int day = 2;
		int dayE = 7;
		Calendar calendar = Calendar.getInstance();
		calendar.setWeekDate(year, week, day);
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		String formattedStart = format1.format(calendar.getTime());
//		System.out.println("formattedStart::::::::::::::: "+formattedStart);
		Calendar calendare = Calendar.getInstance();
		calendare.setWeekDate(year, week, dayE);
		String formattedEnd = format1.format(calendare.getTime());
//		System.out.println("formattedEnd::::END::::::::::: "+formattedEnd);
		if(toogleGroupValue != null && !toogleGroupValue.equals("")) {
			if(toogleGroupValue.equalsIgnoreCase("closed")) {
				getClosetaskCount(formattedStart, formattedEnd);
			}else if(toogleGroupValue.equalsIgnoreCase("open")) {
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
				initData();
				filters.clear();
	}
	
	public void getClosetaskCount(String startDate, String endDate) {
		closeTaskCount = 0;
			
			LocalDate valStart = LocalDate.parse(startDate);
			LocalDate valEnd = LocalDate.parse(endDate);
			filteredData.predicateProperty()
			.bind(Bindings.createObjectBinding(() -> filters.stream().reduce(x -> true, Predicate::and), filters));
				filters.add(FilterBuilder.date_filter(valStart, valEnd));
				filters.add(FilterBuilder.status("close"));
				closeTaskCount = filteredData.size();
				initData();
				filters.clear();
	}
	
	public void initData() {
		this.toFilterTasks = this.taskList.getTasks();
		this.filteredData = new FilteredList<Task>(toFilterTasks, prad -> true);
	}
	
}