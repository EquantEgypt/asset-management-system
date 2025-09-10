-- Assign Assets
INSERT INTO assigned_assets (asset_id, user_id, status, date_assigned) VALUES
    (1, 1, 'GOOD', NOW()),  -- Ahmed Manager gets Dell Latitude
    (3, 1, 'GOOD', NOW()),  -- Ahmed Manager gets Samsung Monitor
    (2, 2, 'GOOD', NOW()),  -- Salma Admin gets HP EliteBook
    (4, 2, 'GOOD', NOW()),  -- Salma Admin gets LG Monitor
    (5, 3, 'GOOD', NOW()),  -- Karim IT gets Logitech Keyboard
    (6, 3, 'GOOD', NOW()),  -- Karim IT gets MS Office License
    (7, 4, 'GOOD', NOW()),  -- Mona Employee gets Photoshop License
    (8, 4, 'UNDER_MAINTENANCE', NOW()), -- Mona Employee has Ergonomic Chair (under maintenance)
    (9, 4, 'GOOD', NOW()); -- Mona Employee gets Conference Room Chair
