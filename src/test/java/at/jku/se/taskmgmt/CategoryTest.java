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
	public void getCategoryName() {
		assertEquals(c.getCategory(), "Homework");
	}
	
	/**
     * Test of setCategory method, of class Category.
     * checks if the category name is changed correctly
     */
	@Test
	public void setCategoryName() {
		c.setCategory("NewHomework");
		assertEquals(c.getCategory(), "NewHomework");
	}
	
}
