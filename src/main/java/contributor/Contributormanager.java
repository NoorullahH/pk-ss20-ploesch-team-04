package contributor;

import java.util.Collection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Contributormanager {
	
	private static Contributormanager INSTANCE;
	
	private ObservableList<Contributor> contributors;
	
	/**
     * this method returns the single instance of the Contributormanager
     * @return the single instance of the Contributormanager
     */
	public static synchronized Contributormanager getInstance() {
		if(INSTANCE == null) {
			INSTANCE = new Contributormanager();
		}
		return INSTANCE;
	}
	
	/**
     * default constructor which initializes the Contributormanager
     */
	private Contributormanager() {
		contributors = FXCollections.observableArrayList();
	}
	
	/**
     * this method adds a new Category to the Categorymanager
     * @param category 	the name of the Category
     * @return indicates if the category was added successfully
     * 
     */
	public boolean addContributor(String contributor) {
		if(contributors == null) {
			return false;
		}
		for(Contributor c:contributors) {
			if(c.getPerson().equals(contributor)) {
				return false;
			}
		}
		contributors.add(new Contributor(contributor));
		return true;
	}
	
	/**
     * this method removes a new Category from the Categorymanager
     * @param category 	the name of the Category
     * @return indicates if the category was removed successfully
     * 
     */
	public boolean removeContributor(String contributor) {
		if(contributors == null) {
			return false;
		}
		int index = -1;	
		for(Contributor c:contributors) {
			if(c.getPerson().equals(contributor)) {
				index = contributors.indexOf(c);
			}
		}
		if(index==-1) {
			return false;
		}else {
			contributors.remove(index);
			return true;
		}
	}
	
	
	/**
     * this method checks if categories have already been added
     * @return indicates if categories have already been added
     */
	public boolean isEmpty() {
		if(contributors == null) {
			return false;
		}else {
			return contributors.isEmpty();
		}
	}
	
	public ObservableList<Contributor> getContributors() {
		return contributors;
	}
	
	/**
     * this method checks if a Collection has the same elements as categories
     * @param list	list of elements to be compared
     * @return indicates if a Collection has the same elements as categories
     */
	public boolean containsAll(Collection<?> list) {
		if(contributors == null) {
			return false;
		}else {
			return contributors.containsAll(list);
		}
	}
}
