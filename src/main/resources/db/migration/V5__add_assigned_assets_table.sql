CREATE TABLE assigned_assets (
            id BIGINT AUTO_INCREMENT PRIMARY KEY,
            asset_id BIGINT NOT NULL,
            user_id BIGINT NOT NULL,
            status ENUM('GOOD','UNDER_MAINTENANCE','RETIRED') NOT NULL,
            date_assigned TIMESTAMP NOT NULL,

            CONSTRAINT fk_assigned_asset FOREIGN KEY (asset_id) REFERENCES assets(asset_id),
            CONSTRAINT fk_assigned_user FOREIGN KEY (user_id) REFERENCES users(user_id)
);
