
INSERT INTO department (department_name) VALUES
                                             ('IT'),
                                             ('HR'),
                                             ('Finance'),
                                             ('Operations'),
                                             ('Logistics');

-- ========================
-- Roles
-- ========================
INSERT INTO role (role_name) VALUES
                                 ('ADMIN'),
                                 ('EMPLOYEE'),
                                 ('DEPARTMENT_MANAGER'),
                                 ('IT');

-- ========================
-- Users
-- ========================
-- password: 12345678
INSERT INTO users (username, full_name, email, password, department_id, role_id, phone, hire_date, is_active, created_at, updated_at)
VALUES
    ('admin1', 'System Admin', 'admin@orange.com', '$2a$12$g18cvUm4OKwpN67qbxKIPecR90LNkORI6m/ZCGjFrGzW6bPO63BvC', 1, 1, '01000000001', '2024-01-01', TRUE, '2024-01-01', '2024-01-01'),
    ('emp1', 'Employee One', 'emp1@orange.com', '$2a$12$g18cvUm4OKwpN67qbxKIPecR90LNkORI6m/ZCGjFrGzW6bPO63BvC', 1, 2, '01000000002', '2024-02-01', TRUE, '2024-02-01', '2024-02-01'),
    ('emp2', 'Employee Two', 'emp2@orange.com', '$2a$12$g18cvUm4OKwpN67qbxKIPecR90LNkORI6m/ZCGjFrGzW6bPO63BvC', 2, 2, '01000000003', '2024-03-01', TRUE, '2024-03-01', '2024-03-01'),
    ('manager1', 'Manager One', 'manager1@orange.com', '$2a$12$g18cvUm4OKwpN67qbxKIPecR90LNkORI6m/ZCGjFrGzW6bPO63BvC', 3, 3, '01000000004', '2024-04-01', TRUE, '2024-04-01', '2024-04-01'),
    ('tech1', 'Tech Support', 'tech1@orange.com', '$2a$12$g18cvUm4OKwpN67qbxKIPecR90LNkORI6m/ZCGjFrGzW6bPO63BvC', 4, 4, '01000000005', '2024-05-01', TRUE, '2024-05-01', '2024-05-01');

-- ========================
-- Asset Categories
-- ========================
INSERT INTO asset_category (category_name) VALUES
                                               ('Laptops'),
                                               ('Monitors'),
                                               ('Printers'),
                                               ('Networking'),
                                               ('Furniture');

-- ========================
-- Asset Types
-- ========================
-- Laptops
INSERT INTO asset_type (category_id, name) VALUES
                                               (1, 'Dell Latitude'),
                                               (1, 'HP EliteBook'),
                                               (1, 'Lenovo ThinkPad'),
                                               (1, 'MacBook Pro');

-- Monitors
INSERT INTO asset_type (category_id, name) VALUES
                                               (2, 'Dell 24 inch'),
                                               (2, 'Samsung 27 inch'),
                                               (2, 'LG UltraWide');

-- Printers
INSERT INTO asset_type (category_id, name) VALUES
                                               (3, 'HP LaserJet'),
                                               (3, 'Canon Inkjet');

-- Networking
INSERT INTO asset_type (category_id, name) VALUES
                                               (4, 'Cisco Router'),
                                               (4, 'TP-Link Switch');

-- Furniture
INSERT INTO asset_type (category_id, name) VALUES
                                               (5, 'Office Chair'),
                                               (5, 'Standing Desk'),
                                               (5, 'Filing Cabinet');
