package contributor;

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
     * private constructor which initializes the Contributormanager
     */
	private Contributormanager() {
		contributors = FXCollections.observableArrayList();
	}
	
	/**
     * this method adds a new Contributor to the Contributormanager
     * @param contributor 	the name of the Contributor
     * @return indicates if the contributor was added successfully
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
     * this method removes a Contributor from the Contributormanager
     * @param contributor 	the name of the Contributor
     * @return indicates if the contributor was removed successfully
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
     * this method checks if the contributors have been initialized
     * @return indicates if contributors have already been initialized
     */
	public boolean isEmpty() {
		if(contributors == null) {
			return false;
		}else {
			return contributors.isEmpty();
		}
	}
	
	/**
     * this method returns a list of the contributors
     * @return list of the contributors
     */
	public ObservableList<Contributor> getContributors() {
		return FXCollections.observableList(contributors);
	}
	
	/**
     * this method sets the contributors to null
     */
	public void setContributorsNull() {
		contributors = null;
	}
}
