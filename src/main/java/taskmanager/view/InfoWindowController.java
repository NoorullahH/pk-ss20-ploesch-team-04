package taskmanager.view;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
/**
 * This class controls the UI of an info screen
 * This info screen is used for confirmation and error messages
 */
public class InfoWindowController {
	
	@FXML
    private Label infoText;
	@FXML
	private Button okButton;

    /**
     * Sets the info text depending on usage
     * @param t
     */
    public void setInfoText(String t) {

    	this.infoText.setText(t);

    }
    
    @FXML
	private void closeInfoScreen(ActionEvent event) {
    	Stage stage = (Stage) okButton.getScene().getWindow();
        stage.close();
    }

}
