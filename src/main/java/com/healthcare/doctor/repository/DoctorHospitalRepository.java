
package com.healthcare.doctor.repository;

import com.healthcare.doctor.entity.DoctorHospital;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorHospitalRepository
        extends JpaRepository<DoctorHospital, Long> {

    // Get all hospital mappings for a doctor
    List<DoctorHospital> findByDoctorId(Long doctorId);

    // Get all doctors mapped to a hospital
    List<DoctorHospital> findByHospitalId(Long hospitalId);

    // Find mapping by doctor and hospital
    Optional<DoctorHospital> findByDoctorIdAndHospitalId(
            Long doctorId,
            Long hospitalId
    );

    // Check duplicate mapping
    boolean existsByDoctorIdAndHospitalId(
            Long doctorId,
            Long hospitalId
    );

    // Check duplicate mapping excluding current mapping
    boolean existsByDoctorIdAndHospitalIdAndIdNot(
            Long doctorId,
            Long hospitalId,
            Long id
    );

    // Find active hospital mappings
    List<DoctorHospital> findByDoctorIdAndActiveTrue(Long doctorId);

    // Find primary hospital
    Optional<DoctorHospital> findByDoctorIdAndPrimaryHospitalTrue(
            Long doctorId
    );

    // Count hospitals mapped to a doctor
    long countByDoctorId(Long doctorId);

    // Count active hospital mappings
    long countByDoctorIdAndActiveTrue(Long doctorId);
}
