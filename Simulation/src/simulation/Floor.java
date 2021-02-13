package simulation;

import people.Person;
import java.util.ArrayList;


public class Floor {
	private ArrayList<Person> elevatorQueue = new ArrayList<Person>();
	private ArrayList<Person> nonQueuedPeople = new ArrayList<Person>();
	int floorNumber;
	
	public Floor(int floorNumber) {
		this.floorNumber = floorNumber;
	}

	public void removeFromElevatorQueue(Person p) {
		if(elevatorQueue.contains(p)) {
			elevatorQueue.remove(elevatorQueue.indexOf(p));
		}	
	}
	
	public void removeFromNonQueuedPeople(Person p){
		if(nonQueuedPeople.contains(p)) {
			nonQueuedPeople.remove(nonQueuedPeople.indexOf(p));
		}
	}
	
	public void addToElevatorQueue(Person p) {
		elevatorQueue.add(p);
		removeFromNonQueuedPeople(p);
	}
	
	public void addNonQueuedPeople(Person p) {
		nonQueuedPeople.add(p);
		p.setCurrentFloor(floorNumber);
	}
	
	public ArrayList<Person> getElevatorQueue() {
		return elevatorQueue;
	}
	
	public ArrayList<Person> getNonQueuedPeople() {
		return nonQueuedPeople;
	}
	
	public int getFloorNumber() {
		return floorNumber;
	}

}
