package at.jku.se.taskmgmt;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import javafx.beans.property.BooleanProperty;
import task.Subtask;

public class SubtaskTest {
	
	private final Subtask s = new Subtask("Homework");
	
	/**
     * Test of getSubtask method, of class Subtask.
     * checks if the subtask name is queried correctly
     */
	@Test
	public void getSubtaskName() {
		assertEquals(s.getSubtask(), "Homework");
	}
	
	/**
     * Test of setSubtask method, of class Subtask.
     * checks if the subtask name is changed correctly
     */
	@Test
	public void setSubtaskName() {
		s.setSubtask("NewHomework");
		assertEquals(s.getSubtask(), "NewHomework");
	}
	
	/**
     * Test of setDone method, of class Subtask.
     * checks if the done value is changed correctly
     */
	@Test
	public void setDone() {
		assertFalse(s.isDone());
		s.setDone(true);
		assertTrue(s.isDone());
	}
	
	/**
     * Test of getDone method, of class Subtask.
     * checks if the done value is returned correctly
     */
	@Test
	public void getDoneProperty() {
		s.setDone(true);
		assertTrue(s.isDone());
		BooleanProperty b = s.getDone();
		assertTrue(b.getValue());
	}
}
