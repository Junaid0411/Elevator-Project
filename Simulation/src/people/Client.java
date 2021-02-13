package people;

public class Client extends Person {
	
	/**
	 * @param personSize the size of the person 
	 * @param timerValue the value they will use to dictate when they move to the
	 * bottom floor and leave.
	 */
	public Client(int personSize, int timerValue ) {
		this.timer = timerValue;
		this.personSize = personSize;
	}
	
}
