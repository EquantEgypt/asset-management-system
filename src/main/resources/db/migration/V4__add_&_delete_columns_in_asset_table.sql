-- Add new columns
ALTER TABLE assets
    ADD COLUMN brand VARCHAR(200) NOT NULL DEFAULT 'Unknown',
    ADD COLUMN all_stock INT NOT NULL DEFAULT 0,
    ADD COLUMN number_of_available_to_assign INT NOT NULL DEFAULT 0,
    ADD COLUMN number_of_maintenance INT NOT NULL DEFAULT 0,
    ADD COLUMN number_of_retired INT NOT NULL DEFAULT 0;

-- First, drop the foreign key constraint that uses the user_id column
ALTER TABLE assets DROP FOREIGN KEY assets_ibfk_3;

-- Now, drop the old columns
ALTER TABLE assets
DROP COLUMN user_id,
    DROP COLUMN status;