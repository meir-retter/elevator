/**
 * Created by Meir on 12/9/2015.
 */
public class Building {
    Floor[] floors;
    int numFloors;

    public Building(int numFloors0) {
        numFloors = numFloors0;
        floors = new Floor[numFloors];
        for (int i = 0; i < numFloors; i++) {
            floors[i] = new Floor();
        }
    }
}
