package FilterView;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import weekday.Months;

public class ChartController implements Initializable{
	
	ObservableList<Months> monthsList = FXCollections.observableArrayList(Months.Januar,Months.Februar,Months.März,Months.April);
	
//	ObservableList<Months> monthsList = FXCollections.observableArrayList(Months.January, Months.February, Months.March,
//			Months.April, Months.May, Months.June, Months.July, Months.August, Months.September, Months.October, Months.November, Months.December);
	
	@FXML
	private ComboBox month;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		month.setItems(monthsList);
		
	}
	@FXML
	private void addMonthsToCombo(){
		Object o = month.getValue();
		if(month.getValue()!=null) {
			month.setValue(o);
		}
	}
}
