package at.jku.se.taskmgmt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import contributor.Contributor;

public class ContributorTest {
	
	private final Contributor c = new Contributor("Chris");
	
	/**
     * Test of getPerson method, of class Contributor
     * checks if the person name is queried correctly
     */
	@Test
	public void getPersonName() {
		assertEquals(c.getPerson(), "Chris");
	}
	
	/**
     * Test of setPerson method, of class Contributor.
     * checks if the person name is changed correctly
     */
	@Test
	public void setPersonName() {
		c.setPerson("Anna");
		assertEquals(c.getPerson(), "Anna");
	}

}
