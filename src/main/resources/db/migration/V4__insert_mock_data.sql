-- ===========================================================
-- V2__insert_mock_data.sql
-- Full Mock Data for Asset Management System
-- ===========================================================

-- ========================
-- Departments
-- ========================
INSERT INTO department (id, name) VALUES
 (1, 'IT'),
 (2, 'HR'),
 (3, 'Finance'),
 (4, 'Operations'),
 (5, 'Logistics');

-- ========================
-- Roles
-- ========================
INSERT INTO role (id, name) VALUES
 (1, 'ADMIN'),
 (2, 'DEPARTMENT_MANAGER'),
 (3, 'IT'),
 (4, 'EMPLOYEE');

-- ========================
-- Users
-- Password: 12345678 (bcrypt hash)
-- ========================
INSERT INTO users (id, username, full_name, email, password, department_id, role_id, phone, hire_date, is_active, created_at, updated_at) VALUES
 (1, 'ahmedsaad_admin', 'Ahmed Saad', 'ahmed.s.elkholy@orange.com',  '$2a$12$g18cvUm4OKwpN67qbxKIPecR90LNkORI6m/ZCGjFrGzW6bPO63BvC', 1, 1, '01010000001', '2024-01-01', TRUE, '2024-01-01', '2024-01-01'),
 (2, 'maryiamreda_emp', 'Maryiam Reda', 'maryiam.reda@orange.com',     '$2a$12$g18cvUm4OKwpN67qbxKIPecR90LNkORI6m/ZCGjFrGzW6bPO63BvC', 2, 4, '01010000002', '2024-01-02', TRUE, '2024-01-02', '2024-01-02'),
 (3, 'ahmedeldera_it', 'Ahmed Elder3', 'hotoeldera@orange.com',       '$2a$12$g18cvUm4OKwpN67qbxKIPecR90LNkORI6m/ZCGjFrGzW6bPO63BvC', 1, 3, '01010000003', '2024-01-03', TRUE, '2024-01-03', '2024-01-03'),
 (4, 'ahmedsamir_emp', 'Ahmed Samir', 'ahmed.samiralsayed@orange.com','$2a$12$g18cvUm4OKwpN67qbxKIPecR90LNkORI6m/ZCGjFrGzW6bPO63BvC', 3, 4, '01010000004', '2024-01-04', TRUE, '2024-01-04', '2024-01-04'),
 (5, 'nourhanhisham_admin', 'Nourhan Hisham', 'nourhan.hisham@orange.com','$2a$12$g18cvUm4OKwpN67qbxKIPecR90LNkORI6m/ZCGjFrGzW6bPO63BvC', 2, 1, '01010000005', '2024-01-05', TRUE, '2024-01-05', '2024-01-05'),
 (6, 'wafaaabdelhafez_it', 'Wafaa Abdelhafez', 'wafaa.abdelhafez.ext@orange.com','$2a$12$g18cvUm4OKwpN67qbxKIPecR90LNkORI6m/ZCGjFrGzW6bPO63BvC', 1, 3, '01010000006', '2024-01-06', TRUE, '2024-01-06', '2024-01-06'),
 (7, 'zeyademam_emp', 'Zeyad Emam', 'farghly.zeyad@orange.com',       '$2a$12$g18cvUm4OKwpN67qbxKIPecR90LNkORI6m/ZCGjFrGzW6bPO63BvC', 4, 4, '01010000007', '2024-01-07', TRUE, '2024-01-07', '2024-01-07'),
 (8, 'mohamedgawad_admin', 'Mohamed Abdelgawad', 'mohamed1.abdelgawad@orange.com','$2a$12$g18cvUm4OKwpN67qbxKIPecR90LNkORI6m/ZCGjFrGzW6bPO63BvC', 3, 1, '01010000008', '2024-01-08', TRUE, '2024-01-08', '2024-01-08'),
 (9, 'mohamedmagdy_mgr', 'Mohamed Magdy', 'mohamed.essa@orange.com','$2a$12$g18cvUm4OKwpN67qbxKIPecR90LNkORI6m/ZCGjFrGzW6bPO63BvC', 2, 2, '01010000009', '2024-01-09', TRUE, '2024-01-09', '2024-01-09'),
 (10, 'nadinenasr_emp', 'Nadine Nasr', 'nadeen.nasr@orange.com',      '$2a$12$g18cvUm4OKwpN67qbxKIPecR90LNkORI6m/ZCGjFrGzW6bPO63BvC', 5, 4, '01010000010', '2024-01-10', TRUE, '2024-01-10', '2024-01-10'),
 (11, 'seifallaehab_emp', 'Seifalla Ehab', 'seifalla.ehab@orange.com','$2a$12$g18cvUm4OKwpN67qbxKIPecR90LNkORI6m/ZCGjFrGzW6bPO63BvC', 1, 4, '01010000011', '2024-01-11', TRUE, '2024-01-11', '2024-01-11'),
 (12, 'fatmahesham_emp', 'Fatma Hesham', 'fatma.hesham@orange.com','$2a$12$g18cvUm4OKwpN67qbxKIPecR90LNkORI6m/ZCGjFrGzW6bPO63BvC', 3, 4, '01010000012', '2024-01-12', TRUE, '2024-01-12', '2024-01-12'),
 (13, 'marimmohamed_emp', 'Marim Mohamed', 'marim.elakhe@orange.com','$2a$12$g18cvUm4OKwpN67qbxKIPecR90LNkORI6m/ZCGjFrGzW6bPO63BvC', 2, 4, '01010000013', '2024-01-13', TRUE, '2024-01-13', '2024-01-13'),
 (14, 'malakshehab_emp', 'Malak Shehab-Eldin', 'malak.ziad@orange.com','$2a$12$g18cvUm4OKwpN67qbxKIPecR90LNkORI6m/ZCGjFrGzW6bPO63BvC', 5, 4, '01010000014', '2024-01-14', TRUE, '2024-01-14', '2024-01-14'),
 (15, 'omarnabil_emp', 'Omar Nabil', 'omer.nabil@orange.com',     '$2a$12$g18cvUm4OKwpN67qbxKIPecR90LNkORI6m/ZCGjFrGzW6bPO63BvC', 4, 4, '01010000015', '2024-01-15', TRUE, '2024-01-15', '2024-01-15');

-- ========================
-- Asset Categories
-- ========================
INSERT INTO asset_category (id, name) VALUES
 (1, 'Electronics'),
 (2, 'Furniture'),
 (3, 'Equipments');

-- ========================
-- Asset Types
-- ========================
INSERT INTO asset_type (id, category_id, name) VALUES
 (1, 1, 'Laptops'),
 (2, 1, 'Printers'),
 (3, 1, 'Monitors'),
 (4, 1, 'Phones'),
 (5, 2, 'Desks'),
 (6, 2, 'Chairs'),
 (7, 3, 'Projectors'),
 (8, 3, 'Cameras'),
 (9, 3, 'Scanners'),
 (10, 3, 'Servers');

-- ========================
-- Assets
-- ========================
INSERT INTO asset (id, category_id, type_id, brand, description, name, location, serial_number, purchase_date, warranty_end_date, status, image_path) VALUES
 (1, 1, 1, 'Dell', '15-inch business laptop', 'Dell Latitude 5420', 'Cairo HQ', 'SN-LAP-001', '2023-01-15', '2026-01-15', 'AVAILABLE', NULL),
 (2, 1, 1, 'HP', 'Lightweight laptop', 'HP ProBook 450', 'Alex Branch', 'SN-LAP-002', '2023-03-10', '2026-03-10', 'ASSIGNED', NULL),
 (3, 1, 2, 'Canon', 'High speed printer', 'Canon LaserJet 200', 'Cairo HQ', 'SN-PRT-001', '2023-02-05', '2025-02-05', 'AVAILABLE', NULL),
 (4, 1, 2, 'HP', 'Color inkjet printer', 'HP OfficeJet 4500', 'Alex Branch', 'SN-PRT-002', '2023-04-20', '2026-04-20', 'ASSIGNED', NULL),
 (5, 1, 3, 'Dell', '24-inch monitor', 'Dell P2419H', 'Cairo HQ', 'SN-MON-001', '2023-05-15', '2027-05-15', 'AVAILABLE', NULL),
 (6, 1, 3, 'Samsung', '27-inch curved monitor', 'Samsung LC27F', 'Cairo HQ', 'SN-MON-002', '2023-07-01', '2026-07-01', 'ASSIGNED', NULL),
 (7, 1, 4, 'Apple', 'iPhone 14 Pro for testing', 'iPhone-14-IT', 'Cairo HQ', 'SN-PHN-001', '2023-09-01', '2026-09-01', 'AVAILABLE', NULL),
 (8, 2, 5, 'IKEA', 'Office desk', 'Micke Desk', 'Cairo HQ', 'SN-DSK-001', '2023-01-20', '2028-01-20', 'AVAILABLE', NULL),
 (9, 2, 5, 'IKEA', 'Standing desk', 'Bekant Desk', 'Alex Branch', 'SN-DSK-002', '2023-06-12', '2029-06-12', 'ASSIGNED', NULL),
 (10, 2, 6, 'HermanMiller', 'Ergonomic chair', 'Aeron Chair', 'Cairo HQ', 'SN-CHR-001', '2023-03-25', '2029-03-25', 'AVAILABLE', NULL),
 (11, 2, 6, 'IKEA', 'Standard office chair', 'Markus Chair', 'Alex Branch', 'SN-CHR-002', '2023-08-15', '2029-08-15', 'ASSIGNED', NULL),
 (12, 3, 7, 'Epson', 'Full HD office projector', 'Epson Projector X500', 'Cairo HQ', 'SN-PRJ-001', '2022-09-01', '2025-09-01', 'AVAILABLE', NULL),
 (13, 3, 8, 'Canon', 'Professional DSLR camera', 'Canon EOS 90D', 'Alex Branch', 'SN-CAM-001', '2023-01-25', '2026-01-25', 'ASSIGNED', NULL),
 (14, 3, 9, 'Fujitsu', 'High-speed document scanner', 'ScanSnap iX1500', 'Cairo HQ', 'SN-SCN-001', '2023-04-15', '2026-04-15', 'AVAILABLE', NULL),
 (15, 3, 10, 'Dell', 'Enterprise rack server', 'PowerEdge R740', 'Cairo Data Center', 'SN-SRV-001', '2023-07-10', '2028-07-10', 'ASSIGNED', NULL),
 (16, 1, 1, 'Apple', 'MacBook Pro 16-inch', 'MacBook Pro 2021', 'Cairo HQ', 'SN-LAP-016', '2023-02-15', '2026-02-15', 'AVAILABLE', NULL),
 (17, 1, 1, 'Dell', 'XPS ultrabook', 'Dell XPS 13', 'Alex Branch', 'SN-LAP-017', '2023-03-20', '2027-03-20', 'ASSIGNED', NULL),
 (18, 1, 3, 'LG', '27-inch 4K monitor', 'LG UltraFine 27', 'Cairo HQ', 'SN-MON-018', '2023-04-05', '2026-04-05', 'AVAILABLE', NULL),
 (19, 1, 3, 'Samsung', '32-inch curved monitor', 'Samsung Odyssey G7', 'Alex Branch', 'SN-MON-019', '2023-04-12', '2027-04-12', 'UNDER_MAINTENANCE', NULL),
 (20, 1, 4, 'Cisco', 'Desk phone', 'Cisco IP Phone 8845', 'Cairo HQ', 'SN-PHN-020', '2022-11-01', '2025-11-01', 'AVAILABLE', NULL),
 (21, 1, 2, 'Epson', 'All-in-one printer', 'Epson EcoTank L3150', 'Alex Branch', 'SN-PRT-021', '2023-05-18', '2026-05-18', 'ASSIGNED', NULL),
 (22, 2, 5, 'Steelcase', 'Adjustable office desk', 'Steelcase Ology', 'Cairo HQ', 'SN-DSK-022', '2023-06-20', '2028-06-20', 'AVAILABLE', NULL),
 (23, 2, 6, 'IKEA', 'Ergonomic chair', 'IKEA Flintan', 'Alex Branch', 'SN-CHR-023', '2023-07-01', '2029-07-01', 'ASSIGNED', NULL),
 (24, 2, 6, 'HermanMiller', 'Executive office chair', 'Embody Chair', 'Cairo HQ', 'SN-CHR-024', '2023-07-15', '2030-07-15', 'AVAILABLE', NULL),
 (25, 3, 7, 'Sony', '4K projector for meetings', 'Sony VPL-VW270', 'Cairo HQ', 'SN-PRJ-025', '2023-02-10', '2027-02-10', 'AVAILABLE', NULL),
 (26, 3, 8, 'Nikon', 'Mirrorless camera', 'Nikon Z6 II', 'Alex Branch', 'SN-CAM-026', '2023-03-12', '2026-03-12', 'ASSIGNED', NULL),
 (27, 3, 9, 'Brother', 'Compact scanner', 'Brother ADS-2200', 'Cairo HQ', 'SN-SCN-027', '2023-04-08', '2026-04-08', 'RETIRED', NULL),
 (28, 3, 10, 'HP', 'Blade server system', 'HP ProLiant DL380', 'Cairo Data Center', 'SN-SRV-028', '2023-01-25', '2028-01-25', 'AVAILABLE', NULL),
 (29, 1, 1, 'Lenovo', 'ThinkPad business laptop', 'ThinkPad X1 Carbon', 'Cairo HQ', 'SN-LAP-029', '2023-05-10', '2026-05-10', 'ASSIGNED', NULL),
 (30, 1, 2, 'Brother', 'Office laser printer', 'Brother HL-L8360CDW', 'Alex Branch', 'SN-PRT-030', '2023-06-02', '2026-06-02', 'AVAILABLE', NULL),
 (31, 1, 3, 'AOC', 'Gaming monitor', 'AOC 24G2', 'Cairo HQ', 'SN-MON-031', '2023-06-20', '2027-06-20', 'AVAILABLE', NULL),
 (32, 2, 5, 'IKEA', 'Corner desk', 'IKEA Bekant Corner', 'Cairo HQ', 'SN-DSK-032', '2023-07-05', '2028-07-05', 'ASSIGNED', NULL),
 (33, 2, 6, 'Steelcase', 'Premium chair', 'Steelcase Gesture', 'Alex Branch', 'SN-CHR-033', '2023-07-18', '2029-07-18', 'AVAILABLE', NULL),
 (34, 3, 7, 'BenQ', 'Conference projector', 'BenQ MH733', 'Cairo HQ', 'SN-PRJ-034', '2023-08-01', '2026-08-01', 'AVAILABLE', NULL),
 (35, 3, 10, 'IBM', 'Mainframe server', 'IBM z15 T02', 'Cairo Data Center', 'SN-SRV-035', '2023-08-15', '2033-08-15', 'ASSIGNED', NULL);

-- ========================
-- Asset Assignments
-- ========================
INSERT INTO asset_assignment (id, asset_id, assigned_to_user_id, assignment_date, status, note) VALUES
 (1, 2, 2, '2024-06-01', 'ACTIVE', 'HP ProBook assigned to Maryiam'),
 (2, 4, 7, '2024-06-15', 'ACTIVE', 'HP OfficeJet printer assigned to Zeyad'),
 (3, 6, 11, '2024-07-01', 'ACTIVE', 'Samsung Monitor assigned to Seifalla'),
 (4, 9, 14, '2024-07-20', 'ACTIVE',  'Standing desk assigned to Malak'),
 (5, 11, 13, '2024-08-01', 'ACTIVE', 'Office chair assigned to Marim'),
 (6, 13, 9, '2024-08-15', 'ACTIVE',  'Camera assigned to Mohamed Magdy'),
 (7, 15, 6, '2024-09-01', 'ACTIVE', 'Server assigned to Wafaa IT staff');
