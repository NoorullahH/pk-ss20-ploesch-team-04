package task;

import java.time.LocalDate;
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
	
	
	/**
     * constructor which initializes the Task without parameters
     */
	public Task() {
		numberOfTasks++;
		taskNumber = numberOfTasks;
	}
	
	/**
     * constructor which initializes a non-recurring Task
     * @param taskDes 			the name of the Task
     * @param detailedTaskDes	the description of the Task
     * @param dueDate			the due Date of the Task
     * @param contributors		list of contributors assigned to the Task
     * @param categories 		list of categories assigned to the Task
     * @param subtasks			list of subtasks assigned to theTask
     * @param attachments		list of attachments assigned to the Task
     */
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
	
	/**
     * constructor which initializes a weekly Task
     * @param taskDes 				the name of the Task
     * @param detailedTaskDes		the description of the Task
     * @param dueDate				the due Date of the Task
     * @param contributors			list of contributors assigned to the Task
     * @param categories 			list of categories assigned to the Task
     * @param subtasks				list of subtasks assigned to theTask
     * @param attachments			list of attachments assigned to the Task
     * @param weekday				the weekday on which the task is repeated
     * @param numberOfRepetitions 	number of repetitions of the Task
     * @param repetitionDate		indicates up to which date the Task is repeated
     */
	public Task(String taskDes, String detailedTaskDes, LocalDate dueDate, ObservableList<Contributor> contributors, ObservableList<Category> categories, ObservableList<Subtask> subtasks, ObservableList<String> attachments, Weekday weekday, int numberOfRepetitions, LocalDate repetitionDate) {
		numberOfTasks++;
		taskNumber = numberOfTasks;
		this.taskDescription = taskDes;
		this.detailedTaskDescription = detailedTaskDes;
		this.dueDate = dueDate;
		this.contributors = contributors;
		this.categories = categories;
		this.subtasks = subtasks;
		this.attachments = attachments;
		this.recurrent = true;
		this.weekly = true;
		this.monthly = false;
		this.weekday = weekday;
		this.numberOfRepetitions = numberOfRepetitions;
		this.repetitionDate = repetitionDate;
		this.creationDate = LocalDate.now(); //ÄNDERN
	}
	
	/**
     * constructor which initializes a monthly Task
     * @param taskDes 				the name of the Task
     * @param detailedTaskDes		the description of the Task
     * @param dueDate				the due Date of the Task
     * @param contributors			list of contributors assigned to the Task
     * @param categories 			list of categories assigned to the Task
     * @param subtasks				list of subtasks assigned to theTask
     * @param attachments			list of attachments assigned to the Task
     * @param monthday				indicates the monthday on which the task is repeated
     * @param numberOfRepetitions 	number of repetitions of the Task
     * @param repetitionDate		indicates up to which date the Task is repeated
     */
	public Task(String taskDes, String detailedTaskDes, LocalDate dueDate, ObservableList<Contributor> contributors, ObservableList<Category> categories, ObservableList<Subtask> subtasks, ObservableList<String> attachments, int monthday, int numberOfRepetitions, LocalDate repetitionDate) {
		numberOfTasks++;
		taskNumber = numberOfTasks;
		this.taskDescription = taskDes;
		this.detailedTaskDescription = detailedTaskDes;
		this.dueDate = dueDate;
		this.contributors = contributors;
		this.categories = categories;
		this.subtasks = subtasks;
		this.attachments = attachments;
		this.recurrent = true;
		this.monthly = true;
		this.weekly = false;
		this.monthday = monthday;
		this.numberOfRepetitions = numberOfRepetitions;
		this.repetitionDate = repetitionDate;
		this.creationDate = LocalDate.now();
	}
	
	/**
     * this method returns the number of the tasks
     * @return the number of the tasks
     */
	public static int getNumberOfTasks() {
		return numberOfTasks;
	}
	
	/**
     * this method sets the number of the tasks
     * @param numberOfTask	the number of tasks
     */
	public static void setNumberOfTasks(int numberOfTasks) {
		Task.numberOfTasks = numberOfTasks;
	}
	
	/**
     * this method returns the task number
     * @return the task number
     */
	public int getTaskNumber() {
		return taskNumber;
	}
	
	/**
     * this method sets the task number
     * @param taskNumber	the task number
     */
	public void setTaskNumber(int taskNumber) {
		this.taskNumber = taskNumber;
	}
	
	/**
     * this method sets the done value of this Task
     * @param done	the value of done (true/false)
     */
	public void setDone(boolean done) {
		this.done.set(done);
	}
	
	/**
     * this method returns the done Property of this Task
     * @return done Property of this Task
     */
	public BooleanProperty getDone() {
		return done;
	}
	
	/**
     * this method returns the done value of this Task
     * @return done value of this Task
     */
	public boolean isDone() {
		return done.get();
	}
	
	/**
     * this method returns the task Description of this Task
     * @return the task Description of this Task
     */
	public String getTaskDescription() {
		return taskDescription;
	}
	
	/**
     * this method sets the task Description of this Task
     * @param taskDescription	the name of the Task
     */
	public void setTaskDescription(String taskDescription) {
		this.taskDescription = taskDescription;
	}
	
	/**
     * this method returns the detailed task Description of this Task
     * @return the detailed task Description of this Task
     */
	public String getDetailedTaskDescription() {
		return detailedTaskDescription;
	}
	
	/**
     * this method sets the detailed task Description of this Task
     * @param detailedTaskDescription	the description of the Task
     */
	public void setDetailedTaskDescription(String detailedTaskDescription) {
		this.detailedTaskDescription = detailedTaskDescription;
	}
	
	/**
     * this method returns the due Date of this Task
     * @return the due Date of this Task
     */
	public LocalDate getDueDate() {
		return dueDate;
	}
	
	/**
     * this method sets the due Date of this Task
     * @param dueDate	the due Date of the Task
     */
	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}
	
	/**
     * this method returns a list of the contributors of this Task
     * @return list of contributors of this Task
     */
	public ObservableList<Contributor> getContributorsList() {
		return contributors;
	}
	
	/**
     * this method returns a String of the contributors of this Task
     * @return String of contributors of this Task
     */
	public String getContributors() {
		String res = "";
		for(Contributor c: contributors) {
			res = res + c.getPerson() +",";
		}
		if(!res.contentEquals("")) {
			res = res.substring(0, res.length()-1);
		}
		return res;
	}
	
	/**
     * this method sets the contributors this Task
     * @param contributors	list of contributors assigned to the Task
     */
	public void setContributors(ObservableList<Contributor> contributors) {
		this.contributors = contributors;
	}
	
	/**
     * this method checks if this task is recurrent
     * @return isRecurrent value of this Task
     */
	public boolean isRecurrent() {
		return recurrent;
	}
	
	/**
     * this method sets the is recurrent value of this Task
     * @param recurrent	the value of recurrent (true/false)
     */
	public void setRecurrent(boolean recurrent) {
		this.recurrent = recurrent;
	}
	
	/**
     * this method checks if this task is repeated weekly
     * @return isWeekly value of this Task
     */
	public boolean isWeekly() {
		return weekly;
	}
	
	/**
     * this method sets the is weekly value of this Task
     * @param weekly	the value of weekly (true/false)
     */
	public void setWeekly(boolean weekly) {
		this.weekly = weekly;
	}
	
	/**
     * this method returns the Weekday of this Task
     * @return weekday  the weekday on which the task is repeated
     */
	public Weekday getWeekday() {
		return weekday;
	}
	
	/**
     * this method sets the weekday of this Task
     * @param weekday	the weekday on which the task is repeated
     */
	public void setWeekday(Weekday weekday) {
		this.weekday = weekday;
	}

	/**
     * this method checks if this task is repeated monthly
     * @return isMonthly value of this Task
     */
	public boolean isMonthly() {
		return monthly;
	}
	
	/**
     * this method sets the is monthly value of this Task
     * @param monthly	the value of monthly (true/false)
     */
	public void setMonthly(boolean monthly) {
		this.monthly = monthly;
	}

	/**
     * this method returns the Monthday of this Task
     * @return monthday  the monthday on which the task is repeated
     */
	public int getMonthday() {
		return monthday;
	}
	
	/**
     * this method sets the monthday of this Task
     * @param monthday	the monthday on which the task is repeated
     */
	public void setMonthday(int monthday) {
		this.monthday = monthday;
	}
	
	/**
     * this method returns the number of repetitions of this Task
     * @return the number of repetitions of this Task
     */
	public int getNumberOfRepetitions() {
		return numberOfRepetitions;
	}
	
	/**
     * this method sets the number of repetitions of this Task
     * @param numberOfRepetitions	number of repetitions of the Task
     */
	public void setNumberOfRepetitions(int numberOfRepetitions) {
		this.numberOfRepetitions = numberOfRepetitions;
	}
	
	/**
     * this method returns a list of the categories of this Task
     * @return list of categories of this Task
     */
	public ObservableList<Category> getCategoriesList() {
		return categories;
	}
	
	/**
     * this method returns a String of the categories of this Task
     * @return String of categories of this Task
     */
	public String getCategories() {
		String res = "";
		for(Category c: categories) {
			res = res + c.getCategory() +",";
		}
		if(!res.contentEquals("")) {
			res = res.substring(0, res.length()-1);
		}
		return res;
	}
	
	/**
     * this method sets the categories this Task
     * @param categories	list of categories assigned to the Task
     */
	public void setCategories(ObservableList<Category> categories) {
		this.categories = categories;
	}
	
	/**
     * this method returns a list of the subtasks of this Task
     * @return list of subtasks of this Task
     */
	public ObservableList<Subtask> getSubtasks() {
		return subtasks;
	}
	
	/**
     * this method sets the subtasks this Task
     * @param subtasks	list of subtasks assigned to the Task
     */
	public void setSubtasks(ObservableList<Subtask> subtasks) {
		this.subtasks = subtasks;
	}
	
	/**
     * this method returns a list of the attachments of this Task
     * @return list of attachments of this Task
     */
	public ObservableList<String> getAttachments() {
		return attachments;
	}
	
	/**
     * this method sets the attachments this Task
     * @param attachments	list of attachments assigned to the Task
     */
	public void setAttachments(ObservableList<String> attachments) {
		this.attachments = attachments;
	}
		
	/**
     * this method adds a new Subtask to this Task
     * @param subtask	name of the Subtask
     * @return indicates if the subtask was added successfully
     */
	public boolean addSubtask(String subtask) {
		if(subtasks == null) {
			return false;
		}
		subtasks.add(new Subtask(subtask));
		return true;
	}
	
	/**
     * this method removes a Subtask from this Task
     * @param subtask 	the name of the Subtask
     * @return indicates if the subtask was removed successfully
     * 
     */
	public boolean removeSubtask(String subtask) {
		if(subtasks == null) {
			return false;
		}		
		int index = -1;	
		for(Subtask s:subtasks) {
			if(s.getSubtask().equals(subtask)) {
				index = subtasks.indexOf(s);
			}
		}
		if(index==-1) {
			return false;
		}else {
			subtasks.remove(index);
			return true;
		}
	}
	
	/**
     * this method returns the creation Date of this Task
     * @return the creation Date of this Task
     */
	public LocalDate getCreationDate() {
		return creationDate;
	}
	
	/**
     * this method sets the creation Date of this Task
     * @param creationDate	the creation Date of the Task
     */
	public void setCreationDate(LocalDate creationDate) {
		this.creationDate = creationDate;
	}
	
	/**
     * this method returns the repetition Date of this Task
     * @return the repetition Date of this Task
     */
	public LocalDate getRepetitionDate() {
		return repetitionDate;
	}
	
	/**
     * this method sets the repetition Date of this Task
     * @param repetitionDate	indicates up to which date the Task is repeated
     */
	public void setRepetitionDate(LocalDate repetitionDate) {
		this.repetitionDate = repetitionDate;
	}

}
