INSERT INTO groups (group_id, group_name) 
VALUES 
(1, 'AB-12'),
(2, 'CD-34');

INSERT INTO students (student_id, first_name, last_name, gender, email, address, age, phone_number, role, group_id)
VALUES 
(1, 'Alex', 'Petrov', 'male', 'AlexPetrov@gmail.com', 'Saint Petersburg', 25, 89523268951, 'student', 1), 
(2, 'Anna', 'Ermakova', 'female', 'AnnaErmakova@gmail.com', 'Kaliningrad', 26, 8952328575, 'student', 2),
(3, 'Roman', 'Sidorov', 'male', 'RomanSidorov@gmail.com', 'Moscow', 23, 89583658547, 'student', 1),
(4, 'Diana', 'Gukova', 'female', 'DianaGukova@gmail.com', 'Rostov', 21, 89538792563, 'student', 2);

INSERT INTO roles (role_id, name) VALUES
(1, 'system administrator'),
(2, 'electrician'),
(3, 'repairer'),
(4, 'methodist'),
(5, 'administrator');

INSERT INTO students_roles (student_id, role_id) VALUES
('1', '3'),
('1', '5'),
('2', '1'),
('3', '4'),
('4', '2');