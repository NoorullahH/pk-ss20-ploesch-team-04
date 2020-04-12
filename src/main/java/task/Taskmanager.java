package task;

import java.time.DayOfWeek;
import java.time.LocalDate;

import category.Category;
import contributor.Contributor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import weekday.Weekday;

public class Taskmanager {
	
	private static Taskmanager INSTANCE;
	
	private ObservableList<Task> tasks;
	private static int size;
	
	public static synchronized Taskmanager getInstance() {
		if(INSTANCE == null) {
			INSTANCE = new Taskmanager();
		}
		return INSTANCE;
	}
		
	public Taskmanager() {
		this.tasks = FXCollections.observableArrayList();
	}
		
	public ObservableList<Task> getTasks() {
		return tasks;
	}

	public boolean addTask(Task task) {
		if(tasks == null) {
			return false;
		}
		tasks.add(task);
		size++;
		return true;
	}
		
	public boolean removeTask(Task task) {
		if(tasks == null) {
			return false;
		}
		int index = -1;	
		for(Task t:tasks) {
			if(t.getTaskNumber()==task.getTaskNumber()) {
				index = tasks.indexOf(t);
			}
		}
		if(index==-1) {
			return false;
		}else {
			tasks.remove(index);
			size--;
			return true;
		}
	}
	
	public boolean removeCategoryFromTasks(String category) {
		if(tasks == null) {
			return false;
		}
		int index = -1;
		int taskIndex = 0;
		for(Task t:tasks) {
			for(Category c:t.getCategories()) {
				if(c.getCategory().equals(category)) {
					index = t.getCategories().indexOf(c);
				}
			}
			if(index!=-1) {
				t.getCategories().remove(index);
			}
			index = -1;
		}
		return true;
	}
	
	public void handleRecurrentTasks(Task t) {
		
		Task newTask = null;
		
		if(t.isRecurrent() && (t.getNumberOfRepetitions()>0)) {
			if(t.isMonthly()) {//monatlich
				if(LocalDate.now().isAfter(t.getCreationDate().plusMonths(1).withDayOfMonth(t.getMonthday()))) {
					System.out.println("Monatlich");
					newTask = new Task(t.getTaskDescription(), t.getDetailedTaskDescription(), null , t.getContributors(), t.getCategories(), t.getSubtasks(), t.getAttachments(), t.isRecurrent(), t.isMonthly(), t.getMonthday(), (t.getNumberOfRepetitions()-1));
					newTask.setCreationDate(t.getCreationDate().plusMonths(1).withDayOfMonth(t.getMonthday()));
					System.out.println("Ja");
					tasks.add(newTask);
					size++;
					for(Task task:tasks) {
						if(task.getTaskNumber()==t.getTaskNumber()) {
							task.setNumberOfRepetitions(0);
						}
					}
				}
			}else if(t.isWeekly()) {//wöchentlich
				System.out.println("Wöchentlich");
				newTask = new Task(t.getTaskDescription(), t.getDetailedTaskDescription(), null, t.getContributors(), t.getCategories(), t.getSubtasks(), t.getAttachments(), t.isRecurrent(), t.isWeekly(), t.getWeekday(), (t.getNumberOfRepetitions()-1));
				newTask.setCreationDate(t.getCreationDate().plusWeeks(1).with(DayOfWeek.valueOf(t.getWeekday().toString())));
				System.out.println("Ja");
				tasks.add(newTask);
				size++;
				for(Task task:tasks) {
					if(task.getTaskNumber()==t.getTaskNumber()) {
						task.setNumberOfRepetitions(0);
					}
				}
			}
		}	
	}

	public static int getSize() {
		return size;
	}

	public static void setSize(int size) {
		Taskmanager.size = size;
	}

}
