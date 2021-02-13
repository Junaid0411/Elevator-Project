package simulation;

import java.util.ArrayList;

public class Building {
	private ArrayList <Elevator> allElevators = new ArrayList<Elevator>();
	ArrayList<Floor> allFloors = new ArrayList<Floor>();	

	
	/**
	 * Creates all the floor objects
	 * @param elevatorCapacity 
	 * @param peopleCoordinator
	 * @param initialDeveloperCount
	 * @param initialEmployeeCount
	 */
	public Building(int elevatorCapacity, PeopleCoordinator peopleCoordinator, int initialDeveloperCount, int initialEmployeeCount, int numberOfFloors) {
		
		for(int i = 0; i < numberOfFloors; i++) {
			Floor floor = new Floor(i);
			allFloors.add(floor);	
		}
		
		
		allElevators.add(new Elevator(elevatorCapacity, allFloors));
		
		
		peopleCoordinator.setFloorObjects(allFloors);
		peopleCoordinator.createInitialPeople(initialDeveloperCount, initialEmployeeCount);
	}
	
	/**
	 * calls tick on all the elevator objects
	 */
	public void tick() {
		for(Elevator e : allElevators) {
			e.tick();
		}
	}
}
