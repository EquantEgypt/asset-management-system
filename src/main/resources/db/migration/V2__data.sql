
INSERT INTO role (role_type) VALUES
('Admin'),
('Department_Manager'),
('IT'),
('Employee');


INSERT INTO department (department_name, manager_id) VALUES
('IT Department', 2),
('HR Department', 2),
('Finance Department', 2);

INSERT INTO users (name, email, password, role_id, department_id) VALUES
('Alice Admin', 'alice.admin@example.com', 'hashed_password1', 1, 1),   -- Admin in IT Dept
('Bob Manager', 'bob.manager@example.com', 'hashed_password2', 2, 2),   -- Department Manager in HR
('Charlie IT', 'charlie.it@example.com', 'hashed_password3', 3, 1),     -- IT role in IT Dept
('Eve Employee', 'MalakZiad@gmail.com', 'hashed_password4', 4, 3); -- Employee in Finance

INSERT INTO category (category_name) VALUES
('IT'),
('Furniture'),
('Equipment');

INSERT INTO type (type_name, category_id) VALUES
('Laptop', 1),
('Desktop', 1),
('Office Chair', 2),
('Projector', 3);

INSERT INTO assets (asset_name, asset_description, category_id, type_id, status) VALUES
('Dell XPS 13', 'Lightweight business laptop', 1, 1, 'available'),
('HP EliteDesk', 'High-performance desktop', 1, 2, 'assigned'),
('ErgoChair 2', ' office chair', 2, 3, 'available'),
('Epson Projector', 'HD projector for meetings', 3, 4, 'maintenance');

INSERT INTO asset_request (user_id, asset_id, status) VALUES
(4, 1, 'new_asset'),
(3, 4, 'maintenance');
