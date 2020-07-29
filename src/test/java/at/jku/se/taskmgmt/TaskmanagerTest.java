package at.jku.se.taskmgmt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.lang.reflect.Field;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

import category.Category;
import contributor.Contributor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import task.Subtask;
import task.Task;
import task.Taskmanager;
import weekday.Weekday;

public class TaskmanagerTest {
	
	private static Taskmanager tm;
	
	/**
     * this method sets the instance of the Taskmanager to null after each test,
     * so that the test cases can be executed more easily
     */
	@AfterEach
	public void resetTaskManager() throws Exception{
		Field instance = Taskmanager.class.getDeclaredField("instance");
		instance.setAccessible(true);
		instance.set(null, null);
	}

	/**
     * Test of getInstance method, of class Taskmanager
     * checks it the Taskmanager has a single instance
     */
	@Test
	public void testTaskmanagerInstance() {
		tm = Taskmanager.getInstance();
		assertTrue(tm.isEmpty());
		assertTrue(tm.addTask(new Task()));
		tm = Taskmanager.getInstance();
		assertFalse(tm.isEmpty());
	}
	
	/**
     * Test of addTask, removeTask and isEmpty methods, of class Taskmanager, with empty list of tasks
     * these methods must return all false because the list of tasks is not initialized
     */
	@Test
	public void testEmptyTaskmanager() {
		tm =Taskmanager.getInstance();
		tm.setTasksNull();
		assertFalse(tm.addTask(new Task()));
		assertFalse(tm.removeTask(new Task()));
		assertFalse(tm.isEmpty());
		assertFalse(tm.removeCategoryFromTasks("Example"));
	}
	
	/**
     * Test of addTask method, of class Taskmanager
     * checks if a Task is added to the Taskmanager
     */
	@Test
	public void testAddTask() {
		tm = Taskmanager.getInstance();
		assertTrue(tm.isEmpty());
		Task newOne = new Task();
		assertTrue(tm.addTask(newOne));
		assertFalse(tm.isEmpty());
		assertTrue(tm.getTasks().contains(newOne));
	}
	
	/**
     * Test of removeTask method, of class Taskmanager
     * checks if a Task is removed from the  Taskmanager
     */
	@Test
	public void testRemoveTask() {
		tm = Taskmanager.getInstance();
		assertTrue(tm.isEmpty());
		Task newOne = new Task();
		assertTrue(tm.addTask(newOne));
		Task newOne2 = new Task();
		assertFalse(tm.removeTask(newOne2));
		assertTrue(tm.addTask(newOne2));
		assertFalse(tm.isEmpty());
		assertTrue(tm.removeTask(newOne));
		assertFalse(tm.isEmpty());
		assertTrue(tm.getTasks().contains(newOne2));
		assertFalse(tm.getTasks().contains(newOne));
	}
	
	
	/**
     * Test of removeCategoryFromTasks method, of class Taskmanager
     * checks if a Category is removed from the tasks of the Taskmanager
     */
	@Test
	public void testRemoveCategoryFromTasks() {
		tm = Taskmanager.getInstance();
		assertTrue(tm.isEmpty());
		
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
		ObservableList<Category> cat2 = FXCollections.observableArrayList();
    	cat2.add(new Category("Homework"));
		Task newOne2 = new Task("Second Assignment", "Recreate a prototype", LocalDate.of(2020, 4, 30),con, cat2, sub, att);
		
		assertTrue(tm.addTask(newOne));
		assertTrue(tm.addTask(newOne2));
		assertFalse(tm.isEmpty());
		assertTrue(tm.removeCategoryFromTasks("School"));
		assertFalse(tm.isEmpty());
		assertTrue(tm.getTasks().contains(newOne));
		assertTrue(tm.getTasks().contains(newOne2));
	}
	
	/**
     * Test of handleRecurrentTask method, of class Taskmanager
     * check if a weekly Task is added correctly
     */
	@Test
	public void testHandleRecurrentWeeklyTaskWithRepetitionDate() {
		tm = Taskmanager.getInstance();
		assertTrue(tm.isEmpty());
		
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
		newOne.setCreationDate(LocalDate.of(2020, 4, 13));
		assertTrue(tm.addTask(newOne));
		
		Task newTask = tm.handleRecurrentTasks(newOne);
		assertTrue(tm.getTasks().contains(newTask));
		newOne = new Task("First Assignment", "Create a prototype", LocalDate.of(2020, 4, 23),con, cat, sub, att, Weekday.MONDAY, 0, LocalDate.of(2020, 5, 23));
		assertNull(tm.handleRecurrentTasks(newOne));
		newOne.setRepetitionDate(LocalDate.of(2020, 04, 01));
		assertNull(tm.handleRecurrentTasks(newOne));	
	}
	
	/**
     * Test of handleRecurrentTask method, of class Taskmanager
     * check if a weekly Task is added correctly
     */
	@Test
	public void testHandleRecurrentWeeklyTaskWithNumberOfRepetitions() {
		tm = Taskmanager.getInstance();
		assertTrue(tm.isEmpty());
		
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
		Task newOne = new Task("First Assignment", "Create a prototype", LocalDate.of(2020, 4, 23),con, cat, sub, att, Weekday.MONDAY, 5, null);		
		newOne.setCreationDate(LocalDate.of(2020, 4, 13));
		assertTrue(tm.addTask(newOne));
		
		Task newTask = tm.handleRecurrentTasks(newOne);
		assertTrue(tm.getTasks().contains(newTask));
		newTask.setCreationDate(LocalDate.of(2020, 10, 10));
		assertNull(tm.handleRecurrentTasks(newTask));
		assertNull(tm.handleRecurrentTasks(newOne));
	}
	
	/**
     * Test of handleRecurrentTask method, of class Taskmanager
     * check if a monthly Task is added correctly
     */
	@Test
	public void testHandleRecurrentMonthlyTaskWithRepetitionDate() {
		tm = Taskmanager.getInstance();
		assertTrue(tm.isEmpty());
		
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
    	
		Task newOne = new Task("First Assignment", "Create a prototype", LocalDate.of(2020, 4, 23),con, cat, sub, att, 31, 0, LocalDate.of(2020, 5, 23));		
		newOne.setCreationDate(LocalDate.of(2020, 2, 13));
		assertTrue(tm.addTask(newOne));
		Task newTask = tm.handleRecurrentTasks(newOne);
		assertTrue(tm.getTasks().contains(newTask));
		newTask.setCreationDate(LocalDate.of(2020, 10, 10));
		assertNull(tm.handleRecurrentTasks(newTask));

		Task newOne2 = new Task("Seconde Assignment", "Create a prototype", LocalDate.of(2020, 4, 23),con, cat, sub, att, 31, 0, LocalDate.of(2020, 4, 23));
		newOne2.setCreationDate(LocalDate.of(2020, 1, 1));
		assertTrue(tm.addTask(newOne2));
		Task newTask2 = tm.handleRecurrentTasks(newOne2);
		assertTrue(tm.getTasks().contains(newTask2));
		
		Task newOne3 = new Task("Third Assignment", "Create a prototype", LocalDate.of(2020, 4, 23),con, cat, sub, att, 31, 0, LocalDate.of(2020, 5, 20));
		newOne3.setCreationDate(LocalDate.of(2019, 1, 1));
		assertTrue(tm.addTask(newOne3));
		Task newTask3 = tm.handleRecurrentTasks(newOne3);
		assertTrue(tm.getTasks().contains(newTask3));
		newTask3.setMonthday(29);
		newTask3.setCreationDate(LocalDate.of(2020, 1, 1));
		tm.handleRecurrentTasks(newTask3);
		
		Task newOne4 = new Task("Fourth Assignment", "Create a prototype", LocalDate.of(2020, 4, 23),con, cat, sub, att, 31, 0, LocalDate.of(2020, 5, 20));
		newOne4.setCreationDate(LocalDate.of(2019, 3, 10));
		assertTrue(tm.addTask(newOne4));
		Task newTask4 = tm.handleRecurrentTasks(newOne4);
		assertTrue(tm.getTasks().contains(newTask4));
		
		Task newOne5 = new Task("Fifth Assignment", "Create a prototype", LocalDate.of(2020, 4, 23),con, cat, sub, att, 31, 0, LocalDate.of(2020, 5, 20));
		newOne5.setCreationDate(LocalDate.of(2019, 5, 10));
		assertTrue(tm.addTask(newOne5));
		Task newTask5 = tm.handleRecurrentTasks(newOne5);
		assertTrue(tm.getTasks().contains(newTask5));
		
		Task newOne6 = new Task("Sixth Assignment", "Create a prototype", LocalDate.of(2020, 4, 23),con, cat, sub, att, 31, 0, LocalDate.of(2020, 5, 20));
		newOne6.setCreationDate(LocalDate.of(2019, 8, 10));
		assertTrue(tm.addTask(newOne6));
		Task newTask6 = tm.handleRecurrentTasks(newOne6);
		assertTrue(tm.getTasks().contains(newTask6));
		
		Task newOne7 = new Task("Seventh Assignment", "Create a prototype", LocalDate.of(2020, 4, 23),con, cat, sub, att, 31, 0, LocalDate.of(2020, 5, 20));
		newOne7.setCreationDate(LocalDate.of(2019, 10, 10));
		assertTrue(tm.addTask(newOne7));
		Task newTask7 = tm.handleRecurrentTasks(newOne7);
		assertTrue(tm.getTasks().contains(newTask7));
	}

	/**
     * Test of handleRecurrentTask method, of class Taskmanager
     * check if a monthly Task is added correctly
     */
	@Test
	public void testHandleRecurrentMonthlyTaskWithNumberOfRepetitions() {
		tm = Taskmanager.getInstance();
		assertTrue(tm.isEmpty());
		
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
		Task newOne = new Task("First Assignment", "Create a prototype", LocalDate.of(2020, 4, 23),con, cat, sub, att, 10, 5, null);		
		newOne.setCreationDate(LocalDate.of(2020, 3, 13));
		assertTrue(tm.addTask(newOne));
		
		Task newTask = tm.handleRecurrentTasks(newOne);
		assertTrue(tm.getTasks().contains(newTask));
		newTask.setCreationDate(LocalDate.of(2020, 10, 10));
		assertNull(tm.handleRecurrentTasks(newTask));
		assertNull(tm.handleRecurrentTasks(newOne));
	}
	
	/**
     * Test of handleRecurrentTask method, of class Taskmanager
     * check if a non-recurring Task is added correctly
     */
	@Test
	public void testHandleNonRecurringTask() {
		tm = Taskmanager.getInstance();
		assertTrue(tm.isEmpty());
		
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
		assertTrue(tm.addTask(newOne));
		
		assertNull(tm.handleRecurrentTasks(newOne));
		newOne.setRecurrent(true);
		newOne.setWeekly(false);
		assertNull(tm.handleRecurrentTasks(newOne));
	}
	
	 /**
     * Test of getSize method, of class Taskmanager.
     */
    @Test
    public void testGetSize() {
    	Taskmanager.setSize(50);
    	assertEquals(50, Taskmanager.getSize());
    }
    
    @Test 
    public void testSaveToXML() {
    	tm = Taskmanager.getInstance();
		assertTrue(tm.isEmpty());
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
		Task one = new Task("First Assignment", "Create a prototype", LocalDate.of(2020, 4, 23),con, cat, sub, att);		
		assertTrue(tm.addTask(one));
		Task two = new Task("Sekund Assigment", "Test", LocalDate.of(2020, 5, 10), con, cat, sub, att);
		assertTrue(tm.addTask(two));
    }
}
