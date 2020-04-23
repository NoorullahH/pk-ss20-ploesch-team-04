package taskmanagerView;

import java.awt.Button;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
/**
 * This class controls the UI of an info screen
 * This info screen is used for confirmation and error messages
 */
public class InfoScreenController {
	
	@FXML
    private Label infoText;

    /**
     * Sets the info text depending on usage
     * @param t
     */
    public void setInfoText(String t) {

        this.infoText.setText(t);

    }

    
}
