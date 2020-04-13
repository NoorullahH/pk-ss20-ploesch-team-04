package task;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class Subtask {
	
	//Subtask besteht aus Text und der Checkbox
	private String subtask;
	private BooleanProperty done = new SimpleBooleanProperty(false);
	
	/**
     * constructor which initializes this Subtask
     * @param subtask	the name of this Subtask
     */
	public Subtask(String subtask) {
		this.subtask = subtask;
		this.done.set(false);
	}

	/**
     * this method returns this Subtask
     * @return the name of this Subtask
     */
	public String getSubtask() {
		return subtask;
	}

	/**
     * this method sets this Subtask
     * @param subtask	the name of this Subtask
     */
	public void setSubtask(String subtask) {
		this.subtask = subtask;
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
}
