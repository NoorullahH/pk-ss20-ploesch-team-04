package at.jku.se.taskmgmt;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.lang.reflect.Field;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import category.Category;
import category.Categorymanager;
import javafx.collections.ObservableList;

/**
 * Test class for Categorymanager
 * @author Mara
 */
public class CategorymanagerTest {
	
	private static Categorymanager cm;
	
	/**
     * this method sets the instance of the Categorymanager to null after each test,
     * so that the test cases can be executed more easily
     */
	@AfterEach
	public void resetCategoryManager() throws Exception{
		Field instance = Categorymanager.class.getDeclaredField("instance");
		instance.setAccessible(true);
		instance.set(null, null);
	}
	
	/**
     * Test of getInstance method
     */
	@Test
	public void testCategorymanagerInstance() {
		cm = Categorymanager.getInstance();
		assertTrue(cm.isEmpty());
		assertTrue(cm.addCategory("Homework"));
		cm = Categorymanager.getInstance();
		assertFalse(cm.isEmpty());
		assertFalse(cm.addCategory("Homework"));
	}
	
	/**
     * Test of addCategory, removeCategory and isEmpty methods with empty list of categories
     * these methods must return all false because the list of categories is not initialized
     */
	@Test
	public void testEmptyCategorymanager() {
		cm = Categorymanager.getInstance();
		cm.setCategoriesNull();
		assertFalse(cm.addCategory("Homework"));
		assertFalse(cm.removeCategory("Homework"));
		assertFalse(cm.isEmpty());
	}
	
	/**
     * Test of addCategory method
     */
	@Test
	public void testAddCategory() {
		cm = Categorymanager.getInstance();
		assertTrue(cm.isEmpty());
		assertTrue(cm.addCategory("Homework"));
		assertFalse(cm.isEmpty());
		assertFalse(cm.addCategory("Homework"));
	}
	
	/**
     * Test of addCategory method
     * categories must be unique, it is checked if the category already exists
     */
	@Test
	public void testAddCategoryTwice() {
		cm = Categorymanager.getInstance();
		assertTrue(cm.isEmpty());
		assertTrue(cm.addCategory("Homework"));
		assertTrue(cm.addCategory("Software Engineering"));
		assertTrue(cm.addCategory("Datenmodellierung"));
		assertFalse(cm.addCategory("Datenmodellierung"));
	}
	
	/**
     * Test of removeCategory method
     */
	@Test
	public void testRemoveCategory() {
		cm = Categorymanager.getInstance();
		assertTrue(cm.isEmpty());
		assertTrue(cm.addCategory("Software Engineering"));
		assertFalse(cm.isEmpty());
		assertTrue(cm.removeCategory("Software Engineering"));
		assertTrue(cm.isEmpty());
	}
	
	/**
     * Test of removeCategory method
     * category which does not exist, can not be removed
     */
	@Test
	public void testRemoveCategoryTwice() {
		cm = Categorymanager.getInstance();
		assertTrue(cm.isEmpty());
		assertTrue(cm.addCategory("Homework"));
		assertTrue(cm.addCategory("Software Engineering"));
		assertTrue(cm.removeCategory("Homework"));
		assertFalse(cm.removeCategory("Homework"));
	}
	
	/**
     * Test of getCategories method
     */
	@Test
	public void testGetCategories() {
		cm = Categorymanager.getInstance();
		assertTrue(cm.addCategory("Homework"));
		assertTrue(cm.addCategory("Software Engineering"));
		ObservableList<Category> cat = cm.getCategories();
		for(Category c:cat) {
			assertTrue(cm.getCategories().contains(c));
		}
	}	
}
