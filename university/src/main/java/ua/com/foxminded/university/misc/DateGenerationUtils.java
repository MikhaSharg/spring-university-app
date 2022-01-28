package ua.com.foxminded.university.misc;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import ua.com.foxminded.university.model.Audience;
import ua.com.foxminded.university.model.Group;
import ua.com.foxminded.university.model.LectureSessions;
import ua.com.foxminded.university.model.Student;
import ua.com.foxminded.university.model.Subject;
import ua.com.foxminded.university.model.Teacher;

import com.github.javafaker.Faker;

@Component
@PropertySource("classpath:generator.properties")
public class DateGenerationUtils {

	private static final Faker faker = new Faker();
	private static final List<String> aplhabet = List.of("ABCDEFJHIJKLMNOPQRSTUVWXYZ".split(""));
	private static final List<String> subjectsList = List.of("Anthropology", "Archaeology", "Biochemistry", "Chemistry",
			"Clinical Medicine", "Computer Science", "Economics", "English Language", "Literature",
			"Experimental Psychology", "Geography", "History", "History of Art", "Human Sciences", "African Studies",
			"Contemporary China Studies", "Law", "Philology and Phonetics", "Management", "Materials",
			"Mathematical Sciences", "Medical Grand Rounds", "Philosophy", "Physics",
			"Politics and International Relations", "Psychiatry", "Social Policy and Intervention", "Sociology",
			"Theology and Religion", "Theory of probability and mathematical statistics", "Theoretical mechanics",
			"Architecture", "Strength of materials", "SAPR");
	private static final List<String> teacherProfilesList = List.of("Doctor of Science ", "Candidate of Sciences",
			"Senior Scientific Associate", "Senior Lecturer", "Assistant Lecturer");

	private DateGenerationUtils() {
	}

	public static String firstname() {
		return faker.name().firstName();
	}

	public static String lastname() {
		return faker.name().lastName();
	}

	public static String[] genders = { "male", "female" };

	public static String gender() {
		return genders[(int) Math.round(Math.random())];
	}

	public static String email() {
		return faker.internet().emailAddress();
	}

	public static String address() {
		return faker.address().streetAddress();
	}

	public static Integer age(int minAge, int maxAge) {
		return ThreadLocalRandom.current().nextInt(minAge, maxAge);
	}

	public static Long phoneNumber() {
		return generatePhoneNumber();
	}

	public static Group group() {
		return new Group(String.format("%s%s-%d%d", random(aplhabet), random(aplhabet),
				ThreadLocalRandom.current().nextInt(9), ThreadLocalRandom.current().nextInt(9)));
	}

	public static Subject subject() {
		return new Subject(random(subjectsList));
	}

	public static Teacher teacher() {
		return new Teacher(firstname(), lastname(), gender(), email(), address(), age(30, 60), phoneNumber(), "teacher",
				random(teacherProfilesList));
	}

	public static <T> T random(List<T> list) {
		return list.get(ThreadLocalRandom.current().nextInt(list.size()));
	}

	public static Student student() {
		return new Student(firstname(), lastname(), gender(), email(), address(), age(20, 30), phoneNumber(),
				"student");
	}

	public static List<Audience> generateAudiences(int audienceCount) {
		List<Audience> audiences = new ArrayList<>(audienceCount);
		for (int i = 0; i < audienceCount; i++) {
			audiences.add(new Audience(i + 100));
		}
		return audiences;

	}

	public static List<LectureSessions> lectureSessions() {
		return Arrays.asList(new LectureSessions("1th", "8:00", "9:20"), new LectureSessions("2th", "9:30", "10:50"),
				new LectureSessions("3th", "11:00", "12:20"), new LectureSessions("4th", "13:00", "14:20"),
				new LectureSessions("5th", "14:30", "15:50"), new LectureSessions("6th", "16:00", "17:20"));
	}

	public static <T> List<T> generateUnique(Supplier<T> supplier, int count) {
		return Stream.generate(supplier).distinct().limit(count).collect(Collectors.toList());
	}

	private static Long generatePhoneNumber() {
		return Long.valueOf(89 + String.valueOf(ThreadLocalRandom.current().nextInt(100000000, 999999999)));
	}

	public static List<LocalDate> generateStudyDates(String holidays, String startDate, String endDate) {
		List<LocalDate[]> holidayDays = parseHolidays(holidays);
		List<LocalDate> totalDays = generateDaysByRange(LocalDate.parse(startDate), LocalDate.parse(endDate));
		List<List<LocalDate>> listOfHolidays = new ArrayList<>();
		for (LocalDate[] holiday : holidayDays) {
			listOfHolidays.add(generateDaysByRange(holiday[0], holiday[1]));
		}
		for (List<LocalDate> holiday : listOfHolidays) {
			totalDays.removeAll(holiday);
		}
		return totalDays;
	}

	private static List<LocalDate[]> parseHolidays(String holidays) {
		String preparedDates = holidays.replaceAll(" ", "");
		String[] splitedDateRanges = preparedDates.split(",");
		List<LocalDate[]> holidayRanges = new ArrayList<>();
		for (String string : splitedDateRanges) {
			String[] dates = string.split("/");
			LocalDate[] arrayOfDates = new LocalDate[2];
			arrayOfDates[0] = LocalDate.parse(dates[0]);
			arrayOfDates[1] = LocalDate.parse(dates[1]);
			holidayRanges.add(arrayOfDates);
		}
		return holidayRanges;
	}

	private static List<LocalDate> generateDaysByRange(LocalDate start, LocalDate end) {
		List<LocalDate> studyDates = new ArrayList<>();
		LocalDate day = start;
		long daysInYear = ChronoUnit.DAYS.between(day, end);
		int addedDays = 0;
		while (addedDays < daysInYear) {
			day = day.plusDays(1);

			if (day.getDayOfWeek() == DayOfWeek.SUNDAY) {
				day = day.plusDays(1);
				addedDays++;
			}
			studyDates.add(day);
			addedDays++;
		}
		return studyDates;
	}

	public static int randomInt(int from, int to) {
		return ThreadLocalRandom.current().nextInt(from, to);

	}
}
