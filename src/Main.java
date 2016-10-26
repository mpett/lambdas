import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by martinpettersson on 24/10/16.
 */
public class Main {
    private List<Person> persons;
    private final int NUMBER_OF_PERSONS = 15;

    public Main() {
        setPersons();
        averageWomanAge();
        averageAgeAsList();
        for (Person person : persons) { person.printPerson(); }
    }

    public static void main(String[] args) {
        new Main();
    }

    private void setPersons() {
        persons = new ArrayList<Person>();
        for (int person = 0; person < NUMBER_OF_PERSONS; person++) {
            double randomNumber = Math.random() * 100;
            int yearDifference = (int) randomNumber;
            boolean gender = (yearDifference % 2 == 0);
            LocalDate birthday = LocalDate.now();
            birthday = birthday.minusYears(yearDifference);
            Person p = new Person("" + person, "name_" + person, birthday, gender);
            persons.add(p);
        }
    }

    private void averageWomanAge() {
        double average =
                persons.stream()
                        .filter(p -> !p.isMale())
                        .mapToInt(Person::getBirthday)
                        .average()
                        .getAsDouble();

        // Print average age among women.
        System.err.println(LocalDate.now().getYear() - average + " - Average age among women\n");
    }

    private void averageAgeAsList() {
        List<Double> averageAsList = Stream.of(
                persons.stream()
                        .filter(p -> !p.isMale())
                        .mapToInt(Person::getBirthday)
                        .average().getAsDouble(),
                persons.stream()
                        .filter(p -> p.isMale())
                        .mapToInt(Person::getBirthday)
                        .average().getAsDouble())
                .collect(Collectors.toList());

        // Print average age as a gender separated list
        System.err.println("As list:\n---------");
        for (double averageElement : averageAsList) System.err.println(LocalDate.now().getYear() - averageElement);
        System.err.println("---------\n");
    }
}

class Person {
    private String id;
    private String name;
    private LocalDate birthday;
    private boolean isMale;

    public Person(String id, String name, LocalDate birthday, boolean isMale) {
        this.id = id;
        this.name = name;
        this.birthday = birthday;
        this.isMale = isMale;
    }

    public String getName() {
        return name;
    }

    public boolean isMale() {
        return isMale;
    }

    public int getBirthday() {
        return birthday.getYear();
    }

    public void printPerson() {
        System.err.println(id + " " + name + " " + (isMale() ? "male" : "female") + " " + birthday);
    }
}
