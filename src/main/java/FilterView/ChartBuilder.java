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

	final static String January = "Januar";
	final static String February = "Februar";
	final static String March = "März";
	final static String April = "April";
	final static String May = "Mai";
	final static String June = "Juni";
	final static String July = "Juli";
	final static String August = "August";
	final static String September = "September";
	final static String October = "Oktober";
	final static String November = "November";
	final static String December = "Dezember";
	
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


	ObservableList<Months> monthList = FXCollections.observableArrayList(Months.Januar, Months.Februar, Months.März,
			Months.April, Months.Mai, Months.Juni, Months.Juli, Months.August, Months.September, Months.Oktober, Months.November, Months.Dezember);
	
//	ObservableList<Months> monthList = FXCollections.observableArrayList(Months.January, Months.February, Months.March,
//			Months.April, Months.May, Months.June, Months.July, Months.August, Months.September, Months.October, Months.November, Months.December);

    FilteredList<Task> filteredData;
    ObservableList<Task> toFilterTasks;
    Taskmanager taskList;
    ObservableList<Predicate<Task>> filters = FXCollections.observableArrayList();
    Months month;
    String filterStartDate = "";
    String filterEndDate = "";

	 @FXML
	private TableView<Task> taskView;
	FilterController filterC = new FilterController();
	Task taskChart = new Task();

	final CategoryAxis xAxis = new CategoryAxis();
	final NumberAxis yAxis = new NumberAxis();
	final StackedBarChart<String, Number> barChart = new StackedBarChart<String, Number>(xAxis, yAxis);
	final XYChart.Series<String, Number> stack1 = new XYChart.Series<String, Number>();
	final XYChart.Series<String, Number> stack2 = new XYChart.Series<String, Number>();
	int openTaskCount = 0;
	int closeTaskCount = 0;
	String toogleGroupValue = "";
	
	List<String> lstFilteredMonth = new ArrayList<String>();
	public ChartBuilder(Months month, FilteredList<Task> filteredData, String startDate, String endDate, String toogleGroupValue) {
		this.month = month;
		this.filteredData = filteredData;
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
	            e.printStackTrace();
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
			e.printStackTrace();
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
		xAxis.setCategories(FXCollections.<String>observableArrayList(Arrays.asList(January, February, March, April,
				May, June, July, August, September, October, November, December)));
		
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
		this.filteredData = data;
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
	
}