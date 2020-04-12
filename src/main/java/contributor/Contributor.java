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
	
}
