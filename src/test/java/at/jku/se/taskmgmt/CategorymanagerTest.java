package at.jku.se.taskmgmt;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.lang.reflect.Field;
import org.junit.After;
import org.junit.Test;
import category.Category;
import category.Categorymanager;
import javafx.collections.ObservableList;

public class CategorymanagerTest {
	
	private static Categorymanager cm;
	
	/**
     * this method sets the instance of the Categorymanager to null after each test,
     * so that the test cases can be executed more easily
     */
	@After
	public void resetCategoryManager() throws Exception{
		Field instance = Categorymanager.class.getDeclaredField("INSTANCE");
		instance.setAccessible(true);
		instance.set(null, null);
	}
	
	/**
     * Test of getInstance method, of class Categorymanager
     * checks it the Categorymanager has a single instance
     */
	@Test
	public void checkCategorymanagerInstance() {
		cm = Categorymanager.getInstance();
		assertTrue(cm.isEmpty());
		assertTrue(cm.addCategory("Homework"));
		cm = Categorymanager.getInstance();
		assertFalse(cm.isEmpty());
		assertFalse(cm.addCategory("Homework"));
	}
	
	/**
     * Test of addCategory, removeCategory and isEmpty methods, of class Categorymanager, with empty list of categories
     * these methods must return all false because the list of categories is not initialized
     */
	@Test
	public void emptyCategorymanager() {
		cm = Categorymanager.getInstance();
		cm.setCategoriesNull();
		assertFalse(cm.addCategory("Homework"));
		assertFalse(cm.removeCategory("Homework"));
		assertFalse(cm.isEmpty());
	}
	
	/**
     * Test of addCategory method, of class Categorymanager
     * checks if the category name is added to the Categorymanager
     */
	@Test
	public void addCategory() {
		cm = Categorymanager.getInstance();
		assertTrue(cm.isEmpty());
		assertTrue(cm.addCategory("Homework"));
		assertFalse(cm.isEmpty());
		assertFalse(cm.addCategory("Homework"));
	}
	
	/**
     * Test of addCategory method, of class Categorymanager
     * categories must be unique, it is checked if the category already exists
     */
	@Test
	public void addCategoryTwice() {
		cm = Categorymanager.getInstance();
		assertTrue(cm.isEmpty());
		assertTrue(cm.addCategory("Homework"));
		assertTrue(cm.addCategory("Software Engineering"));
		assertTrue(cm.addCategory("Datenmodellierung"));
		assertFalse(cm.addCategory("Datenmodellierung"));
	}
	
	/**
     * Test of removeCategory method, of class Categorymanager
     * checks if the category name is removed from the Categorymanager
     */
	@Test
	public void removeCategory() {
		cm = Categorymanager.getInstance();
		assertTrue(cm.isEmpty());
		assertTrue(cm.addCategory("Software Engineering"));
		assertFalse(cm.isEmpty());
		assertTrue(cm.removeCategory("Software Engineering"));
		assertTrue(cm.isEmpty());
	}
	
	/**
     * Test of removeCategory method, of class Categorymanager
     * category which does not exist, can not be removed
     */
	@Test
	public void removeCategoryTwice() {
		cm = Categorymanager.getInstance();
		assertTrue(cm.isEmpty());
		assertTrue(cm.addCategory("Homework"));
		assertTrue(cm.addCategory("Software Engineering"));
		assertTrue(cm.removeCategory("Homework"));
		assertFalse(cm.removeCategory("Homework"));
	}
	
	/**
     * Test of getCategories method, of class Categorymanager
     */
	@Test
	public void getCategories() {
		cm = Categorymanager.getInstance();
		assertTrue(cm.addCategory("Homework"));
		assertTrue(cm.addCategory("Software Engineering"));
		ObservableList<Category> cat = cm.getCategories();
		for(Category c:cat) {
			assertTrue(cm.getCategories().contains(c));
		}
	}
	
}
