INSERT INTO groups (group_id, group_name) 
VALUES 
(1, 'AB-12'),
(2, 'CD-34');

INSERT INTO students (student_id, first_name, last_name, gender, email, address, age, phone_number, role, group_id)
VALUES 
(1, 'Alex', 'Petrov', 'male', 'AlexPetrov@gmail.com', 'Saint Petersburg', 25, 89523268951, 'student', 1), 
(2, 'Anna', 'Ermakova', 'female', 'AnnaErmakova@gmail.com', 'Kaliningrad', 26, 8952328575, 'student', 2),
(3, 'Roman', 'Sidorov', 'male', 'RomanSidorov@gmail.com', 'Moscow', 23, 89583658547, 'student', 1),
(4, 'Diana', 'Gukova', 'female', 'DianaGukova@gmail.com', 'Rostov', 21, 89538792563, 'student', 2),
(5, 'Dmitry', 'Solodin', 'male', 'MikhailSolodin@gmail.com', 'Andora', 35, 89528769523, 'student', 1);
