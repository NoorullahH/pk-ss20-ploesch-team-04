package category;

public class Category {
	
	private String name;
	
	/**
     * constructor which initializes this Category
     * @param category	the name of this Category
     */
	public Category(String category) {
		this.name = category;
	}
	
	/**
     * this method returns the value of this Category
     * @return the name of this Category
     */
	public String getCategory() {
		return name;
	}
	
	/**
     * this method sets the value of this Category
     * @param category	the name of this Category
     */
	public void setCategory(String category) {
		this.name = category;
	}


	/**
	 * this method checks if the current category name is equal to 
	 * another 
	 *@param obj  another name
	 *@return indicates if the if the category names are the same
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null || this.name == null || this.getClass() != obj.getClass()) {
			return false;
		}

	    Category other = (Category) obj;
	    return this.name.equals(other.name);	
	}
	
	/**
	 * this method generates a hashCode for the category name
	 *@return int number
	 */
	@Override
	public int hashCode() {
	    return 31 * 7 + (name == null ? 0 : name.hashCode());
	}
}
