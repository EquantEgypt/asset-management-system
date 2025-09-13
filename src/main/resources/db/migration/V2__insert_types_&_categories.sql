INSERT INTO role (role_type) VALUES ('Employee');           -- role_id = 1
INSERT INTO role (role_type) VALUES ('Department_Manager'); -- role_id = 2
INSERT INTO role (role_type) VALUES ('Admin');              -- role_id = 3
INSERT INTO role (role_type) VALUES ('IT');                 -- role_id = 4

INSERT INTO department (department_name) VALUES
('team 1'),
('team 2'),
('team 3');



--  'Password123##'
INSERT INTO users (username, email, password, department_id, role_id)
VALUES
    ('Ahmed Manager', 'manager@orange.com', '$2a$12$xBzMKeBDytHqS/94JgbB0u8iJGAa0zC0qBlNHr1PZs1i8n16LtiGS', 1, 2),
    ('Salma Admin', 'admin@orange.com', '$2a$12$xBzMKeBDytHqS/94JgbB0u8iJGAa0zC0qBlNHr1PZs1i8n16LtiGS', 1, 3),
    ('Karim IT', 'it@orange.com', '$2a$12$xBzMKeBDytHqS/94JgbB0u8iJGAa0zC0qBlNHr1PZs1i8n16LtiGS', 1, 4),
    ('Mona Employee', 'employee@orange.com', '$2a$12$xBzMKeBDytHqS/94JgbB0u8iJGAa0zC0qBlNHr1PZs1i8n16LtiGS', 1, 1),
    ('Mona IT', 'THIS@orange.com', '$2a$12$xBzMKeBDytHqS/94JgbB0u8iJGAa0zC0qBlNHr1PZs1i8n16LtiGS', 3, 4),
    ('Mona MANAGER', 'THIS_M@orange.com', '$2a$12$xBzMKeBDytHqS/94JgbB0u8iJGAa0zC0qBlNHr1PZs1i8n16LtiGS', 3, 2),

    ('malak Manager', 'M_manager@orange.com', '$2a$12$xBzMKeBDytHqS/94JgbB0u8iJGAa0zC0qBlNHr1PZs1i8n16LtiGS', 2, 2);