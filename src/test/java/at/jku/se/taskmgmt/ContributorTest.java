package at.jku.se.taskmgmt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import category.Category;
import contributor.Contributor;

/**
 * Test class for Contributor
 * @author Mara
 */

public class ContributorTest {
	
	private final Contributor c = new Contributor("Chris");
	
	/**
     * Test of getPerson method
     */
	@Test
	public void testGetPersonName() {
		assertEquals("Chris", c.getPerson());
	}
	
	/**
     * Test of setPerson method
     */
	@Test
	public void testSetPersonName() {
		c.setPerson("Anna");
		assertEquals("Anna", c.getPerson());
	}
	
	/**
     * Test of equals method
     */
	@Test
	public void testEquals() {
		c.setPerson("Test");
		Object a = new Contributor("Test");
		assertTrue(c.equals(a));
		Object b = new Category("Shopping");
		assertFalse(c.equals(b));
		assertFalse(c.equals(null));
		c.setPerson(null);
		assertFalse(c.equals(a));
	}
	
	/**
     * Test of hashCode method
     */
	@Test
	public void testHashCode() {
		c.setPerson("Test");
		assertEquals(c.hashCode(), 31*7+c.getPerson().hashCode());
		c.setPerson(null);
		assertEquals(c.hashCode(), 31*7);
	}
}
