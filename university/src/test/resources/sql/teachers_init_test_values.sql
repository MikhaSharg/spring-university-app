INSERT INTO subjects (subject_id, subject_name) VALUES 
(1L, 'math'),
(2L, 'astornomy'),
(3L, 'lenguege'),
(4L, 'history');

INSERT INTO teachers (teacher_id, first_name, last_name, gender, email, address, age, phone_number, role, profile)
VALUES 
(1L, 'Alex', 'Petrov', 'male', 'AlexPetrov@gmail.com', 'Saint Petersburg', 25, 895232, 'no', 'one'), 
(2L, 'Anna', 'Ermakova', 'female', 'AnnaErmakova@gmail.com', 'Kaliningrad', 26, 89523, 'teacher', 'two'),
(3L, 'Roman', 'Sidorov', 'male', 'RomanSidorov@gmail.com', 'Moscow', 23, 895836, 'no', 'three'),
(4L, 'Diana', 'Gukova', 'female', 'DianaGukova@gmail.com', 'Rostov', 21, 89538, 'no', 'four'),
(5L, 'Dmitry', 'Solodin', 'male', 'MikhailSolodin@gmail.com', 'Andora', 35, 8952, 'teacher', 'five');

INSERT INTO teachers_subjects (teacher_id, subject_id) VALUES 
(1L, 1L),
(1L, 2L),
(1L, 3L),
(1L, 4L),
(2L, 2L),
(2L, 3L),
(3L, 4L),
(4L, 1L),
(4L, 4L);
