
CREATE TABLE doctor_availability (

                                     id BIGINT AUTO_INCREMENT PRIMARY KEY,

                                     doctor_id BIGINT NOT NULL,

                                     day_of_week VARCHAR(20) NOT NULL,

                                     start_time TIME NOT NULL,

                                     end_time TIME NOT NULL,

                                     consultation_mode VARCHAR(20) NOT NULL,

                                     slot_duration_minutes INT NOT NULL,

                                     active BOOLEAN NOT NULL DEFAULT TRUE,

                                     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

                                     updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                                         ON UPDATE CURRENT_TIMESTAMP,

                                     CONSTRAINT fk_doctor_availability_doctor
                                         FOREIGN KEY (doctor_id)
                                             REFERENCES doctors(id)
                                             ON DELETE CASCADE,

                                     CONSTRAINT chk_availability_time
                                         CHECK (end_time > start_time),

                                     CONSTRAINT chk_slot_duration
                                         CHECK (slot_duration_minutes > 0),

                                     INDEX idx_doctor_availability_doctor_id (doctor_id),

                                     INDEX idx_doctor_availability_day (day_of_week),

                                     INDEX idx_doctor_availability_active (active)
);
