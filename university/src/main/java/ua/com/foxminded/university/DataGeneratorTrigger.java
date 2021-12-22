package ua.com.foxminded.university;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import ua.com.foxminded.university.dao.LectureDao;
import ua.com.foxminded.university.misc.DataGenerator;

@Component
public class DataGeneratorTrigger implements ApplicationRunner {

	private final DataGenerator dataGenerator;
	private final LectureDao lectureDao;

	public DataGeneratorTrigger(DataGenerator dataGenerator, LectureDao lectureDao) {
		super();
		this.dataGenerator = dataGenerator;
		this.lectureDao = lectureDao;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		if (lectureDao.findAll().isEmpty()) {
			dataGenerator.generate();
		}

	}

}
