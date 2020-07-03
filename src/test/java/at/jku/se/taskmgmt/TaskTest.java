
package at.jku.se.taskmgmt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import category.Category;
import javafx.beans.property.BooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import task.Subtask;
import task.Task;
import weekday.Weekday;
import contributor.Contributor;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;


public class TaskTest {
    
	private Task t;
	
	@BeforeEach
	public void initializeTask() {
		t = new Task();
	}
	
	 /**
     * Test of non-recurring Task constructor, of class Task.
     * checks if the parameters are queried correctly
     */
	@Test
	public void testNonRecurringTask() {
		ObservableList<Contributor> con = FXCollections.observableArrayList();
    	con.add(new Contributor("Max"));
    	con.add(new Contributor("Anna"));
    	ObservableList<Category> cat = FXCollections.observableArrayList();
    	cat.add(new Category("Homework"));
    	cat.add(new Category("School"));
    	ObservableList<Subtask> sub = FXCollections.observableArrayList();
    	sub.add(new Subtask("Define requirements"));
    	sub.add(new Subtask("Complete Task"));
    	ObservableList<String> att = FXCollections.observableArrayList();
    	att.add("URL1");
    	att.add("URL2");
		Task newOne = new Task("First Assignment", "Create a prototype", LocalDate.of(2020, 4, 23),con, cat, sub, att);
		assertEquals("First Assignment", newOne.getTaskDescription());
		assertEquals("Create a prototype", newOne.getDetailedTaskDescription());
		assertEquals(newOne.getDueDate(), LocalDate.of(2020, 4, 23));
		assertTrue(newOne.getContributorsList().containsAll(con));
		assertTrue(newOne.getCategoriesList().containsAll(cat));
		assertTrue(newOne.getSubtasks().containsAll(sub));
		assertTrue(newOne.getAttachments().containsAll(att));
		assertFalse(newOne.isRecurrent());
	}
	
	/**
     * Test of weekly recurring Task constructor, of class Task.
     * checks if the parameters are queried correctly
     */
	@Test
	public void testWeeklyRecurringTask() {
		ObservableList<Contributor> con = FXCollections.observableArrayList();
    	con.add(new Contributor("Max"));
    	con.add(new Contributor("Anna"));
    	ObservableList<Category> cat = FXCollections.observableArrayList();
    	cat.add(new Category("Homework"));
    	cat.add(new Category("School"));
    	ObservableList<Subtask> sub = FXCollections.observableArrayList();
    	sub.add(new Subtask("Define requirements"));
    	sub.add(new Subtask("Complete Task"));
    	ObservableList<String> att = FXCollections.observableArrayList();
    	att.add("URL1");
    	att.add("URL2");
		Task newOne = new Task("First Assignment", "Create a prototype", LocalDate.of(2020, 4, 23),con, cat, sub, att, Weekday.MONDAY, 0, LocalDate.of(2020, 5, 23));		
		assertEquals("First Assignment", newOne.getTaskDescription());
		assertEquals("Create a prototype", newOne.getDetailedTaskDescription());
		assertEquals(newOne.getDueDate(), LocalDate.of(2020, 4, 23));
		assertTrue(newOne.getContributorsList().containsAll(con));
		assertTrue(newOne.getCategoriesList().containsAll(cat));
		assertTrue(newOne.getSubtasks().containsAll(sub));
		assertTrue(newOne.getAttachments().containsAll(att));
		assertTrue(newOne.isRecurrent());
		assertTrue(newOne.isWeekly());
		assertFalse(newOne.isMonthly());
		assertEquals(Weekday.MONDAY, newOne.getWeekday());
		assertEquals(0, newOne.getNumberOfRepetitions());
		assertEquals(newOne.getRepetitionDate(), LocalDate.of(2020, 5, 23));
	}
	
	/**
     * Test of monthly recurring Task constructor, of class Task.
     * checks if the parameters are queried correctly
     */
	@Test
	public void testMonthlyRecurringTask() {
		ObservableList<Contributor> con = FXCollections.observableArrayList();
    	con.add(new Contributor("Max"));
    	con.add(new Contributor("Anna"));
    	ObservableList<Category> cat = FXCollections.observableArrayList();
    	cat.add(new Category("Homework"));
    	cat.add(new Category("School"));
    	ObservableList<Subtask> sub = FXCollections.observableArrayList();
    	sub.add(new Subtask("Define requirements"));
    	sub.add(new Subtask("Complete Task"));
    	ObservableList<String> att = FXCollections.observableArrayList();
    	att.add("URL1");
    	att.add("URL2");
		Task newOne = new Task("First Assignment", "Create a prototype", LocalDate.of(2020, 4, 23),con, cat, sub, att, 10, 0, LocalDate.of(2020, 5, 23));		
		assertEquals("First Assignment", newOne.getTaskDescription());
		assertEquals("Create a prototype", newOne.getDetailedTaskDescription());
		assertEquals(newOne.getDueDate(), LocalDate.of(2020, 4, 23));
		assertTrue(newOne.getContributorsList().containsAll(con));
		assertTrue(newOne.getCategoriesList().containsAll(cat));
		assertTrue(newOne.getSubtasks().containsAll(sub));
		assertTrue(newOne.getAttachments().containsAll(att));
		assertTrue(newOne.isRecurrent());
		assertTrue(newOne.isMonthly());
		assertFalse(newOne.isWeekly());
		assertEquals(10, newOne.getMonthday());
		assertEquals(0, newOne.getNumberOfRepetitions());
		assertEquals(newOne.getRepetitionDate(), LocalDate.of(2020, 5, 23));
	}
	
	/**
     * Test of getTaskDescription method, of class Task.
     * checks if the task Description name is queried correctly
     */
    @Test
    public void testGetTaskDescroption() {
    	t.setTaskDescription("Shopping");
    	assertEquals("Shopping", t.getTaskDescription());
    }
    
    /**
     * Test of getDetailedTaskDescription, of class Task.
     * checks if the detailed task Description name is queried correctly
     */
    @Test
    public void testGetDetailedTaskDescription() {
    	t.setDetailedTaskDescription("Create a prototype");
    	assertEquals("Create a prototype", t.getDetailedTaskDescription());
    }
    
    /**
     * Test of  getDueDate, of class Task.
     * checks if the due Date is queried correctly
     */
    @Test
    public void testGetDueDate() {
    	t.setDueDate(LocalDate.of(2020, 10, 4));
    	assertEquals(t.getDueDate(), LocalDate.of(2020, 10, 4));
    }
    
    /**
     * Test of getContributorsList, of class Task.
     */
    @Test
    public void testGetContributorsList() {
    	ObservableList<Contributor> con = FXCollections.observableArrayList();
    	con.add(new Contributor("Max"));
    	con.add(new Contributor("Anna"));
    	con.add(new Contributor("Luke"));
    	t.setContributors(con);
    	for(Contributor c:con) {
			assertTrue(t.getContributorsList().contains(c));
    	}
    }
    
    /**
     * Test of getContributors, of class Task.
     */
    @Test
    public void testGetContributors() {
    	ObservableList<Contributor> con = FXCollections.observableArrayList();
    	con.add(new Contributor("Max"));
    	con.add(new Contributor("Anna"));
    	con.add(new Contributor("Luke"));
    	t.setContributors(con);
    	assertEquals("Max, Anna, Luke", t.getContributors());
    	ObservableList<Contributor> con2 = FXCollections.observableArrayList();
    	t.setContributors(con2);
    	assertEquals("", t.getContributors());
    }
    
    /**
     * Test of  isRecurrent, of class Task.
     * checks if a Task is recurrent
     */
    @Test
    public void testIsRecurrent() {
    	t.setRecurrent(true);
    	assertTrue(t.isRecurrent());
    	t.setRecurrent(false);
    	assertFalse(t.isRecurrent());
    }
    
    /**
     * Test of  isWeekly, of class Task.
     * checks if a Task is repeated weekly
     */
    @Test
    public void testIsWeekly() {
    	t.setWeekly(true);
    	assertTrue(t.isWeekly());
    	t.setWeekly(false);
    	assertFalse(t.isWeekly());
    }
    
    /**
     * Test of  getWeekday, of class Task.
     */
    @Test
    public void testGetWeekday() {
    	t.setWeekday(Weekday.MONDAY);
    	assertEquals(Weekday.MONDAY, t.getWeekday());
    }
    
    /**
     * Test of  isMonthly, of class Task.
     * checks if a Task is repeated monthly
     */
    @Test
    public void testIsMonthly() {
    	t.setMonthly(true);
    	assertTrue(t.isMonthly());
    	t.setMonthly(false);
    	assertFalse(t.isMonthly());
    }
    
    /**
     * Test of  getMonthday, of class Task.
     */
    @Test
    public void testGetMonthday() {
    	t.setMonthday(10);
    	assertEquals(10, t.getMonthday());
    }
    
    /**
     * Test of  getNumberOfRepetitions, of class Task.
     */
    @Test
    public void testGetNumberOfRepetitions() {
    	t.setNumberOfRepetitions(5);
    	assertEquals(5, t.getNumberOfRepetitions());
    }
    
    /**
     * Test of getCategoriesList, of class Task.
     */
    @Test
    public void testGetCategoriesList() {
    	ObservableList<Category> cat = FXCollections.observableArrayList();
    	cat.add(new Category("Homework"));
    	cat.add(new Category("School"));
    	cat.add(new Category("Household"));
    	t.setCategories(cat);
    	for(Category c:cat) {
			assertTrue(t.getCategoriesList().contains(c));
    	}
    }
    
    /**
     * Test of getCategories, of class Task.
     */
    @Test
    public void testGetCategories() {
    	ObservableList<Category> cat = FXCollections.observableArrayList();
    	cat.add(new Category("Homework"));
    	cat.add(new Category("School"));
    	cat.add(new Category("Household"));
    	t.setCategories(cat);
    	assertEquals("Homework, School, Household", t.getCategories());
    	ObservableList<Category> cat2 = FXCollections.observableArrayList();
    	t.setCategories(cat2);
    	assertEquals("", t.getCategories());
    }

    /**
     * Test of getSubtasks, of class Task.
     */
    @Test
    public void testGetSubtasks() {
    	ObservableList<Subtask> sub = FXCollections.observableArrayList();
    	sub.add(new Subtask("Define requirements"));
    	sub.add(new Subtask("Complete Task"));
    	sub.add(new Subtask("Submit the task"));
    	t.setSubtasks(sub);
    	for(Subtask s:sub) {
			assertTrue(t.getSubtasks().contains(s));
    	}
    }

    /**
     * Test of getAttachments, of class Task.
     */
    @Test
    public void testGetAttachments() {
    	ObservableList<String> att = FXCollections.observableArrayList();
    	att.add("URL1");
    	att.add("URL2");
    	att.add("URL3");
    	t.setAttachments(att);
    	for(String s:att) {
			assertTrue(t.getAttachments().contains(s));
    	}
    }
    
    /**
     * Test of addSubtask method, of class Task.
     */
    //Test of addSubtask, of class Task.
    @Test
    public void testAddSubtask() {
    	assertFalse(t.addSubtask("Example"));
    	ObservableList<Subtask> sub = FXCollections.observableArrayList();
    	sub.add(new Subtask("Define requirements"));
    	sub.add(new Subtask("Complete Task"));
    	t.setSubtasks(sub);
    	t.setSubtasks(sub);
    	assertTrue(t.addSubtask("Submit the task"));
    }
    
    /**
     * Test of removeSubtask method, of class Task
     * checks if the subtask name is removed correctly
     */
    @Test
    public void testRemoveSubtask() {
    	assertFalse(t.removeSubtask("Example"));
    	ObservableList<Subtask> sub = FXCollections.observableArrayList();
    	sub.add(new Subtask("Define requirements"));
    	sub.add(new Subtask("Complete Task"));
    	t.setSubtasks(sub);
    	assertTrue(t.addSubtask("Submit the task"));
    	assertTrue(t.removeSubtask("Submit the task"));
    	assertFalse(t.removeSubtask("Submit the task"));
    }
    
    /**
     * Test of  getCreationDate, of class Task.
     * checks if the creation Date is queried correctly
     */
    @Test
    public void testGetCreationDate() {
    	t.setCreationDate(LocalDate.of(2020, 10, 4));
    	assertEquals(t.getCreationDate(), LocalDate.of(2020, 10, 4));
    }
    
    /**
     * Test of  getRepetitionDate, of class Task.
     * checks if the repetition Date is queried correctly
     */
    @Test
    public void testGetRepetitionDate() {
    	t.setRepetitionDate(LocalDate.of(2020, 05, 10));;
    	assertEquals(t.getRepetitionDate(), LocalDate.of(2020, 05, 10));
    }
    
    /**
     * Test of getNumberOfTasks method, of class Task.
     */
    @Test
    public void testGetNumberOfTasks() {
    	Task.setNumberOfTasks(25);
    	assertEquals(25, Task.getNumberOfTasks());
    }
    
    /**
     * Test of getTaskNumber method, of class Task.
     */
    @Test
    public void testGetTaskNumber() {
    	t.setTaskNumber(30);
    	assertEquals(30, t.getTaskNumber());
    }
    
    /**
     * Test of setDone method, of class Task.
     * checks if the done value is changed correctly
     */
	@Test
	public void testSetDone() {
		assertFalse(t.isDone());
		t.setDone(true);
		assertTrue(t.isDone());
	}
	
	/**
     * Test of getDone method, of class Task.
     * checks if the done value is returned correctly
     */
	@Test
	public void testGetDoneProperty() {
		t.setDone(true);
		assertTrue(t.isDone());
		BooleanProperty b = t.getDone();
		assertTrue(b.getValue());
	}
}
