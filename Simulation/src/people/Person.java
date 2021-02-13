package people;

public class Person {
	int satisfactionTimer;
	int currentFloor = 0;
	int destinationFloor = 0;
	int waitTimeSeconds = 0;
	int timer;
	int personSize;
	boolean readyToLeave = false;
	
	public int getCurrentFloor() {
		return currentFloor;
	} 

	
	public void setCurrentFloor(int f) {
		currentFloor = f;
	}
	
	public int getDestinationFloor() {
		return destinationFloor;
	}
	
	public void setDestinationFloor(int d) {
		destinationFloor = d;
	}
	
	public int getPersonSize() {
		return personSize;
	}
	
	public void readyToLeave() {
		readyToLeave = true;
	}
	
	public boolean getReadyToLeave() {
		return readyToLeave;
	}
	
	public void increaseWaitTime() {
		waitTimeSeconds += 10;
	}
	
	public int getWaitTime() {
		return waitTimeSeconds;
	}
	
	public void resetWaitTime() {
		waitTimeSeconds = 0;
	}

	/**
	 * increments the timer.
	 * @return returns true if the timer is equal to or less than 0
	 */
	public boolean timer() {
		timer-=10;
		return (timer <= 0);
	}


}
