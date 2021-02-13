package simulation;

public class Simulator {
	Building building;
	PeopleCoordinator peopleCoordinator;
	
	public Simulator(double p, double q, int initialDeveloperCount, int initialEmployeeCount, int numberOfTicks, int ticksPerMinute, int elevatorCapacity, int seed, boolean completeInstantly, int numberOfFloors) {
		peopleCoordinator = new PeopleCoordinator(p, q, seed);
		building = new Building(elevatorCapacity, peopleCoordinator, initialDeveloperCount, initialEmployeeCount, numberOfFloors);
		run(numberOfTicks, ticksPerMinute, completeInstantly);
	}
	
	long z = 3;
	
	public void run(int numberOfTicks, int ticksPerMinute, boolean completeInstantly) {
		int simulationTime = 0;
		for (int i = 0; i < numberOfTicks; i++) {
			if (!completeInstantly){
				try {
					Thread.sleep(60000/ticksPerMinute);
				} catch (InterruptedException ie) {
					Thread.currentThread().interrupt();
				}
			}
			tick();
			simulationTime +=10;
			if(simulationTime % 200 == 0) {
				System.out.println("\n                                                                   "
						                                                                             
						                                                                                   
					+ "Simulation seconds: " + simulationTime +   ". Ticks: " + simulationTime/10);
			}
		}
		
		peopleCoordinator.printPersonStats();
	}
	
	
	
	
	
	public void tick() {
		building.tick();
		peopleCoordinator.tick();
	}
}

