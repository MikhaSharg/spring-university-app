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
    room_number int NOT NULL UNIQUE,
    CONSTRAINT PK_audiences_audience_id PRIMARY KEY (audience_id)
    );

CREATE TABLE lectures_shedule (
    lecture_id int GENERATED ALWAYS AS IDENTITY,
    lecture_name varchar(50) NOT NULL,
    lecture_date date NOT NULL,
    audience_id int NOT NULL,
    group_id int NOT NULL,
    subject_id int NOT NULL,
    CONSTRAINT PK_lectures_shedule_id PRIMARY KEY (lecture_id),
    CONSTRAINT FK_lectures_audiences FOREIGN KEY (audience_id) REFERENCES audiences (audience_id), 
    CONSTRAINT FK_lectures_groups FOREIGN KEY (group_id) REFERENCES groups (group_id), 
    CONSTRAINT FK_lectures_subjects FOREIGN KEY (subject_id) REFERENCES subjects (subject_id)
    )









