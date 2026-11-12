
package com.healthcare.doctor.repository;

import com.healthcare.doctor.entity.Doctor;
import com.healthcare.doctor.enums.DoctorStatus;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DoctorRepository
        extends JpaRepository<Doctor, Long> {

    Optional<Doctor> findByEmail(String email);

    Optional<Doctor> findByPhoneNumber(String phoneNumber);

    Optional<Doctor> findByLicenseNumber(String licenseNumber);

    Optional<Doctor> findByIdentityUserId(Long identityUserId);

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByLicenseNumber(String licenseNumber);

    Page<Doctor> findBySpecializationIdAndStatus(
            Long specializationId,
            DoctorStatus status,
            Pageable pageable);

    Page<Doctor> findByStatus(
            DoctorStatus status,
            Pageable pageable);

    @Query("""
            SELECT d FROM Doctor d
            WHERE LOWER(d.firstName)
                LIKE LOWER(CONCAT('%', :keyword, '%'))
               OR LOWER(d.lastName)
                LIKE LOWER(CONCAT('%', :keyword, '%'))
               OR LOWER(d.email)
                LIKE LOWER(CONCAT('%', :keyword, '%'))
            """)
    Page<Doctor> searchDoctors(
            @Param("keyword") String keyword,
            Pageable pageable);
}
