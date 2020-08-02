package at.jku.se.taskmgmt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import category.Category;
import contributor.Contributor;
import filter.view.FilterBuilder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import task.Subtask;
import task.Task;
import task.Taskmanager;

/**
 * Test class for FilterBuilder
 * @author Mara
 */

class FilterBuilderTest {
	
	/**
	 * Initializes the tasks before each test
	 */
	@BeforeEach
	public void initializeTasks() {
		Taskmanager tm = Taskmanager.getInstance();
		tm.setEmpty();
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
    	
		Task one = new Task("First Assignment", "Create a prototype", LocalDate.of(2020, 4, 23),con, cat, sub, att, 31, 0, LocalDate.of(2020, 5, 23));	
		Task two = new Task("Second Assignment", "Test", LocalDate.of(2020, 5, 30), con, cat, sub, att);
		tm.addTask(one);
		tm.addTask(two);
		
		ObservableList<Contributor> con2 = FXCollections.observableArrayList();
    	con2.add(new Contributor("Tom"));
    	ObservableList<Category> cat2 = FXCollections.observableArrayList();
    	cat2.add(new Category("Software Engineering"));
    	cat2.add(new Category("Informationmanagement"));
    	Task three = new Task("Homework", "Number 1", LocalDate.of(2020, 6, 10), con2, cat2, sub, att);
    	three.setDone(true);
    	tm.addTask(three);
    	assertEquals(3, tm.getTasks().size());
	}
	
	/**
	 * Tests the description Filter
	 */
	@Test
	public void testDescription() {
		Taskmanager tm = Taskmanager.getInstance();
		assertFalse(tm.isEmpty());
		
		Predicate<Task> desc = FilterBuilder.description("Assignment");
		ObservableList<Task> tasks = tm.getTasks();
		List<Task> result = tasks.stream().filter(desc).collect(Collectors.toList());
		assertEquals(2, result.size());
		
		Predicate<Task> desc2 = FilterBuilder.description("Second");
		List<Task> result2 = tasks.stream().filter(desc2).collect(Collectors.toList());
		assertEquals(1, result2.size());
	}
	
	/**
	 * Tests the detailed description Filter
	 */
	@Test
	public void testDetailDesc() {
		Taskmanager tm = Taskmanager.getInstance();
		assertFalse(tm.isEmpty());
		
		Predicate<Task> deDesc = FilterBuilder.detailDesc("Create");
		ObservableList<Task> tasks = tm.getTasks();
		List<Task> result = tasks.stream().filter(deDesc).collect(Collectors.toList());
		assertEquals(1, result.size());
	}
	
	/**
	 * Tests the date Filter
	 */
	@Test
	public void testDateFilter() {
		Taskmanager tm = Taskmanager.getInstance();
		assertFalse(tm.isEmpty());
		
		Predicate<Task> date = FilterBuilder.dateFilter(LocalDate.of(2020, 4, 1), LocalDate.of(2020, 5, 31));
		ObservableList<Task> tasks = tm.getTasks();
		List<Task> result = tasks.stream().filter(date).collect(Collectors.toList());
		assertEquals(2, result.size());
		
		Predicate<Task> date2 = FilterBuilder.dateFilter(null, LocalDate.of(2020, 5, 10));
		List<Task> result2 = tasks.stream().filter(date2).collect(Collectors.toList());
		assertEquals(1, result2.size());
		
		Predicate<Task> date3 = FilterBuilder.dateFilter(LocalDate.of(2020, 5, 10), null);
		List<Task> result3 = tasks.stream().filter(date3).collect(Collectors.toList());
		assertEquals(2, result3.size());
		
		Predicate<Task> date4 = FilterBuilder.dateFilter(null, null);
		List<Task> result4 = tasks.stream().filter(date4).collect(Collectors.toList());
		assertEquals(3, result4.size());
	}
	
	/**
	 * Tests the day Filter
	 */
	@Test
	public void testDateFilterDay() {
		Taskmanager tm = Taskmanager.getInstance();
		assertFalse(tm.isEmpty());
		
		Predicate<Task> date = FilterBuilder.dateFilterDay(LocalDate.of(2020, 5, 30));
		ObservableList<Task> tasks = tm.getTasks();
		List<Task> result = tasks.stream().filter(date).collect(Collectors.toList());
		assertEquals(1, result.size());
		
		Predicate<Task> date2 = FilterBuilder.dateFilterDay(null);
		List<Task> result2 = tasks.stream().filter(date2).collect(Collectors.toList());
		assertEquals(3, result2.size());
	}
	
	/**
	 * Tests the category Filter
	 */
	@Test
	public void testCategory() {
		Taskmanager tm = Taskmanager.getInstance();
		assertFalse(tm.isEmpty());
		
		ObservableList<Category> cat = FXCollections.observableArrayList();
    	cat.add(new Category("Homework"));
		Predicate<Task> cate = FilterBuilder.category(cat);
		ObservableList<Task> tasks = tm.getTasks();
		List<Task> result = tasks.stream().filter(cate).collect(Collectors.toList());
		assertEquals(2, result.size());
		
		ObservableList<Category> cat2 = FXCollections.observableArrayList();
    	cat2.add(new Category("Software Engineering"));
    	Predicate<Task> cate2 = FilterBuilder.category(cat2);
    	List<Task> result2 = tasks.stream().filter(cate2).collect(Collectors.toList());
		assertEquals(1, result2.size());
	}
	
	/**
	 * Tests the contributor Filter
	 */
	@Test
	public void testContributes() {
		Taskmanager tm = Taskmanager.getInstance();
		assertFalse(tm.isEmpty());
		
		ObservableList<Contributor> con = FXCollections.observableArrayList();
    	con.add(new Contributor("Max"));
		Predicate<Task> cont = FilterBuilder.contributes(con);
		ObservableList<Task> tasks = tm.getTasks();
		List<Task> result = tasks.stream().filter(cont).collect(Collectors.toList());
		assertEquals(2, result.size());
		
		ObservableList<Contributor> con2 = FXCollections.observableArrayList();
    	con2.add(new Contributor("Tom"));
    	Predicate<Task> cont2 = FilterBuilder.contributes(con2);
    	List<Task> result2 = tasks.stream().filter(cont2).collect(Collectors.toList());
		assertEquals(1, result2.size());
	}
	
	/**
	 * Tests the attachment Filter
	 */
	@Test
	public void testAttachment() {
		Taskmanager tm = Taskmanager.getInstance();
		assertFalse(tm.isEmpty());
		
		Predicate<Task> att = FilterBuilder.attachment("yes");
		ObservableList<Task> tasks = tm.getTasks();
		List<Task> result = tasks.stream().filter(att).collect(Collectors.toList());
		assertEquals(3, result.size());
		
		Predicate<Task> att2 = FilterBuilder.attachment("no");
		List<Task> result2 = tasks.stream().filter(att2).collect(Collectors.toList());
		assertEquals(0, result2.size());
		
		Predicate<Task> att3 = FilterBuilder.attachment(null);
		assertThrows(NullPointerException.class, () -> tasks.stream().filter(att3).collect(Collectors.toList()));
	}
	
	/**
	 * Tests the status Filter
	 */
	@Test
	public void testStatus() {
		Taskmanager tm = Taskmanager.getInstance();
		assertFalse(tm.isEmpty());
		
		Predicate<Task> status = FilterBuilder.status("open");
		ObservableList<Task> tasks = tm.getTasks();
		List<Task> result = tasks.stream().filter(status).collect(Collectors.toList());
		assertEquals(2, result.size());
		
		Predicate<Task> status2 = FilterBuilder.status("closed");
		List<Task> result2 = tasks.stream().filter(status2).collect(Collectors.toList());
		assertEquals(1, result2.size());
		
		Predicate<Task> status3 = FilterBuilder.status(null);
		assertThrows(NullPointerException.class, () -> tasks.stream().filter(status3).collect(Collectors.toList()));
	}

}
