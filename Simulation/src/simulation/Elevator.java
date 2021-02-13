package simulation;

import java.util.ArrayList;
import java.util.Collections;

import people.Person;


public class Elevator {
	
	ArrayList<Floor> allFloors = new ArrayList<Floor>();
	private static ArrayList<Integer> floorRequests = new ArrayList<Integer>(); 
	private ArrayList<Person> peopleInElevator = new ArrayList<Person>();
	private int maximumCapacity;
	private int currentCapacity = 0;
	private int currentFloor = 0;
	private int destinationFloor = 8;
	private int direction = 1;
	private boolean isMoving = false;
	private boolean doorsOpen = false;
	private boolean readyToLeave = false; 
	private boolean atFloor = true;
	
	
	/**
	 * @param maximumCapacity the maximum capacity of the elevator
	 * @param allFloors an array containing all the floors
	 */
	public Elevator(int maximumCapacity, ArrayList<Floor> allFloors) {
		this.maximumCapacity = maximumCapacity;
		this.allFloors = allFloors;
		currentFloor = 0;
	}
	
	
	public void tick() {
		System.out.println("");
		checkForNewPeopleRequests();
		checkForNewFloorRequests();
		Collections.sort(floorRequests);
		
		if(atFloor) {
			atFloor();
		}
		
		whereToGoNext();
		
		if(readyToLeave) {
			doorsOpen = false;
			readyToLeave = false;
		}
		else if(isMoving) {
			move();
		}
	
		//printFloorInformation();
	}
	
	/**
	 * This decides where the elevator should go next by looking at the direction
	 * it is going in, then choosing the closest floor in that direction. If the elevator is full,
	 * then a requests for someone in the elevator is fulfilled
	 * 
	 */
	public void whereToGoNext() {
		checkForReset();
		
		if(floorRequests.size() > 0) {
			checkForTurnAround();
		}
		
		if(currentCapacity < maximumCapacity) {
			if (direction == 1) {
				for (int i : floorRequests) {
					if(i > currentFloor) {
						if(i != destinationFloor) {
						goToFloor(i);
						break;
						}
						else {
							break;
						}
					}
				}
			}
			
			if(direction == -1) {
				for (int i = floorRequests.size()-1; i >= 0; i--) {
					if(floorRequests.get(i) < currentFloor) {
						if(floorRequests.get(i) != destinationFloor) {
							goToFloor(floorRequests.get(i));
							break;
						}
						else {
							break;
						}
					}
				}
			}		
		}
		
		else {
			doPersonRequest();
		}
	}

	
	public void doPersonRequest() {
		goToFloor(peopleInElevator.get(0).getDestinationFloor());
		
	}
	
	
	/**
	 * turns the elevator around when there are no more requests in that direction
	 */
	public void checkForTurnAround(){
		if (direction == 1) {
			direction = -1;
			for(int i : floorRequests) {
				if(i > currentFloor) {
					direction = 1;
					break;
				}
			}
		}
		
		else {
			direction = 1;
			for(int i : floorRequests) {
				if(i < currentFloor) {
					direction = -1;
					break;
				}
			}
		}
	}
	
	/**
	 * makes the elevator go to floor 0 if there are no requests.
	 */
	public void checkForReset() {
		if(floorRequests.size() == 0 && currentFloor != 0) {
			direction = -1;
			goToFloor(0);
		}
	}
	
	
	/**
	 * checks through all the floors elevatorQueues, if someone is in an elevator queue and
	 * that floor is not in the floorRequests array, then add it.
	 */
	public void checkForNewFloorRequests() {
		for(Floor f : allFloors) {
			if(f.getElevatorQueue().size() > 0 && !floorRequests.contains(f.getFloorNumber()) && f.getFloorNumber()!=currentFloor) {
				floorRequests.add(f.getFloorNumber());
			}
		}
	}
	
	
	/**
	 * checks through everyone in the elevator and adds their destinationFloor in the floorRequests 
	 * array.
	 */
	public void checkForNewPeopleRequests() {
		for(Person p : peopleInElevator) {
			if( !floorRequests.contains(p.getDestinationFloor())){
				floorRequests.add(p.getDestinationFloor());
			}
		}
	}
	
	/**
	 * @param destination the floor the elevator is to go to
	 */
	public void goToFloor(int destination) {
		this.destinationFloor = destination;
		isMoving = true;
		atFloor = false;
		//System.out.println("Elevator is going to floor: " + destinationFloor);
	}
	
	
	/**
	 * Moves the elevator.
	 * If currentFloor is equal to destinationFloor then set isMoving to false	 
	 */
	public void move() {
		currentFloor += direction;
		System.out.println("Elevator's current Floor: " + currentFloor);
		System.out.println("Reuqests queue: " + floorRequests);
		System.out.println("elevator: " + peopleInElevator);
		
		if(currentFloor == destinationFloor) {
			isMoving = false;
			doorsOpen = true;
			atFloor = true;
			
		}
		
	}
	
	public void atFloor() {
		dropOff();
		pickUp();
		readyToLeave = true;
	}

	/**
	 * Looks through the people who are in the peopleInElevator array and removes those whose 
	 * destination floor is the current floor. 
	 * 
	 * Deducts their peronSize from the currentCapacity and adds them to the
	 * currentFloor's nonqueuedPeople. 
	 * 
	 * Removes out dated request from the floorRequest queue.
	 */
	public void dropOff() {
		for (Person p : new ArrayList<>(peopleInElevator)){
			if (p.getDestinationFloor() == currentFloor){
				peopleInElevator.remove(peopleInElevator.indexOf(p));
				System.out.println("dropped Off person: " + p.getClass().getTypeName());
				currentCapacity -= p.getPersonSize();
				getFloor(currentFloor).addNonQueuedPeople(p);
				
				if(floorRequests.contains(p.getDestinationFloor())) {
					floorRequests.remove(floorRequests.indexOf(p.getDestinationFloor()));
				}
			} 
		}	
	}	
	
	/**
	 * moves people from that floor's elevtatorQueue into the elevator providing that Person's personSize 
	 * does not exceed that of the elevator's
	 * maximum capacity. 
	 * 
	 * Increases current capacity with the personSize of the Person who just entered
	 * the elevator. 
	 * 
	 * If the elevatorQueue for that floor is empty then that floor is removed from the floorRequests array.
	 */
	public void pickUp() {
		for(Person p : new ArrayList<>(getFloor(currentFloor).getElevatorQueue())) {
			if(currentCapacity + p.getPersonSize() <= maximumCapacity) {
				peopleInElevator.add(p);
				System.out.println("picked up person: " + p.getClass().getTypeName());
				currentCapacity += p.getPersonSize();
				getFloor(currentFloor).removeFromElevatorQueue(p);
			}
		}
		
		if (getFloor(currentFloor).getElevatorQueue().size() == 0 && floorRequests.contains(currentFloor)) {
			floorRequests.remove(floorRequests.indexOf(currentFloor));
		}
		
	}

	int w = 2049120399;
	
	/**
	 * this method returns a floor based on the floorNumber given
	 * @param floorNumber the floor to be return
	 * @return returns a floor
	 */
	public Floor getFloor(int floorNumber) {
		for(Floor f : allFloors) {
			if (f.getFloorNumber() == floorNumber){
				return f;
			}
		}
		return null;
	}
	
	/**
	 * print the elevatorQueue and the nonQueuedPeople array information for each floor
	 */
	public void printFloorInformation() {
		System.out.println("");
		for(Floor f : allFloors) {
			System.out.println("Floor " + f.getFloorNumber() + " Elevator queue: " + f.getElevatorQueue());
			System.out.println("Floor " + f.getFloorNumber() + " getNonQueuedPeople: " + f.getNonQueuedPeople());
		}
	}
}