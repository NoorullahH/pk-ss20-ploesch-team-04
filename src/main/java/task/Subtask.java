package task;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 * Class which creates a subtask
 * @author Mara
 */
public class Subtask {
	
	private String task;
	private BooleanProperty done = new SimpleBooleanProperty(false);
	
	/**
     * constructor which initializes this Subtask
     * @param subtask	the name of this Subtask
     */
	public Subtask(String subtask) {
		this.task = subtask;
		this.done.set(false);
	}

	/**
     * this method returns this Subtask
     * @return the name of this Subtask
     */
	public String getSubtask() {
		return task;
	}

	/**
     * this method sets the name of this Subtask
     * @param subtask	the name of this Subtask
     */
	public void setSubtask(String subtask) {
		this.task = subtask;
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
