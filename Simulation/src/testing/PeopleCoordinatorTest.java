package testing;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import junit.framework.Assert;
import simulation.Elevator;
import simulation.Floor;
import simulation.PeopleCoordinator;

public class PeopleCoordinatorTest {
	PeopleCoordinator peopleCoordinator = new PeopleCoordinator(0.002, 0.003, 84625493);
	ArrayList<Floor> allFloors = new ArrayList<Floor>();
	

		@Before
		public void init() {
			
			for(int i = 0; i < 8; i++) {
				Floor floor = new Floor(i);
				allFloors.add(floor);	
			}
			
			peopleCoordinator.setFloorObjects(allFloors);
		}
		
		@Test
		public void getFloor() {
			Assert.assertEquals(1, peopleCoordinator.getFloor(1).getFloorNumber());
		}

}
