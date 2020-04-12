/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.jku.se.taskmgmt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import category.Category;
import contributor.Contributor;
import task.Subtask;
import task.Task;
import weekday.Weekday;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.LinkedList;


public class TaskTest {
    
	private Task t;
	
//	@BeforeEach
//	private void initEach() {
//		t = new Task();
//	}
   
   //Test of getTaskDescription, of class Task.
    @Test
    public void getTaskDescroption() {
    	t.setTaskDescription("Hausübung");
    	assertEquals(t.getTaskDescription(), "Hausübung");
    }
    
    //Test of getDetailedTaskDescription, of class Task.
    @Test
    public void getDetailedTaskDescription() {
    	t.setDetailedTaskDescription("Prototyp erstellen");
    	assertEquals(t.getDetailedTaskDescription(), "Prototyp erstellen");
    }
    
    //Test of getDueDate, of class Task.
    @Test
    public void getDueDate() {
    	t.setDueDate(LocalDate.of(2020, 10, 4));
    	assertEquals(t.getDueDate(), LocalDate.of(2020, 10, 4));
    }
    
    //Test of getContributors, of class Task.
//    @Test
//    public void getContributors() {
//    	LinkedList<Contributor> con = new LinkedList<>();
//    	con.add(new Contributor("Max"));
//    	con.add(new Contributor("Anna"));
//    	t.setContributors(con);
//    	assertEquals(t.getContributors(), "Max, Anna, ");
//    }
    
    //Test of isRecurrent, of class Task.
    @Test
    public void isRecurrent() {
    	t.setRecurrent(true);
    	assertTrue(t.isRecurrent());
    	t.setRecurrent(false);
    	assertFalse(t.isRecurrent());
    }
    
    //Test of isWeekly, of class Task.
    @Test
    public void isWeekly() {
    	t.setWeekly(true);
    	assertTrue(t.isWeekly());
    	t.setWeekly(false);
    	assertFalse(t.isWeekly());
    }
    
    //Test of getWeekday, of class Task.
    @Test
    public void getWeekday() {
    	t.setWeekday(Weekday.MONDAY);
    	assertEquals(t.getWeekday(), Weekday.MONDAY);
    }
    
    //Test of isMonthly, of class Task.
    @Test
    public void isMonthly() {
    	t.setMonthly(true);
    	assertTrue(t.isMonthly());
    	t.setMonthly(false);
    	assertFalse(t.isMonthly());
    }
    
    //Test of getMonthday, of class Task.
    @Test
    public void getMonthday() {
    	t.setMonthday(10);
    	assertEquals(t.getMonthday(), 10);
    }
    
    //Test of getNumberOfRepetitions, of class Task.
    @Test
    public void getNumberOfRepetitions() {
    	t.setNumberOfRepetitions(5);
    	assertEquals(t.getNumberOfRepetitions(), 5);
    }
    
    //Test of getCategories, of class Task.
//    @Test
//    public void getCategories() {
//    	LinkedList<Category> cat = new LinkedList<>();
//    	cat.add(new Category("Homework"));
//    	cat.add(new Category("School"));
//    	t.setCategories(cat);
//    	assertEquals(t.getCategories(), "Homework, School, ");
//    }
    
//    //Test of getSubtasks, of class Task.
//    @Test
//    public void getSubtasks() {
//    	LinkedList<Subtask> sub = new LinkedList<>();
//    	sub.add(new Subtask("Define requirements"));
//    	sub.add(new Subtask("Submit the task"));
//    	t.setSubtasks(sub);
//    	assertTrue(t.getSubtasks().containsAll(sub));
//    }
//    
//    //Test of getAttachments, of class Task.
//    @Test
//    public void getAttachments() {
//    	LinkedList<String> att = new LinkedList<>();
//    	att.add("URL1");
//    	att.add("URL2");
//    	t.setAttachments(att);
//    	assertEquals(t.getAttachments(), "URL1, URL2, ");
//    }
//    
//    //Test of addSubtask, of class Task.
//    @Test
//    public void addSubtask() {
//    	LinkedList<Subtask> sub = new LinkedList<>();
//    	sub.add(new Subtask("Define requirements"));
//    	t.setSubtasks(sub);
//    	Subtask sb = new Subtask("Submit the task");
//    	t.addSubtask(sb);
//    	assertTrue(t.getSubtasks().contains(sb));
//    }
//    
//    //Test of removeSubtask, of class Task.
//    @Test
//    public void removeSubtask() throws Exception {
//    	LinkedList<Subtask> sub = new LinkedList<>();
//    	sub.add(new Subtask("Define requirements"));
//    	t.setSubtasks(sub);
//    	Subtask a = new Subtask("Submit the task");
//    	t.addSubtask(a);
//    	assertTrue(t.getSubtasks().contains(a));
//    	t.removeSubtask(a);
//    	assertFalse(t.getSubtasks().contains(a));
//    }
//    
//    //Test of removeSubtaskException, of class Task.
//    @Test
//    public void removeSubtaskException() throws Exception {
//    	LinkedList<Subtask> sub = new LinkedList<>();
//    	sub.add(new Subtask("Define requirements"));
//    	t.setSubtasks(sub);
//    	Subtask a = new Subtask("Submit the task");
//    	t.addSubtask(a);
//    	assertTrue(t.getSubtasks().contains(a));
//    	t.removeSubtask(a);
//    	assertFalse(t.getSubtasks().contains(a));
//    	assertThrows(Exception.class, () -> t.removeSubtask(a));
//    }
//    
//    //Test of constructorExceptions, of class Task.
//    @Test
//    public void constructorExceptions() throws Exception{
//    	LinkedList<Contributor> con = new LinkedList<>();
//    	con.add(new Contributor("Max"));
//    	LinkedList<Category> cat = new LinkedList<>();
//    	cat.add(new Category("Software Engineering"));
//    	LinkedList<Subtask> sub = new LinkedList<>();
//    	sub.add(new Subtask("Prototyp"));
//    	LinkedList<String> att = new LinkedList<>();
//    	att.add("URL1");
//    	att.add("URL2");
//    	Task newTask = new Task("Homework", "Prototyp",LocalDate.of(2020, 10, 5), con, true, true, Weekday.MONDAY, false, 0 , 5, cat, sub, att);
//    	Task newTask2 = new Task("Homework", "Prototyp",LocalDate.of(2020, 10, 5), con, true, false, null, true, 10, 5, cat, sub, att);
//    	Task newTask3 = new Task("Homework", "Prototyp",LocalDate.of(2020, 10, 5), con, false, false, null, false, 0 , 5, cat, sub, att);
//    	assertThrows(NullPointerException.class, () -> new Task(null, "Prototyp",LocalDate.of(2020, 10, 5), con, true, true, Weekday.MONDAY, false, 0 , 5, cat, sub, att));
//    	assertThrows(NullPointerException.class, () -> new Task("Homework", "Prototyp",null, con, true, true, Weekday.MONDAY, false, 0 , 5, cat, sub, att));		
//    }
}
