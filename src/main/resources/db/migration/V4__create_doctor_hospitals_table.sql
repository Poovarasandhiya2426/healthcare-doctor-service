
CREATE TABLE doctor_hospitals (
                                  id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                  doctor_id BIGINT NOT NULL,
                                  hospital_id BIGINT NOT NULL,
                                  department VARCHAR(150),
                                  joining_date DATE,
                                  consultation_fee DECIMAL(10,2),
                                  primary_hospital BOOLEAN NOT NULL DEFAULT FALSE,
                                  active BOOLEAN NOT NULL DEFAULT TRUE,
                                  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                                      ON UPDATE CURRENT_TIMESTAMP,

                                  CONSTRAINT fk_doctor_hospital_doctor
                                      FOREIGN KEY (doctor_id)
                                          REFERENCES doctors(id)
                                          ON DELETE CASCADE,

                                  UNIQUE KEY uk_doctor_hospital (doctor_id, hospital_id),
                                  INDEX idx_doctor_hospitals_doctor_id (doctor_id),
                                  INDEX idx_doctor_hospitals_hospital_id (hospital_id)
);
