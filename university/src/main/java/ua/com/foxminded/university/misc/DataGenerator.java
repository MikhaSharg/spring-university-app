package ua.com.foxminded.university.misc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import ua.com.foxminded.university.dao.*;
import ua.com.foxminded.university.model.*;

import static java.util.stream.Collectors.toMap;
import static ua.com.foxminded.university.misc.DateGenerationUtils.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
public class DataGenerator {

	private static final Logger log = LoggerFactory.getLogger(DataGenerator.class);

	private final StudentDao studentDao;
	private final GroupDao groupDao;
	private final TeacherDao teacherDao;
	private final SubjectDao subjectDao;
	private final AudienceDao audienceDao;
	private final LectureSessionsDao lectureSessionsDao;
	private final LectureDao lectureDao;
	private final GeneratorConfig config;

	public DataGenerator(StudentDao studentDao, GroupDao groupDao, TeacherDao teacherDao, SubjectDao subjectDao,
			AudienceDao audienceDao, LectureSessionsDao lectureSessionsDao, LectureDao lectureDao,
			GeneratorConfig config) {
		this.studentDao = studentDao;
		this.groupDao = groupDao;
		this.teacherDao = teacherDao;
		this.subjectDao = subjectDao;
		this.audienceDao = audienceDao;
		this.lectureSessionsDao = lectureSessionsDao;
		this.lectureDao = lectureDao;
		this.config = config;
	}

	@Transactional
	public void generate() {
		log.info("Generating data using config {}", config);
		generate(config);
	}

	@Transactional
	public void generate(GeneratorConfig config) {

		List<Group> groups = groupDao.saveAll(generateUnique(DateGenerationUtils::group, config.getGroupsCount()));
		log.info("Generated {} groups", groups.size());

		List<Student> students = generateUnique(DateGenerationUtils::student, config.getStudentCount());
		students = studentDao.saveAll(
				allocateStudents(groups, students, config.getGroupsMinStudents(), config.getGroupsMaxStudnets()));
		log.info("Generated {} students", students.size());

		List<Teacher> teachers = teacherDao
				.saveAll(generateUnique(DateGenerationUtils::teacher, config.getTeacherCount()));
		log.info("Generated {} teachers", teachers.size());

		List<Subject> subjects = subjectDao
				.saveAll(generateUnique(DateGenerationUtils::subject, config.getTeacherCount()
						* config.getMaxSubjectCountForOneTeacher() / config.getMaxTeacherCountForOneSubject()));
		log.info("Generated {} teacherss", teachers.size());

		allocateSubjetsToTeachers(teachers, subjects, config.getMaxSubjectCountForOneTeacher(),
				config.getMaxTeacherCountForOneSubject());

		List<Audience> audiences = audienceDao.saveAll(generateAudiences(config.getAudienceCount()));
		log.info("Generated {} audiences", audiences.size());

		List<LectureSessions> sessions = lectureSessionsDao.saveAll(lectureSessions());
		log.info("Generated {} sessions", sessions.size());

		List<Teacher> teachersWithSubjects = teacherDao.findAll();

		List<LocalDate> studyDays = generateStudyDates(config.getHolidays());
		log.info("Generated {} study days", studyDays.size());

		List<Lecture> lectures = new ArrayList<>();

		studyDays.stream().forEach(date -> {

			for (LectureSessions session : sessions) {

				List<Integer> indexOfTeachers = new ArrayList<>();
				List<Integer> indexOfAudiences = new ArrayList<>();
				for (int i = 0; i < teachersWithSubjects.size(); i++) {
					indexOfTeachers.add(i);
				}

				for (int i = 0; i < audiences.size(); i++) {
					indexOfAudiences.add(i);
				}

				Collections.shuffle(indexOfTeachers);
				Collections.shuffle(indexOfAudiences);

				for (Group group : groups) {

					int indexOfTeacher = indexOfTeachers.remove(0);
					int indexOfAudience = indexOfAudiences.remove(0);
					Lecture lecture = new Lecture(date, session, audiences.get(indexOfAudience),
							teachersWithSubjects.get(indexOfTeacher).getSubjects()
									.get(randomInt(0, teachersWithSubjects.get(indexOfTeacher).getSubjects().size())),
							teachers.get(indexOfTeacher), group);
					lectures.add(lecture);
				}
			}

		});
		lectureDao.saveAll(lectures);
		log.info("Generated {} lectures", lectures.size());
	}

	private List<Teacher> allocateSubjetsToTeachers(List<Teacher> teachers, List<Subject> subjects,
			Integer maxSubjectCountForOneTeacher, Integer maxTeacherCountForOneSubject) {
		List<ArrayList<Subject>> subjectsList = new ArrayList<>();
		List<Teacher> teachersWithSubjects = new ArrayList(teachers);
		for (int i = 0; i < maxTeacherCountForOneSubject; i++) {
			subjectsList.add(new ArrayList(subjects));
		}
		int indexListSubject = 0;
		for (Teacher teacher : teachersWithSubjects) {
			for (int i = 0; i < maxSubjectCountForOneTeacher; i++) {
				if (indexListSubject == maxTeacherCountForOneSubject) {
					indexListSubject = 0;
				}
				subjectDao.addSubjectToTeacher(teacher.getId(), subjectsList.get(indexListSubject).remove(0).getId());
			}
			indexListSubject++;
		}
		return teachersWithSubjects;
	}

	private List<Student> allocateStudents(List<Group> groups, List<Student> students, Integer minStudentsCount,
			Integer maxStudentsCount) {
		List<Student> unallocatedStudents = new ArrayList<>(students);
		Collections.shuffle(unallocatedStudents);

		if (students.size() < groups.size() * minStudentsCount) {
			throw new IllegalArgumentException("Too few students, try to increase total students count!");
		}
		int studentIndex = unallocatedStudents.size();
		for (Group group : groups) {
			for (int i = 0; i < minStudentsCount; i++) {
				Student student = unallocatedStudents.remove(--studentIndex);
				student.setGroupId(group.getId());
			}
		}

		Map<Group, Integer> countMap = groups.stream().collect(toMap(g -> g, g -> minStudentsCount));
		for (Student student : unallocatedStudents) {
			Group group;
			int attempts = 5;
			do {
				group = random(groups);
				attempts--;
				if (attempts == 0) {
					long min = countMap.values().stream().mapToLong(it -> it).min().orElseThrow();
					if (min <= maxStudentsCount) {
						throw new IllegalArgumentException("Too few students, try to increase total students count!");
					}
				}
			} while (countMap.get(group) >= maxStudentsCount);
			student.setGroupId(group.getId());
			countMap.put(group, countMap.getOrDefault(group, 0) + 1);
		}
		return new ArrayList<>(students);
	}
}
