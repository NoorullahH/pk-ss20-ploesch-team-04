package at.jku.se.taskmgmt;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

import category.Category;
import contributor.Contributor;

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
	
	/**
     * Test of equals method, of class Category.
     * checks if the category name is equal to another category name
     */
	@Test
	public void testEquals() {
		c.setCategory("Test");
		Object a = new Category("Test");
		assertTrue(c.equals(a));
		Object b = new Contributor("Max");
		assertFalse(c.equals(b));
		assertFalse(c.equals(null));
		c.setCategory(null);
		assertFalse(c.equals(a));
	}
	
	/**
     * Test of hashCode method, of class Category.
     * checks if the hashCode is generated correctly
     */
	@Test
	public void testHashCode() {
		c.setCategory("Test");
		assertEquals(c.hashCode(), 31*7+c.getCategory().hashCode());
		c.setCategory(null);
		assertEquals(c.hashCode(), 31*7);
	}
}
