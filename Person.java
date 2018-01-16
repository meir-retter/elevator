/**
 * Created by Meir on 12/2/2015.
 */
public class Person {
    int ID;
    int startFloor;
    int goalFloor;
    int startTime;
    int direction;

    public Person(int startFloor0, int goalFloor0, int startTime0, int ID0) {
        ID = ID0;
        startFloor = startFloor0;
        goalFloor = goalFloor0;
        startTime = startTime0;
        direction = goalFloor - startFloor;
    }
}
