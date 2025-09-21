-- Departments
INSERT INTO department (name) VALUES ('IT'), ('HR'), ('Finance');

-- Roles
INSERT INTO role (name) VALUES ('ADMIN'), ('IT'), ('EMPLOYEE'), ('DEPARTMENT_MANAGER');

-- Admin user
INSERT INTO users (username, full_name, email, password, department_id, role_id, phone, hire_date, is_active, created_at, updated_at)
VALUES (
   'admin',
   'System Administrator',
   'admin@orange.com',
   '$2a$12$QGAysO8P4/rHSRfbN7wVFuRC58yxVnfSqBYb6Dam5qDe2kRyWyZAq',
   1,                 -- IT department
   1,                 -- ADMIN role
   '01000000001',
   '2020-01-01',
   TRUE,
   NOW(),
   NOW()
);

-- IT user
INSERT INTO users (username, full_name, email, password, department_id, role_id, phone, hire_date, is_active, created_at, updated_at)
VALUES (
   'it_user',
   'IT Support',
   'it@orange.com',
   '$2a$12$QGAysO8P4/rHSRfbN7wVFuRC58yxVnfSqBYb6Dam5qDe2kRyWyZAq',
   1,                 -- IT department
   2,                 -- IT role
   '01000000002',
   '2021-06-01',
   TRUE,
   NOW(),
   NOW()
);

-- Employee user
INSERT INTO users (username, full_name, email, password, department_id, role_id, phone, hire_date, is_active, created_at, updated_at)
VALUES (
   'employee1',
   'John Employee',
   'employee1@orange.com',
   '$2a$12$QGAysO8P4/rHSRfbN7wVFuRC58yxVnfSqBYb6Dam5qDe2kRyWyZAq',
   2,
   3,
   '01000000003',
   '2022-03-15',
   TRUE,
   NOW(),
   NOW()
);
-- Categories
INSERT INTO asset_category (name) VALUES ('Hardware'), ('Software');

-- Types
INSERT INTO asset_type (category_id, name) VALUES
    (1, 'Laptop'),
    (1, 'Monitor'),
    (2, 'License');

-- Assets
INSERT INTO asset (category_id, type_id, brand, description, name, location, serial_number, purchase_date, warranty_end_date, status, image_path)
VALUES
    (1, 1, 'Dell', 'Dell Latitude 5420', 'Laptop A', 'Cairo HQ', 'SN10001', '2023-01-10', '2025-01-10', 'AVAILABLE', 'images/laptop1.png'),
    (1, 1, 'HP', 'HP ProBook 450 G8', 'Laptop B', 'Alex Branch', 'SN10002', '2022-09-05', '2025-09-05', 'ASSIGNED', 'images/laptop2.png'),
    (1, 2, 'LG', '27-inch UltraWide Monitor', 'Monitor B', 'Giza Office', 'SN20002', '2022-06-20', '2025-06-20', 'AVAILABLE', 'images/monitor2.png'),
    (2, 3, 'Microsoft', 'Office 365 Business Premium', 'License A', 'Cairo HQ', 'SN30001', '2023-02-01', '2024-02-01', 'RETIRED', 'images/license1.png');
