
package com.healthcare.doctor.repository;

import com.healthcare.doctor.entity.DoctorQualification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorQualificationRepository
        extends JpaRepository<DoctorQualification, Long> {

    List<DoctorQualification> findByDoctorId(Long doctorId);

    Optional<DoctorQualification> findByIdAndDoctorId(
            Long id,
            Long doctorId
    );

    boolean existsByDoctorIdAndDegreeIgnoreCaseAndUniversityIgnoreCase(
            Long doctorId,
            String degree,
            String university
    );

    long countByDoctorId(Long doctorId);
}
