package category;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Class which represents the existing categories
 * @author Mara
 */
public final class Categorymanager {
	
	private static Categorymanager instance;
	
	private ObservableList<Category> categories;
	
	/**
     * private constructor which initializes the Categorymanager
     */
	private Categorymanager() {
		categories = FXCollections.observableArrayList();
	}
	
	/**
     * this method returns the single instance of the Categorymanager
     * @return the single instance of the Categorymanager
     */
	public static synchronized Categorymanager getInstance() {
		if(instance == null) {
			instance = new Categorymanager();
		}
		return instance;
	}
	
	/**
     * this method adds a new Category to the Categorymanager
     * @param category 	the name of the Category
     * @return indicates if the category was added successfully
     * 
     */
	public boolean addCategory(String category) {
		if(categories == null) {
			return false;
		}
		for(Category c:categories) {
			if(c.getCategory().equals(category)) {
				return false; 
			}
		}
		categories.add(new Category(category));
		return true;
	}
	
	/**
     * this method removes a Category from the Categorymanager
     * @param category 	the name of the Category
     * @return indicates if the category was removed successfully
     * 
     */
	public boolean removeCategory(String category) {
		if(categories == null) {
			return false;
		}		
		int index = -1;	
		for(Category c:categories) {
			if(c.getCategory().equals(category)) {
				index = categories.indexOf(c);
			}
		}
		if(index==-1) {
			return false;
		}else {
			categories.remove(index);
			return true;
		}
	}
	
	/**
     * this method checks if the categories have been initialized
     * @return indicates if categories have already been initialized
     */
	public boolean isEmpty() {
		if(categories == null) {
			return false;
		}else {
			return categories.isEmpty();
		}
	}
	
	/**
     * this method returns a list of the categories
     * @return list of the categories
     */
	public ObservableList<Category> getCategories() {
		return FXCollections.observableList(categories);
	}
	
	/**
     * this method sets the categories to null
     */
	public void setCategoriesNull() {
		categories = null;
	}
}
