package testing;

import org.junit.Before;
import org.junit.Test;

import junit.framework.Assert;
import people.MaintenanceCrew;

public class PeopleTest {
	MaintenanceCrew mc = new MaintenanceCrew(4,50);	
	
	@Before
	public void init() {
	
	}
	

		
	@Test
	public void getSize() {
		Assert.assertEquals(4, mc.getPersonSize());
	}
	
	@Test
	public void timer() {
		Assert.assertEquals(false, mc.timer());
	}
}	
	

