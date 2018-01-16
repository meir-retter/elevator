Elevator Simulator

This program simulates a simple algorithm for an elevator system in a building. The simulation depends on the following
constants (their default values are shown)

numFloors: 18
numElevators: 3
elevatorCapacity: 10 people
timeBetweenFloors: 2 sec
timeToOpenDoors: 1 sec
timeToCloseDoors: 1 sec
timeToExitElevator: 2 sec/person
timeToEnterElevator: 3 sec/person
numberOfPeople: 200
simulationLength: 7200 sec

If you want, you can edit any of these right at the beginning of the main method.

Here's what happens (assuming the default constants): 200 times are randomly selected out of the first 4800 seconds of a
7200 second period. These are the times that a person will appear on a random floor, headed to a different random floor.

Three elevators are created: One starting on the top going down, one starting on the bottom going up, and one starting
at a random floor going in a random direction. (If there are more than three elevators, the program will simply generate
more of the random-elevators.)

The elevators begin to move, picking up any people in the same direction. It then drops them off when it reaches their
respective goal floors.

The wait times are calculated for all the people (time they left the elevator - time they appeared on the floor). After
the simulation, the average wait time is printed out.

In general, that's the only thing that will be printed. If you'd like the program to print out more details (when people
are getting on and off, which elevators are moving where, etc.) then make a command line argument called "logging". It
must be the first command line argument, and any others don't matter. The only way to edit the default values is by
editing the code, but that's easily done by going to the beginning of the main method in the Controller class.