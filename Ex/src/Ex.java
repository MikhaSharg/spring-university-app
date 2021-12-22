import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Ex {

	public static void main(String[] args) {

List <Person> persons = Arrays.asList(new Person("Alex", "male"), 
		new Person("Nata", "female"), 
		new Person("Roman", "male"), 
		new Person("Diana", "female"));
		
		Map<String,List<Person>> personMap= persons.stream().collect(Collectors.groupingBy(Person::getGender)); 
		
		System.out.println(personMap);
		
	}
}