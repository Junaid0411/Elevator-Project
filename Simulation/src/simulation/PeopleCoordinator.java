package simulation;

import java.util.ArrayList;
import java.util.Random;

import people.*;

public class PeopleCoordinator{
	ArrayList <MaintenanceCrew> allMaintenanceCrew = new ArrayList<MaintenanceCrew>();
	ArrayList <Developer> allDevelopers = new ArrayList<Developer>();
	ArrayList <Employee> allEmployees = new ArrayList<Employee>();
	ArrayList <Client> allClients  = new ArrayList<Client>();
	ArrayList <Integer> allWaitTimes  = new ArrayList<Integer>();
	Random rand = new Random();
	double p;
	double q;
	ArrayList<Floor> allFloors = new ArrayList<Floor>();
	private int totalClientsCreated;
	private int totalMaintenanceCrewCreated;
	int developersMoved;
	int employeesMoved;
	

	/**
	 * 
	 * @param p the probability of Developers and Employees moving
	 * @param q the probability of Clients being created.
	 * @param seed the seed the random random function uses.
	 */
	public PeopleCoordinator(double p, double q, int seed) {
		rand.setSeed(seed);
		this.q = q * 1000;
		this.p = p * 1000;	
		
		this.p = (int)Math.round(this.p);
		this.q = (int)Math.round(this.q);
		
		this.q *= 10;
		this.p *= 10;
		
		System.out.println(this.p);
		System.out.println(this.q);	
	}	

	
	
	public void tick() {
		tryToMoveDevelopers();
		tryToMoveEmployees();
		tryToCreateClient();
		tryToCreateMaintenanceCrew();
		leaveTimer();
		removeIfReadyToLeave();
		waitTimeLogic();
	}
	
	
	/**
	 * for all Developers, Employees, Clients and Maintenance Crews: Increment their timer 
	 * if they are not at their destination floor
	 */
	public void waitTimeLogic() {
		for(Client c: allClients) {
			if(c.getCurrentFloor() != c.getDestinationFloor()) {
				c.increaseWaitTime();
			}
			else if (c.getWaitTime() > 0) {
				allWaitTimes.add(c.getWaitTime());
				c.resetWaitTime();
			}
		}
		
		for(Employee e: allEmployees) {
			if(e.getCurrentFloor() != e.getDestinationFloor()) {
				e.increaseWaitTime();
			}
			else if (e.getWaitTime() > 0) {
				allWaitTimes.add(e.getWaitTime());
				e.resetWaitTime();
			}
		}
		
		
		for(Developer d: allDevelopers) {
			if(d.getCurrentFloor() != d.getDestinationFloor()) {
				d.increaseWaitTime();
			}
			else if (d.getWaitTime() > 0) {
				allWaitTimes.add(d.getWaitTime());
				d.resetWaitTime();
			}
		}

		
		for (MaintenanceCrew m: allMaintenanceCrew) {
			if(m.getCurrentFloor() != m.getDestinationFloor()) {
				m.increaseWaitTime();
			}
			else if (m.getWaitTime() > 0) {
				allWaitTimes.add(m.getWaitTime());
				m.resetWaitTime();
			}
		}
		
		
	}
	

	/**
	 * tries to move Employee with probability p
	 */
	public void tryToMoveEmployees() {
		for (int i = 0; i < allEmployees.size(); i++) {
			int randomNumber = rand.nextInt(1000);
			if(randomNumber < p) {
				employeeChooseFloor(allEmployees.get(i));
				employeesMoved++;
			}
		}
	}
	
	
	/**
	 * tries to move developer with probability p
	 */
	public void tryToMoveDevelopers() {
		for (int i = 0; i < allDevelopers.size(); i++) {
			int randomNumber = rand.nextInt(1000);
			if(randomNumber < p) {
				 developerChooseFloor(allDevelopers.get(i));
				 developersMoved++;
			}
		}	
	}
	
	/**
	 * tries to create Client with probability q
	 */

	public void tryToCreateClient() {
		int randomNumber = rand.nextInt(1000);
		if(randomNumber < q) {
			createClient(1);	
		}
	}
	
	/**
	 * tries to create maintenance crew with 0.005% chance
	 */
	public void tryToCreateMaintenanceCrew() {
		int randomNumber = rand.nextInt(1000);
		if(randomNumber < 5) {
			createMaintenanceCrew(1);	
		}
	}

	
	
	/**
	 * Client picks a floor in the bottom half of the building.
	 * Checks their availability then moves them to the ElevatorQueue for their current floor.
	 * @param d the Developer who is choosing a floor.
	 */
	
	public void developerChooseFloor(Developer d) {
		 int min = (int) Math.ceil(allFloors.size()/2.0);
		 int max = allFloors.size()-1; 
		 int range = max - min + 1;
		 int chosenFloor = rand.nextInt(range) + min;
		 System.out.println("Developer is moving from floor " + d.getCurrentFloor() + " to floor " + chosenFloor);
		 if (isAvailibleToMove(d, chosenFloor)) {   
			 movePersonToElevatorQueue(d, chosenFloor);
		 }
	}
	
	
	/**
	 * Employee chooses a floor anywhere in the building. Checks their availability then moves them to the
	 * ElevatorQueue for their current floor.
	 * @param e the Employee who is choosing a floor.
	 */
	public void employeeChooseFloor(Employee e) {
		int chosenFloor = rand.nextInt(allFloors.size());
		System.out.println("Employee is moving from floor " + e.getCurrentFloor() + " to floor " + chosenFloor);
		if (isAvailibleToMove(e, chosenFloor)) {
			movePersonToElevatorQueue(e, chosenFloor);
		}
	}
	
	/**
	 * Client picks a floor in the bottom half of the building.
	 * Checks their availability then moves them to the ElevatorQueue for their current floor.
	 * @param c the Client who is choosing a floor.
	 */
	public void clientChooseFloor(Client c) {
		int min = 0;
		int max = (int) Math.floor(allFloors.size()/2.0);
		System.out.println("Client max value: "  + max);
		int range = Math.round(max) - min + 1; 
		int chosenFloor = rand.nextInt(range) + min;
		System.out.println("Client is moving from floor " + c.getCurrentFloor() + " to floor " + chosenFloor);
		if (isAvailibleToMove(c, chosenFloor)) {
			movePersonToElevatorQueue(c, chosenFloor);
		}
	}
	
	/**
	 * Maintenance Crew choose a floor anywhere in the building. Checks their availability then moves them to the
	 * ElevatorQueue for their current floor.
	 * @param m the Maintenance Crew member who is choosing a floor.
	 */
	public void maintenanceCrewChooseFloor(MaintenanceCrew m){
		int chosenFloor = rand.nextInt(allFloors.size());
		System.out.println("Maintenance Crew is moving from floor " + m.getCurrentFloor() + " to floor " + chosenFloor);
		if (isAvailibleToMove(m, chosenFloor)) {
			movePersonToElevatorQueue(m, chosenFloor);
		}
	}
	

	
	/**
	 * This functions checks if the person has chosen the floor they are currently one. As well it checks they are not
	 * waiting in a queue or in an elevator
	 * @param p the person we are checking the availability of
	 * @param chosenFloor the floor that person just chose
	 * @return returns true if the person has chosen a floor that they are not currently and if that person is not current
	 * in/waiting for the elevator
	 */
	public boolean isAvailibleToMove(Person p, int chosenFloor) {
		return(p.getCurrentFloor() != chosenFloor) && (p.getCurrentFloor() == p.getDestinationFloor());
	}
	

	public void movePersonToElevatorQueue(Person p, int destinationFloor) {
		p.setDestinationFloor(destinationFloor);
		getFloor(p.getCurrentFloor()).addToElevatorQueue(p);
	}
	
	/**
	 * loops through all clients and all maintenanceCrew. If their leave timer is 0, move them to the bottom floor
	 * and set their readyToLeave to true.
	 */
	public void leaveTimer() {
		for(Client c : allClients) {
			if (c.getCurrentFloor() != 0){
				if (c.timer()){
					if(!c.getReadyToLeave()) {
						System.out.println(c.getClass().getTypeName() + " are finished and they are now leaving");
						movePersonToElevatorQueue(c,0);
					}
					c.readyToLeave();
				}
			}
		}
		
		for(MaintenanceCrew m : allMaintenanceCrew) {
			if (m.getCurrentFloor() != 0){
				if (m.timer()){
					if(!m.getReadyToLeave()) {
						System.out.println(m.getClass().getTypeName() + " are finished and they are now leaving");
						movePersonToElevatorQueue(m,0);
					}
					m.readyToLeave();
				}
			}
		}
	}

	/**
	 * loops through all clients and maintenanceCrew and checks if they are ready to leave. 
	 * if so remove them from the allCLients/allMaintenanceCrew arrays. As well as remove them for the
	 * their respective floor.
	 */
	public void removeIfReadyToLeave() {
		for(Client c : new ArrayList<>(allClients)) {
			if(c.getCurrentFloor() == 0 && c.getReadyToLeave()) {
				allClients.remove(allClients.indexOf(c));
				System.out.println("Client has left");
				getFloor(0).removeFromNonQueuedPeople(c);
			}
		}
		
		for(MaintenanceCrew m : new ArrayList<>(allMaintenanceCrew)) {
			if(m.getCurrentFloor() == 0 && m.getReadyToLeave()) {
				allMaintenanceCrew.remove(allMaintenanceCrew.indexOf(m));
				System.out.println("Maintenance Crew has left");
				getFloor(0).removeFromNonQueuedPeople(m);
			}
		}
	}
	
	
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
	 * 
	 * @param d number of developers that gets passed into the createDevelopers function
	 * @param e number of employees that gets passed into the createEmployees function
	 */
	public void createInitialPeople(int d, int e){
		createDeveloper(d);
		createEmployee(e);
	}
	
	
	/**
	 * 
	 * @param numberOfMaintenanceCrew used for controlling how many maintenance Crews are created
	 */
	public void createMaintenanceCrew(int numberOfMaintenanceCrew) {
		int min = 1200;
		int max = 2400; 
		int range = max - min + 1; 
		for(int i = 0; i < numberOfMaintenanceCrew; i++) {
			int timerValue = rand.nextInt(range) + min;
			MaintenanceCrew maintenanceCrew = new MaintenanceCrew(4, timerValue);
			System.out.println("Maintenance Crew created");	
			allMaintenanceCrew.add(maintenanceCrew);
			getFloor(0).addNonQueuedPeople(maintenanceCrew);
			maintenanceCrewChooseFloor(maintenanceCrew );
			totalMaintenanceCrewCreated++;
		}
	}
	
	
	/**
	 * 
	 * @param numberOfEmployees used for controlling how many employees are created
	 */
	public void createEmployee(int numberOfEmployees) {
		for(int i = 0; i < numberOfEmployees; i++) {
			Employee employee = new Employee(1);
			allEmployees.add(employee);
			getFloor(0).addNonQueuedPeople(employee);
			employeeChooseFloor(employee);
		}
	}
	
	
	/**
	 * 
	 * @param numberOfDevelopers used for controlling how many developers are created
	 */
	public void createDeveloper(int numberOfDevelopers) {
		for(int i = 0; i < numberOfDevelopers; i++) {
			 Developer developer = new Developer(1);
			 allDevelopers.add(developer);
			 getFloor(0).addNonQueuedPeople(developer);
			 developerChooseFloor(developer);
		}
	}
	
	/**
	 * 
	 * @param numberOfClients used for controlling how many clients are created
	 */
	public void createClient(int numberOfClients) {
		int min = 600;
		int max = 1800; 
		int range = max - min + 1; 
		for(int i = 0; i < numberOfClients; i++) {
			int timerValue = rand.nextInt(range) + min;
			Client client = new Client(1, timerValue);
			System.out.println("Client Created");	
			allClients.add(client);
			getFloor(0).addNonQueuedPeople(client);
			clientChooseFloor(client);
			totalClientsCreated++;
			
		}
	}

	public void setFloorObjects(ArrayList<Floor> allFloors){
		this.allFloors = allFloors;
	}
	

	/**
	 * prints the average wait times. Also prints how many Clients and MaintenanceCrews were created
	 * Also prints how many times Developers and Employees moved.
	 */
	
	public void printPersonStats() {
		int total = 0;
		for(int i : allWaitTimes) {
			total += i;
		}
		System.out.println("Average simulation wait time: " + total/allWaitTimes.size() + " seconds");
		System.out.println("Average number of ticks per request: " + total/allWaitTimes.size()/10);
		System.out.println("Total clients created: " + totalClientsCreated);
		System.out.println("Total maintenance Crew created: " + totalMaintenanceCrewCreated);
		System.out.println("Developers moved a total of: " + developersMoved + " times.");
		System.out.println("Employees moved a total of: " + employeesMoved + " times.");
	}


}