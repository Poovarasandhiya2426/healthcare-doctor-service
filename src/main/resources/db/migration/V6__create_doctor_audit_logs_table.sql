
CREATE TABLE doctor_audit_logs (
                                   id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                   doctor_id BIGINT,
                                   action VARCHAR(100) NOT NULL,
                                   performed_by BIGINT,
                                   performed_by_role VARCHAR(50),
                                   description VARCHAR(500),
                                   old_value JSON,
                                   new_value JSON,
                                   ip_address VARCHAR(45),
                                   created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

                                   CONSTRAINT fk_doctor_audit_log_doctor
                                       FOREIGN KEY (doctor_id)
                                           REFERENCES doctors(id)
                                           ON DELETE SET NULL,

                                   INDEX idx_doctor_audit_logs_doctor_id (doctor_id),
                                   INDEX idx_doctor_audit_logs_action (action),
                                   INDEX idx_doctor_audit_logs_created_at (created_at)
);
