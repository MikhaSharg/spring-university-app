CREATE TABLE groups
(
    group_id SERIAL PRIMARY KEY,
    group_name varchar(10) NOT NULL UNIQUE
);

CREATE TABLE students (
    student_id SERIAL PRIMARY KEY,
    first_name varchar(50) NOT NULL,
    last_name varchar (50) NOT NULL,
    gender varchar (50) NOT NULL,
    email varchar (50) NOT NULL,
    address varchar (50) NOT NULL,
    age int NOT NULL,
    phone_number bigint NOT NULL,
    role varchar (50),
    group_id int NOT NULL,
    CONSTRAINT FK_students_groups FOREIGN KEY (group_id) REFERENCES groups (group_id)
    );

CREATE TABLE teachers (
    teacher_id SERIAL PRIMARY KEY,
    first_name varchar(50) NOT NULL,
    last_name varchar (50) NOT NULL,
    gender varchar (50) NOT NULL,
    email varchar (50) NOT NULL,
    address varchar (50) NOT NULL,
    age int NOT NULL,
    phone_number bigint NOT NULL,
    role varchar (50),
    profile varchar(50) NOT NULL
    );
    
CREATE TABLE persons (
    person_id int GENERATED ALWAYS AS IDENTITY,
    first_name varchar(50) NOT NULL,
    last_name varchar (50) NOT NULL,
    gender varchar (50) NOT NULL,
    email varchar (50) NOT NULL,
    address varchar (50) NOT NULL,
    age int NOT NULL,
    phone_number int NOT NULL,
    role varchar (50),
    CONSTRAINT PK_persons_person_id PRIMARY KEY (person_id) 
    );
    
CREATE TABLE subjects (
    subject_id SERIAL PRIMARY KEY,
    subject_name varchar(50) NOT NULL
    );
    
CREATE TABLE teachers_subjects (
    teacher_id int REFERENCES teachers (teacher_id) ON UPDATE CASCADE ON DELETE CASCADE,
    subject_id int REFERENCES subjects (subject_id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT PK_teachers_subjects PRIMARY KEY (teacher_id, subject_id)
    );
    
CREATE TABLE audiences (
    audience_id SERIAL PRIMARY KEY,
    room_number int NOT NULL UNIQUE
    );
    
 CREATE TABLE lecture_sessions (
    session_id SERIAL PRIMARY KEY,
 	period varchar(10) NOT NULL,
    start_time varchar (10) NOT NULL UNIQUE,
    end_time varchar (10) NOT NULL UNIQUE
    );   

CREATE TABLE lectures(
    lecture_id SERIAL PRIMARY KEY,
    lecture_date date NOT NULL,
    session_id int NOT NULL,
    audience_id int NOT NULL,
    subject_id int NOT NULL,
    teacher_id int NOT NULL,
    group_id int NOT NULL,
    
    CONSTRAINT FK_lectures_sessions FOREIGN KEY (session_id) REFERENCES lecture_sessions (session_id), 
    CONSTRAINT FK_lectures_teachers FOREIGN KEY (teacher_id) REFERENCES teachers (teacher_id), 
    CONSTRAINT FK_lectures_audiences FOREIGN KEY (audience_id) REFERENCES audiences (audience_id), 
    CONSTRAINT FK_lectures_groups FOREIGN KEY (group_id) REFERENCES groups (group_id), 
    CONSTRAINT FK_lectures_subjects FOREIGN KEY (subject_id) REFERENCES subjects (subject_id)
    );

CREATE TABLE roles (
    role_id SERIAL PRIMARY KEY,
 	name varchar(20) NOT NULL
    );   

CREATE TABLE teachers_roles (
    teacher_id int REFERENCES teachers (teacher_id) ON UPDATE CASCADE ON DELETE CASCADE,
    role_id int REFERENCES roles (role_id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT PK_teachers_roles PRIMARY KEY (teacher_id, role_id)
    );
    
CREATE TABLE students_roles (
    student_id int REFERENCES students (student_id) ON UPDATE CASCADE ON DELETE CASCADE,
    role_id int REFERENCES roles (role_id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT PK_students_roles PRIMARY KEY (student_id, role_id)
    );

CREATE TABLE persons_roles (
    person_id int REFERENCES persons (person_id) ON UPDATE CASCADE ON DELETE CASCADE,
    role_id int REFERENCES roles (role_id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT PK_persons_roles PRIMARY KEY (person_id, role_id)
    );


