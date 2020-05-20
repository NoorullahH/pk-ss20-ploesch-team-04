package FilterView;

import java.util.Arrays;
import javafx.application.Application;
import javafx.collections.FXCollections;
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

public class ChartBuilder extends Application {

	final static String January = "January";
	final static String February = "February";
	final static String March = "March";
	final static String April = "April";
	final static String May = "May";
	final static String June = "June";
	final static String July = "July";
	final static String August = "August";
	final static String September = "September";
	final static String October = "October";
	final static String November = "November";
	final static String December = "December";

	TableView tableView = new TableView();
    FilteredList<Task> filteredData;

	 @FXML
	private TableView<Task> taskView;
	FilterController filterC = new FilterController();
	Task taskChart = new Task();

	final CategoryAxis xAxis = new CategoryAxis();
	final NumberAxis yAxis = new NumberAxis();
	final StackedBarChart<String, Number> barChart = new StackedBarChart<String, Number>(xAxis, yAxis);
	final XYChart.Series<String, Number> stack1 = new XYChart.Series<String, Number>();
	final XYChart.Series<String, Number> stack2 = new XYChart.Series<String, Number>();
	

	@Override
	public void start(Stage stage) {
		stage.setTitle("Taskmanagement Chart");
		barChart.setTitle("Tasks");
		xAxis.setLabel("Months");
		xAxis.setCategories(FXCollections.<String>observableArrayList(Arrays.asList(January, February, March, April,
				May, June, July, August, September, October, November, December)));
		yAxis.setLabel("Value");
		stack1.setName("Open Tasks");

		// getTableView().getItems().size()
		//taskView.getItems().size()
		stack1.getData().add(new XYChart.Data<String, Number>(January, this.filteredData.size()));
		stack1.getData().add(new XYChart.Data<String, Number>(February, this.filteredData.size()));
		stack1.getData().add(new XYChart.Data<String, Number>(March, this.filteredData.size()));
		stack1.getData().add(new XYChart.Data<String, Number>(April, this.filteredData.size()));
		stack1.getData().add(new XYChart.Data<String, Number>(May, this.filteredData.size()));
		stack1.getData().add(new XYChart.Data<String, Number>(June, this.filteredData.size()));
		stack1.getData().add(new XYChart.Data<String, Number>(July, this.filteredData.size()));
		stack1.getData().add(new XYChart.Data<String, Number>(August, this.filteredData.size()));
		stack1.getData().add(new XYChart.Data<String, Number>(September, this.filteredData.size()));
		stack1.getData().add(new XYChart.Data<String, Number>(October, this.filteredData.size()));
		stack1.getData().add(new XYChart.Data<String, Number>(November, this.filteredData.size()));
		stack1.getData().add(new XYChart.Data<String, Number>(December, this.filteredData.size()));

		stack2.setName("Closed Tasks");
		stack2.getData().add(new XYChart.Data<String, Number>(January, this.filteredData.size()));
		stack2.getData().add(new XYChart.Data<String, Number>(February, this.filteredData.size()));
		stack2.getData().add(new XYChart.Data<String, Number>(March, this.filteredData.size()));
		stack2.getData().add(new XYChart.Data<String, Number>(April, this.filteredData.size()));
		stack2.getData().add(new XYChart.Data<String, Number>(May, this.filteredData.size()));
		stack2.getData().add(new XYChart.Data<String, Number>(June, this.filteredData.size()));
		stack2.getData().add(new XYChart.Data<String, Number>(July, this.filteredData.size()));
		stack2.getData().add(new XYChart.Data<String, Number>(August, this.filteredData.size()));
		stack2.getData().add(new XYChart.Data<String, Number>(September, this.filteredData.size()));
		stack2.getData().add(new XYChart.Data<String, Number>(October, this.filteredData.size()));
		stack2.getData().add(new XYChart.Data<String, Number>(November, this.filteredData.size()));
		stack2.getData().add(new XYChart.Data<String, Number>(December, this.filteredData.size()));

		Scene scene = new Scene(barChart, 800, 600);
		barChart.getData().addAll(stack1, stack2);
		stage.setScene(scene);
		stage.show();
        System.out.println(this.filteredData.size());
	
	}
	
	public void initdata(FilteredList<Task> data) {
		this.filteredData = data;
	}
	
}