package testing;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import simulation.Elevator;
import simulation.PeopleCoordinator;

@RunWith(Suite.class)
@SuiteClasses({ElevatorTest.class, PeopleTest.class, PeopleCoordinatorTest.class})
public class AllTests {

}
