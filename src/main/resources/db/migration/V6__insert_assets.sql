-- Insert Assets (Hardware - Laptop, Monitor, Keyboard)
INSERT INTO assets (asset_name, asset_description, category_id, type_id, brand,
                    all_stock, number_of_available_to_assign, number_of_maintenance, number_of_retired)
VALUES
    ('Dell Latitude 5420', 'Business laptop with 16GB RAM and 512GB SSD', 1, 1, 'Dell', 10, 8, 1, 1),
    ('HP EliteBook 840', 'High performance laptop for employees', 1, 1, 'HP', 7, 7, 0, 0),
    ('Samsung Monitor 24"', 'Full HD Monitor 24-inch', 1, 2, 'Samsung', 12, 10, 1, 1),
    ('LG Monitor 27"', '4K Monitor 27-inch', 1, 2, 'LG', 5, 5, 0, 0),
    ('Logitech Keyboard K120', 'Basic wired keyboard', 1, 3, 'Logitech', 20, 18, 1, 1),

-- Insert Assets (Software - Licenses)
    ('Microsoft Office License', 'Office 365 Subscription', 2, 4, 'Microsoft', 30, 30, 0, 0),
    ('Adobe Photoshop License', 'Creative Cloud Photoshop', 2, 4, 'Adobe', 15, 15, 0, 0),

-- Insert Assets (Furniture - Chairs)
    ('Ergonomic Chair', 'Adjustable ergonomic office chair', 3, 5, 'Herman Miller', 10, 9, 1, 0),
    ('Conference Room Chair', 'Standard chair for meeting rooms', 3, 5, 'IKEA', 25, 24, 1, 0);
