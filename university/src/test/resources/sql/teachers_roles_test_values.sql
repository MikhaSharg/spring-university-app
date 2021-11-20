INSERT INTO teachers (teacher_id, first_name, last_name, gender, email, address, age, phone_number, role, profile) VALUES 
(1L, 'Alex', 'Petrov', 'male', 'AlexPetrov@gmail.com', 'Saint Petersburg', 68, 89313262896, 'teacher', 'Professor'),
(2L, 'Anna', 'Ermakova', 'female', 'AnnaErmakova@gmail.com', 'Kaliningrad', 48, 89215895789, 'teacher', 'Assistant Lecturer'),
(3L, 'Roman', 'Sidorov', 'male', 'RomanSidorov@gmail.com', 'Moscow', 53, 89112568975, 'teacher', 'Doctor of Technical Science'),
(4L, 'Diana', 'Gukova', 'female', 'DianaGukova@gmail.com', 'Rostov', 52, 89225896325, 'teacher', 'Senior Lecturer'),
(5L, 'Dmitry', 'Solodin', 'male', 'MikhailSolodin@gmail.com', 'Andora', 56, 89052655985, 'teacher', 'Candidate of Technical Science');

INSERT INTO roles (role_id, name) VALUES
(1, 'system administrator'),
(2, 'electrician'),
(3, 'repairer'),
(4, 'methodist'),
(5, 'administrator');

INSERT INTO teachers_roles (teacher_id, role_id) VALUES
('1', '3'),
('1', '5'),
('2', '1'),
('3', '4'),
('4', '2');