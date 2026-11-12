
package com.healthcare.doctor.repository;

import com.healthcare.doctor.entity.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    // Find doctor by email
    Optional<Doctor> findByEmail(String email);

    // Find doctor by phone number
    Optional<Doctor> findByPhoneNumber(String phoneNumber);

    // Find doctor by medical license number
    Optional<Doctor> findByLicenseNumber(String licenseNumber);

    // Find doctor by Identity Service user ID
    Optional<Doctor> findByIdentityUserId(Long identityUserId);

    // Check duplicate email
    boolean existsByEmail(String email);

    // Check duplicate phone number
    boolean existsByPhoneNumber(String phoneNumber);

    // Check duplicate license number
    boolean existsByLicenseNumber(String licenseNumber);

    // Find doctors by specialization
    Page<Doctor> findBySpecializationIdAndStatus(
            Long specializationId,
            String status,
            Pageable pageable
    );

    // Find doctors by status
    Page<Doctor> findByStatus(String status, Pageable pageable);

    // Search doctors by name or email
    @Query("""
            SELECT d FROM Doctor d
            WHERE LOWER(d.firstName) LIKE LOWER(CONCAT('%', :keyword, '%'))
               OR LOWER(d.lastName) LIKE LOWER(CONCAT('%', :keyword, '%'))
               OR LOWER(d.email) LIKE LOWER(CONCAT('%', :keyword, '%'))
            """)
    Page<Doctor> searchDoctors(
            @Param("keyword") String keyword,
            Pageable pageable
    );
}
