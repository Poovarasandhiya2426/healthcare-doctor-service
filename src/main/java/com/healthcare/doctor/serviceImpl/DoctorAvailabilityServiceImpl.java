
package com.healthcare.doctor.serviceImpl;

import com.healthcare.doctor.dto.request.DoctorAvailabilityRequest;
import com.healthcare.doctor.dto.response.DoctorAvailabilityResponse;
import com.healthcare.doctor.entity.Doctor;
import com.healthcare.doctor.entity.DoctorAvailability;
import com.healthcare.doctor.enums.WeekDay;
import com.healthcare.doctor.exception.BadRequestException;
import com.healthcare.doctor.exception.ResourceNotFoundException;
import com.healthcare.doctor.mapper.DoctorAvailabilityMapper;
import com.healthcare.doctor.repository.DoctorAvailabilityRepository;
import com.healthcare.doctor.repository.DoctorRepository;
import com.healthcare.doctor.service.DoctorAvailabilityService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DoctorAvailabilityServiceImpl
        implements DoctorAvailabilityService {

    private final DoctorAvailabilityRepository availabilityRepository;
    private final DoctorRepository doctorRepository;
    private final DoctorAvailabilityMapper availabilityMapper;

    // Create doctor availability
    @Override
    public DoctorAvailabilityResponse createAvailability(
            DoctorAvailabilityRequest request) {

        validateTimeRange(
                request.getStartTime(),
                request.getEndTime());

        Doctor doctor = findDoctorById(request.getDoctorId());

        List<DoctorAvailability> existingSchedules =
                availabilityRepository.findByDoctorIdAndDayOfWeekAndActiveTrue(
                        doctor.getId(),
                        request.getDayOfWeek());

        if (!existingSchedules.isEmpty()) {
            throw new BadRequestException(
                    "Active availability already exists for this day");
        }

        DoctorAvailability availability =
                availabilityMapper.toEntity(request, doctor);

        DoctorAvailability savedAvailability =
                availabilityRepository.save(availability);

        return availabilityMapper.toResponse(savedAvailability);
    }

    // Get availability by ID
    @Override
    @Transactional(readOnly = true)
    public DoctorAvailabilityResponse getAvailabilityById(
            Long availabilityId) {

        DoctorAvailability availability =
                findAvailabilityById(availabilityId);

        return availabilityMapper.toResponse(availability);
    }

    // Get all availability records for a doctor
    @Override
    @Transactional(readOnly = true)
    public List<DoctorAvailabilityResponse> getAvailabilityByDoctorId(
            Long doctorId) {

        findDoctorById(doctorId);

        return availabilityRepository.findByDoctorIdAndActiveTrue(doctorId)
                .stream()
                .map(availabilityMapper::toResponse)
                .toList();
    }

    // Get availability by doctor and weekday
    @Override
    @Transactional(readOnly = true)
    public List<DoctorAvailabilityResponse> getAvailabilityByDoctorAndDay(
            Long doctorId,
            String dayOfWeek) {

        findDoctorById(doctorId);

        WeekDay weekDay = parseWeekDay(dayOfWeek);

        return availabilityRepository
                .findByDoctorIdAndDayOfWeekAndActiveTrue(
                        doctorId, weekDay)
                .stream()
                .map(availabilityMapper::toResponse)
                .toList();
    }

    // Update availability
    @Override
    public DoctorAvailabilityResponse updateAvailability(
            Long availabilityId,
            DoctorAvailabilityRequest request) {

        validateTimeRange(
                request.getStartTime(),
                request.getEndTime());

        DoctorAvailability availability =
                findAvailabilityById(availabilityId);

        Doctor doctor = findDoctorById(request.getDoctorId());

        List<DoctorAvailability> existingSchedules =
                availabilityRepository.findByDoctorIdAndDayOfWeekAndActiveTrue(
                        doctor.getId(),
                        request.getDayOfWeek());

        boolean duplicateSchedule = existingSchedules.stream()
                .anyMatch(existing ->
                        !existing.getId().equals(availabilityId));

        if (duplicateSchedule) {
            throw new BadRequestException(
                    "Active availability already exists for this day");
        }

        availability.setDoctor(doctor);
        availability.setDayOfWeek(request.getDayOfWeek());
        availability.setStartTime(request.getStartTime());
        availability.setEndTime(request.getEndTime());
        availability.setConsultationMode(request.getConsultationMode());
        availability.setSlotDurationMinutes(
                request.getSlotDurationMinutes());

        DoctorAvailability updatedAvailability =
                availabilityRepository.save(availability);

        return availabilityMapper.toResponse(updatedAvailability);
    }

    // Deactivate availability
    @Override
    public DoctorAvailabilityResponse deactivateAvailability(
            Long availabilityId) {

        DoctorAvailability availability =
                findAvailabilityById(availabilityId);

        if (!availability.getActive()) {
            throw new BadRequestException(
                    "Doctor availability is already inactive");
        }

        availability.setActive(false);

        DoctorAvailability updatedAvailability =
                availabilityRepository.save(availability);

        return availabilityMapper.toResponse(updatedAvailability);
    }

    // Find doctor by ID
    private Doctor findDoctorById(Long doctorId) {

        if (doctorId == null || doctorId <= 0) {
            throw new BadRequestException(
                    "Valid doctor ID is required");
        }

        return doctorRepository.findById(doctorId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Doctor not found with ID: " + doctorId));
    }

    // Find availability by ID
    private DoctorAvailability findAvailabilityById(
            Long availabilityId) {

        if (availabilityId == null || availabilityId <= 0) {
            throw new BadRequestException(
                    "Valid availability ID is required");
        }

        return availabilityRepository.findById(availabilityId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Doctor availability not found with ID: "
                                + availabilityId));
    }

    // Validate consultation time
    private void validateTimeRange(
            java.time.LocalTime startTime,
            java.time.LocalTime endTime) {

        if (startTime == null || endTime == null
                || !endTime.isAfter(startTime)) {

            throw new BadRequestException(
                    "End time must be after start time");
        }
    }

    // Convert String to WeekDay enum
    private WeekDay parseWeekDay(String dayOfWeek) {

        if (dayOfWeek == null || dayOfWeek.isBlank()) {
            throw new BadRequestException(
                    "Day of week is required");
        }

        try {
            return WeekDay.valueOf(
                    dayOfWeek.trim().toUpperCase());
        } catch (IllegalArgumentException exception) {
            throw new BadRequestException(
                    "Invalid day of week: " + dayOfWeek);
        }
    }
}
