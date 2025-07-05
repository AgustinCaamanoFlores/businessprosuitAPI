-- Baseline de tablas principales para usuarios
CREATE TABLE IF NOT EXISTS usr_roles (
    usr_role_id INT AUTO_INCREMENT PRIMARY KEY,
    usr_role_name VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS usr_users (
    usr_id INT AUTO_INCREMENT PRIMARY KEY,
    usr_cmp_id INT NOT NULL,
    usr_first_name VARCHAR(100) NOT NULL,
    usr_last_name VARCHAR(100) NOT NULL,
    usr_email VARCHAR(255),
    usr_phone VARCHAR(50),
    usr_residence VARCHAR(2),
    usr_consent TINYINT DEFAULT 0,
    usr_consent_date DATETIME,
    usr_address VARCHAR(255),
    usr_id_number VARCHAR(50),
    usr_created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    usr_updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS usr_user_roles (
    usr_user_role_usr_id INT NOT NULL,
    usr_user_role_role_id INT NOT NULL,
    PRIMARY KEY (usr_user_role_usr_id, usr_user_role_role_id),
    CONSTRAINT fk_usr_roles_user FOREIGN KEY (usr_user_role_usr_id) REFERENCES usr_users(usr_id),
    CONSTRAINT fk_usr_roles_role FOREIGN KEY (usr_user_role_role_id) REFERENCES usr_roles(usr_role_id)
);
