INSERT INTO role (role_type) VALUES ('Employee');           -- role_id = 1
INSERT INTO role (role_type) VALUES ('Department_Manager'); -- role_id = 2
INSERT INTO role (role_type) VALUES ('Admin');              -- role_id = 3
INSERT INTO role (role_type) VALUES ('IT');                 -- role_id = 4

INSERT INTO department (department_name) VALUES ('team 1');

--  'password123'
INSERT INTO users (username, email, password, department_id, role_id)
VALUES
    ('Ahmed Manager', 'manager@orange.com', '$2a$12$KxbfmLRj7wwfuWLS524qIugqjXMyPkETp2QVVukGdFH8z3Ys8AicC', 1, 2),

    ('Salma Admin', 'admin@orange.com', '$2a$12$KxbfmLRj7wwfuWLS524qIugqjXMyPkETp2QVVukGdFH8z3Ys8AicC', 1, 3),

    ('Karim IT', 'it@orange.com', '$2a$12$KxbfmLRj7wwfuWLS524qIugqjXMyPkETp2QVVukGdFH8z3Ys8AicC', 1, 4),

    ('Mona Employee', 'employee@orange.com', '$2a$12$KxbfmLRj7wwfuWLS524qIugqjXMyPkETp2QVVukGdFH8z3Ys8AicC', 1, 1);