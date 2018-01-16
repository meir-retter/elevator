import java.util.*;

/**
 * Created by Meir on 12/2/2015.
 */
public class Simulation {
    int numFloors;
    int numElevators;
    int elevatorCapacity;
    int timeBetweenFloors;
    int timeToOpenDoors;
    int timeToCloseDoors;
    int timeToExitElevator;
    int timeToEnterElevator;
    int numberOfPeople;
    int simulationLength;
    boolean logging;
    int IDcount;
    Building building;
    int timeToStopGeneratingPeople;
    ArrayList<Integer> waitTimes;
    Integer[] binaryPeopleGeneratingArray; // determines when people will be generated
    PriorityQueue<Event> EQ = new PriorityQueue<Event>();

    public Simulation(int numElevators0, int elevatorCapacity0, int timeBetweenFloors0,
                      int timeToOpenDoors0, int timeToCloseDoors0, int timeToExitElevator0, int timeToEnterElevator0,
                      int numberOfPeople0, int simulationLength0, Building building0, Boolean logging0) {
        numElevators = numElevators0;
        logging = logging0;
        elevatorCapacity = elevatorCapacity0;
        timeBetweenFloors = timeBetweenFloors0;
        timeToOpenDoors = timeToOpenDoors0;
        timeToCloseDoors = timeToCloseDoors0;
        timeToExitElevator = timeToExitElevator0;
        timeToEnterElevator = timeToEnterElevator0;
        numberOfPeople = numberOfPeople0;
        simulationLength = simulationLength0;
        IDcount = 0;
        building = building0;
        waitTimes = new ArrayList<Integer>();
        timeToStopGeneratingPeople = simulationLength * 2 / 3;
        numFloors = building.numFloors;

        binaryPeopleGeneratingArray = new Integer[timeToStopGeneratingPeople];
        for (int i = 0; i < numberOfPeople; i++) {
            binaryPeopleGeneratingArray[i] = 1;
        }
        for (int i = numberOfPeople; i < timeToStopGeneratingPeople; i++) {
            binaryPeopleGeneratingArray[i] = 0;
        }
        Collections.shuffle(Arrays.asList(binaryPeopleGeneratingArray));
    }

    public void printIfLogging(String s) {
        if (logging) System.out.println(s);
    }

    public void generatePerson(Building building, int time) {
        Random random = new Random();
        int startFloorNum = random.nextInt(numFloors);
        int goalFloorNum = startFloorNum;
        while (goalFloorNum == startFloorNum) {
            goalFloorNum = random.nextInt(numFloors); // make sure goalFloor != startFloor
        }
        int ID = IDcount;
        IDcount++;
        Person person = new Person(startFloorNum,goalFloorNum,time,ID);
        printIfLogging("Person " + ID + " just appeared on floor " + startFloorNum + " and is headed to floor " + goalFloorNum);
        building.floors[startFloorNum].addPerson(person);


    }

    public void run() {
        for (int t = 0; t < 7200; t++) {
            printIfLogging("Time " + t);
            if (t < timeToStopGeneratingPeople && binaryPeopleGeneratingArray[t] == 1) {
                generatePerson(building, t);
            }
            while (EQ.peek().time == t) {
                Event event = EQ.poll();
                if (event != null) {
                    event.act();
                }
            }
        }
    }









}
