package at.jku.se.taskmgmt;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.lang.reflect.Field;
import java.util.LinkedList;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import category.Category;
import category.Categorymanager;

public class CategorymanagerTest {
	
	private static Categorymanager cm = Categorymanager.getInstance();
	
	
	@BeforeEach
    public void resetSingleton() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
       Field instance = Categorymanager.class.getDeclaredField("INSTANCE");
       instance.setAccessible(true);
       instance.set(null, null);
    }
	
	//Test of addCategory method, of class Categorymanager
	@Test
	public void addCategoryName() throws Exception {
		assertTrue(cm.isEmpty());
		assertTrue(cm.addCategory("Homework"));
		assertFalse(cm.isEmpty());
	}
		
	//Test of addCategory method, of class Categorymanager to 
	@Test
	public void addCategoryNameException() {
		assertTrue(cm.isEmpty());
		assertTrue(cm.addCategory("Software Engineering"));
		assertTrue(cm.addCategory("Homework"));
		assertFalse(cm.addCategory("Homework"));
	}
	
//	@Test
//	public void addCategory() {
//		Categorymanager cm = null;
//		assertFalse(cm.addCategory("Homework"));
//		
//	}
	
	//Test of removeCategory method, of class Categorymanager
	@Test
	public void removeCategoryName() {
		assertFalse(cm.isEmpty());
		assertTrue(cm.addCategory("Homework"));
		assertFalse(cm.isEmpty());
		assertTrue(cm.removeCategory("Homework"));
		assertTrue(cm.isEmpty());
	}
//	
//	//Test of removeCategory method, of class Categorymanager to throw an Exception
//	@Test
//	public void removeCategoryNameException() throws Exception {
//		assertTrue(cm.isEmpty());
//		cm.addCategory("Homework");
//		cm.addCategory("Software Engineering");
//		cm.removeCategory("Homework");
//		assertThrows(Exception.class, () -> cm.removeCategory("Homework"));
//	}
//		
//	//Test of setCategories method, of class Categorymanager.
//	@Test
//	public void setCategories() {
//		LinkedList<Category> c = new LinkedList<Category>();
//		c.add(new Category("Software"));
//		c.add(new Category("Household"));
//		assertFalse(cm.containsAll(c));
//		cm.setCategories(c);
//		assertTrue(cm.containsAll(c));
//	}
//	
//	//Test of getCategories method, of class Categorymanager.
//	@Test
//	public void getCategories() throws Exception {
//		cm.addCategory("Homework");
//		cm.addCategory("Software Engineering");
//		LinkedList<Category> cat = cm.getCategories();
//		for(Category c:cat) {
//			assertTrue(cm.getCategories().contains(c));
//		}
//	}
//	
//	//Test of Constructor(LinkedList<Category> categories), of class Categorymanager.
//	@Test
//	public void constuctorCategories() {
//		LinkedList<Category> c = new LinkedList<Category>();
//		c.add(new Category("Software"));
//		c.add(new Category("Household"));
//		Categorymanager catman = new Categorymanager(c);
//		assertTrue(catman.containsAll(c));
//	}
//	
}
