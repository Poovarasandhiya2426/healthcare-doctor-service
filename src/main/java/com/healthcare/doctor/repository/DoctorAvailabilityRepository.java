
package com.healthcare.doctor.repository;

import com.healthcare.doctor.entity.DoctorAvailability;
import com.healthcare.doctor.enums.WeekDay;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorAvailabilityRepository
        extends JpaRepository<DoctorAvailability, Long> {

    // Get all availability records for a doctor
    List<DoctorAvailability> findByDoctorId(Long doctorId);

    // Get active availability records for a doctor
    List<DoctorAvailability> findByDoctorIdAndActiveTrue(
            Long doctorId);

    // Get availability by doctor and day
    List<DoctorAvailability> findByDoctorIdAndDayOfWeek(
            Long doctorId,
            WeekDay dayOfWeek);

    // Get active availability by doctor and day
    List<DoctorAvailability> findByDoctorIdAndDayOfWeekAndActiveTrue(
            Long doctorId,
            WeekDay dayOfWeek);

    // Find availability by ID and doctor ID
    Optional<DoctorAvailability> findByIdAndDoctorId(
            Long id,
            Long doctorId);

    // Check whether doctor has availability for a day
    boolean existsByDoctorIdAndDayOfWeekAndActiveTrue(
            Long doctorId,
            WeekDay dayOfWeek);

    boolean existsByDoctorIdAndDayOfWeekAndActiveTrueAndIdNot(
            Long doctorId,
            WeekDay dayOfWeek,
            Long availabilityId
    );
}
