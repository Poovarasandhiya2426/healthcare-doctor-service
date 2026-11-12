
ALTER TABLE doctors
    ADD CONSTRAINT fk_doctor_specialization
        FOREIGN KEY (specialization_id)
            REFERENCES specializations(id)
            ON DELETE SET NULL;
