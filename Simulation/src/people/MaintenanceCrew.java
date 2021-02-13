package people;

public class MaintenanceCrew extends Person {
	
	/**
	 * @param personSize the size of the person 
	 * @param timerValue the value they will use to dictate when they move to the
	 * bottom floor and leave.
	 */
	public MaintenanceCrew(int personSize, int timerValue) {
		this.timer = timerValue;
		this.personSize = personSize;
	}

}