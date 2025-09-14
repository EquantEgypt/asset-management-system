-- Add new columns
ALTER TABLE assets
    ADD COLUMN brand VARCHAR(200) NOT NULL DEFAULT 'Unknown',
    ADD COLUMN quantity INT NOT NULL DEFAULT 0;

ALTER TABLE assets
DROP FOREIGN KEY `assets_ibfk_3`;

-- Now, drop the old columns
ALTER TABLE assets
DROP COLUMN user_id,
    DROP COLUMN status;