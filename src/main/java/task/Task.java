package task;

import java.time.LocalDate;
import java.util.Comparator;
import javax.xml.bind.annotation.XmlElement;

import javafx.beans.property.SimpleStringProperty;

public class Task implements Comparator<Task> {

	@XmlElement
	private String taskDescription;
	@XmlElement
	private String detailsDescription;
	@XmlElement
	private String contributors;
	@XmlElement
	private String category;
	@XmlElement
	private String attachment;
	@XmlElement
	private LocalDate date;

	public Task(String taskDescription, String detailsDescription, String contributors, String category,
			String attachment, LocalDate date) {
		super();
		this.taskDescription = taskDescription;
		this.detailsDescription = detailsDescription;
		this.contributors = contributors;
		this.category = category;
		this.attachment = attachment;
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

	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public int compare(Task o1, Task o2) {
		// TODO Auto-generated method stub
		return 0;
	}

}
