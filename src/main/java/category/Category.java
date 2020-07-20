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
	 *@author Dino
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null || this.getClass() != obj.getClass()) {
			return false;
		}

	    Category other = (Category) obj;
		if(this.name == null || other.name == null || !this.name.equals(other.name)) {
			return false;
	    }
		
	    return true;	
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}	
}
