
CREATE TABLE doctors (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         identity_user_id BIGINT UNIQUE,
                         first_name VARCHAR(100) NOT NULL,
                         last_name VARCHAR(100) NOT NULL,
                         email VARCHAR(150) NOT NULL UNIQUE,
                         phone_number VARCHAR(15) NOT NULL UNIQUE,
                         date_of_birth DATE,
                         gender VARCHAR(20),
                         specialization_id BIGINT,
                         license_number VARCHAR(100) NOT NULL UNIQUE,
                         years_of_experience INT DEFAULT 0,
                         consultation_fee DECIMAL(10,2),
                         profile_image_url VARCHAR(500),
                         biography TEXT,
                         status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                             ON UPDATE CURRENT_TIMESTAMP
);
