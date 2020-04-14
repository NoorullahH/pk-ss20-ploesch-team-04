package task;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ObservableList;

import category.Category;
import contributor.Contributor;
import weekday.Weekday;


public class Task {
	
	private static int numberOfTasks;
	private int taskNumber;
	private String taskDescription;
	private String detailedTaskDescription;
	private LocalDate dueDate;
	private ObservableList<Contributor> contributors;
	private boolean recurrent;
	private boolean weekly;
	private Weekday weekday;  
	private boolean monthly;
	private int monthday;
	private int numberOfRepetitions;
	private LocalDate repetitionDate;
	private ObservableList<Category> categories;
	private ObservableList<Subtask> subtasks;
	private ObservableList<String> attachments;
	private BooleanProperty done = new SimpleBooleanProperty(false);
	private LocalDate creationDate;
	
	
	public Task() {
		
	}
	
	//einfache Task
	public Task(String taskDes, String detailedTaskDes, LocalDate dueDate, ObservableList<Contributor> contributors, ObservableList<Category> categories, ObservableList<Subtask> subtasks, ObservableList<String> attachments) {	
		numberOfTasks++;
		taskNumber = numberOfTasks;
		this.taskDescription = taskDes;
		this.detailedTaskDescription = detailedTaskDes;
		this.dueDate = dueDate;
		this.contributors = contributors;
		this.categories = categories;
		this.subtasks = subtasks;
		this.attachments = attachments;
		this.recurrent = false;
		this.weekly = false;
		this.monthly = false;
		this.creationDate = LocalDate.now();
	}
	
	//wöchentlich wiederholende Task
	public Task(String taskDes, String detailedTaskDes, LocalDate dueDate, ObservableList<Contributor> contributors, ObservableList<Category> categories, ObservableList<Subtask> subtasks, ObservableList<String> attachments, boolean recurrent, boolean weekly, Weekday weekday, int numberOfRepetitions, LocalDate repetitionDate) {
		numberOfTasks++;
		taskNumber = numberOfTasks;
		this.taskDescription = taskDes;
		this.detailedTaskDescription = detailedTaskDes;
		this.dueDate = dueDate;
		this.contributors = contributors;
		this.categories = categories;
		this.subtasks = subtasks;
		this.attachments = attachments;
		this.recurrent = recurrent;
		this.weekly = weekly;
		this.monthly = false;
		this.weekday = weekday;
		this.numberOfRepetitions = numberOfRepetitions;
		this.repetitionDate = repetitionDate;
		this.creationDate = LocalDate.now(); //ÄNDERN
	}
	
	//monatlich wiederholende Task
	public Task(String taskDes, String detailedTaskDes, LocalDate dueDate, ObservableList<Contributor> contributors, ObservableList<Category> categories, ObservableList<Subtask> subtasks, ObservableList<String> attachments, boolean recurrent, boolean monthly, int monthday, int numberOfRepetitions, LocalDate repetitionDate) {
		numberOfTasks++;
		taskNumber = numberOfTasks;
		this.taskDescription = taskDes;
		this.detailedTaskDescription = detailedTaskDes;
		this.dueDate = dueDate;
		this.contributors = contributors;
		this.categories = categories;
		this.subtasks = subtasks;
		this.attachments = attachments;
		this.recurrent = recurrent;
		this.monthly = monthly;
		this.weekly = false;
		this.monthday = monthday;
		this.numberOfRepetitions = numberOfRepetitions;
		this.repetitionDate = repetitionDate;
		this.creationDate = LocalDate.now();
	}
	
	
	public int getTaskNumber() {
		return taskNumber;
	}
	
	public void setTaskNumber(int taskNumber) {
		this.taskNumber = taskNumber;
	}
	
	public void setDone(boolean done) {
		this.done.set(done);
	}

	public BooleanProperty getDone() {
		return done;
	}
	
	public boolean isDone() {
		return done.get();
	}

	//this method returns the taskDescription
	public String getTaskDescription() {
		return taskDescription;
	}

	//this method sets the taskDescription
	public void setTaskDescription(String taskDescription) {
		this.taskDescription = taskDescription;
	}

	//this method returns the detaieldTaskDescription
	public String getDetailedTaskDescription() {
		return detailedTaskDescription;
	}

	//this method sets the detaieldTaskDescription
	public void setDetailedTaskDescription(String detailedTaskDescription) {
		this.detailedTaskDescription = detailedTaskDescription;
	}

	//this method returns the dueDate
	public LocalDate getDueDate() {
		return dueDate;
	}

	//this method sets the dueDate
	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	//this method returns the contributors as a String
	public ObservableList<Contributor> getContributors() {
		return contributors;
	}
	
	//this method sets the contributors
	public void setContributors(ObservableList<Contributor> contributors) {
		this.contributors = contributors;
	}

	//this method checks if a task isRecurrent
	public boolean isRecurrent() {
		return recurrent;
	}

	//this method sets if a task isRecurrent
	public void setRecurrent(boolean recurrent) {
		this.recurrent = recurrent;
	}

	//this method checks if the repetition isWeekly
	public boolean isWeekly() {
		return weekly;
	}

	//this method sets if the repetition isWeekly
	public void setWeekly(boolean weekly) {
		this.weekly = weekly;
	}

	//this method returns the Weekday
	public Weekday getWeekday() {
		return weekday;
	}

	//this method sets the Weekday
	public void setWeekday(Weekday weekday) {
		this.weekday = weekday;
	}

	//this method checks if the repetition isMonthly
	public boolean isMonthly() {
		return monthly;
	}

	//this method sets if the repetition isMonthly
	public void setMonthly(boolean monthly) {
		this.monthly = monthly;
	}

	//this method returns the Monthday
	public int getMonthday() {
		return monthday;
	}

	//this method sets the Monthday
	public void setMonthday(int monthday) {
		this.monthday = monthday;
	}

	//this method returns the numberofRepititions
	public int getNumberOfRepetitions() {
		return numberOfRepetitions;
	}

	//this method sets the numberofRepititions
	public void setNumberOfRepetitions(int numberOfRepetitions) {
		this.numberOfRepetitions = numberOfRepetitions;
	}

	//this method returns the categories as a String
	public ObservableList<Category> getCategories() {
		return categories;
	}

	//this method sets the categories
	public void setCategories(ObservableList<Category> categories) {
		this.categories = categories;
	}

	//this method returns the subtasks
	public ObservableList<Subtask> getSubtasks() {
		return subtasks;
	}

	//this method sets the subtasks
	public void setSubtasks(ObservableList<Subtask> subtasks) {
		this.subtasks = subtasks;
	}

	//this method returns the attachments as a String
	public ObservableList<String> getAttachments() {
		return attachments;
	}

	//this method sets the attachments
	public void setAttachments(ObservableList<String> attachments) {
		this.attachments = attachments;
	}
	
	//this method add a Subtask to subtasks
	public void addSubtask(Subtask st) {
		subtasks.add(st);
	}
	
	//this method removes a subtask from subtasks
	//a subtask can only be removed if it exists
	public void removeSubtask(Subtask st) throws Exception {
		if(!subtasks.contains(st)) {
			throw new Exception("Subtask doe not exist!");
		}else {
			subtasks.remove(st);
		}
	}

	public LocalDate getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDate creationDate) {
		this.creationDate = creationDate;
	}

	public LocalDate getRepetitionDate() {
		return repetitionDate;
	}

	public void setRepetitionDate(LocalDate repetitionDate) {
		this.repetitionDate = repetitionDate;
	}
	
}
