package task;

import java.util.LinkedList;

public class Taskmanager {
	
	private LinkedList<Task> tasks;
		
	public Taskmanager() {
		tasks = new LinkedList<Task>();
	}
		
	public Taskmanager(LinkedList<Task> tasks) { //falls aus XML Datei importiert
		this.tasks = tasks;
	}
		
	public void addTask(Task task) {
		tasks.add(task);
	}
		
	public void removeTask(Task task) throws Exception {
		if(!tasks.contains(task)) {
			throw new Exception("Task does not exist!");
		}else {
			tasks.remove(task);
		}
	}
		
	public void changeTask(Task task) throws Exception {
		if(!tasks.contains(task)) {
			throw new Exception("Task does not exist!");
		}else {
			int listIndex = tasks.indexOf(task);
			tasks.get(listIndex);
		}
		
	}

}
