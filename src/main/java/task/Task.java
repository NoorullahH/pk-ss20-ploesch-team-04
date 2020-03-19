package task;

import java.time.LocalDate;
import java.util.Comparator;
import javax.xml.bind.annotation.XmlElement;

import javafx.beans.property.SimpleStringProperty;

public class Task{

	
	private String taskDescription;
	
	private String detailsDescription;
	
	private String contributors;
	
	private String category;
	
	//private String attachment;
	
	private LocalDate date;
	
	
	public Task() {
		
	}

	public Task(String taskDescription, String detailsDescription, String contributors, String category, LocalDate date) {
		super();
		this.taskDescription = taskDescription;
		this.detailsDescription = detailsDescription;
		this.contributors = contributors;
		this.category = category;
		this.date = date;
	}

	public String getTaskDescription() {
		return taskDescription;
	}

	public void setTaskDescription(String taskDescription) {
		this.taskDescription = taskDescription;
	}

	public String getDetailsDescription() {
		return detailsDescription;
	}

	public void setDetailsDescription(String detailsDescription) {
		this.detailsDescription = detailsDescription;
	}

	public String getContributors() {
		return contributors;
	}

	public void setContributors(String contributors) {
		this.contributors = contributors;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}


}
