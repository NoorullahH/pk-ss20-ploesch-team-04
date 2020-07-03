package at.jku.se.taskmgmt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import category.Category;

public class CategoryTest {
	
	private final Category c= new Category("Homework");
	

	/**
     * Test of getCategory method, of class Category.
     * checks if the category name is queried correctly
     */
	@Test
	public void testGetCategoryName() {
		assertEquals("Homework", c.getCategory());
	}
	
	/**
     * Test of setCategory method, of class Category.
     * checks if the category name is changed correctly
     */
	@Test
	public void testSetCategoryName() {
		c.setCategory("NewHomework");
		assertEquals("NewHomework", c.getCategory());
	}
	
}
