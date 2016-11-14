import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
        genderMap();
        printPeople();
    }

    public static void main(String[] args) {
        new Main();
        Person p = new Person("ads", "asdkm", LocalDate.now(), true);
        p.getBirthday();

    }

    private void printPeople() {
        System.err.println("");
        for (Person person : persons) { person.printPerson(); }
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
        final double thisYear = LocalDate.now().getYear();
        double average =
                persons.stream()
                        .filter(p -> !p.isMale())
                        .mapToInt(p -> (int) thisYear - p.getBirthday())
                        .average()
                        .getAsDouble();
        // Print average age among women.
        System.err.println(average + " - Average age among women\n");
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

    private void genderMap() {
        Map<Boolean, List<Person>> genderMap = persons.stream()
                .collect(Collectors.groupingBy(Person::isMale));
        // Print the map contents.
        System.err.println("As map:\n---------");
        List<Person> females = genderMap.get(false);
        List<Person> males = genderMap.get(true);
        for (Person male : males) male.printPerson();
        System.err.println("----------");
        for (Person female : females) female.printPerson();
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
