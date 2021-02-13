package testing;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import junit.framework.Assert;
import simulation.Elevator;
import simulation.Floor;

public class ElevatorTest {
	ArrayList<Floor> allFloors = new ArrayList<Floor>();
	Elevator elevator;
	
	@Before
	public void init() {
		
		for(int i = 0; i < 8; i++) {
			Floor floor = new Floor(i);
			allFloors.add(floor);	
		elevator = new Elevator(4, allFloors);
}
	}

	
	@Test
	public void getFloor() {
		Assert.assertEquals(1, elevator.getFloor(1).getFloorNumber());
	}
}