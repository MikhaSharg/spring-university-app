INSERT INTO groups (group_id, group_name) VALUES 
(1L, 'AB-12'),
(2L, 'CD-34'),
(3L, 'EF-56'),
(4L, 'GH-78'),
(5L, 'IJ-90');

INSERT INTO teachers (teacher_id, first_name, last_name, gender, email, address, age, phone_number, role, profile) VALUES 
(1L, 'Alex', 'Petrov', 'male', 'AlexPetrov@gmail.com', 'Saint Petersburg', 52, 89313262896, 'no', 'one'),
(2L, 'Anna', 'Ermakova', 'female', 'AnnaErmakova@gmail.com', 'Kaliningrad', 45, 89215895789, 'no', 'two'),
(3L, 'Roman', 'Sidorov', 'male', 'RomanSidorov@gmail.com', 'Moscow', 60, 89112568975, 'no', 'three'),
(4L, 'Diana', 'Gukova', 'female', 'DianaGukova@gmail.com', 'Rostov', 37, 89225896325, 'no', 'four'),
(5L, 'Dmitry', 'Solodin', 'male', 'MikhailSolodin@gmail.com', 'Andora', 32, 89052655985, 'student', 'five');

INSERT INTO subjects (subject_id, subject_name) VALUES
(1L, 'Theory of probability and mathematical statistics'),
(2L, 'Theoretical mechanics'),
(3L, 'Architecture'),
(4L, 'Strength of materials'),
(5L, 'SAPR');

INSERT INTO audiences (audience_id, room_number) VALUES
(1L, 100),
(2L, 101),
(3L, 102),
(4L, 103),
(5L, 104);

INSERT INTO lectures (lecture_id, lecture_name, lecture_date, teacher_id, audience_id, group_id, subject_id) VALUES
('')


