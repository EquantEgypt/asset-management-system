    -- V2__mock_data.sql
    -- Mock Data for Asset Management System

    -- ========================
    -- Departments
    -- ========================
    INSERT INTO department (name) VALUES
                                      ('IT'),
                                      ('HR'),
                                      ('Finance'),
                                      ('Operations'),
                                      ('Logistics');

    -- ========================
    -- Roles
    -- ========================
    INSERT INTO role (name) VALUES
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
    INSERT INTO asset_category (name) VALUES
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
    -- ========================
    -- Assets
    -- ========================
    INSERT INTO asset (category_id, type_id, brand, description, name, location, serial_number, purchase_date, warranty_end_date, status, image_path)
    VALUES
        (1, 1, 'Dell', 'Dell Latitude Laptop', 'Laptop-001', 'Office A', 'SN001', '2023-01-15 10:00:00', '2026-01-15 10:00:00', 'AVAILABLE', '/images/laptop.png'),
        (1, 2, 'HP', 'HP EliteBook Laptop', 'Laptop-002', 'Office B', 'SN002', '2023-02-20 10:00:00', '2026-02-20 10:00:00', 'ASSIGNED', '/images/laptop.png'),
        (1, 3, 'Lenovo', 'Lenovo ThinkPad Laptop', 'Laptop-003', 'Office C', 'SN003', '2023-03-25 10:00:00', '2026-03-25 10:00:00', 'UNDER_MAINTENANCE', '/images/laptop.png'),
        (2, 5, 'Dell', 'Dell 24 inch Monitor', 'Monitor-001', 'Office A', 'SN004', '2023-04-10 10:00:00', '2026-04-10 10:00:00', 'AVAILABLE', '/images/monitor.png');


    -- ========================
    -- Asset Assignments
    -- ========================
    INSERT INTO asset_assignment (asset_id, assigned_to_user_id, assignment_date, status, return_date, note)
    VALUES
        -- Assign a Dell Latitude to emp1
        (1, 2, '2024-06-01 10:00:00', 'ACTIVE', NULL, 'Standard issue for new IT employee'),

        -- Assign an HP EliteBook to emp2
        (2, 3, '2024-06-15 11:30:00', 'ACTIVE', NULL, 'Standard issue for new HR employee'),

        -- Assign a Lenovo ThinkPad to manager1, which is now closed
        (3, 4, '2024-07-01 09:00:00', 'CLOSED', '2025-01-01 17:00:00', 'Upgraded to a newer model'),

        -- Assign a MacBook Pro to tech1
        (4, 5, '2024-07-20 14:00:00', 'ACTIVE', NULL, 'Issued for mobile development testing');