package ua.com.foxminded.university.dao.jdbc;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.object.BatchSqlUpdate;
import org.springframework.stereotype.Repository;

import ua.com.foxminded.university.dao.AbstractCrudDao;
import ua.com.foxminded.university.dao.TeacherDao;
import ua.com.foxminded.university.model.Teacher;
import ua.com.foxminded.university.model.Subject;

@Repository
public class JdbcTeacherDao extends AbstractCrudDao<Teacher> implements TeacherDao {

	public static final String ID = "ID";
	public static final String FIRST_NAME = "FIRST_NAME";
	public static final String LAST_NAME = "LAST_NAME";
	public static final String GENDER = "GENDER";
	public static final String EMAIL = "EMAIL";
	public static final String ADDRESS = "ADDRESS";
	public static final String AGE = "AGE";
	public static final String PHONE_NUMBER = "PHONE_NUMBER";
	public static final String ROLE = "ROLE";
	public static final String PROFILE = "PROFILE";

	private static final String UPDATE_ONE = "UPDATE teachers SET first_name=?, last_name=?, gender=?, email=?, address=?, age=?, phone_number=?, role=?, profile=? \n"
			+ "WHERE teacher_id=?";
	private static final String SELECT_BY_ID = "SELECT * FROM teachers WHERE teacher_id=?";
	private static final String DELETE_BY_ID = "DELETE FROM teachers WHERE teacher_id=?";
	private static final String INSERT_ONE_NAMED = "INSERT INTO teachers (first_name, last_name, gender, email, address, age, phone_number, role, profile) \n"
			+ "VALUES (:FIRST_NAME, :LAST_NAME, :GENDER, :EMAIL, :ADDRESS, :AGE, :PHONE_NUMBER, :ROLE, :PROFILE)";
	private static final String UPDATE_ONE_NAMED = "UPDATE teachers SET first_name=:FIRST_NAME, last_name=:LAST_NAME, gender=:GENDER, email=:EMAIL, address=:ADDRESS, \n"
			+ "age=:AGE, phone_number=:PHONE_NUMBER, role=:ROLE, profile=:PROFILE  WHERE teacher_id=:ID";
	private static final String SELECT_ONE_BY_ID = "SELECT t.*, s.* FROM teachers t LEFT JOIN teachers_subjects ts ON t.teacher_id = ts.teacher_id LEFT JOIN subjects s \n"
			+ "ON s.subject_id = ts.subject_id WHERE t.teacher_id = ?";
	private static final String SELECT_All_SUBJECTS = "SELECT t.*, s.* FROM teachers t LEFT JOIN teachers_subjects ts ON t.teacher_id = ts.teacher_id LEFT JOIN subjects s \n"
			+ "ON s.subject_id = ts.subject_id";
	private static final String SELECT_All_TEACHERS_BY_SUBJECT_ID = "SELECT t.* FROM teachers t JOIN teachers_subjects ts USING (teacher_id) WHERE ts.subject_id = ?";
	private static final Object FIRED = "fired_teacher";

	
	public JdbcTeacherDao(JdbcTemplate jdbsTemplate, RowMapper<Teacher> rowMapper) {
		super(jdbsTemplate, rowMapper);
	}

	@Override
	protected void setInsertParams(PreparedStatement ps, Teacher entity) throws SQLException {
		ps.setString(1, entity.getFirstName());
		
		ps.setString(2, entity.getLastName());
		ps.setString(3, entity.getGender());
		ps.setString(4, entity.getEmail());
		ps.setString(5, entity.getAddress());
		ps.setInt(6, entity.getAge());
		ps.setLong(7, entity.getPhoneNumber());
		ps.setString(8, entity.getRole());
		ps.setString(9, entity.getProfile());

	}

	@Override
	protected void setUpdateParams(PreparedStatement ps, Teacher entity) throws SQLException {
		ps.setString(1, entity.getFirstName());
		ps.setString(2, entity.getLastName());
		ps.setString(3, entity.getGender());
		ps.setString(4, entity.getEmail());
		ps.setString(5, entity.getAddress());
		ps.setInt(6, entity.getAge());
		ps.setLong(7, entity.getPhoneNumber());
		ps.setString(8, entity.getRole());
		ps.setString(9, entity.getProfile());
		ps.setLong(10, entity.getId());
	}

	@Override
	protected Teacher createNewWithId(long id, Teacher entity) {
		return new Teacher(id, entity.getFirstName(), entity.getLastName(), entity.getGender(), entity.getEmail(),
				entity.getAddress(), entity.getAge(), entity.getPhoneNumber(), entity.getRole(), entity.getProfile());
	}

	@Override
	protected Teacher createNew(Teacher entity) {
		return new Teacher(entity.getId(), entity.getFirstName(), entity.getLastName(), entity.getGender(),
				entity.getEmail(), entity.getAddress(), entity.getAge(), entity.getPhoneNumber(), entity.getRole(),
				entity.getProfile());

	}

	@Override
	protected Map<String, Object> mapInsertParam(Teacher entity) {
		Map<String, Object> insertParam = new HashMap<>();
		insertParam.put(FIRST_NAME, entity.getFirstName());
		insertParam.put(LAST_NAME, entity.getLastName());
		insertParam.put(GENDER, entity.getGender());
		insertParam.put(EMAIL, entity.getEmail());
		insertParam.put(ADDRESS, entity.getAddress());
		insertParam.put(AGE, entity.getAge());
		insertParam.put(PHONE_NUMBER, entity.getPhoneNumber());
		insertParam.put(ROLE, entity.getRole());
		insertParam.put(PROFILE, entity.getProfile());
		return insertParam;
	}

	@Override
	protected Map<String, Object> mapUpdateParam(Teacher entity) {
		Map<String, Object> updatedParam = new HashMap<>();
		updatedParam.put(ID, entity.getId());
		updatedParam.put(FIRST_NAME, entity.getFirstName());
		updatedParam.put(LAST_NAME, entity.getLastName());
		updatedParam.put(GENDER, entity.getGender());
		updatedParam.put(EMAIL, entity.getEmail());
		updatedParam.put(ADDRESS, entity.getAddress());
		updatedParam.put(AGE, entity.getAge());
		updatedParam.put(PHONE_NUMBER, entity.getPhoneNumber());
		updatedParam.put(ROLE, entity.getRole());
		updatedParam.put(PROFILE, entity.getProfile());
		return updatedParam;
	}

	@Override
	protected void declareInsertParams(BatchSqlUpdate batchSqlUpdate) {
		batchSqlUpdate.declareParameter(new SqlParameter(Types.VARCHAR, FIRST_NAME));
		batchSqlUpdate.declareParameter(new SqlParameter(Types.VARCHAR, LAST_NAME));
		batchSqlUpdate.declareParameter(new SqlParameter(Types.VARCHAR, GENDER));
		batchSqlUpdate.declareParameter(new SqlParameter(Types.VARCHAR, EMAIL));
		batchSqlUpdate.declareParameter(new SqlParameter(Types.VARCHAR, ADDRESS));
		batchSqlUpdate.declareParameter(new SqlParameter(Types.INTEGER, AGE));
		batchSqlUpdate.declareParameter(new SqlParameter(Types.BIGINT, PHONE_NUMBER));
		batchSqlUpdate.declareParameter(new SqlParameter(Types.VARCHAR, ROLE));
		batchSqlUpdate.declareParameter(new SqlParameter(Types.VARCHAR, PROFILE));
	}

	@Override
	protected void declareUpdateParams(BatchSqlUpdate batchSqlUpdate) {
		batchSqlUpdate.declareParameter(new SqlParameter(Types.VARCHAR, FIRST_NAME));
		batchSqlUpdate.declareParameter(new SqlParameter(Types.VARCHAR, LAST_NAME));
		batchSqlUpdate.declareParameter(new SqlParameter(Types.VARCHAR, GENDER));
		batchSqlUpdate.declareParameter(new SqlParameter(Types.VARCHAR, EMAIL));
		batchSqlUpdate.declareParameter(new SqlParameter(Types.VARCHAR, ADDRESS));
		batchSqlUpdate.declareParameter(new SqlParameter(Types.INTEGER, AGE));
		batchSqlUpdate.declareParameter(new SqlParameter(Types.BIGINT, PHONE_NUMBER));
		batchSqlUpdate.declareParameter(new SqlParameter(Types.VARCHAR, ROLE));
		batchSqlUpdate.declareParameter(new SqlParameter(Types.VARCHAR, PROFILE));
		batchSqlUpdate.declareParameter(new SqlParameter(Types.INTEGER, ID));

	}

	@Override
	protected String getInsertOneNamedQuery() {
		return INSERT_ONE_NAMED;
	}

	@Override
	protected String getUpdateOneNamedQuery() {
		return UPDATE_ONE_NAMED;
	}

	@Override
	protected String getUpdateQuery() {
		return UPDATE_ONE;
	}

	@Override
	protected String getSelectByIdQuery() {
		return SELECT_BY_ID;
	}

	@Override
	protected String getDeleteByIdQuery() {
		return DELETE_BY_ID;
	}

	@Override
	protected SimpleJdbcInsert getJdbcInsert() {
		SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
		jdbcInsert.withTableName("teachers").setGeneratedKeyNames("teacher_id");
		return jdbcInsert;
	}

	@Override
	protected String getColumnNameWithId() {
		return "teacher_id";
	}

	@Override
	protected Optional<Teacher> findEntityById(Long id) {
		try {
			return Optional.ofNullable(findTeacherById(id));

		} catch (Exception e) {
			return Optional.empty();
		}
	}

	public Teacher findTeacherById(Long id) {

		return jdbcTemplate.query(SELECT_ONE_BY_ID, ps -> ps.setLong(1, id), rs -> {

			List<Subject> subjects = new ArrayList<>();
			Teacher teacher = null;
			while (rs.next()) {
				subjects.add(new Subject(rs.getLong("subject_id"), rs.getString("subject_name")));
				if (rs.getRow() == 1) {
					teacher = new Teacher(rs.getLong("teacher_id"), rs.getString("first_name"),
							rs.getString("last_name"), rs.getString("gender"), rs.getString("email"),
							rs.getString("address"), rs.getInt("age"), rs.getLong("phone_number"), rs.getString("role"),
							rs.getString("profile"));
				}
			}
			if (subjects.get(0).getName() == null) {
				subjects.clear();
			}

			teacher.setSubjects(subjects);

			return pointFiredTeacher(teacher);
		});

	}

	@Override
	protected List<Teacher> findAllEntities() {
		return findAllTeachers();
	}

	public List<Teacher> findAllTeachers() {
		return jdbcTemplate.query(SELECT_All_SUBJECTS, rs -> {

			Long id = null;
			int countId = 1;
			List<Teacher> teachers = new ArrayList<>();

			while (rs.next()) {

				if (id == null && countId == 1) {
					id = rs.getLong("teacher_id");

					teachers.add(new Teacher(rs.getLong("teacher_id"), rs.getString("first_name"),
							rs.getString("last_name"), rs.getString("gender"), rs.getString("email"),
							rs.getString("address"), rs.getInt("age"), rs.getLong("phone_number"), rs.getString("role"),
							rs.getString("profile")));
					teachers.get(teachers.size() - 1)
							.addSubject(new Subject(rs.getLong("subject_id"), rs.getString("subject_name")));

					if (teachers.get(teachers.size() - 1).getSubjects().get(0).getName() == null) {
						teachers.get(teachers.size() - 1).getSubjects().clear();
					}
					countId++;
				}

				else if (id == rs.getLong("teacher_id") && countId != 1 && !rs.isLast()) {
					teachers.get(teachers.size() - 1)
							.addSubject(new Subject(rs.getLong("subject_id"), rs.getString("subject_name")));
					if (teachers.get(teachers.size() - 1).getSubjects().get(0).getName() == null) {
						teachers.get(teachers.size() - 1).getSubjects().clear();
					}

				}

				else if (rs.isLast()) {
					if (id == rs.getLong("teacher_id")) {
						teachers.get(teachers.size() - 1)
								.addSubject(new Subject(rs.getLong("subject_id"), rs.getString("subject_name")));

						if (teachers.get(teachers.size() - 1).getSubjects().get(0).getName() == null) {
							teachers.get(teachers.size() - 1).getSubjects().clear();
						}
					} else {
						teachers.add(new Teacher(rs.getLong("teacher_id"), rs.getString("first_name"),
								rs.getString("last_name"), rs.getString("gender"), rs.getString("email"),
								rs.getString("address"), rs.getInt("age"), rs.getLong("phone_number"),
								rs.getString("role"), rs.getString("profile")));
						teachers.get(teachers.size() - 1)
								.addSubject(new Subject(rs.getLong("subject_id"), rs.getString("subject_name")));
						if (teachers.get(teachers.size() - 1).getSubjects().get(0).getName() == null) {
							teachers.get(teachers.size() - 1).getSubjects().clear();
						}
					}
				}

				else if (id != rs.getLong("teacher_id")) {

					id = rs.getLong("teacher_id");
					teachers.add(new Teacher(rs.getLong("teacher_id"), rs.getString("first_name"),
							rs.getString("last_name"), rs.getString("gender"), rs.getString("email"),
							rs.getString("address"), rs.getInt("age"), rs.getLong("phone_number"), rs.getString("role"),
							rs.getString("profile")));
					teachers.get(teachers.size() - 1)
							.addSubject(new Subject(rs.getLong("subject_id"), rs.getString("subject_name")));
					if (teachers.get(teachers.size() - 1).getSubjects().get(0).getName() == null) {
						teachers.get(teachers.size() - 1).getSubjects().clear();
					}

				}

			}
			return pointeFiredTeacher(teachers);
		});
	}

	@Override
	public List<Teacher> findAllTeachersBySubjectId(Long subjectId) {
		return jdbcTemplate.query(SELECT_All_TEACHERS_BY_SUBJECT_ID, ps -> ps.setLong(1, subjectId), rs -> {
			List <Teacher> teachers = new ArrayList<>();
			while(rs.next()) {
				teachers.add(new Teacher(rs.getLong("teacher_id"), rs.getString("first_name"),
						rs.getString("last_name"), rs.getString("gender"), rs.getString("email"),
						rs.getString("address"), rs.getInt("age"), rs.getLong("phone_number"),
						rs.getString("role"), rs.getString("profile")));
			}
			
			return pointeFiredTeacher(teachers);
		});
	}
	
	private List<Teacher> pointeFiredTeacher (List<Teacher> teachers) {
		List<Teacher> pointedFiredLectures = new ArrayList<>();
		
		if(!teachers.isEmpty()) {
		teachers.stream().forEach(teach-> {
			
			if(teach.getRole().equals(FIRED)) {
				
		teach.setIsFired(true);
		pointedFiredLectures.add(teach);
		
		} 
			
			else {pointedFiredLectures.add(teach);}
			
			
			
			
			
		});
		return pointedFiredLectures;
		} return teachers;
	}
	
	private Teacher pointFiredTeacher (Teacher teacher) {
		Teacher pointingTeacher = teacher;
		if(pointingTeacher.getRole().equals(FIRED)) {
			pointingTeacher.setIsFired(true);
		}
		return pointingTeacher;	
		}
}
	
