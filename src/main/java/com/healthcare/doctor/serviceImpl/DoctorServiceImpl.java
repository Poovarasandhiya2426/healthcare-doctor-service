
package com.healthcare.doctor.serviceImpl;

import com.healthcare.doctor.dto.request.DoctorRegistrationRequest;
import com.healthcare.doctor.dto.request.DoctorUpdateRequest;
import com.healthcare.doctor.dto.response.DoctorResponse;
import com.healthcare.doctor.dto.response.DoctorSummaryResponse;
import com.healthcare.doctor.entity.Doctor;
import com.healthcare.doctor.exception.BadRequestException;
import com.healthcare.doctor.exception.DuplicateResourceException;
import com.healthcare.doctor.exception.ResourceNotFoundException;
import com.healthcare.doctor.mapper.DoctorMapper;
import com.healthcare.doctor.repository.DoctorRepository;
import com.healthcare.doctor.service.DoctorService;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final DoctorMapper doctorMapper;

    // Register a new doctor
    @Override
    public DoctorResponse registerDoctor(
            DoctorRegistrationRequest request) {

        if (doctorRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException(
                    "Doctor email already exists");
        }

        if (doctorRepository.existsByPhoneNumber(
                request.getPhoneNumber())) {
            throw new DuplicateResourceException(
                    "Doctor phone number already exists");
        }

        if (doctorRepository.existsByLicenseNumber(
                request.getLicenseNumber())) {
            throw new DuplicateResourceException(
                    "Doctor license number already exists");
        }

        if (doctorRepository.findByIdentityUserId(
                request.getIdentityUserId()).isPresent()) {
            throw new DuplicateResourceException(
                    "Identity user is already linked to a doctor");
        }

        Doctor doctor = doctorMapper.toEntity(request);

        Doctor savedDoctor = doctorRepository.save(doctor);

        return doctorMapper.toResponse(savedDoctor);
    }

    // Get doctor by ID
    @Override
    @Transactional(readOnly = true)
    public DoctorResponse getDoctorById(Long doctorId) {

        Doctor doctor = findDoctorById(doctorId);

        return doctorMapper.toResponse(doctor);
    }

    // Get doctor by email
    @Override
    @Transactional(readOnly = true)
    public DoctorResponse getDoctorByEmail(String email) {

        Doctor doctor = doctorRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Doctor not found with email: " + email));

        return doctorMapper.toResponse(doctor);
    }

    // Get all doctors with pagination
    @Override
    @Transactional(readOnly = true)
    public Page<DoctorSummaryResponse> getAllDoctors(
            Pageable pageable) {

        return doctorRepository.findAll(pageable)
                .map(doctorMapper::toSummaryResponse);
    }

    // Update doctor details
    @Override
    public DoctorResponse updateDoctor(
            Long doctorId,
            DoctorUpdateRequest request) {

        Doctor doctor = findDoctorById(doctorId);

        doctorRepository.findByEmail(request.getEmail())
                .filter(existing -> !existing.getId().equals(doctorId))
                .ifPresent(existing -> {
                    throw new DuplicateResourceException(
                            "Email is already used by another doctor");
                });

        doctorRepository.findByPhoneNumber(request.getPhoneNumber())
                .filter(existing -> !existing.getId().equals(doctorId))
                .ifPresent(existing -> {
                    throw new DuplicateResourceException(
                            "Phone number is already used by another doctor");
                });

        doctorMapper.updateEntity(request, doctor);

        Doctor updatedDoctor = doctorRepository.save(doctor);

        return doctorMapper.toResponse(updatedDoctor);
    }

    // Deactivate doctor
    @Override
    public DoctorResponse deactivateDoctor(Long doctorId) {

        Doctor doctor = findDoctorById(doctorId);

        if ("INACTIVE".equalsIgnoreCase(doctor.getStatus())) {
            throw new BadRequestException(
                    "Doctor is already inactive");
        }

        doctor.setStatus("INACTIVE");

        Doctor updatedDoctor = doctorRepository.save(doctor);

        return doctorMapper.toResponse(updatedDoctor);
    }

    // Search doctors
    @Override
    @Transactional(readOnly = true)
    public Page<DoctorSummaryResponse> searchDoctors(
            String keyword,
            Pageable pageable) {

        if (keyword == null || keyword.isBlank()) {
            throw new BadRequestException(
                    "Search keyword cannot be empty");
        }

        return doctorRepository.searchDoctors(
                        keyword.trim(), pageable)
                .map(doctorMapper::toSummaryResponse);
    }

    // Get doctors by specialization
    @Override
    @Transactional(readOnly = true)
    public Page<DoctorSummaryResponse> getDoctorsBySpecialization(
            Long specializationId,
            Pageable pageable) {

        if (specializationId == null || specializationId <= 0) {
            throw new BadRequestException(
                    "Valid specialization ID is required");
        }

        return doctorRepository.findBySpecializationIdAndStatus(
                specializationId,
                "ACTIVE",
                pageable
        ).map(doctorMapper::toSummaryResponse);
    }

    // Get doctors by status
    @Override
    @Transactional(readOnly = true)
    public Page<DoctorSummaryResponse> getDoctorsByStatus(
            String status,
            Pageable pageable) {

        if (status == null || status.isBlank()) {
            throw new BadRequestException(
                    "Doctor status is required");
        }

        String normalizedStatus = status.trim().toUpperCase();

        if (!normalizedStatus.equals("ACTIVE")
                && !normalizedStatus.equals("INACTIVE")) {
            throw new BadRequestException(
                    "Invalid doctor status. Use ACTIVE or INACTIVE");
        }

        return doctorRepository.findByStatus(
                        normalizedStatus, pageable)
                .map(doctorMapper::toSummaryResponse);
    }

    // Common method to find doctor by ID
    private Doctor findDoctorById(Long doctorId) {

        if (doctorId == null || doctorId <= 0) {
            throw new BadRequestException(
                    "Valid doctor ID is required");
        }

        return doctorRepository.findById(doctorId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Doctor not found with ID: " + doctorId));
    }
}
