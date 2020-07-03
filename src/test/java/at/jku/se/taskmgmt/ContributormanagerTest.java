package at.jku.se.taskmgmt;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import contributor.Contributor;
import contributor.Contributormanager;
import javafx.collections.ObservableList;

public class ContributormanagerTest {
	
private static Contributormanager cm;
	
	/**
     * this method sets the instance of the Contributormanager to null after each test,
     * so that the test cases can be executed more easily
     */
	@AfterEach
	public void resetContributorManager() throws Exception{
		Field instance = Contributormanager.class.getDeclaredField("INSTANCE");
		instance.setAccessible(true);
		instance.set(null, null);
	}
	
	/**
     * Test of addContributor, removeContributor and isEmpty methods, of class Contributormanager, with empty list of contributors
     * these methods must return all false because the list of contributors is not initialized
     */
	@Test
	public void testEmptyContributormanager() {
		cm = Contributormanager.getInstance();
		cm.setContributorsNull();
		assertFalse(cm.addContributor("Chris"));
		assertFalse(cm.removeContributor("Chris"));
		assertFalse(cm.isEmpty());
	}
	
	/**
     * Test of addContributor method, of class Contributormanager
     * checks if the contributor name is added to the Contributormanager
     */
	@Test
	public void testAddContributor() {
		cm = Contributormanager.getInstance();
		assertTrue(cm.isEmpty());
		assertTrue(cm.addContributor("Chris"));
		assertFalse(cm.isEmpty());
		assertFalse(cm.addContributor("Chris"));
	}
	
	/**
     * Test of addContributor method, of class Contributormanager
     * contributors must be unique, it is checked if the contributor already exists
     */
	@Test
	public void testAddContributorTwice() {
		cm = Contributormanager.getInstance();
		assertTrue(cm.isEmpty());
		assertTrue(cm.addContributor("Chris"));
		assertTrue(cm.addContributor("Anna"));
		assertTrue(cm.addContributor("Max"));
		assertFalse(cm.addContributor("Max"));
	}
	
	/**
     * Test of getInstance method, of class Contributormanager
     * checks it the Contributormanager has a single instance
     */
	@Test
	public void testContributormanagerInstance() {
		cm = Contributormanager.getInstance();
		assertTrue(cm.isEmpty());
		assertTrue(cm.addContributor("Chris"));
		cm = Contributormanager.getInstance();
		assertFalse(cm.isEmpty());
		assertFalse(cm.addContributor("Chris"));
	}
	
	/**
     * Test of removeContributor method, of class Contributormanager
     * checks if the contributor name is removed from the Contributormanager
     */
	@Test
	public void testRemoveContributor() {
		cm = Contributormanager.getInstance();
		assertTrue(cm.isEmpty());
		assertTrue(cm.addContributor("Chris"));
		assertFalse(cm.isEmpty());
		assertTrue(cm.removeContributor("Chris"));
		assertTrue(cm.isEmpty());
	}
	
	/**
     * Test of removeContributor method, of class Contributormanager
     * contributor which does not exist, can not be removed
     */
	@Test
	public void testRemoveContributorTwice() {
		cm = Contributormanager.getInstance();
		assertTrue(cm.isEmpty());
		assertTrue(cm.addContributor("Chris"));
		assertTrue(cm.addContributor("Anna"));
		assertTrue(cm.removeContributor("Chris"));
		assertFalse(cm.removeContributor("Chris"));
	}
	
	/**
     * Test of getContributors method, of class Contributormanager
     */
	@Test
	public void testGetContributors() {
		cm = Contributormanager.getInstance();
		assertTrue(cm.addContributor("Chris"));
		assertTrue(cm.addContributor("Anna"));
		ObservableList<Contributor> con = cm.getContributors();
		for(Contributor c:con) {
			assertTrue(cm.getContributors().contains(c));
		}
	}

}
