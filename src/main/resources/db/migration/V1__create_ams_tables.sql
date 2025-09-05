
CREATE TABLE users (
    user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(200) NOT NULL UNIQUE,
    password VARCHAR(10) NOT NULL,
    role_id BIGINT NOT NULL,
    department_id BIGINT,
    FOREIGN KEY (role_id) REFERENCES role(role_id),
    FOREIGN KEY (department_id) REFERENCES department(department_id)
);

CREATE TABLE role (
    role_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    role_type ENUM('Admin', 'Department_Manager', 'IT', 'Employee') NOT NULL UNIQUE
);

CREATE TABLE department (
    department_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    department_name VARCHAR(100) NOT NULL UNIQUE,

);

CREATE TABLE assets (
    asset_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    asset_name VARCHAR(200) NOT NULL,
    asset_description TEXT,
    category_id BIGINT NOT NULL,
    type_id BIGINT NOT NULL,
    user_id BIGINT,  -- if assigned to a user
    status ENUM('available', 'assigned', 'maintenance', 'retired', 'reserved') NOT NULL DEFAULT 'available',
    FOREIGN KEY (category_id) REFERENCES category(category_id),
    FOREIGN KEY (type_id) REFERENCES type(type_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE category (
    category_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    category_name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE type (
    type_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    type_name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE asset_request (
    request_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    asset_id BIGINT,
    request_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status ENUM('new_asset', 'maintenance') NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (asset_id) REFERENCES assets(asset_id)
);
