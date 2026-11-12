
CREATE TABLE doctor_qualifications (
                                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                       doctor_id BIGINT NOT NULL,
                                       degree VARCHAR(150) NOT NULL,
                                       university VARCHAR(200) NOT NULL,
                                       year_of_completion INT NOT NULL,
                                       certificate_url VARCHAR(500),
                                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                                           ON UPDATE CURRENT_TIMESTAMP,

                                       CONSTRAINT fk_doctor_qualification_doctor
                                           FOREIGN KEY (doctor_id)
                                               REFERENCES doctors(id)
                                               ON DELETE CASCADE,

                                       INDEX idx_doctor_qualification_doctor_id (doctor_id)
);
