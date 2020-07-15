package filter.view;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
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

	private static final String JANUARY = "JANUARY";
	private static final String FEBRUARY = "FEBRUARY";
	private static final String MARCH = "MARCH";
	private static final String APRIL = "APRIL";
	private static final String MAY = "MAY";
	private static final String JUNE = "JUNE";
	private static final String JULY = "JULY";
	private static final String AUGUST = "AUGUST";
	private static final String SEPTEMBER = "SEPTEMBER";
	private static final String OCTOBER = "OCTOBER";
	private static final String NOVEMBER = "NOVEMBER";
	private static final String DECEMBER = "DECEMBER";

	private ObservableList<Months> monthList = FXCollections.observableArrayList(Months.JANUARY, Months.FEBRUARY,
			Months.MARCH, Months.APRIL, Months.MAY, Months.JUNE, Months.JULY, Months.AUGUST, Months.SEPTEMBER,
			Months.OCTOBER, Months.NOVEMBER, Months.DECEMBER);

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
	private final CategoryAxis xAxis = new CategoryAxis();
	private final NumberAxis yAxis = new NumberAxis();
	private final StackedBarChart<String, Number> barChart = new StackedBarChart<>(xAxis, yAxis);
	private final XYChart.Series<String, Number> stack1 = new XYChart.Series<>();
	private final XYChart.Series<String, Number> stack2 = new XYChart.Series<>();
	private int openTaskCount;
	private int closeTaskCount;
	private String toogleGroupValue = "";

	private List<String> lstFilteredMonth = new ArrayList<>();

	public ChartBuilder(Months month, FilteredList<Task> filteredData, String startDate, String endDate,
			String toogleGroupValue) {
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

	// This function calculate the number of months between start and end date In
	// the format only Month.
	public void prepareFilterMonthList() {
		lstFilteredMonth = new ArrayList<>();
		try {
			String date1 = filterStartDate;
			String date2 = filterEndDate;

			DateFormat formater = new SimpleDateFormat("yyyy-MM-dd");

			String dateInputPattern = "yyyy-MM-dd";
			String dateTargetPattern = "MMMM";

			Calendar beginCalendar = Calendar.getInstance();
			Calendar finishCalendar = Calendar.getInstance();

			try {
				if (date1 != null && !date1.equals("")) {
					beginCalendar.setTime(formater.parse(date1));
				}
				if (date2 != null && !date2.equals("")) {
					finishCalendar.setTime(formater.parse(date2));
				}
			} catch (ParseException e) {
				LOGGER.log(Level.SEVERE, "Exception occured", e);
			}
			if (date1 != null && !date1.equals("") && date2 != null && !date2.equals("")) {
				while (beginCalendar.before(finishCalendar) || beginCalendar.equals(finishCalendar)) {
					// add one month to date per loop
					String date = formater.format(beginCalendar.getTime()).toUpperCase();
					SimpleDateFormat sdf = new SimpleDateFormat(dateInputPattern, Locale.ENGLISH);

					java.util.Date date12 = sdf.parse(date);

					sdf.applyPattern(dateTargetPattern);
					sdf.applyPattern(dateTargetPattern);

					lstFilteredMonth.add(sdf.format(date12).toUpperCase());
					beginCalendar.add(Calendar.MONTH, 1);
				}
			} else if (date1 != null && !date1.equals("") && (date2 != null && !date2.equals(""))) {
				String date = formater.format(beginCalendar.getTime()).toUpperCase();
				java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(dateInputPattern, Locale.ENGLISH);
				java.util.Date date12 = sdf.parse(date);

				sdf.applyPattern(dateTargetPattern);
				sdf.applyPattern(dateTargetPattern);
				lstFilteredMonth.add(sdf.format(date12).toUpperCase());
			}

		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Exception occured (Filter Month List)", e);
		}

	}

	@Override
	public void start(Stage stage) {
		stage.setTitle("Taskmanagement Chart");
		if (month != null) {
			barChart.setTitle("Tasks of the month " + month);
		}
		// If user do not select month from combo then it shows Only Task
		else if (this.filterStartDate != null && !this.filterStartDate.equals("") && this.filterEndDate != null
				&& !this.filterEndDate.equals("")) {
			barChart.setTitle("Tasks");
		}
		// If user select only start date then it shows Only start date
		else if (this.filterStartDate != null && !this.filterStartDate.equals("")) {
			barChart.setTitle("Tasks of " + this.filterStartDate);
		}

		xAxis.setLabel("Months");
		xAxis.setCategories(FXCollections.<String>observableArrayList(Arrays.asList(JANUARY, FEBRUARY, MARCH, APRIL,
				MAY, JUNE, JULY, AUGUST, SEPTEMBER, OCTOBER, NOVEMBER, DECEMBER)));

		yAxis.setLabel("Tasks");
		stack1.setName("Open Tasks");

		// Make Open task Bars with Open task count
		for (int count = 0; count < monthList.size(); count++) {
			Months months = monthList.get(count);
			openTaskCount = 0;
			if (lstFilteredMonth != null && lstFilteredMonth.size() >= 1) {
				for (int i = 0; i < lstFilteredMonth.size(); i++) {
					String filterMonth = lstFilteredMonth.get(i);
					if (months.toString().contentEquals(filterMonth)) {
						getOpentaskCount(months.toString());
						if (toogleGroupValue != null && !toogleGroupValue.equals("")) {
							if (toogleGroupValue.equalsIgnoreCase("open")) {
							} else {
								openTaskCount = 0;
							}
						}
						stack1.getData().add(new XYChart.Data<String, Number>(months.toString(), openTaskCount));
					} else {
						stack1.getData().add(new XYChart.Data<String, Number>(months.toString(), 0));
					}
				}
			} else {

				getOpentaskCount(months.toString());
				if (toogleGroupValue != null && !toogleGroupValue.equals("")) {
					if (toogleGroupValue.equalsIgnoreCase("open")) {
					} else {
						openTaskCount = 0;
					}
				}
				stack1.getData().add(new XYChart.Data<String, Number>(months.toString(), openTaskCount));
			}
		}

		stack2.setName("Closed Tasks");
		// Make Closed task Bars with Closed task count
		for (int count = 0; count < monthList.size(); count++) {
			Months months = monthList.get(count);
			closeTaskCount = 0;

			if (lstFilteredMonth != null && lstFilteredMonth.size() >= 1) {
				for (int i = 0; i < lstFilteredMonth.size(); i++) {
					String filterMonth = lstFilteredMonth.get(i);
					if (months.toString().contentEquals(filterMonth)) {
						getClosetaskCount(months.toString());
						if (toogleGroupValue != null && !toogleGroupValue.equals("")) {
							if (toogleGroupValue.equalsIgnoreCase("closed")) {
							} else {
								closeTaskCount = 0;
							}
						}
						stack2.getData().add(new XYChart.Data<String, Number>(months.toString(), closeTaskCount));
					} else {
						stack2.getData().add(new XYChart.Data<String, Number>(months.toString(), 0));
					}
				}
			} else {
				getClosetaskCount(months.toString());
				if (toogleGroupValue != null && !toogleGroupValue.equals("")) {
					if (toogleGroupValue.equalsIgnoreCase("closed")) {
					} else {
						closeTaskCount = 0;
					}
				}
				stack2.getData().add(new XYChart.Data<String, Number>(months.toString(), closeTaskCount));
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
		if (this.filterStartDate != null && !this.filterStartDate.equals("")
				&& (this.filterEndDate == null || this.filterEndDate.equals(""))) {
			LocalDate valStart = LocalDate.parse(filterStartDate);

			System.out.println("h " + valStart);

			// applying filter for open task and set list size on openTaskCount variable
			filteredData.predicateProperty().bind(
					Bindings.createObjectBinding(() -> filters.stream().reduce(x -> true, Predicate::and), filters));
			filters.add(FilterBuilder.date_filter_day(valStart));
			filters.add(FilterBuilder.status("open"));
			openTaskCount = filteredData.size();
			initData();
			filters.clear();
		}
		// If user select month or both dates
		else if (month != null && !("").equals(month)) {
			String starAndEndDate = getStartAndEndDateOfMonth(month);
			String[] date = starAndEndDate.split(",");
			String startDate = date[0];
			String endDate = date[1];
			LocalDate valStart = LocalDate.parse(startDate);
			LocalDate valEnd = LocalDate.parse(endDate);
			filteredData.predicateProperty().bind(
					Bindings.createObjectBinding(() -> filters.stream().reduce(x -> true, Predicate::and), filters));
			filters.add(FilterBuilder.date_filter(valStart, valEnd));
			filters.add(FilterBuilder.status("open"));
			openTaskCount = filteredData.size();
			initData();
			filters.clear();
		}
	}

	public void getClosetaskCount(String month) {
		// If user select only From date
		if (this.filterStartDate != null && !this.filterStartDate.equals("")
				&& (this.filterEndDate == null || this.filterEndDate.equals(""))) {
			LocalDate valStart = LocalDate.parse(filterStartDate);
			filteredData.predicateProperty().bind(
					Bindings.createObjectBinding(() -> filters.stream().reduce(x -> true, Predicate::and), filters));
			filters.add(FilterBuilder.date_filter_day(valStart));
			filters.add(FilterBuilder.status("close"));
			closeTaskCount = filteredData.size();
			initData();
			filters.clear();
		}
		// If user select month or both dates
		else if (month != null && !month.equals("")) {
			String starAndEndDate = getStartAndEndDateOfMonth(month);
			String[] date = starAndEndDate.split(",");
			String startDate = date[0];
			String endDate = date[1];
			LocalDate valStart = LocalDate.parse(startDate);
			LocalDate valEnd = LocalDate.parse(endDate);
			filteredData.predicateProperty().bind(
					Bindings.createObjectBinding(() -> filters.stream().reduce(x -> true, Predicate::and), filters));
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
		if (month.toString().equals(JANUARY)) {
			startDate = year + "-01-01";
			endDate = year + "-01-31";
		} else if (month.equalsIgnoreCase(FEBRUARY)) {
			startDate = year + "-02-01";
			endDate = year + "-02-28";
		} else if (month.equalsIgnoreCase(MARCH)) {
			startDate = year + "-03-01";
			endDate = year + "-03-31";
		} else if (month.equalsIgnoreCase(APRIL)) {
			startDate = year + "-04-01";
			endDate = year + "-04-30";
		} else if (month.equalsIgnoreCase(MAY)) {
			startDate = year + "-05-01";
			endDate = year + "-05-31";
		} else if (month.equalsIgnoreCase(JUNE)) {
			startDate = year + "-06-01";
			endDate = year + "-06-30";
		} else if (month.equalsIgnoreCase(JULY)) {
			startDate = year + "-07-01";
			endDate = year + "-07-31";
		} else if (month.equalsIgnoreCase(AUGUST)) {
			startDate = year + "-08-01";
			endDate = year + "-08-31";
		} else if (month.equalsIgnoreCase(SEPTEMBER)) {
			startDate = year + "-09-01";
			endDate = year + "-09-30";
		} else if (month.equalsIgnoreCase(OCTOBER)) {
			startDate = year + "-10-01";
			endDate = year + "-10-31";
		} else if (month.equalsIgnoreCase(NOVEMBER)) {
			startDate = year + "-11-01";
			endDate = year + "-11-30";
		} else if (month.equalsIgnoreCase(DECEMBER)) {
			startDate = year + "-12-01";
			endDate = year + "-12-31";
		}

		return startDate + "," + endDate;
	}
}