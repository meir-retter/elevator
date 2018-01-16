import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Meir on 12/15/2015.
 */
public class Controller {

    public static int sum(ArrayList<Integer> A) {
        int ret = 0;
        for (int i : A) {
            ret += i;
        }
        return ret;
    }

    public static void main(String[] args) {
        int numFloors = 18;
        int numElevators = 3;
        int elevatorCapacity = 10;
        int timeBetweenFloors = 2;
        int timeToOpenDoors = 1;
        int timeToCloseDoors = 1;
        int timeToExitElevator = 2;
        int timeToEnterElevator = 3;
        int numberOfPeople = 200;
        int simulationLength = 7200;

        Building building  = new Building(numFloors);
        boolean logOrNot = false;
        if (args.length > 0) {
            if (args[0].equals("logging")) {
                logOrNot = true;
            }
        }
        Simulation simulation = new Simulation(numElevators, elevatorCapacity, timeBetweenFloors,
                timeToOpenDoors, timeToCloseDoors, timeToExitElevator, timeToEnterElevator,
                numberOfPeople, simulationLength, building, logOrNot);

        Elevator[] elevators = new Elevator[simulation.numElevators];
        // put one elevator on bottom floor going up,
        // and another on top floor going down
        // put rest of elevators randomly in other floors
        elevators[0] = new Elevator(1,0,0,simulation);
        if (simulation.numElevators > 1){
            elevators[simulation.numElevators - 1] = new Elevator(-1, simulation.numFloors - 1,
                    simulation.numElevators - 1, simulation);
        }
        if (simulation.numElevators > 2) {
            Random random = new Random();
            for (int i = 1; i < simulation.numElevators - 1; i++) {
                elevators[i] = new Elevator(new int[]{-1, 1}[random.nextInt(2)],
                        random.nextInt(simulation.numFloors), i, simulation);
            }
        }

        for (Elevator elevator : elevators) {
            simulation.EQ.add(new Event(Event.EventType.MOVE, 0, elevator, building, simulation));
        }
        simulation.run();
        System.out.println("Average wait time: " +
                sum(simulation.waitTimes)/(float)simulation.waitTimes.size() + " seconds");
    }
}
