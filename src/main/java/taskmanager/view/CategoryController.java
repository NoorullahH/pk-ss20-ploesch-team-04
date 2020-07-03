package taskmanagerView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import category.Category;
import category.Categorymanager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CategoryController implements Initializable{
	
	Categorymanager categories;
	
	@FXML
	private TextField categoryNameField;
	@FXML
	private Button addCategoryButton;
	@FXML
	private ListView<String> categoryList = new ListView<>();
	@FXML
	private Button backButton;
	@FXML
	private Button deleteCategoryButton;
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		categories = Categorymanager.getInstance();
		
		categoryList.setItems(getCategoryList());
		categoryList.setStyle("-fx-font-size: 16 ;");
	}
	
	//This method will return an observableList of the Categoryy
	public ObservableList<String> getCategoryList() {
		ObservableList<String> catList = FXCollections.observableArrayList();
		ObservableList<Category> dummy = categories.getCategories();
		for(Category c:dummy) {
			catList.add(c.getCategory());
		}
		return catList;
	}
	
	
	@FXML
	public void addCategory(ActionEvent event) {
		categories.addCategory(categoryNameField.getText());
		categoryList.setItems(getCategoryList());
		categoryNameField.setText("");
	}
	
	@FXML 
	public void deleteCategory(ActionEvent event) throws IOException {
		String category = categoryList.getSelectionModel().getSelectedItem();
		if(categories.removeCategory(categoryList.getSelectionModel().getSelectedItem())) {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("MainWindow.fxml"));
			Parent root = loader.load();
			MainWindowController controller = loader.<MainWindowController>getController();
			controller.deleteCategoryFromTask(category);
		}
		categoryList.setItems(getCategoryList());	
	}
	
	@FXML
	public void backtoMain(ActionEvent event) throws IOException {

		Parent parent = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
		Scene scene = new Scene(parent);
		Stage windowStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		windowStage.setScene(scene);
		windowStage.show();
	}
}
