
package com.healthcare.doctor.serviceImpl;

import com.healthcare.doctor.constants.DoctorConstants;
import com.healthcare.doctor.dto.request.DoctorRegistrationRequest;
import com.healthcare.doctor.dto.request.DoctorUpdateRequest;
import com.healthcare.doctor.dto.response.DoctorResponse;
import com.healthcare.doctor.dto.response.DoctorSummaryResponse;
import com.healthcare.doctor.entity.Doctor;
import com.healthcare.doctor.enums.DoctorStatus;
import com.healthcare.doctor.exception.BadRequestException;
import com.healthcare.doctor.exception.DuplicateResourceException;
import com.healthcare.doctor.exception.ResourceNotFoundException;
import com.healthcare.doctor.mapper.DoctorMapper;
import com.healthcare.doctor.repository.DoctorRepository;
import com.healthcare.doctor.security.DoctorUserPrincipal;
import com.healthcare.doctor.service.DoctorService;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final DoctorMapper doctorMapper;

    // ==========================================
    // REGISTER DOCTOR
    // ==========================================

    @Override
    public DoctorResponse registerDoctor(
            DoctorRegistrationRequest request) {

        if (doctorRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException(
                    DoctorConstants.DUPLICATE_EMAIL);
        }

        if (doctorRepository.existsByPhoneNumber(
                request.getPhoneNumber())) {

            throw new DuplicateResourceException(
                    DoctorConstants.DUPLICATE_PHONE);
        }

        if (doctorRepository.existsByLicenseNumber(
                request.getLicenseNumber())) {

            throw new DuplicateResourceException(
                    DoctorConstants.DUPLICATE_LICENSE);
        }

        if (doctorRepository.findByIdentityUserId(
                request.getIdentityUserId()).isPresent()) {

            throw new DuplicateResourceException(
                    DoctorConstants.IDENTITY_USER_ALREADY_LINKED);
        }

        Doctor doctor = doctorMapper.toEntity(request);

        Doctor savedDoctor = doctorRepository.save(doctor);

        return doctorMapper.toResponse(savedDoctor);
    }

    // ==========================================
    // GET DOCTOR BY ID
    // ==========================================

    @Override
    @Transactional(readOnly = true)
    public DoctorResponse getDoctorById(Long doctorId) {

        Doctor doctor = findDoctorById(doctorId);

        return doctorMapper.toResponse(doctor);
    }

    // ==========================================
    // GET DOCTOR BY EMAIL
    // ==========================================

    @Override
    @Transactional(readOnly = true)
    public DoctorResponse getDoctorByEmail(String email) {

        Doctor doctor = doctorRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(
                        DoctorConstants.DOCTOR_NOT_FOUND
                                + " with email: " + email));

        return doctorMapper.toResponse(doctor);
    }

    // ==========================================
    // GET ALL DOCTORS
    // ==========================================

    @Override
    @Transactional(readOnly = true)
    public Page<DoctorSummaryResponse> getAllDoctors(
            Pageable pageable) {

        return doctorRepository.findAll(pageable)
                .map(doctorMapper::toSummaryResponse);
    }

    // ==========================================
    // UPDATE DOCTOR
    // ==========================================

    @Override
    public DoctorResponse updateDoctor(
            Long doctorId,
            DoctorUpdateRequest request) {

        Doctor doctor = findDoctorById(doctorId);

        // Verify ADMIN or doctor's own profile
        validateDoctorOwnership(doctor);

        doctorRepository.findByEmail(request.getEmail())
                .filter(existing ->
                        !existing.getId().equals(doctorId))
                .ifPresent(existing -> {
                    throw new DuplicateResourceException(
                            DoctorConstants.DUPLICATE_EMAIL);
                });

        doctorRepository.findByPhoneNumber(
                        request.getPhoneNumber())
                .filter(existing ->
                        !existing.getId().equals(doctorId))
                .ifPresent(existing -> {
                    throw new DuplicateResourceException(
                            DoctorConstants.DUPLICATE_PHONE);
                });

        doctorMapper.updateEntity(request, doctor);

        Doctor updatedDoctor = doctorRepository.save(doctor);

        return doctorMapper.toResponse(updatedDoctor);
    }

    // ==========================================
    // DEACTIVATE DOCTOR
    // ==========================================

    @Override
    public DoctorResponse deactivateDoctor(Long doctorId) {

        Doctor doctor = findDoctorById(doctorId);

        // Verify ADMIN or doctor's own profile
        validateDoctorOwnership(doctor);

        if (doctor.getStatus() == DoctorStatus.INACTIVE) {
            throw new BadRequestException(
                    DoctorConstants.DOCTOR_ALREADY_INACTIVE);
        }

        doctor.setStatus(DoctorStatus.INACTIVE);

        Doctor updatedDoctor = doctorRepository.save(doctor);

        return doctorMapper.toResponse(updatedDoctor);
    }

    // ==========================================
    // SEARCH DOCTORS
    // ==========================================

    @Override
    @Transactional(readOnly = true)
    public Page<DoctorSummaryResponse> searchDoctors(
            String keyword,
            Pageable pageable) {

        if (keyword == null || keyword.isBlank()) {
            throw new BadRequestException(
                    DoctorConstants.INVALID_SEARCH_KEYWORD);
        }

        return doctorRepository.searchDoctors(
                        keyword.trim(), pageable)
                .map(doctorMapper::toSummaryResponse);
    }

    // ==========================================
    // GET DOCTORS BY SPECIALIZATION
    // ==========================================

    @Override
    @Transactional(readOnly = true)
    public Page<DoctorSummaryResponse> getDoctorsBySpecialization(
            Long specializationId,
            Pageable pageable) {

        if (specializationId == null || specializationId <= 0) {
            throw new BadRequestException(
                    DoctorConstants.INVALID_SPECIALIZATION_ID);
        }

        return doctorRepository.findBySpecialization_IdAndStatus(
                        specializationId,
                        DoctorStatus.ACTIVE,
                        pageable)
                .map(doctorMapper::toSummaryResponse);
    }

    // ==========================================
    // GET DOCTORS BY STATUS
    // ==========================================

    @Override
    @Transactional(readOnly = true)
    public Page<DoctorSummaryResponse> getDoctorsByStatus(
            String status,
            Pageable pageable) {

        if (status == null || status.isBlank()) {
            throw new BadRequestException(
                    DoctorConstants.DOCTOR_STATUS_REQUIRED);
        }

        DoctorStatus doctorStatus;

        try {
            doctorStatus = DoctorStatus.valueOf(
                    status.trim().toUpperCase());

        } catch (IllegalArgumentException exception) {
            throw new BadRequestException(
                    DoctorConstants.INVALID_DOCTOR_STATUS);
        }

        return doctorRepository.findByStatus(
                        doctorStatus, pageable)
                .map(doctorMapper::toSummaryResponse);
    }

    // ==========================================
    // VALIDATE DOCTOR OWNERSHIP
    // ==========================================

    private void validateDoctorOwnership(Doctor doctor) {

        Authentication authentication =
                SecurityContextHolder.getContext()
                        .getAuthentication();

        if (authentication == null
                || !authentication.isAuthenticated()) {

            throw new AccessDeniedException(
                    "Authentication is required.");
        }

        // ADMIN can update or deactivate any doctor
        boolean isAdmin = authentication.getAuthorities()
                .stream()
                .anyMatch(authority ->
                        "ROLE_ADMIN".equals(
                                authority.getAuthority()));

        if (isAdmin) {
            return;
        }

        // Only the authenticated doctor can modify own profile
        Object principal = authentication.getPrincipal();

        if (!(principal instanceof DoctorUserPrincipal doctorPrincipal)) {
            throw new AccessDeniedException(
                    "Invalid authenticated user.");
        }

        Long authenticatedUserId = doctorPrincipal.userId();
        Long doctorIdentityUserId = doctor.getIdentityUserId();

        if (authenticatedUserId == null
                || !Objects.equals(
                authenticatedUserId,
                doctorIdentityUserId)) {

            throw new AccessDeniedException(
                    "You are not authorized to modify this doctor profile.");
        }
    }

    // ==========================================
    // FIND DOCTOR BY ID
    // ==========================================

    private Doctor findDoctorById(Long doctorId) {

        if (doctorId == null || doctorId <= 0) {
            throw new BadRequestException(
                    DoctorConstants.INVALID_DOCTOR_ID);
        }

        return doctorRepository.findById(doctorId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        DoctorConstants.DOCTOR_NOT_FOUND
                                + " with ID: " + doctorId));
    }
}
