package FilterView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.function.Predicate;

import category.Category;
import contributor.Contributor;
import javafx.collections.ObservableList;
import task.Task;

/**
 * @author Dino
 *
 */
public class FilterBuilder {

	// filter description
	/**
	 * @param description
	 * @return
	 */
	public static Predicate<Task> description(String description) {
		return task -> task.getTaskDescription().contains(description);
	}

	// filter detail_desc
	/**
	 * @param detail_desc
	 * @return
	 */
	public static Predicate<Task> detail_desc(String detail_desc) {
		return task -> task.getDetailedTaskDescription().contains(detail_desc);
	}

	// filter date
	/**
	 * @param from
	 * @param until
	 * @return
	 */
	public static Predicate<Task> date_filter(LocalDate from, LocalDate until) {
		if (from == null && until == null) {
			return x -> true;
		} else if (until == null) {
			return task -> task.getDueDate().isAfter(from);
		} else if (from != null) {
			return task -> task.getDueDate().isBefore(until);

		} else {
			return task -> task.getDueDate().isAfter(from) && task.getDueDate().isBefore(until);

		}
	}

	// filter category
	/**
	 * @param categories
	 * @return
	 */
	public static Predicate<Task> category(ObservableList<Category> categories) {
		return task -> task.getCategoriesList().containsAll(categories);
	}

	// filter contributes
	/**
	 * @param Contributors
	 * @return
	 */
	public static Predicate<Task> contributes(ObservableList<Contributor> Contributors) {
		return task -> task.getContributorsList().containsAll(Contributors);
	}

	// filter attachment
	/**
	 * @param attachment
	 * @return
	 */
	public static Predicate<Task> attachment(String attachment) {
		if(attachment==null) {return null;};

		if (attachment.equals("yes")) {
			return task -> task.getAttachments().size() != 0;
		} else {
			return task -> task.getAttachments().size() == 0;
		}
	}

	// filter status (open , closed)
	/**
	 * @param status
	 * @return
	 */
	public static Predicate<Task> status(String status) {
		if(status==null) {return null;};
		if (status.equals("open")) {
			return task -> task.getDone().getValue()== false;
		} else {
			return task -> task.getDone().getValue()== true;
		}
	}

}
