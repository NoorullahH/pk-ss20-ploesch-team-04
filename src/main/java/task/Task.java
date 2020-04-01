package task;

import java.time.LocalDate;
import java.util.LinkedList;

import category.Category;
import contributor.Contributor;
import weekday.Weekday;


public class Task{

	private String taskDescription;
	private String detailedTaskDescription;
	private LocalDate dueDate;
	private LinkedList<Contributor> contributors;
	private boolean recurrent;
	private boolean weekly;
	private Weekday weekday;  
	private boolean monthly;
	private int monthday;
	private int numberOfRepetitions;
	private LinkedList<Category> categories;
	private LinkedList<Subtask> subtasks;
	private LinkedList<String> attachments;
	
	public Task() {	
	}
	
	public Task(String taskDescription, String detailedTaskDescription,LocalDate dueDate, LinkedList<Contributor> contributors, boolean recurrent, boolean weekly, Weekday weekday, boolean monthly, int monthday, int numberOfRepetitions, LinkedList<Category> categories, LinkedList<Subtask> subtasks, LinkedList<String> attachments) {
		if(taskDescription == null) {
			throw new NullPointerException("Category must have a name!");
		}
		if(dueDate == null) {
			throw new NullPointerException("Due date must be specified!");
		}
		
		this.taskDescription = taskDescription;
		this.detailedTaskDescription = detailedTaskDescription;
		this.dueDate = dueDate;
	 	this.contributors = contributors;
	 	this.recurrent = recurrent;
	 	if(recurrent) {
	 		if(weekly) {
	 			this.weekly = weekly;
	 			this.weekday = weekday;
	 		}else {
	 			this.monthly = monthly;
	 			this.monthday = monthday;
	 		}
	 		this.numberOfRepetitions = numberOfRepetitions;
	 	}
		this.categories = categories;
		this.subtasks = subtasks;
		this.attachments = attachments;
	}


	public String getTaskDescription() {
		return taskDescription;
	}


	public void setTaskDescription(String taskDescription) {
		this.taskDescription = taskDescription;
	}


	public String getDetailedTaskDescription() {
		return detailedTaskDescription;
	}


	public void setDetailedTaskDescription(String detailedTaskDescription) {
		this.detailedTaskDescription = detailedTaskDescription;
	}


	public LocalDate getDueDate() {
		return dueDate;
	}


	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}


	public String getContributors() {
		String s = "";
		for(Contributor c:contributors) {
			s = s + c.getPerson() + ", ";
		}
		return s;
	}
	

	public void setContributors(LinkedList<Contributor> contributors) {
		this.contributors = contributors;
	}


	public boolean isRecurrent() {
		return recurrent;
	}


	public void setRecurrent(boolean recurrent) {
		this.recurrent = recurrent;
	}


	public boolean isWeekly() {
		return weekly;
	}


	public void setWeekly(boolean weekly) {
		this.weekly = weekly;
	}


	public Weekday getWeekday() {
		return weekday;
	}


	public void setWeekday(Weekday weekday) {
		this.weekday = weekday;
	}


	public boolean isMonthly() {
		return monthly;
	}


	public void setMonthly(boolean monthly) {
		this.monthly = monthly;
	}


	public int getMonthday() {
		return monthday;
	}


	public void setMonthday(int monthday) {
		this.monthday = monthday;
	}


	public int getNumberOfRepetitions() {
		return numberOfRepetitions;
	}


	public void setNumberOfRepetitions(int numberOfRepetitions) {
		this.numberOfRepetitions = numberOfRepetitions;
	}


	public String getCategories() {
		String s = "";
		for(Category c:categories) {
			s = s + c.getCategory() + ", ";
		}
		return s;
	}


	public void setCategories(LinkedList<Category> categories) {
		this.categories = categories;
	}


	public LinkedList<Subtask> getSubtasks() {
		return subtasks;
	}


	public void setSubtasks(LinkedList<Subtask> subtasks) {
		this.subtasks = subtasks;
	}


	public LinkedList<String> getAttachments() {
		return attachments;
	}


	public void setAttachments(LinkedList<String> attachments) {
		this.attachments = attachments;
	}
	
	public void addSubtask(Subtask st) {
		subtasks.add(st);
	}
	
	public void removeSubtask(Subtask st) throws Exception {
		if(!subtasks.contains(st)) {
			throw new Exception("Subtask doe not exist!");
		}else {
			subtasks.remove(st);
		}
	}
	 
}
