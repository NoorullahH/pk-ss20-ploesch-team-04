
package at.jku.se.taskmgmt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import calendar.Weekday;
import category.Category;
import javafx.beans.property.BooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import task.Subtask;
import task.Task;
import contributor.Contributor;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

/**
 * Test class for Tasks
 * @author Mara
 */
public class TaskTest {
    
	private Task t;
	
	@BeforeEach
	public void initializeTask() {
		t = new Task();
	}
	
	 /**
     * Test of non-recurring Task constructor
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
		assertTrue(newOne.getSubtasksList().containsAll(sub));
		assertTrue(newOne.getAttachmentsList().containsAll(att));
		assertFalse(newOne.isRecurrent());
	}
	
	/**
     * Test of weekly recurring Task constructor
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
		assertTrue(newOne.getSubtasksList().containsAll(sub));
		assertTrue(newOne.getAttachmentsList().containsAll(att));
		assertTrue(newOne.isRecurrent());
		assertTrue(newOne.isWeekly());
		assertFalse(newOne.isMonthly());
		assertEquals(Weekday.MONDAY, newOne.getWeekday());
		assertEquals(0, newOne.getNumberOfRepetitions());
		assertEquals(newOne.getRepetitionDate(), LocalDate.of(2020, 5, 23));
	}
	
	/**
     * Test of monthly recurring Task constructor
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
		assertTrue(newOne.getSubtasksList().containsAll(sub));
		assertTrue(newOne.getAttachmentsList().containsAll(att));
		assertTrue(newOne.isRecurrent());
		assertTrue(newOne.isMonthly());
		assertFalse(newOne.isWeekly());
		assertEquals(10, newOne.getMonthday());
		assertEquals(0, newOne.getNumberOfRepetitions());
		assertEquals(newOne.getRepetitionDate(), LocalDate.of(2020, 5, 23));
	}
	
	/**
     * Test of getTaskDescription method
     */
    @Test
    public void testGetTaskDescroption() {
    	t.setTaskDescription("Shopping");
    	assertEquals("Shopping", t.getTaskDescription());
    }
    
    /**
     * Test of getDetailedTaskDescription
     */
    @Test
    public void testGetDetailedTaskDescription() {
    	t.setDetailedTaskDescription("Create a prototype");
    	assertEquals("Create a prototype", t.getDetailedTaskDescription());
    }
    
    /**
     * Test of  getDueDate
     */
    @Test
    public void testGetDueDate() {
    	t.setDueDate(LocalDate.of(2020, 10, 4));
    	assertEquals(t.getDueDate(), LocalDate.of(2020, 10, 4));
    }
    
    /**
     * Test of getContributorsList
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
     * Test of getContributors
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
     * Test of  isRecurrent
     */
    @Test
    public void testIsRecurrent() {
    	t.setRecurrent(true);
    	assertTrue(t.isRecurrent());
    	t.setRecurrent(false);
    	assertFalse(t.isRecurrent());
    }
    
    /**
     * Test of  isWeekly
     */
    @Test
    public void testIsWeekly() {
    	t.setWeekly(true);
    	assertTrue(t.isWeekly());
    	t.setWeekly(false);
    	assertFalse(t.isWeekly());
    }
    
    /**
     * Test of  getWeekday
     */
    @Test
    public void testGetWeekday() {
    	t.setWeekday(Weekday.MONDAY);
    	assertEquals(Weekday.MONDAY, t.getWeekday());
    }
    
    /**
     * Test of  isMonthly
     */
    @Test
    public void testIsMonthly() {
    	t.setMonthly(true);
    	assertTrue(t.isMonthly());
    	t.setMonthly(false);
    	assertFalse(t.isMonthly());
    }
    
    /**
     * Test of  getMonthday
     */
    @Test
    public void testGetMonthday() {
    	t.setMonthday(10);
    	assertEquals(10, t.getMonthday());
    }
    
    /**
     * Test of  getNumberOfRepetitions
     */
    @Test
    public void testGetNumberOfRepetitions() {
    	t.setNumberOfRepetitions(5);
    	assertEquals(5, t.getNumberOfRepetitions());
    }
    
    /**
     * Test of getCategoriesList
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
     * Test of getCategories
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
     * Test of getSubtasksList
     */
    @Test
    public void testGetSubtasksList() {
    	ObservableList<Subtask> sub = FXCollections.observableArrayList();
    	sub.add(new Subtask("Define requirements"));
    	sub.add(new Subtask("Complete Task"));
    	sub.add(new Subtask("Submit the task"));
    	t.setSubtasks(sub);
    	for(Subtask s:sub) {
			assertTrue(t.getSubtasksList().contains(s));
    	}
    }
    
    /**
     * Test of getSubtasks
     */
    @Test
    public void testGetSubtasks() {
    	ObservableList<Subtask> sub = FXCollections.observableArrayList();
    	sub.add(new Subtask("Define requirements"));
    	sub.add(new Subtask("Complete Task"));
    	sub.add(new Subtask("Submit the task"));
    	t.setSubtasks(sub);
    	assertEquals("Define requirements, Complete Task, Submit the task", t.getSubtasks());
    	ObservableList<Subtask> sub2 = FXCollections.observableArrayList();
    	t.setSubtasks(sub2);
    	assertEquals("", t.getSubtasks());
    }

    /**
     * Test of getAttachmentsList
     */
    @Test
    public void testGetAttachmentsList() {
    	ObservableList<String> att = FXCollections.observableArrayList();
    	att.add("URL1");
    	att.add("URL2");
    	att.add("URL3");
    	t.setAttachments(att);
    	for(String s:att) {
			assertTrue(t.getAttachmentsList().contains(s));
    	}
    }
    
    /**
     * Test of getAttachments
     */
    @Test
    public void testGetAttachments() {
    	ObservableList<String> att = FXCollections.observableArrayList();
    	att.add("URL1");
    	att.add("URL2");
    	att.add("URL3");
    	t.setAttachments(att);
    	assertEquals("URL1, URL2, URL3", t.getAttachments());
    	ObservableList<String> att2 = FXCollections.observableArrayList();
    	t.setAttachments(att2);;
    	assertEquals("", t.getAttachments());
    }
    
    /**
     * Test of addSubtask method
     */
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
     * Test of removeSubtask method
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
     * Test of  getCreationDate
     */
    @Test
    public void testGetCreationDate() {
    	t.setCreationDate(LocalDate.of(2020, 10, 4));
    	assertEquals(t.getCreationDate(), LocalDate.of(2020, 10, 4));
    }
    
    /**
     * Test of  getRepetitionDate
     */
    @Test
    public void testGetRepetitionDate() {
    	t.setRepetitionDate(LocalDate.of(2020, 05, 10));;
    	assertEquals(t.getRepetitionDate(), LocalDate.of(2020, 05, 10));
    }
    
    /**
     * Test of getNumberOfTasks method
     */
    @Test
    public void testGetNumberOfTasks() {
    	Task.setNumberOfTasks(25);
    	assertEquals(25, Task.getNumberOfTasks());
    }
    
    /**
     * Test of getTaskNumber method
     */
    @Test
    public void testGetTaskNumber() {
    	t.setTaskNumber(30);
    	assertEquals(30, t.getTaskNumber());
    }
    
    /**
     * Test of setDone method
     */
	@Test
	public void testSetDone() {
		assertFalse(t.isDone());
		t.setDone(true);
		assertTrue(t.isDone());
	}
	
	/**
     * Test of getDone method
     */
	@Test
	public void testGetDoneProperty() {
		t.setDone(true);
		assertTrue(t.isDone());
		BooleanProperty b = t.getDone();
		assertTrue(b.getValue());
	}
}
