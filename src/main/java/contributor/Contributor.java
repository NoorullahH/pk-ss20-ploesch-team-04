package contributor;

public class Contributor {
	
	private String person;
	
	/**
     * constructor which initializes this Contributor
     * @param person	the name of this Contributor
     */
	public Contributor(String person) {
		this.person = person;
	}
	
	/**
     * this method returns this person
     * @return the name of this Contributor
     */
	public String getPerson() {
		return person;
	}
	
	/**
     * this method sets this person
     * @param person	this name of the Contributor
     */
	public void setPerson(String person) {
		this.person = person;
	}
	
	/**
	 * this method checks if the current contributor name is equal to 
	 * another 
	 *@param obj  another name
	 *@return indicates if the if the contributor names are the same
	 */
	@Override
	public boolean equals(Object obj) {
	   if (obj == null ||this.person == null || this.getClass() != obj.getClass() ) {
		   return false;
	   }
	        
	   Contributor other = (Contributor) obj;
	   return this.person.equals(other.person);	
	}	
	
	/**
	 * this method generates a hashCode for the contributor name
	 *@return int number
	 */
	@Override
	public int hashCode() {
	    return 31 * 7 + (person == null ? 0 : person.hashCode());
	}
}
