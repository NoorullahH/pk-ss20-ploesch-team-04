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
	 *@author Dino
	 */
	@Override
	public boolean equals(Object obj) {
	   if (obj == null ||this.getClass() != obj.getClass() ) {
		   return false;
	   }
	        
	   Contributor other = (Contributor) obj;
	   if (this.person == null || other.person == null || !this.person.equals(other.person)) {
	       return false;
	   }
	   
	   return true;	
	}	
	
	@Override
	public int hashCode() {
		return super.hashCode();
	}	
}
