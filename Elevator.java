/**
 * Created by Meir on 12/2/2015.
 */
public class Elevator {
    int ID;
    int currentDirection; // negative means going down, positive means going up
    int currentFloor;
    int capacity;
    int currentNumOfPeople;
    Person[] people;
    Simulation simulation;


    public Elevator(int initialDirection, int initialFloor, int ID0, Simulation simulation0) {
        ID = ID0;
        currentDirection = initialDirection;
        currentFloor = initialFloor;
        simulation = simulation0;
        capacity = simulation.elevatorCapacity;
        people = new Person[capacity];
        currentNumOfPeople = 0;
    }

    public int numPeopleWithGoal(int goal) {
        int ret = 0;
        for (Person p : people) {
            if (p != null && p.goalFloor == goal) {
                ret++;
            }
        }
        return ret;
    }

    public void addPerson(Person person) {
        if (currentNumOfPeople < capacity) {
            people[currentNumOfPeople] = person;
            currentNumOfPeople++;
        }
    }

    public void removePeople(int currentTime) { // remove all people that should be getting off at this floor
        if (currentNumOfPeople == 0) return;
        int timeTakenInMethod = 0;
        for (int i = 0; i < currentNumOfPeople; i++) {
            if (people[i].goalFloor == currentFloor) {
                simulation.printIfLogging("Person " + people[i].ID + " got off at floor " + currentFloor);
                simulation.printIfLogging("The number of people on Elevator " + ID + " is now " + (currentNumOfPeople-1));
                timeTakenInMethod += simulation.timeToExitElevator;

//                System.out.println("Adding to waitTimes: " + (currentTime - people[i].startTime));
//                System.out.println("currentTime was " + currentTime);
//                System.out.println("startTime of person was " + people[i].startTime);
                simulation.waitTimes.add(currentTime + timeTakenInMethod - people[i].startTime);
                people[i] = null;
                currentNumOfPeople--;
            }
        }
        Person[] tempArr = new Person[capacity];
        int tempPointer = 0;
        for (int i = 0; i < capacity; i++) {
            if (people[i] != null) {
                tempArr[tempPointer] = people[i];
                tempPointer++;
            }
        }
        people = tempArr.clone();
    }

    public void reverseDirection() {
        simulation.printIfLogging("Elevator " + ID + " reversed direction");
        currentDirection *= -1;
    }







}
