package at.jku.se.taskmgmt;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import task.Subtask;

public class SubtaskTest {
	
	private final Subtask s = new Subtask("Homework");
	
	//Test of getSubtask method, of class Subtask.
	@Test
	public void getSubtaskName() {
		assertEquals(s.getSubtask(), "Homework");
	}
		
	//Test of setSubtask method, of class Subtask.
	@Test
	public void setSubtaskName() {
		s.setSubtask("NewHomework");
		assertEquals(s.getSubtask(), "NewHomework");
	}

}
