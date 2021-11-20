package ua.com.foxminded.university.dao.mappers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ua.com.foxminded.university.model.Lecture;

@ExtendWith(MockitoExtension.class)
class LectureMapperTest {

	@Mock
	ResultSet resultSet;

	@InjectMocks
	LectureMapper lectureMapper;
	
	@Test
	void shouldMapRow() throws SQLException {
		when(resultSet.getLong("lecture_id")).thenReturn(1L, 2L);
		when(resultSet.getDate("lecture_date")).thenReturn(Date.valueOf(LocalDate.of(2021, 10, 10)), 
				Date.valueOf(LocalDate.of(2021, 10, 10)));
		
		Lecture expected1 = new Lecture(1L, LocalDate.of(2021, 10, 10));
		Lecture expected2 = new Lecture(2L, LocalDate.of(2021, 10, 10));
		
		assertThat(lectureMapper.mapRow(resultSet, ThreadLocalRandom.current().nextInt())).isEqualTo(expected1);
		assertThat(lectureMapper.mapRow(resultSet, ThreadLocalRandom.current().nextInt())).isEqualTo(expected2);
	}

}
