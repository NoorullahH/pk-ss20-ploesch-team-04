package taskmanagerView;

import javafx.scene.control.Label;
import javafx.fxml.FXML;
/**
 * This class controls the UI of an info screen
 * This info screen is used for confirmation and error messages
 */
public class InfoWindowController {
	
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
