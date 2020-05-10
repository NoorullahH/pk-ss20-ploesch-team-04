package category;

public class Category {
	
	private String category;
	
	/**
     * constructor which initializes this Category
     * @param category	the name of this Category
     */
	public Category(String category) {
		this.category = category;
	}
	
	/**
     * this method returns the value of this Category
     * @return the name of this Category
     */
	public String getCategory() {
		return category;
	}
	
	/**
     * this method sets the value of this Category
     * @param category	the name of this Category
     */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 *@author Dino
	 */
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		   if (obj == null) {
	            return false;
	        }

	        if (!Category.class.isAssignableFrom(obj.getClass())) {
	            return false;
	        }

	        final Category other = (Category) obj;
	        if ((this.category == null) ? (other.category != null) : !this.category.equals(other.category)) {
	            return false;
	        }

	        return true;	
	        }	
	
		
}
