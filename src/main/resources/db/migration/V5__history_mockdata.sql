-- ========================
-- Asset History
-- ========================
INSERT INTO asset_history (id, asset_id, user_id, note, timestamp, status) VALUES
 -- Asset 1: Dell Latitude - available from start
 (1, 1, 1, 'Asset registered in system', '2023-01-15 10:00:00', 'AVAILABLE'),

 -- Asset 2: HP ProBook - available then assigned to Maryiam
 (2, 2, 1, 'Asset registered in system', '2023-03-10 09:00:00', 'AVAILABLE'),
 (3, 2, 2, 'Assigned HP ProBook to Maryiam Reda', '2024-06-01 09:30:00', 'ASSIGNED'),

 -- Asset 3: Canon Printer - available only
 (4, 3, 1, 'Asset registered in system', '2023-02-05 14:00:00', 'AVAILABLE'),

 -- Asset 4: HP Printer - available then assigned to Zeyad
 (5, 4, 1, 'Asset registered in system', '2023-04-20 11:00:00', 'AVAILABLE'),
 (6, 4, 7, 'Assigned HP Printer to Zeyad Emam', '2024-06-15 10:15:00', 'ASSIGNED'),

 -- Asset 6: Samsung Monitor - available then assigned to Seifalla
 (7, 6, 1, 'Asset registered in system', '2023-07-01 12:00:00', 'AVAILABLE'),
 (8, 6, 11, 'Assigned Samsung Monitor to Seifalla Ehab', '2024-07-01 14:00:00', 'ASSIGNED'),

 -- Asset 9: Standing Desk - available then assigned to Malak
 (9, 9, 1, 'Asset registered in system', '2023-06-12 09:00:00', 'AVAILABLE'),
 (10, 9, 14, 'Assigned Standing Desk to Malak Shehab', '2024-07-20 10:00:00', 'ASSIGNED'),

 -- Asset 11: Markus Chair - available then assigned to Marim
 (11, 11, 1, 'Asset registered in system', '2023-08-15 16:00:00', 'AVAILABLE'),
 (12, 11, 13, 'Assigned Markus Chair to Marim Mohamed', '2024-08-01 09:00:00', 'ASSIGNED'),

 -- Asset 13: Canon Camera - available then assigned to Mohamed Magdy
 (13, 13, 1, 'Asset registered in system', '2023-01-25 11:30:00', 'AVAILABLE'),
 (14, 13, 9, 'Assigned Canon Camera to Mohamed Magdy', '2024-08-15 13:00:00', 'ASSIGNED'),

 -- Asset 15: Dell Server - available then assigned to Wafaa IT
 (15, 15, 1, 'Asset registered in system', '2023-07-10 10:45:00', 'AVAILABLE'),
 (16, 15, 6, 'Assigned Dell PowerEdge Server to Wafaa Abdelhafez', '2024-09-01 15:00:00', 'ASSIGNED');
