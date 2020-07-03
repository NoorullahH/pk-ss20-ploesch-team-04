package FilterView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;

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

public class ChartBuilder extends Application {

	private static final String january = "Januar";
	private static final String february = "Februar";
	private static final String march = "März";
	private static final String april = "April";
	private static final String may = "Mai";
	private static final String june = "Juni";
	private static final String july = "Juli";
	private static final String august = "August";
	private static final String september = "September";
	private static final String october = "Oktober";
	private static final String november = "November";
	private static final String december = "Dezember";
	
//	final static String January = "January";
//	final static String February = "February";
//	final static String March = "March";
//	final static String April = "April";
//	final static String May = "May";
//	final static String June = "June";
//	final static String July = "July";
//	final static String August = "August";
//	final static String September = "September";
//	final static String October = "October";
//	final static String November = "November";
//	final static String December = "December";


	private ObservableList<Months> monthList = FXCollections.observableArrayList(Months.JANUAR, Months.FEBRUAR, Months.MÄRZ,
			Months.APRIL, Months.MAI, Months.JUNI, Months.JULI, Months.AUGUST, Months.SEPTEMBER, Months.OKTOBER, Months.NOVEMBER, Months.DEZEMBER);
	
//	ObservableList<Months> monthList = FXCollections.observableArrayList(Months.January, Months.February, Months.March,
//			Months.April, Months.May, Months.June, Months.July, Months.August, Months.September, Months.October, Months.November, Months.December);

    private FilteredList<Task> filteredData;
    private ObservableList<Task> toFilterTasks;
    private Taskmanager taskList;
    private ObservableList<Predicate<Task>> filters = FXCollections.observableArrayList();
    private Months month;
    private String filterStartDate = "";
    private String filterEndDate = "";
    
    private static final Logger LOGGER = Logger.getLogger(ChartBuilder.class.getName());

	@FXML
	private TableView<Task> taskView;
//	private FilterController filter = new FilterController();
//	private Task taskChart = new Task();

	private final CategoryAxis xAxis = new CategoryAxis();
	private final NumberAxis yAxis = new NumberAxis();
	private final StackedBarChart<String, Number> barChart = new StackedBarChart<String, Number>(xAxis, yAxis);
	private final XYChart.Series<String, Number> stack1 = new XYChart.Series<String, Number>();
	private final XYChart.Series<String, Number> stack2 = new XYChart.Series<String, Number>();
	private int openTaskCount = 0;
	private int closeTaskCount = 0;
	private String toogleGroupValue = "";
	
	private List<String> lstFilteredMonth = new ArrayList<String>();
	
	public ChartBuilder(Months month, FilteredList<Task> filteredData, String startDate, String endDate, String toogleGroupValue) {
		this.month = month;
		this.filteredData = new FilteredList<Task>(filteredData);
		this.filterStartDate = startDate;
		this.filterEndDate = endDate;
		this.toogleGroupValue = toogleGroupValue;
		
		openTaskCount = 0;
		closeTaskCount = 0;
		taskList = Taskmanager.getInstance();
		prepareFilterMonthList();

	}
	
	//This function calculate the number of months between start and end date In the format only Month.
	public void prepareFilterMonthList() {
		lstFilteredMonth = new ArrayList<String>();
		try {
	        String date1 = filterStartDate;
	        String date2 = filterEndDate;

	        DateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
	        
	        String dateInputPattern = "yyyy-MM-dd"; 
	        String dateTargetPattern = "MMMM";             
	            
	        Calendar beginCalendar = Calendar.getInstance();
	        Calendar finishCalendar = Calendar.getInstance();

	        try {
	        	if(date1 != null && !date1.equals("")) {
	        		beginCalendar.setTime(formater.parse(date1));
	        	}
	        	if(date2 != null && !date2.equals("")) {
	        		finishCalendar.setTime(formater.parse(date2));
	        	}
	        } catch (ParseException e) {
	        	LOGGER.log(Level.SEVERE, "Exception occured", e);
	        }
	        if(date1 != null && !date1.equals("") && date2 != null && !date2.equals("")) {
		        while (beginCalendar.before(finishCalendar)  || beginCalendar.equals(finishCalendar)) {
		            // add one month to date per loop
		            String date =     formater.format(beginCalendar.getTime()).toUpperCase();
		            java.text.SimpleDateFormat sdf = 
		                    new java.text.SimpleDateFormat( dateInputPattern );
		            
		                java.util.Date date12 = sdf.parse( date );
	
		                sdf.applyPattern( dateTargetPattern );
		            sdf.applyPattern( dateTargetPattern );
		            
		            lstFilteredMonth.add(sdf.format(date12));
		            beginCalendar.add(Calendar.MONTH, 1);
		        }
	        }else if(date1 != null && !date1.equals("") && (date2 != null || !date2.equals(""))) {
	        	String date =     formater.format(beginCalendar.getTime()).toUpperCase();
	            java.text.SimpleDateFormat sdf = 
	                    new java.text.SimpleDateFormat( dateInputPattern );
	            java.util.Date date12 = sdf.parse( date );
	            
	            sdf.applyPattern( dateTargetPattern );
	            sdf.applyPattern( dateTargetPattern );
	            lstFilteredMonth.add(sdf.format(date12));
	        }
	        
	        
		}catch(Exception e) {
			LOGGER.log(Level.SEVERE, "Exception occured (Filter Month List)", e);
		}
		

	}
	
	@Override
	public void start(Stage stage) {
		stage.setTitle("Taskmanagement Chart");
		if(month != null) {
			barChart.setTitle("Tasks of the month "+month);
		}
		//If user do not select month from combo then it shows Only Task
		else if(this.filterStartDate != null && !this.filterStartDate.equals("") && this.filterEndDate != null && !this.filterEndDate.equals("")){
			barChart.setTitle("Tasks");
		}
		//If user select only start date then it shows Only start date
		else if(this.filterStartDate != null && !this.filterStartDate.equals("")){
			barChart.setTitle("Tasks of "+this.filterStartDate);
		}
		
		xAxis.setLabel("Months");
		xAxis.setCategories(FXCollections.<String>observableArrayList(Arrays.asList(january, february, march, april,
				may, june, july, august, september, october, november, december)));
		
		yAxis.setLabel("Value");
		stack1.setName("Open Tasks");

		// getTableView().getItems().size()
		//taskView.getItems().size()
		//Make Open task Bars with Open task count
		for(int count = 0; count < monthList.size(); count++) {
			Months month = monthList.get(count);
			openTaskCount = 0;
			if(lstFilteredMonth != null && lstFilteredMonth.size() > 0) {
				for(int i = 0; i < lstFilteredMonth.size(); i++) {
					String filterMonth = lstFilteredMonth.get(i);
					if(month.toString().contentEquals(filterMonth)) {
						getOpentaskCount(month.toString());
						if(toogleGroupValue != null && !toogleGroupValue.equals("")) {
							if(toogleGroupValue.equalsIgnoreCase("open")) {
								openTaskCount = openTaskCount;
							}else {
								openTaskCount = 0;
							}
						}
						stack1.getData().add(new XYChart.Data<String, Number>(month.toString(), openTaskCount));
					}
					else {
						stack1.getData().add(new XYChart.Data<String, Number>(month.toString(), 0));
					}
				}
			}else {
				
				getOpentaskCount(month.toString());
				if(toogleGroupValue != null && !toogleGroupValue.equals("")) {
					if(toogleGroupValue.equalsIgnoreCase("open")) {
						openTaskCount = openTaskCount;
					}else {
						openTaskCount = 0;
					}
				}
				stack1.getData().add(new XYChart.Data<String, Number>(month.toString(), openTaskCount));
			}
		}


		stack2.setName("Closed Tasks");
		//Make Closed task Bars with Closed task count
		for(int count = 0; count < monthList.size(); count++) {
			Months month = monthList.get(count);
			closeTaskCount = 0;
			
			if(lstFilteredMonth != null && lstFilteredMonth.size() > 0) {
				for(int i = 0; i < lstFilteredMonth.size(); i++) {
					String filterMonth = lstFilteredMonth.get(i);
					if(month.toString().contentEquals(filterMonth)) {
						getClosetaskCount(month.toString());
						if(toogleGroupValue != null && !toogleGroupValue.equals("")) {
							if(toogleGroupValue.equalsIgnoreCase("closed")) {
								closeTaskCount = closeTaskCount;
							}else {
								closeTaskCount = 0;
							}
						}
						stack2.getData().add(new XYChart.Data<String, Number>(month.toString(), closeTaskCount));
					}
					else {
						stack2.getData().add(new XYChart.Data<String, Number>(month.toString(), 0));
					}
				}
			}else {
				getClosetaskCount(month.toString());
				if(toogleGroupValue != null && !toogleGroupValue.equals("")) {
					if(toogleGroupValue.equalsIgnoreCase("closed")) {
						closeTaskCount = closeTaskCount;
					}else {
						closeTaskCount = 0;
					}
				}
				stack2.getData().add(new XYChart.Data<String, Number>(month.toString(), closeTaskCount));
			}
		}

		Scene scene = new Scene(barChart, 800, 600);
		barChart.getData().addAll(stack1, stack2);
		stage.setScene(scene);
		stage.show();
	
	}
	
	public void initdata(FilteredList<Task> data) {
		this.filteredData = new FilteredList<Task>(data);
	}
	
	public void getOpentaskCount(String month) {
		if(this.filterStartDate != null && !this.filterStartDate.equals("") && (this.filterEndDate == null || this.filterEndDate.equals(""))) {
			LocalDate valStart = LocalDate.parse(filterStartDate);
			//applying filter for open task and set list size on openTaskCount variable
			filteredData.predicateProperty()
			.bind(Bindings.createObjectBinding(() -> filters.stream().reduce(x -> true, Predicate::and), filters));
				filters.add(FilterBuilder.date_filter_day(valStart));
				filters.add(FilterBuilder.status("open"));
				openTaskCount = filteredData.size();
				initData();
				filters.clear();
		}
		//If user select month or both dates
		else if(month != null && !month.equals("")){
			String starAndEndDate = getStartAndEndDateOfMonth(month);
			String[] date = starAndEndDate.split(",");
			String startDate = date[0];
			String endDate = date[1];
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
	}
	
	public void getClosetaskCount(String month) {
		//If user select only From date
		if(this.filterStartDate != null && !this.filterStartDate.equals("") && (this.filterEndDate == null || this.filterEndDate.equals(""))) {
			LocalDate valStart = LocalDate.parse(filterStartDate);
			filteredData.predicateProperty()
			.bind(Bindings.createObjectBinding(() -> filters.stream().reduce(x -> true, Predicate::and), filters));
				filters.add(FilterBuilder.date_filter_day(valStart));
				filters.add(FilterBuilder.status("close"));
				closeTaskCount = filteredData.size();
				initData();
				filters.clear();
		}
		//If user select month or both dates
		else if(month != null && !month.equals("")){
			String starAndEndDate = getStartAndEndDateOfMonth(month);
			String[] date = starAndEndDate.split(",");
			String startDate = date[0];
			String endDate = date[1];
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
			
	}
	
	public void initData() {
		this.toFilterTasks = this.taskList.getTasks();
		this.filteredData = new FilteredList<Task>(toFilterTasks, prad -> true);
	}
	
	
	public String getStartAndEndDateOfMonth(String month) {
		String startDate = "";
		String endDate = "";
		String year = "2020";
		if(month.toString().equals("January") || month.toString().equals("januar")) {
			startDate = year+"-01-01";
			
			endDate = year+"-01-31";
		}else if(month.toString().equalsIgnoreCase("February") || month.toString().equalsIgnoreCase("februar")) {
			startDate = year+"-02-01";
			endDate = year+"-02-28";
		}else if(month.toString().equalsIgnoreCase("March") || month.toString().equalsIgnoreCase("märz")) {
			startDate = year+"-03-01";
			
			endDate = year+"-03-31";
		}else if(month.toString().equalsIgnoreCase("April")) {
			startDate = year+"-04-01";
			
			endDate = year+"-04-30";
			LocalDate valEnd = LocalDate.parse(endDate);
		}else if(month.toString().equalsIgnoreCase("May") || month.toString().equalsIgnoreCase("mai")) {
			startDate = year+"-05-01";
			
			endDate = year+"-05-31";
		}else if(month.toString().equalsIgnoreCase("June") || month.toString().equalsIgnoreCase("juni")) {
			startDate = year+"-06-01";
			
			endDate = year+"-06-30";
		}else if(month.toString().equalsIgnoreCase("July") || month.toString().equalsIgnoreCase("juli")) {
			startDate = year+"-07-01";
			
			endDate = year+"-07-31";
		}else if(month.toString().equalsIgnoreCase("August") || month.toString().equalsIgnoreCase("august")) {
			startDate = year+"-08-01";
			
			endDate = year+"-08-31";
		}else if(month.toString().equalsIgnoreCase("September") || month.toString().equalsIgnoreCase("september")) {
			startDate = year+"-09-01";
			
			endDate = year+"-09-30";
		}else if(month.toString().equalsIgnoreCase("October") || month.toString().equalsIgnoreCase("oktober")) {
			startDate = year+"-10-01";
			
			endDate = year+"-10-31";
		}else if(month.toString().equalsIgnoreCase("November") || month.toString().equalsIgnoreCase("november")) {
			startDate = year+"-11-01";
			
			endDate = year+"-11-30";
		}else if(month.toString().equalsIgnoreCase("December") || month.toString().equalsIgnoreCase("dezember")) {
			startDate = year+"-12-01";
			
			endDate = year+"-12-31";
		}
		
		return startDate +","+endDate;
	}
	
	public void start2(Stage stage) {
		stage.setTitle("Taskmanagement Chart");
		if(month != null) {
			barChart.setTitle("Tasks of the month "+month);
		}
		//If user do not select month from combo then it shows Only Task
		else if(this.filterStartDate != null && !this.filterStartDate.equals("") && this.filterEndDate != null && !this.filterEndDate.equals("")){
			barChart.setTitle("Tasks");
		}
		//If user select only start date then it shows Only start date
		else if(this.filterStartDate != null && !this.filterStartDate.equals("")){
			barChart.setTitle("Tasks of "+this.filterStartDate);
		}
		
		xAxis.setLabel("Months");
		xAxis.setCategories(FXCollections.<String>observableArrayList(Arrays.asList(january, february, march, april,
				may, june, july, august, september, october, november, december)));
		
		yAxis.setLabel("Value");
		stack1.setName("Open Tasks");

		// getTableView().getItems().size()
		//taskView.getItems().size()
		//Make Open task Bars with Open task count
		for(int count = 0; count < monthList.size(); count++) {
			Months month = monthList.get(count);
			openTaskCount = 0;
			if(lstFilteredMonth != null && lstFilteredMonth.size() > 0) {
				for(int i = 0; i < lstFilteredMonth.size(); i++) {
					String filterMonth = lstFilteredMonth.get(i);
					if(month.toString().contentEquals(filterMonth)) {
						getOpentaskCount(month.toString());
						if(toogleGroupValue != null && !toogleGroupValue.equals("")) {
							if(toogleGroupValue.equalsIgnoreCase("open")) {
								openTaskCount = openTaskCount;
							}else {
								openTaskCount = 0;
							}
						}
						stack1.getData().add(new XYChart.Data<String, Number>(month.toString(), openTaskCount));
					}
					else {
						stack1.getData().add(new XYChart.Data<String, Number>(month.toString(), 0));
					}
				}
			}else {
				
				getOpentaskCount(month.toString());
				if(toogleGroupValue != null && !toogleGroupValue.equals("")) {
					if(toogleGroupValue.equalsIgnoreCase("open")) {
						openTaskCount = openTaskCount;
					}else {
						openTaskCount = 0;
					}
				}
				stack1.getData().add(new XYChart.Data<String, Number>(month.toString(), openTaskCount));
			}
		}


		stack2.setName("Closed Tasks");
		//Make Closed task Bars with Closed task count
		for(int count = 0; count < monthList.size(); count++) {
			Months month = monthList.get(count);
			closeTaskCount = 0;
			
			if(lstFilteredMonth != null && lstFilteredMonth.size() > 0) {
				for(int i = 0; i < lstFilteredMonth.size(); i++) {
					String filterMonth = lstFilteredMonth.get(i);
					if(month.toString().contentEquals(filterMonth)) {
						getClosetaskCount(month.toString());
						if(toogleGroupValue != null && !toogleGroupValue.equals("")) {
							if(toogleGroupValue.equalsIgnoreCase("closed")) {
								closeTaskCount = closeTaskCount;
							}else {
								closeTaskCount = 0;
							}
						}
						stack2.getData().add(new XYChart.Data<String, Number>(month.toString(), closeTaskCount));
					}
					else {
						stack2.getData().add(new XYChart.Data<String, Number>(month.toString(), 0));
					}
				}
			}else {
				getClosetaskCount(month.toString());
				if(toogleGroupValue != null && !toogleGroupValue.equals("")) {
					if(toogleGroupValue.equalsIgnoreCase("closed")) {
						closeTaskCount = closeTaskCount;
					}else {
						closeTaskCount = 0;
					}
				}
				stack2.getData().add(new XYChart.Data<String, Number>(month.toString(), closeTaskCount));
			}
		}

		Scene scene = new Scene(barChart, 800, 600);
		barChart.getData().addAll(stack1, stack2);
		stage.setScene(scene);
		stage.show();
	
	}
	
}