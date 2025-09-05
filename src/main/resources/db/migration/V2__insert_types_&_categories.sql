INSERT INTO role (role_type) VALUES ('Employee');
INSERT INTO role (role_type) VALUES ('Department_Manager');
INSERT INTO role (role_type) VALUES ('Admin');
INSERT INTO role (role_type) VALUES ('IT');
INSERT INTO department (department_name) VALUES ('team 1');
INSERT INTO users (username, email, password,   department_id, role_id)
VALUES
('Ahmed', 'ahmed@orange.com', '12345678',   1, 2);