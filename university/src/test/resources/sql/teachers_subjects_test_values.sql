INSERT INTO subjects (subject_id, subject_name) VALUES
(1L, 'Theory of probability and mathematical statistics'),
(2L, 'Theoretical mechanics'),
(3L, 'Architecture'),
(4L, 'Strength of materials'),
(5L, 'SAPR');

INSERT INTO teachers (teacher_id, first_name, last_name, gender, email, address, age, phone_number, role, profile)
VALUES 
(1L, 'Alex', 'Petrov', 'male', 'AlexPetrov@gmail.com', 'Saint Petersburg', 25, 89313262896, 'teacher', 'one'),
(2L, 'Anna', 'Ermakova', 'female', 'AnnaErmakova@gmail.com', 'Kaliningrad', 26, 89215895789, 'teacher', 'two'),
(3L, 'Roman', 'Sidorov', 'male', 'RomanSidorov@gmail.com', 'Moscow', 23, 89112568975, 'teacher', 'three'),
(4L, 'Diana', 'Gukova', 'female', 'DianaGukova@gmail.com', 'Rostov', 21, 89225896325, 'teacher', 'four'),
(5L, 'Dmitry', 'Solodin', 'male', 'MikhailSolodin@gmail.com', 'Andora', 35, 89052655985, 'teacher', 'five');

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