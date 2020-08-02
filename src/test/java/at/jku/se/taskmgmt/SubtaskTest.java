package at.jku.se.taskmgmt;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import javafx.beans.property.BooleanProperty;
import task.Subtask;

/**
 * Test class for Subtask
 * @author Mara
 */
public class SubtaskTest {
	
	private final Subtask s = new Subtask("Homework");
	
	/**
     * Test of getSubtask method
     */
	@Test
	public void testGetSubtaskName() {
		assertEquals("Homework", s.getSubtask());
	}
	
	/**
     * Test of setSubtask method
     */
	@Test
	public void testSetSubtaskName() {
		s.setSubtask("NewHomework");
		assertEquals("NewHomework", s.getSubtask());
	}
	
	/**
     * Test of setDone method
     */
	@Test
	public void testSetDone() {
		assertFalse(s.isDone());
		s.setDone(true);
		assertTrue(s.isDone());
	}
	
	/**
     * Test of getDone method
     */
	@Test
	public void testGetDoneProperty() {
		s.setDone(true);
		assertTrue(s.isDone());
		BooleanProperty b = s.getDone();
		assertTrue(b.getValue());
	}
}
