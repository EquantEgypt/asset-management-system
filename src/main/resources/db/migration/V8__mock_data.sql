-- Insert more departments
INSERT INTO department (department_name) VALUES 
('HR'), ('Finance'), ('IT Support'), ('Marketing');

-- Insert more users (20 total across roles)
INSERT INTO users (username, email, password, department_id, role_id) VALUES
-- ('Ahmed Manager', 'manager@orange.com', '$2a$12$xBzMKeBDytHqS/94JgbB0u8iJGAa0zC0qBlNHr1PZs1i8n16LtiGS', 1, 2),
-- ('Salma Admin', 'admin@orange.com', '$2a$12$xBzMKeBDytHqS/94JgbB0u8iJGAa0zC0qBlNHr1PZs1i8n16LtiGS', 1, 3),
-- ('Karim IT', 'it@orange.com', '$2a$12$xBzMKeBDytHqS/94JgbB0u8iJGAa0zC0qBlNHr1PZs1i8n16LtiGS', 2, 4),
-- ('Mona Employee', 'employee@orange.com', '$2a$12$xBzMKeBDytHqS/94JgbB0u8iJGAa0zC0qBlNHr1PZs1i8n16LtiGS', 2, 1),

('Omar HR', 'omar.hr@orange.com', '$2a$12$hashedPass', 3, 1),
('Laila Finance', 'laila.finance@orange.com', '$2a$12$hashedPass', 4, 1),
('Tamer Marketing', 'tamer.marketing@orange.com', '$2a$12$hashedPass', 5, 1),
('Nour IT Support', 'nour.support@orange.com', '$2a$12$hashedPass', 6, 4),

('Yasmin Admin', 'yasmin.admin@orange.com', '$2a$12$hashedPass', 3, 3),
('Hassan Manager', 'hassan.manager@orange.com', '$2a$12$hashedPass', 4, 2),
('Zeinab Employee', 'zeinab.employee@orange.com', '$2a$12$hashedPass', 5, 1),
('Mostafa IT', 'mostafa.it@orange.com', '$2a$12$hashedPass', 6, 4),

('Rana HR', 'rana.hr@orange.com', '$2a$12$hashedPass', 3, 1),
('Fady Finance', 'fady.finance@orange.com', '$2a$12$hashedPass', 4, 1),
('Samir Marketing', 'samir.marketing@orange.com', '$2a$12$hashedPass', 5, 1),
('Heba IT Support', 'heba.support@orange.com', '$2a$12$hashedPass', 6, 4),

('Adel Admin', 'adel.admin@orange.com', '$2a$12$hashedPass', 3, 3),
('Nadia Manager', 'nadia.manager@orange.com', '$2a$12$hashedPass', 4, 2),
('Ibrahim Employee', 'ibrahim.employee@orange.com', '$2a$12$hashedPass', 5, 1),
('Sara IT', 'sara.it@orange.com', '$2a$12$hashedPass', 6, 4);
INSERT INTO assets (asset_name, asset_description, category_id, type_id, brand,
                    all_stock, number_of_available_to_assign, number_of_maintenance, number_of_retired)
VALUES
-- Laptops
('Dell Latitude 5420', 'Business laptop with 16GB RAM and 512GB SSD', 1, 1, 'Dell', 10, 8, 1, 1),
('HP EliteBook 840', 'High performance laptop for employees', 1, 1, 'HP', 7, 7, 0, 0),
('Lenovo ThinkPad X1', 'Durable laptop for managers', 1, 1, 'Lenovo', 5, 4, 1, 0),
('Apple MacBook Pro', '16-inch MacBook Pro for creatives', 1, 1, 'Apple', 3, 3, 0, 0),

-- Monitors
('Samsung Monitor 24"', 'Full HD Monitor 24-inch', 1, 2, 'Samsung', 12, 10, 1, 1),
('LG Monitor 27"', '4K Monitor 27-inch', 1, 2, 'LG', 5, 5, 0, 0),
('Dell UltraSharp 32"', 'Professional 4K Monitor', 1, 2, 'Dell', 4, 3, 1, 0),

-- Keyboards
('Logitech Keyboard K120', 'Basic wired keyboard', 1, 3, 'Logitech', 20, 18, 1, 1),
('Razer Mechanical Keyboard', 'Gaming-style mechanical keyboard', 1, 3, 'Razer', 6, 6, 0, 0),

-- Software Licenses
('Microsoft Office License', 'Office 365 Subscription', 2, 4, 'Microsoft', 30, 30, 0, 0),
('Adobe Photoshop License', 'Creative Cloud Photoshop', 2, 4, 'Adobe', 15, 15, 0, 0),
('AutoCAD License', 'Engineering design software license', 2, 4, 'Autodesk', 10, 9, 1, 0),

-- Furniture
('Ergonomic Chair', 'Adjustable ergonomic office chair', 3, 5, 'Herman Miller', 10, 9, 1, 0),
('Conference Room Chair', 'Standard chair for meeting rooms', 3, 5, 'IKEA', 25, 24, 1, 0),
('Standing Desk', 'Adjustable height standing desk', 3, 5, 'FlexiSpot', 8, 7, 1, 0),
('Executive Office Desk', 'Premium wooden office desk', 3, 5, 'Steelcase', 5, 5, 0, 0),

-- Extra hardware/software
('Cisco Router', 'Enterprise-grade networking router', 1, 1, 'Cisco', 3, 3, 0, 0),
('HP Printer', 'Laser printer for office use', 1, 1, 'HP', 6, 5, 1, 0),
('Tableau License', 'Business analytics software', 2, 4, 'Tableau', 12, 12, 0, 0),
('Zoom License', 'Enterprise video conferencing license', 2, 4, 'Zoom', 20, 19, 1, 0);
INSERT INTO assigned_assets (asset_id, user_id, status, date_assigned) VALUES
(1, 1, 'GOOD', NOW()),  
(2, 2, 'GOOD', NOW()),  
(3, 5, 'UNDER_MAINTENANCE', NOW()),  
(4, 6, 'GOOD', NOW()),  
(5, 7, 'RETIRED', NOW()),  

(6, 8, 'GOOD', NOW()),  
(7, 9, 'GOOD', NOW()),  
(8, 10, 'UNDER_MAINTENANCE', NOW()),  
(9, 11, 'GOOD', NOW()),  
(10, 12, 'GOOD', NOW()),  

(11, 13, 'GOOD', NOW()),  
(12, 14, 'UNDER_MAINTENANCE', NOW()),  
(13, 15, 'GOOD', NOW()),  
(14, 16, 'GOOD', NOW()),  
(15, 17, 'GOOD', NOW()),  

(16, 18, 'RETIRED', NOW()),  
(17, 19, 'GOOD', NOW()),  
(18, 20, 'GOOD', NOW()),  
(19, 3, 'UNDER_MAINTENANCE', NOW()),  
(20, 4, 'GOOD', NOW());

