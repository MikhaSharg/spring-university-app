INSERT INTO subjects (subject_id, subject_name) VALUES 
(1L, 'math'),
(2L, 'astronomy'),
(3L, 'probability theory'),
(4L, 'history');

INSERT INTO teachers (teacher_id, first_name, last_name, gender, email, address, age, phone_number, role, profile)
VALUES 
(1L, 'Alex', 'Petrov', 'male', 'AlexPetrov@gmail.com', 'Saint Petersburg', 25, 89313262896, 'no', 'one');

INSERT INTO teachers_subjects (teacher_id, subject_id) VALUES 
(1L, 1L),
(1L, 2L),
(1L, 3L),
(1L, 4L);
