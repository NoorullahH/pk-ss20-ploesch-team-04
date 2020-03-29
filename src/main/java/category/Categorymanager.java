package category;

import java.util.Collection;
import java.util.LinkedList;

public class Categorymanager {
	
	private LinkedList<Category> categories;
	
	public Categorymanager() {
		categories = new LinkedList<Category>();
	}
		
	public Categorymanager(LinkedList<Category> categories) { //falls aus XML Datei importiert
		this.categories = categories;
	}
		
	public void addCategory(Category category) throws Exception {
		for(Category c:categories) {
			if(c.getCategory().equals(category.getCategory())) {
				throw new Exception("Category already exists!"); //falls Kategorie bereits existiert
			}
		}
		categories.add(category);
	}
		
	public void removeCategory(Category category) throws Exception {
		if(categories.contains(category) == false) {
			throw new Exception("Category does not exist!"); //falls Kategorie nicht existiert 
		}else {
			categories.remove(category);
		}
	}
	
	public LinkedList<Category> getCategories() {
		return categories;
	}
	
	public void setCategories(LinkedList<Category> categories) {
		this.categories = categories;
	}
	
	public boolean isEmpty() {
		return categories.isEmpty();
	}
	
	public boolean containsAll(Collection<?> arg0) {
		return categories.containsAll(arg0);
	}
}
