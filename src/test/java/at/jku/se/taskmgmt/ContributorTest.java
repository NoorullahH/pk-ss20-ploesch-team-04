package at.jku.se.taskmgmt;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import contributor.Contributor;

public class ContributorTest {
	
	private final Contributor c = new Contributor("Chris");
	
	//Test of getPerson method, of class Contributor.
	@Test
	public void getPersonName() {
		assertEquals(c.getPerson(), "Chris");
	}
		
	//Test of setPerson method, of class Contributor.
	@Test
	public void setPersonName() {
		c.setPerson("Anna");
		assertEquals(c.getPerson(), "Anna");
	}

}
