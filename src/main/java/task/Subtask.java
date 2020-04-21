package task;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class Subtask {
	
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
     * this method sets the name of this Subtask
     * @param subtask	the name of this Subtask
     */
	public void setSubtask(String subtask) {
		this.subtask = subtask;
	}
	
	/**
     * this method sets the done value of this Subtask
     * @param done	the value of done (true/false)
     */
	public void setDone(boolean done) {
		this.done.set(done);
	}
	
	/**
     * this method returns the done Property of this Subtask
     * @return done Property of this Subtask
     */
	public BooleanProperty getDone() {
		return done;
	}
	
	/**
     * this method returns the done value of this Subtask
     * @return done value of this Subtask
     */
	public boolean isDone() {
		return done.get();
	}
}
