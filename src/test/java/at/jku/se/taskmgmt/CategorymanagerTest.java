package at.jku.se.taskmgmt;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.LinkedList;
import org.junit.Test;
import category.Category;
import category.Categorymanager;

public class CategorymanagerTest {
	
	private final Categorymanager cm = new Categorymanager();
	
	//Test of addCategory method, of class Categorymanager
	@Test
	public void addCategoryName() throws Exception {
		assertTrue(cm.isEmpty());
		Category a = new Category("Homework");
		cm.addCategory(a);
		assertFalse(cm.isEmpty());
	}
		
	//Test of addCategory method, of class Categorymanager to throw an Exception
	@Test
	public void addCategoryNameException() throws Exception {
		assertTrue(cm.isEmpty());
		Category a = new Category("Homework");
		Category b = new Category("Software Engineering");
		Category c = new Category("Homework");
		cm.addCategory(a);
		cm.addCategory(b);
		assertThrows(Exception.class, () -> cm.addCategory(c));
	}
		
	//Test of removeCategory method, of class Categorymanager
	@Test
	public void removeCategoryName() throws Exception {
		assertTrue(cm.isEmpty());
		Category a = new Category("Homework");
		cm.addCategory(a);
		assertFalse(cm.isEmpty());
		cm.removeCategory(a);
		assertTrue(cm.isEmpty());
	}
	
	//Test of removeCategory method, of class Categorymanager to throw an Exception
	@Test
	public void removeCategoryNameException() throws Exception {
		assertTrue(cm.isEmpty());
		Category a = new Category("Homework");
		Category b = new Category("Software Engineering");
		cm.addCategory(a);
		cm.addCategory(b);
		cm.removeCategory(a);
		assertThrows(Exception.class, () -> cm.removeCategory(a));
	}
		
	//Test of setCategories method, of class Categorymanager.
	@Test
	public void setCategories() {
		LinkedList<Category> c = new LinkedList<Category>();
		c.add(new Category("Software"));
		c.add(new Category("Household"));
		assertFalse(cm.containsAll(c));
		cm.setCategories(c);
		assertTrue(cm.containsAll(c));
	}
	
	//Test of getCategories method, of class Categorymanager.
	@Test
	public void getCategories() throws Exception {
		Category a = new Category("Homework");
		Category b = new Category("Software Engineering");
		cm.addCategory(a);
		cm.addCategory(b);
		LinkedList<Category> c = cm.getCategories();
		assertTrue(c.contains(a));
		assertTrue(c.contains(b));
	}
	
	//Test of Constructor(LinkedList<Category> categories), of class Categorymanager.
	@Test
	public void constuctorCategories() {
		LinkedList<Category> c = new LinkedList<Category>();
		c.add(new Category("Software"));
		c.add(new Category("Household"));
		Categorymanager catman = new Categorymanager(c);
		assertTrue(catman.containsAll(c));
	}
	
}
