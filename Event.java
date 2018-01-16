import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Meir on 12/2/2015.
 */
public class Event implements Comparable<Event>{
    int time;
    EventType type;
    Elevator elevatorToAffect;
    Building building;
    Simulation simulation;

    enum EventType {
        LET_ON,
        LET_OFF,
        OPEN,
        CLOSE,
        MOVE
    }

    public int compareTo(Event otherEvent) {
        if (time == otherEvent.time) {
            Random random = new Random();
            return new int[]{1,-1}[random.nextInt(2)];
        } else {
            return time - otherEvent.time;
        }
    }

    public Event(EventType type0, int time0, Elevator elevatorToAffect0, Building building0, Simulation simulation0) {
        time = time0;
        type = type0;
        building = building0;
        simulation = simulation0;
        elevatorToAffect = elevatorToAffect0;
    }

    public void act() {
        if (type == EventType.LET_ON) {
            int numPeopleGettingOn = 0;
            ArrayList<Person> peopleToRemoveFromFloor = new ArrayList<Person>();
            for (Person p : building.floors[elevatorToAffect.currentFloor].people) {
                if (p.direction * elevatorToAffect.currentDirection > 0) { // if they're going in the same direction
                    peopleToRemoveFromFloor.add(p);
                    elevatorToAffect.addPerson(p);
                    numPeopleGettingOn++;
                    simulation.printIfLogging("Person " + p.ID + " entered Elevator " + elevatorToAffect.ID);
                    simulation.printIfLogging("The number of people on Elevator " + elevatorToAffect.ID + " is now " +
                            elevatorToAffect.currentNumOfPeople);
                }
            }
            for (Person p : peopleToRemoveFromFloor) {
                building.floors[elevatorToAffect.currentFloor].removePerson(p);
            }
            simulation.EQ.add(new Event(EventType.CLOSE, time + numPeopleGettingOn * simulation.timeToEnterElevator,
                    elevatorToAffect, building, simulation));


        } else if (type == EventType.LET_OFF) {
            int numPeopleGettingOff = elevatorToAffect.numPeopleWithGoal(elevatorToAffect.currentFloor);
            elevatorToAffect.removePeople(time);
            simulation.EQ.add(new Event(EventType.LET_ON, time + numPeopleGettingOff * simulation.timeToExitElevator,
                    elevatorToAffect, building, simulation));
        } else if (type == EventType.OPEN) {
            simulation.EQ.add(new Event(EventType.LET_OFF, time + simulation.timeToOpenDoors, elevatorToAffect,
                    building, simulation));

        } else if (type == EventType.CLOSE) {
            simulation.EQ.add(new Event(EventType.MOVE, time + simulation.timeToCloseDoors, elevatorToAffect,
                    building, simulation));
        } else if (type == EventType.MOVE) {
            if ((elevatorToAffect.currentDirection < 0 && elevatorToAffect.currentFloor == 0) ||
                    (elevatorToAffect.currentDirection > 0 && elevatorToAffect.currentFloor == building.numFloors-1)) {
                elevatorToAffect.reverseDirection();
                if (building.floors[elevatorToAffect.currentFloor].people.size() == 0) {
                    simulation.EQ.add(new Event(EventType.MOVE, time, elevatorToAffect,
                            building, simulation));
                } else {
                    simulation.EQ.add(new Event(EventType.OPEN, time + simulation.timeBetweenFloors, elevatorToAffect,
                            building, simulation));
                }
            } else {
                if (elevatorToAffect.currentDirection < 0) {
                    simulation.printIfLogging("Elevator " + elevatorToAffect.ID + " moved from floor " +
                            elevatorToAffect.currentFloor + " to floor " + (elevatorToAffect.currentFloor - 1));
                    elevatorToAffect.currentFloor--;
                }
                if (elevatorToAffect.currentDirection > 0) {
                    simulation.printIfLogging("Elevator " + elevatorToAffect.ID + " moved from floor " +
                            elevatorToAffect.currentFloor + " to floor " + (elevatorToAffect.currentFloor + 1));
                    elevatorToAffect.currentFloor++;
                }
                if (building.floors[elevatorToAffect.currentFloor].people.size() > 0 ||
                        elevatorToAffect.numPeopleWithGoal(elevatorToAffect.currentFloor) > 0) {
                    simulation.EQ.add(new Event(EventType.OPEN, time + simulation.timeBetweenFloors, elevatorToAffect,
                            building, simulation));
                } else { // no one's on the floor
                    simulation.EQ.add(new Event(EventType.MOVE, time + simulation.timeBetweenFloors, elevatorToAffect,
                            building, simulation));
                }

            }



        }
    }


}
