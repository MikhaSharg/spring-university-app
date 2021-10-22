INSERT INTO subjects (subject_id, subject_name) VALUES 
(1L, 'math'),
(2L, 'astronomy'),
(3L, 'probability theory'),
(4L, 'history');

INSERT INTO teachers (teacher_id, first_name, last_name, gender, email, address, age, phone_number, role, profile)
VALUES 
(1L, 'Alex', 'Petrov', 'male', 'AlexPetrov@gmail.com', 'Saint Petersburg', 25, 89313262896, 'no', 'one'),
(2L, 'Anna', 'Ermakova', 'female', 'AnnaErmakova@gmail.com', 'Kaliningrad', 26, 89215895789, 'no', 'two'),
(3L, 'Roman', 'Sidorov', 'male', 'RomanSidorov@gmail.com', 'Moscow', 23, 89112568975, 'no', 'three'),
(4L, 'Diana', 'Gukova', 'female', 'DianaGukova@gmail.com', 'Rostov', 21, 89225896325, 'no', 'four'),
(5L, 'Dmitry', 'Solodin', 'male', 'MikhailSolodin@gmail.com', 'Andora', 35, 89052655985, 'student', 'five');

INSERT INTO teachers_subjects (teacher_id, subject_id) VALUES 
(1L, 1L),
(1L, 2L),
(1L, 3L),
(1L, 4L),
(2L, 2L),
(2L, 3L),
(3L, 4L),
(4L, 1L),
(4L, 4L),
(5L, 1L),
(5L, 3L),
(5L, 4L);