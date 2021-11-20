INSERT INTO lecture_sessions (session_id, period, start_time, end_time) VALUES
(1L, '1th', '8:00', '9:20'),
(2L, '2th', '9:30', '10:50'),
(3L, '3th', '11:00', '12:20'),
(4L, '4th', '13:00', '14:20'),
(5L, '5th', '14:30', '15:50'),
(6L, '6th', '16:00', '17:20');

INSERT INTO audiences (audience_id, room_number) VALUES
(1L, 100),
(2L, 101),
(3L, 102),
(4L, 103),
(5L, 104);

INSERT INTO subjects (subject_id, subject_name) VALUES
(1L, 'Theory of probability and mathematical statistics'),
(2L, 'Theoretical mechanics'),
(3L, 'Architecture'),
(4L, 'Strength of materials'),
(5L, 'SAPR');

INSERT INTO teachers (teacher_id, first_name, last_name, gender, email, address, age, phone_number, role, profile) VALUES 
(1L, 'Alex', 'Petrov', 'male', 'AlexPetrov@gmail.com', 'Saint Petersburg', 68, 89313262896, 'teacher', 'Professor'),
(2L, 'Anna', 'Ermakova', 'female', 'AnnaErmakova@gmail.com', 'Kaliningrad', 48, 89215895789, 'teacher', 'Assistant Lecturer'),
(3L, 'Roman', 'Sidorov', 'male', 'RomanSidorov@gmail.com', 'Moscow', 53, 89112568975, 'teacher', 'Doctor of Technical Science'),
(4L, 'Diana', 'Gukova', 'female', 'DianaGukova@gmail.com', 'Rostov', 52, 89225896325, 'teacher', 'Senior Lecturer'),
(5L, 'Dmitry', 'Solodin', 'male', 'MikhailSolodin@gmail.com', 'Andora', 56, 89052655985, 'teacher', 'Candidate of Technical Science');

INSERT INTO groups (group_id, group_name) VALUES 
(1L, 'AB-12'),
(2L, 'CD-34'),
(3L, 'EF-56'),
(4L, 'GH-78'),
(5L, 'IJ-90');

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

INSERT INTO lectures (lecture_id, lecture_date, session_id, audience_id, subject_id, teacher_id, group_id) VALUES 
(1L,'2021-11-11', 1L, 1L, 1L, 1L, 1L),
(2L,'2021-11-11', 2L, 2L, 2L, 2L, 2L),
(3L,'2021-11-11', 3L, 3L, 3L, 3L, 3L),
(4L,'2021-11-11', 4L, 4L, 4L, 4L, 4L),
(5L,'2021-11-11', 5L, 5L, 5L, 5L, 5L),
(6L,'2021-11-11', 6L, 3L, 2L, 4L, 3L),
(7L,'2021-11-12', 1L, 1L, 1L, 1L, 1L),
(8L,'2021-11-12', 2L, 2L, 2L, 2L, 2L),
(9L,'2021-11-12', 3L, 3L, 3L, 3L, 3L),
(10L,'2021-11-12', 4L, 4L, 4L, 4L, 4L),
(11L,'2021-11-12', 5L, 5L, 5L, 5L, 5L),
(12L,'2021-11-12', 6L, 3L, 2L, 4L, 3L),
(13L,'2021-11-13', 1L, 1L, 1L, 1L, 1L),
(14L,'2021-11-13', 2L, 2L, 2L, 2L, 2L),
(15L,'2021-11-13', 3L, 3L, 3L, 3L, 3L),
(16L,'2021-11-13', 4L, 4L, 4L, 4L, 4L),
(17L,'2021-11-13', 5L, 5L, 5L, 5L, 5L),
(18L,'2021-11-13', 6L, 3L, 2L, 4L, 3L),
(19L,'2021-11-14', 1L, 1L, 1L, 1L, 1L),
(20L,'2021-11-14', 2L, 2L, 2L, 2L, 2L),
(21L,'2021-11-14', 3L, 3L, 3L, 3L, 3L),
(22L,'2021-11-14', 4L, 4L, 4L, 4L, 4L),
(23L,'2021-11-14', 5L, 5L, 5L, 5L, 5L),
(24L,'2021-11-14', 6L, 3L, 2L, 4L, 3L),
(25L,'2021-11-15', 1L, 1L, 1L, 1L, 1L),
(26L,'2021-11-15', 2L, 2L, 2L, 2L, 2L),
(27L,'2021-11-15', 3L, 3L, 3L, 3L, 3L),
(28L,'2021-11-15', 4L, 4L, 4L, 4L, 4L),
(29L,'2021-11-15', 5L, 5L, 5L, 5L, 5L),
(30L,'2021-11-15', 6L, 3L, 2L, 4L, 3L),
(31L,'2021-11-16', 1L, 1L, 1L, 1L, 1L),
(32L,'2021-11-16', 2L, 2L, 2L, 2L, 2L),
(33L,'2021-11-16', 3L, 3L, 3L, 3L, 3L),
(34L,'2021-11-16', 4L, 4L, 4L, 4L, 4L),
(35L,'2021-11-16', 5L, 5L, 5L, 5L, 5L),
(36L,'2021-11-16', 6L, 3L, 2L, 4L, 3L);