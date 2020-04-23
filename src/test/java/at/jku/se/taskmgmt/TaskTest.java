
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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
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
	public void nonRecurringTask() {
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
		assertEquals(newOne.getTaskDescription(), "First Assignment");
		assertEquals(newOne.getDetailedTaskDescription(), "Create a prototype");
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
	public void weeklyRecurringTask() {
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
		assertEquals(newOne.getTaskDescription(), "First Assignment");
		assertEquals(newOne.getDetailedTaskDescription(), "Create a prototype");
		assertEquals(newOne.getDueDate(), LocalDate.of(2020, 4, 23));
		assertTrue(newOne.getContributorsList().containsAll(con));
		assertTrue(newOne.getCategoriesList().containsAll(cat));
		assertTrue(newOne.getSubtasks().containsAll(sub));
		assertTrue(newOne.getAttachments().containsAll(att));
		assertTrue(newOne.isRecurrent());
		assertTrue(newOne.isWeekly());
		assertFalse(newOne.isMonthly());
		assertEquals(newOne.getWeekday(), Weekday.MONDAY);
		assertEquals(newOne.getNumberOfRepetitions(), 0);
		assertEquals(newOne.getRepetitionDate(), LocalDate.of(2020, 5, 23));
	}
	
	/**
     * Test of monthly recurring Task constructor, of class Task.
     * checks if the parameters are queried correctly
     */
	@Test
	public void monthlyRecurringTask() {
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
		assertEquals(newOne.getTaskDescription(), "First Assignment");
		assertEquals(newOne.getDetailedTaskDescription(), "Create a prototype");
		assertEquals(newOne.getDueDate(), LocalDate.of(2020, 4, 23));
		assertTrue(newOne.getContributorsList().containsAll(con));
		assertTrue(newOne.getCategoriesList().containsAll(cat));
		assertTrue(newOne.getSubtasks().containsAll(sub));
		assertTrue(newOne.getAttachments().containsAll(att));
		assertTrue(newOne.isRecurrent());
		assertTrue(newOne.isMonthly());
		assertFalse(newOne.isWeekly());
		assertEquals(newOne.getMonthday(), 10);
		assertEquals(newOne.getNumberOfRepetitions(), 0);
		assertEquals(newOne.getRepetitionDate(), LocalDate.of(2020, 5, 23));
	}
	
	/**
     * Test of getTaskDescription method, of class Task.
     * checks if the task Description name is queried correctly
     */
    @Test
    public void getTaskDescroption() {
    	t.setTaskDescription("Shopping");
    	assertEquals(t.getTaskDescription(), "Shopping");
    }
    
    /**
     * Test of getDetailedTaskDescription, of class Task.
     * checks if the detailed task Description name is queried correctly
     */
    @Test
    public void getDetailedTaskDescription() {
    	t.setDetailedTaskDescription("Create a prototype");
    	assertEquals(t.getDetailedTaskDescription(), "Create a prototype");
    }
    
    /**
     * Test of  getDueDate, of class Task.
     * checks if the due Date is queried correctly
     */
    @Test
    public void getDueDate() {
    	t.setDueDate(LocalDate.of(2020, 10, 4));
    	assertEquals(t.getDueDate(), LocalDate.of(2020, 10, 4));
    }
    
    /**
     * Test of getContributors, of class Task.
     */
    @Test
    public void getContributors() {
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
     * Test of  isRecurrent, of class Task.
     * checks if a Task is recurrent
     */
    @Test
    public void isRecurrent() {
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
    public void isWeekly() {
    	t.setWeekly(true);
    	assertTrue(t.isWeekly());
    	t.setWeekly(false);
    	assertFalse(t.isWeekly());
    }
    
    /**
     * Test of  getWeekday, of class Task.
     */
    @Test
    public void getWeekday() {
    	t.setWeekday(Weekday.MONDAY);
    	assertEquals(t.getWeekday(), Weekday.MONDAY);
    }
    
    /**
     * Test of  isMonthly, of class Task.
     * checks if a Task is repeated monthly
     */
    @Test
    public void isMonthly() {
    	t.setMonthly(true);
    	assertTrue(t.isMonthly());
    	t.setMonthly(false);
    	assertFalse(t.isMonthly());
    }
    
    /**
     * Test of  getMonthday, of class Task.
     */
    @Test
    public void getMonthday() {
    	t.setMonthday(10);
    	assertEquals(t.getMonthday(), 10);
    }
    
    /**
     * Test of  getNumberOfRepetitions, of class Task.
     */
    @Test
    public void getNumberOfRepetitions() {
    	t.setNumberOfRepetitions(5);
    	assertEquals(t.getNumberOfRepetitions(), 5);
    }
    
    /**
     * Test of getCategories, of class Task.
     */
    @Test
    public void getCategories() {
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
     * Test of getSubtasks, of class Task.
     */
    @Test
    public void getSubtasks() {
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
    public void getAttachments() {
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
    public void addSubtask() {
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
    public void removeSubtask() {
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
    public void getCreationDate() {
    	t.setCreationDate(LocalDate.of(2020, 10, 4));
    	assertEquals(t.getCreationDate(), LocalDate.of(2020, 10, 4));
    }
    
    /**
     * Test of  getRepetitionDate, of class Task.
     * checks if the repetition Date is queried correctly
     */
    @Test
    public void getRepetitionDate() {
    	t.setRepetitionDate(LocalDate.of(2020, 05, 10));;
    	assertEquals(t.getRepetitionDate(), LocalDate.of(2020, 05, 10));
    }
    
    /**
     * Test of getNumberOfTasks method, of class Task.
     */
    @Test
    public void getNumberOfTasks() {
    	Task.setNumberOfTasks(25);
    	assertEquals(Task.getNumberOfTasks(), 25);
    }
    
    /**
     * Test of getTaskNumber method, of class Task.
     */
    @Test
    public void getTaskNumber() {
    	t.setTaskNumber(30);
    	assertEquals(t.getTaskNumber(), 30);
    }
    
    /**
     * Test of setDone method, of class Task.
     * checks if the done value is changed correctly
     */
	@Test
	public void setDone() {
		assertFalse(t.isDone());
		t.setDone(true);
		assertTrue(t.isDone());
	}
	
	/**
     * Test of getDone method, of class Task.
     * checks if the done value is returned correctly
     */
	@Test
	public void getDoneProperty() {
		t.setDone(true);
		assertTrue(t.isDone());
		BooleanProperty b = t.getDone();
		assertTrue(b.getValue());
	}
}
