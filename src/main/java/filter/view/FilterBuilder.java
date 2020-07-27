package filter.view;

import java.time.LocalDate;
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
	public static Predicate<Task> detailDesc(String detailDesc) {
		return task -> task.getDetailedTaskDescription().contains(detailDesc);
	}

	// filter date
	/**
	 * @param from
	 * @param until
	 * @return
	 */
	public static Predicate<Task> dateFilter(LocalDate from, LocalDate until) {
		if (from == null && until == null) {
			return x -> true;
		} else if (until == null) {
			return task -> task.getDueDate().isAfter(from);
		} else if (from == null) {
			return task -> task.getDueDate().isBefore(until);
		} else {
			return task -> (task.getDueDate().isAfter(from) || task.getDueDate().equals(from)) && (task.getDueDate().isBefore(until) || task.getDueDate().equals(until));

		}
	}
	
	// filter date
		/**
		 * @param from
		 * @return
		 */
		public static Predicate<Task> dateFilterDay(LocalDate from) {
			if (from == null ) {
				return x -> true;
			}  else {
				return task -> task.getDueDate().isEqual(from);
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
	public static Predicate<Task> contributes(ObservableList<Contributor> contributors) {
		return task -> task.getContributorsList().containsAll(contributors);
	}
	
	// filter contributes
		/**
		 * @param Contributors
		 * @return
		 */
		public static Predicate<Task> mulContributes() {
			return task -> task.getContributorsList().size() > 1;
		}

	// filter attachment
	/**
	 * @param attachment
	 * @return
	 */
	public static Predicate<Task> attachment(String attachment) {
		if(attachment==null) {
			return null;
		}
		if ("yes".equals(attachment)) {
			return task -> !task.getAttachmentsList().isEmpty();
		} else {
			return task -> task.getAttachmentsList().isEmpty();
		}
	}

	// filter status (open , closed)
	/**
	 * @param status
	 * @return
	 */
	public static Predicate<Task> status(String status) {
		if(status==null) {
			return null;
		}
		if ("open".equals(status)) {
			return task -> task.getDone().getValue()== false;
		} else {
			return task -> task.getDone().getValue()== true;
		}
	}

}
