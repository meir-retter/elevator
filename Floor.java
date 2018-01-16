import java.util.ArrayList;

/**
 * Created by Meir on 12/9/2015.
 */
public class Floor {
    public ArrayList<Person> people;

    public Floor() {
        people = new ArrayList<Person>();
    }

    public void addPerson(Person person) {
        people.add(person);
    }

    public void removePerson(Person person) { // maybe change it to removePeople(direction)
        people.remove(person);
    }
}
