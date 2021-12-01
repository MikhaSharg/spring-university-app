package ua.com.foxminded.university.dao.jdbc;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.object.BatchSqlUpdate;
import org.springframework.stereotype.Repository;

import ua.com.foxminded.university.dao.AbstractCrudDao;
import ua.com.foxminded.university.dao.LectureDao;
import ua.com.foxminded.university.model.Audience;
import ua.com.foxminded.university.model.Group;
import ua.com.foxminded.university.model.Lecture;
import ua.com.foxminded.university.model.LectureSessions;
import ua.com.foxminded.university.model.Subject;
import ua.com.foxminded.university.model.Teacher;

@Repository
public class JdbcLectureDao extends AbstractCrudDao<Lecture> implements LectureDao {

	public static final String ID = "ID";
	public static final String LECTURE_DATE = "LECTURE_DATE";
	public static final String SESSION_ID = "SESSION_ID";
	public static final String AUDIENCE_ID = "AUDIENCE_ID";
	public static final String SUBJECT_ID = "SUBJECT_ID";
	public static final String TEACHER_ID = "TEACHER_ID";
	public static final String GROUP_ID = "GROUP_ID";

	public static final String SELECT_ONE_BY_ID = "SELECT l.*, ls.*, a.*, s.*, t.*, g.* \n"
			+ "FROM lectures l JOIN lecture_sessions ls USING (session_id) JOIN audiences a USING(audience_id) \n"
			+ "JOIN subjects s USING (subject_id) JOIN teachers t USING (teacher_id) JOIN groups g USING (group_id) \n"
			+ "WHERE lecture_id = ?";

	public static final String SELECT_All = "SELECT l.*, ls.*, a.*, s.*, t.*, g.* \n"
			+ "FROM lectures l JOIN lecture_sessions ls USING (session_id) JOIN audiences a USING(audience_id) \n"
			+ "JOIN subjects s USING (subject_id) JOIN teachers t USING (teacher_id) JOIN groups g USING (group_id)";

	private static final String INSERT_ONE_NAMED = "INSERT INTO lectures (lecture_date, session_id, audience_id, subject_id, teacher_id, group_id) VALUES \n"
			+ "(:LECTURE_DATE, :SESSION_ID, :AUDIENCE_ID, :SUBJECT_ID, :TEACHER_ID, :GROUP_ID)";

	private static final String UPDATE_ONE_NAMED = "UPDATE lectures SET lecture_date=:LECTURE_DATE, session_id=:SESSION_ID, audience_id=:AUDIENCE_ID, \n"
			+ "subject_id=:SUBJECT_ID, teacher_id=:TEACHER_ID, group_id=:GROUP_ID WHERE lecture_id=:ID";

	private static final String UPDATE_ONE = "UPDATE lectures SET lecture_date=?, session_id=?, audience_id=?, subject_id=?, teacher_id=?, group_id=? \n"
			+ "WHERE lecture_id=?";

	private static final String DELETE_ONE_BY_ID = "DELETE FROM lectures  WHERE lecture_id=?";

	private static final String SELECT_BY_DATE = "SELECT l.*, ls.*, a.*, s.*, t.*, g.* \n"
			+ "FROM lectures l JOIN lecture_sessions ls USING (session_id) JOIN audiences a USING(audience_id) \n"
			+ "JOIN subjects s USING (subject_id) JOIN teachers t USING (teacher_id) JOIN groups g USING (group_id) \n"
			+ "WHERE lecture_date = ?";

	private static final String SELECT_BY_DATE_RANGE = "SELECT l.*, ls.*, a.*, s.*, t.*, g.* \n"
			+ "FROM lectures l JOIN lecture_sessions ls USING (session_id) JOIN audiences a USING(audience_id) \n"
			+ "JOIN subjects s USING (subject_id) JOIN teachers t USING (teacher_id) JOIN groups g USING (group_id) \n"
			+ "WHERE lecture_date  BETWEEN ? AND ?";
	
	private static final String SELECT_FOR_GROUP_BY_DATE_RANGE = "SELECT l.*, ls.*, a.*, s.*, t.*, g.* \n"
			+ "FROM lectures l JOIN lecture_sessions ls USING (session_id) JOIN audiences a USING(audience_id) \n"
			+ "JOIN subjects s USING (subject_id) JOIN teachers t USING (teacher_id) JOIN groups g USING (group_id) \n"
			+ "WHERE lecture_date = ? AND g.group_id=?";
	
	private static final String SELECT_FOR_TEACHER_BY_DATE_RANGE = "SELECT l.*, ls.*, a.*, s.*, t.*, g.* \n"
			+ "FROM lectures l JOIN lecture_sessions ls USING (session_id) JOIN audiences a USING(audience_id) \n"
			+ "JOIN subjects s USING (subject_id) JOIN teachers t USING (teacher_id) JOIN groups g USING (group_id) \n"
			+ "WHERE lecture_date = ? AND t.teacher_id=?";
	

	public JdbcLectureDao(JdbcTemplate jdbsTemplate, RowMapper<Lecture> rowMapper) {
		super(jdbsTemplate, rowMapper);
	}

	@Override
	protected String getColumnNameWithId() {
		return "lecture_id";
	}

	@Override
	protected void declareUpdateParams(BatchSqlUpdate batchSqlUpdate) {
		batchSqlUpdate.declareParameter(new SqlParameter(Types.DATE, LECTURE_DATE));
		batchSqlUpdate.declareParameter(new SqlParameter(Types.INTEGER, SESSION_ID));
		batchSqlUpdate.declareParameter(new SqlParameter(Types.INTEGER, AUDIENCE_ID));
		batchSqlUpdate.declareParameter(new SqlParameter(Types.INTEGER, SUBJECT_ID));
		batchSqlUpdate.declareParameter(new SqlParameter(Types.INTEGER, TEACHER_ID));
		batchSqlUpdate.declareParameter(new SqlParameter(Types.INTEGER, GROUP_ID));
		batchSqlUpdate.declareParameter(new SqlParameter(Types.INTEGER, ID));
	}

	@Override
	protected void declareInsertParams(BatchSqlUpdate batchSqlUpdate) {
		batchSqlUpdate.declareParameter(new SqlParameter(Types.DATE, LECTURE_DATE));
		batchSqlUpdate.declareParameter(new SqlParameter(Types.INTEGER, SESSION_ID));
		batchSqlUpdate.declareParameter(new SqlParameter(Types.INTEGER, AUDIENCE_ID));
		batchSqlUpdate.declareParameter(new SqlParameter(Types.INTEGER, SUBJECT_ID));
		batchSqlUpdate.declareParameter(new SqlParameter(Types.INTEGER, TEACHER_ID));
		batchSqlUpdate.declareParameter(new SqlParameter(Types.INTEGER, GROUP_ID));
	}

	@Override
	protected Map<String, Object> mapUpdateParam(Lecture entity) {
		Map<String, Object> updatedParam = new HashMap<>();
		updatedParam.put(LECTURE_DATE, Date.valueOf(entity.getDate()));
		updatedParam.put(SESSION_ID, entity.getSession().getId());
		updatedParam.put(AUDIENCE_ID, entity.getAudience().getId());
		updatedParam.put(SUBJECT_ID, entity.getSubject().getId());
		updatedParam.put(TEACHER_ID, entity.getTeacher().getId());
		updatedParam.put(GROUP_ID, entity.getGroup().getId());
		updatedParam.put(ID, entity.getId());
		return updatedParam;
	}

	@Override
	protected Map<String, Object> mapInsertParam(Lecture entity) {
		Map<String, Object> insertParam = new HashMap<>();
		insertParam.put(LECTURE_DATE, Date.valueOf(entity.getDate()));
		insertParam.put(SESSION_ID, entity.getSession().getId());
		insertParam.put(AUDIENCE_ID, entity.getAudience().getId());
		insertParam.put(SUBJECT_ID, entity.getSubject().getId());
		insertParam.put(TEACHER_ID, entity.getTeacher().getId());
		insertParam.put(GROUP_ID, entity.getGroup().getId());
		return insertParam;
	}

	@Override
	protected void setInsertParams(PreparedStatement ps, Lecture entity) throws SQLException {
		ps.setDate(1, Date.valueOf(entity.getDate()));
		ps.setLong(2, entity.getSession().getId());
		ps.setLong(3, entity.getAudience().getId());
		ps.setLong(4, entity.getSubject().getId());
		ps.setLong(5, entity.getTeacher().getId());
		ps.setLong(6, entity.getGroup().getId());
	}

	@Override
	protected String getSelectByIdQuery() {
		return SELECT_ONE_BY_ID;
	}

	@Override
	protected void setUpdateParams(PreparedStatement ps, Lecture entity) throws SQLException {
		ps.setDate(1, Date.valueOf(entity.getDate()));
		ps.setLong(2, entity.getSession().getId());
		ps.setLong(3, entity.getAudience().getId());
		ps.setLong(4, entity.getSubject().getId());
		ps.setLong(5, entity.getTeacher().getId());
		ps.setLong(6, entity.getGroup().getId());
		ps.setLong(7, entity.getId());

	}

	@Override
	protected Lecture createNewWithId(long id, Lecture entity) {
		return new Lecture(id, entity.getDate(), entity.getSession(), entity.getAudience(), entity.getSubject(),
				entity.getTeacher(), entity.getGroup());
	}

	@Override
	protected Lecture createNew(Lecture entity) {
		return new Lecture(entity.getId(), entity.getDate(), entity.getSession(), entity.getAudience(),
				entity.getSubject(), entity.getTeacher(), entity.getGroup());
	}

	@Override
	protected String getUpdateQuery() {
		return UPDATE_ONE;
	}

	@Override
	protected String getUpdateOneNamedQuery() {
		return UPDATE_ONE_NAMED;
	}

	@Override
	protected String getInsertOneNamedQuery() {
		return INSERT_ONE_NAMED;
	}

	@Override
	protected String getDeleteByIdQuery() {
		return DELETE_ONE_BY_ID;
	}

	@Override
	protected SimpleJdbcInsert getJdbcInsert() {
		SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
		jdbcInsert.withTableName("lectures").setGeneratedKeyNames("lecture_id");
		return jdbcInsert;
	}

	@Override
	protected Optional<Lecture> findEntityById(Long id) {
		try {
			return Optional.ofNullable(findLectureById(id));
		} catch (Exception e) {
			return Optional.empty();
		}
	}

	protected Lecture findLectureById(Long id) {

		return jdbcTemplate.query(SELECT_ONE_BY_ID, ps -> ps.setLong(1, id), rs -> {
			Lecture lecture = null;
			if (rs.next()) {
				LectureSessions lectureSessions = new LectureSessions(rs.getLong("session_id"), rs.getString("period"),
						rs.getString("start_time"), rs.getString("end_time"));
				Audience audence = new Audience(rs.getLong("audience_id"), rs.getInt("room_number"));
				Subject subject = new Subject(rs.getLong("subject_id"), rs.getString("subject_name"));
				Teacher teacher = new Teacher(rs.getLong("teacher_id"), rs.getString("first_name"),
						rs.getString("last_name"), rs.getString("gender"), rs.getString("email"),
						rs.getString("address"), rs.getInt("age"), rs.getLong("phone_number"), rs.getString("role"),
						rs.getString("profile"));
				Group group = new Group(rs.getLong("group_id"), rs.getString("group_name"));

				lecture = new Lecture(rs.getLong("lecture_id"), rs.getDate("lecture_date").toLocalDate(),
						lectureSessions, audence, subject, teacher, group);
			}
			return lecture;

		});
	}

	@Override
	protected List<Lecture> findAllEntities() {
		return jdbcTemplate.query(SELECT_All, rs -> {

			List<Lecture> lectures = new ArrayList<>();
			while (rs.next()) {
				LectureSessions lectureSessions = new LectureSessions(rs.getLong("session_id"), rs.getString("period"),
						rs.getString("start_time"), rs.getString("end_time"));
				Audience audence = new Audience(rs.getLong("audience_id"), rs.getInt("room_number"));
				Subject subject = new Subject(rs.getLong("subject_id"), rs.getString("subject_name"));
				Teacher teacher = new Teacher(rs.getLong("teacher_id"), rs.getString("first_name"),
						rs.getString("last_name"), rs.getString("gender"), rs.getString("email"),
						rs.getString("address"), rs.getInt("age"), rs.getLong("phone_number"), rs.getString("role"),
						rs.getString("profile"));
				Group group = new Group(rs.getLong("group_id"), rs.getString("group_name"));

				Lecture lecture = new Lecture(rs.getLong("lecture_id"), rs.getDate("lecture_date").toLocalDate(),
						lectureSessions, audence, subject, teacher, group);

				lectures.add(lecture);
			}

			return lectures;
		});

	}

	@Override
	public List<Lecture> findLecturesOneDate(LocalDate date) {
		return jdbcTemplate.query(SELECT_BY_DATE, ps -> {
			ps.setDate(1, Date.valueOf(date));
		}, rs -> {

			List<Lecture> lectures = new ArrayList<>();
			while (rs.next()) {
				LectureSessions lectureSessions = new LectureSessions(rs.getLong("session_id"), rs.getString("period"),
						rs.getString("start_time"), rs.getString("end_time"));
				Audience audence = new Audience(rs.getLong("audience_id"), rs.getInt("room_number"));
				Subject subject = new Subject(rs.getLong("subject_id"), rs.getString("subject_name"));
				Teacher teacher = new Teacher(rs.getLong("teacher_id"), rs.getString("first_name"),
						rs.getString("last_name"), rs.getString("gender"), rs.getString("email"),
						rs.getString("address"), rs.getInt("age"), rs.getLong("phone_number"), rs.getString("role"),
						rs.getString("profile"));
				Group group = new Group(rs.getLong("group_id"), rs.getString("group_name"));

				Lecture lecture = new Lecture(rs.getLong("lecture_id"), rs.getDate("lecture_date").toLocalDate(),
						lectureSessions, audence, subject, teacher, group);
				lectures.add(lecture);
			}
			return lectures;
		});
	}

	@Override
	public List<Lecture> findLectureForDateRange(LocalDate startDate, LocalDate endDate) {
		return jdbcTemplate.query(SELECT_BY_DATE_RANGE, ps -> {
			ps.setDate(1, Date.valueOf(startDate));
			ps.setDate(2, Date.valueOf(endDate));
		}, rs -> {

			List<Lecture> lectures = new ArrayList<>();
			while (rs.next()) {
				LectureSessions lectureSessions = new LectureSessions(rs.getLong("session_id"), rs.getString("period"),
						rs.getString("start_time"), rs.getString("end_time"));
				Audience audence = new Audience(rs.getLong("audience_id"), rs.getInt("room_number"));
				Subject subject = new Subject(rs.getLong("subject_id"), rs.getString("subject_name"));
				Teacher teacher = new Teacher(rs.getLong("teacher_id"), rs.getString("first_name"),
						rs.getString("last_name"), rs.getString("gender"), rs.getString("email"),
						rs.getString("address"), rs.getInt("age"), rs.getLong("phone_number"), rs.getString("role"),
						rs.getString("profile"));
				Group group = new Group(rs.getLong("group_id"), rs.getString("group_name"));

				Lecture lecture = new Lecture(rs.getLong("lecture_id"), rs.getDate("lecture_date").toLocalDate(),
						lectureSessions, audence, subject, teacher, group);
				lectures.add(lecture);
			}
			return lectures;
		});
	}

	@Override
	public List<Lecture> findLecturesForGroupByDate(Long id, LocalDate date) {
		return jdbcTemplate.query(SELECT_FOR_GROUP_BY_DATE_RANGE, ps -> {
			ps.setDate(1, Date.valueOf(date));
			ps.setLong(2, id);
		}, rs -> {

			List<Lecture> lectures = new ArrayList<>();
			while (rs.next()) {
				LectureSessions lectureSessions = new LectureSessions(rs.getLong("session_id"), rs.getString("period"),
						rs.getString("start_time"), rs.getString("end_time"));
				Audience audence = new Audience(rs.getLong("audience_id"), rs.getInt("room_number"));
				Subject subject = new Subject(rs.getLong("subject_id"), rs.getString("subject_name"));
				Teacher teacher = new Teacher(rs.getLong("teacher_id"), rs.getString("first_name"),
						rs.getString("last_name"), rs.getString("gender"), rs.getString("email"),
						rs.getString("address"), rs.getInt("age"), rs.getLong("phone_number"), rs.getString("role"),
						rs.getString("profile"));
				Group group = new Group(rs.getLong("group_id"), rs.getString("group_name"));

				Lecture lecture = new Lecture(rs.getLong("lecture_id"), rs.getDate("lecture_date").toLocalDate(),
						lectureSessions, audence, subject, teacher, group);
				lectures.add(lecture);
			}
			return lectures;
		});
	}

	@Override
	public List<Lecture> findLecturesForTeacherByDate(Long teacherId, LocalDate date) {
		return jdbcTemplate.query(SELECT_FOR_TEACHER_BY_DATE_RANGE, ps -> {
			ps.setDate(1, Date.valueOf(date));
			ps.setLong(2, teacherId);
		}, rs -> {

			List<Lecture> lectures = new ArrayList<>();
			while (rs.next()) {
				LectureSessions lectureSessions = new LectureSessions(rs.getLong("session_id"), rs.getString("period"),
						rs.getString("start_time"), rs.getString("end_time"));
				Audience audence = new Audience(rs.getLong("audience_id"), rs.getInt("room_number"));
				Subject subject = new Subject(rs.getLong("subject_id"), rs.getString("subject_name"));
				Teacher teacher = new Teacher(rs.getLong("teacher_id"), rs.getString("first_name"),
						rs.getString("last_name"), rs.getString("gender"), rs.getString("email"),
						rs.getString("address"), rs.getInt("age"), rs.getLong("phone_number"), rs.getString("role"),
						rs.getString("profile"));
				Group group = new Group(rs.getLong("group_id"), rs.getString("group_name"));

				Lecture lecture = new Lecture(rs.getLong("lecture_id"), rs.getDate("lecture_date").toLocalDate(),
						lectureSessions, audence, subject, teacher, group);
				lectures.add(lecture);
			}
			return lectures;
		});
	}
}
