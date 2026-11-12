
package com.healthcare.doctor.serviceImpl;

import com.healthcare.doctor.constants.DoctorConstants;
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

import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DoctorAvailabilityServiceImpl
        implements DoctorAvailabilityService {

    private final DoctorAvailabilityRepository availabilityRepository;
    private final DoctorRepository doctorRepository;
    private final DoctorAvailabilityMapper availabilityMapper;

    // ==========================================
    // CREATE DOCTOR AVAILABILITY
    // ==========================================

    @Override
    public DoctorAvailabilityResponse createAvailability(
            DoctorAvailabilityRequest request) {

        validateRequest(request);

        Doctor doctor = findDoctorById(request.getDoctorId());

        validateDuplicateAvailability(
                doctor.getId(),
                request.getDayOfWeek(),
                null
        );

        DoctorAvailability availability =
                availabilityMapper.toEntity(request, doctor);

        DoctorAvailability savedAvailability =
                availabilityRepository.save(availability);

        return availabilityMapper.toResponse(savedAvailability);
    }

    // ==========================================
    // GET AVAILABILITY BY ID
    // ==========================================

    @Override
    @Transactional(readOnly = true)
    public DoctorAvailabilityResponse getAvailabilityById(
            Long availabilityId) {

        DoctorAvailability availability =
                findAvailabilityById(availabilityId);

        return availabilityMapper.toResponse(availability);
    }

    // ==========================================
    // GET AVAILABILITY BY DOCTOR ID
    // ==========================================

    @Override
    @Transactional(readOnly = true)
    public List<DoctorAvailabilityResponse> getAvailabilityByDoctorId(
            Long doctorId) {

        Doctor doctor = findDoctorById(doctorId);

        return availabilityRepository
                .findByDoctorIdAndActiveTrue(doctor.getId())
                .stream()
                .map(availabilityMapper::toResponse)
                .toList();
    }

    // ==========================================
    // GET AVAILABILITY BY DOCTOR AND WEEKDAY
    // ==========================================

    @Override
    @Transactional(readOnly = true)
    public List<DoctorAvailabilityResponse> getAvailabilityByDoctorAndDay(
            Long doctorId,
            String dayOfWeek) {

        Doctor doctor = findDoctorById(doctorId);

        WeekDay weekDay = parseWeekDay(dayOfWeek);

        return availabilityRepository
                .findByDoctorIdAndDayOfWeekAndActiveTrue(
                        doctor.getId(),
                        weekDay
                )
                .stream()
                .map(availabilityMapper::toResponse)
                .toList();
    }

    // ==========================================
    // UPDATE AVAILABILITY
    // ==========================================

    @Override
    public DoctorAvailabilityResponse updateAvailability(
            Long availabilityId,
            DoctorAvailabilityRequest request) {

        validateRequest(request);

        DoctorAvailability availability =
                findAvailabilityById(availabilityId);

        Doctor doctor = findDoctorById(request.getDoctorId());

        validateDuplicateAvailability(
                doctor.getId(),
                request.getDayOfWeek(),
                availabilityId
        );

        availability.setDoctor(doctor);
        availability.setDayOfWeek(request.getDayOfWeek());
        availability.setStartTime(request.getStartTime());
        availability.setEndTime(request.getEndTime());
        availability.setConsultationMode(request.getConsultationMode());
        availability.setSlotDurationMinutes(
                request.getSlotDurationMinutes()
        );

        DoctorAvailability updatedAvailability =
                availabilityRepository.save(availability);

        return availabilityMapper.toResponse(updatedAvailability);
    }

    // ==========================================
    // DEACTIVATE AVAILABILITY
    // ==========================================

    @Override
    public DoctorAvailabilityResponse deactivateAvailability(
            Long availabilityId) {

        DoctorAvailability availability =
                findAvailabilityById(availabilityId);

        if (!Boolean.TRUE.equals(availability.getActive())) {
            throw new BadRequestException(
                    DoctorConstants.AVAILABILITY_ALREADY_INACTIVE
            );
        }

        availability.setActive(false);

        DoctorAvailability updatedAvailability =
                availabilityRepository.save(availability);

        return availabilityMapper.toResponse(updatedAvailability);
    }

    // ==========================================
    // FIND DOCTOR BY ID
    // ==========================================

    private Doctor findDoctorById(Long doctorId) {

        if (doctorId == null || doctorId <= 0) {
            throw new BadRequestException(
                    DoctorConstants.INVALID_DOCTOR_ID
            );
        }

        return doctorRepository.findById(doctorId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                DoctorConstants.DOCTOR_NOT_FOUND
                                        + " with ID: " + doctorId
                        )
                );
    }

    // ==========================================
    // FIND AVAILABILITY BY ID
    // ==========================================

    private DoctorAvailability findAvailabilityById(
            Long availabilityId) {

        if (availabilityId == null || availabilityId <= 0) {
            throw new BadRequestException(
                    DoctorConstants.INVALID_AVAILABILITY_ID
            );
        }

        return availabilityRepository.findById(availabilityId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                DoctorConstants.AVAILABILITY_NOT_FOUND
                                        + " with ID: " + availabilityId
                        )
                );
    }

    // ==========================================
    // VALIDATE REQUEST
    // ==========================================

    private void validateRequest(DoctorAvailabilityRequest request) {

        if (request == null) {
            throw new BadRequestException(
                    DoctorConstants.AVAILABILITY_REQUEST_EMPTY
            );
        }

        if (request.getDoctorId() == null
                || request.getDoctorId() <= 0) {

            throw new BadRequestException(
                    DoctorConstants.INVALID_DOCTOR_ID
            );
        }

        if (request.getDayOfWeek() == null) {
            throw new BadRequestException(
                    DoctorConstants.DAY_OF_WEEK_REQUIRED
            );
        }

        validateTimeRange(
                request.getStartTime(),
                request.getEndTime()
        );
    }

    // ==========================================
    // VALIDATE DUPLICATE AVAILABILITY
    // ==========================================

    private void validateDuplicateAvailability(
            Long doctorId,
            WeekDay dayOfWeek,
            Long excludeAvailabilityId) {

        List<DoctorAvailability> existingSchedules =
                availabilityRepository
                        .findByDoctorIdAndDayOfWeekAndActiveTrue(
                                doctorId,
                                dayOfWeek
                        );

        boolean duplicateSchedule = existingSchedules.stream()
                .anyMatch(existing ->
                        !existing.getId().equals(excludeAvailabilityId)
                );

        if (duplicateSchedule) {
            throw new BadRequestException(
                    DoctorConstants.AVAILABILITY_ALREADY_EXISTS
            );
        }
    }

    // ==========================================
    // VALIDATE CONSULTATION TIME
    // ==========================================

    private void validateTimeRange(
            LocalTime startTime,
            LocalTime endTime) {

        if (startTime == null || endTime == null
                || !endTime.isAfter(startTime)) {

            throw new BadRequestException(
                    DoctorConstants.INVALID_AVAILABILITY_TIME_RANGE
            );
        }
    }

    // ==========================================
    // CONVERT STRING TO WEEKDAY ENUM
    // ==========================================

    private WeekDay parseWeekDay(String dayOfWeek) {

        if (dayOfWeek == null || dayOfWeek.isBlank()) {
            throw new BadRequestException(
                    DoctorConstants.DAY_OF_WEEK_REQUIRED
            );
        }

        try {
            return WeekDay.valueOf(
                    dayOfWeek.trim().toUpperCase()
            );

        } catch (IllegalArgumentException exception) {

            throw new BadRequestException(
                    DoctorConstants.INVALID_DAY_OF_WEEK
                            + ": " + dayOfWeek
            );
        }
    }
}
