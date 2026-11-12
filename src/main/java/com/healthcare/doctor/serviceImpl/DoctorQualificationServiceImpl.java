
package com.healthcare.doctor.serviceImpl;

import com.healthcare.doctor.constants.DoctorConstants;
import com.healthcare.doctor.dto.request.DoctorQualificationRequest;
import com.healthcare.doctor.dto.response.DoctorQualificationResponse;
import com.healthcare.doctor.entity.Doctor;
import com.healthcare.doctor.entity.DoctorQualification;
import com.healthcare.doctor.exception.BadRequestException;
import com.healthcare.doctor.exception.DuplicateResourceException;
import com.healthcare.doctor.exception.ResourceNotFoundException;
import com.healthcare.doctor.mapper.DoctorQualificationMapper;
import com.healthcare.doctor.repository.DoctorQualificationRepository;
import com.healthcare.doctor.repository.DoctorRepository;
import com.healthcare.doctor.service.DoctorQualificationService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Year;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DoctorQualificationServiceImpl
        implements DoctorQualificationService {

    private final DoctorQualificationRepository qualificationRepository;
    private final DoctorRepository doctorRepository;
    private final DoctorQualificationMapper qualificationMapper;

    // ==========================================
    // CREATE DOCTOR QUALIFICATION
    // ==========================================

    @Override
    public DoctorQualificationResponse createQualification(
            DoctorQualificationRequest request) {

        validateRequest(request);

        Doctor doctor = findDoctorById(request.getDoctorId());

        validateDuplicateQualification(
                doctor.getId(),
                request.getDegree(),
                request.getUniversity(),
                null
        );

        DoctorQualification qualification =
                qualificationMapper.toEntity(request, doctor);

        DoctorQualification savedQualification =
                qualificationRepository.save(qualification);

        return qualificationMapper.toResponse(savedQualification);
    }

    // ==========================================
    // GET QUALIFICATION BY ID
    // ==========================================

    @Override
    @Transactional(readOnly = true)
    public DoctorQualificationResponse getQualificationById(
            Long qualificationId) {

        DoctorQualification qualification =
                findQualificationById(qualificationId);

        return qualificationMapper.toResponse(qualification);
    }

    // ==========================================
    // GET QUALIFICATIONS BY DOCTOR ID
    // ==========================================

    @Override
    @Transactional(readOnly = true)
    public List<DoctorQualificationResponse> getQualificationsByDoctorId(
            Long doctorId) {

        Doctor doctor = findDoctorById(doctorId);

        return qualificationRepository.findByDoctorId(doctor.getId())
                .stream()
                .map(qualificationMapper::toResponse)
                .toList();
    }

    // ==========================================
    // UPDATE DOCTOR QUALIFICATION
    // ==========================================

    @Override
    public DoctorQualificationResponse updateQualification(
            Long qualificationId,
            DoctorQualificationRequest request) {

        validateRequest(request);

        DoctorQualification qualification =
                findQualificationById(qualificationId);

        Long existingDoctorId =
                qualification.getDoctor().getId();

        if (!existingDoctorId.equals(request.getDoctorId())) {
            throw new BadRequestException(
                    DoctorConstants.QUALIFICATION_MOVE_NOT_ALLOWED
            );
        }

        validateDuplicateQualification(
                existingDoctorId,
                request.getDegree(),
                request.getUniversity(),
                qualificationId
        );

        qualificationMapper.updateEntity(qualification, request);

        DoctorQualification updatedQualification =
                qualificationRepository.save(qualification);

        return qualificationMapper.toResponse(updatedQualification);
    }

    // ==========================================
    // DELETE DOCTOR QUALIFICATION
    // ==========================================

    @Override
    public void deleteQualification(Long qualificationId) {

        DoctorQualification qualification =
                findQualificationById(qualificationId);

        qualificationRepository.delete(qualification);
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
    // FIND QUALIFICATION BY ID
    // ==========================================

    private DoctorQualification findQualificationById(
            Long qualificationId) {

        if (qualificationId == null || qualificationId <= 0) {
            throw new BadRequestException(
                    DoctorConstants.INVALID_QUALIFICATION_ID
            );
        }

        return qualificationRepository.findById(qualificationId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                DoctorConstants.QUALIFICATION_NOT_FOUND
                                        + " with ID: " + qualificationId
                        )
                );
    }

    // ==========================================
    // VALIDATE REQUEST
    // ==========================================

    private void validateRequest(
            DoctorQualificationRequest request) {

        if (request == null) {
            throw new BadRequestException(
                    DoctorConstants.QUALIFICATION_REQUEST_EMPTY
            );
        }

        if (request.getDoctorId() == null
                || request.getDoctorId() <= 0) {

            throw new BadRequestException(
                    DoctorConstants.INVALID_DOCTOR_ID
            );
        }

        if (request.getDegree() == null
                || request.getDegree().isBlank()) {

            throw new BadRequestException(
                    DoctorConstants.DEGREE_REQUIRED
            );
        }

        if (request.getUniversity() == null
                || request.getUniversity().isBlank()) {

            throw new BadRequestException(
                    DoctorConstants.UNIVERSITY_REQUIRED
            );
        }

        if (request.getYearOfCompletion() == null
                || request.getYearOfCompletion() < 1900
                || request.getYearOfCompletion() > Year.now().getValue()) {

            throw new BadRequestException(
                    DoctorConstants.INVALID_COMPLETION_YEAR
            );
        }
    }

    // ==========================================
    // VALIDATE DUPLICATE QUALIFICATION
    // ==========================================

    private void validateDuplicateQualification(
            Long doctorId,
            String degree,
            String university,
            Long excludeQualificationId) {

        String normalizedDegree = degree.trim();
        String normalizedUniversity = university.trim();

        boolean duplicate = qualificationRepository
                .findByDoctorId(doctorId)
                .stream()
                .anyMatch(existing ->
                        !existing.getId().equals(excludeQualificationId)
                                && existing.getDegree() != null
                                && existing.getUniversity() != null
                                && existing.getDegree().trim()
                                .equalsIgnoreCase(normalizedDegree)
                                && existing.getUniversity().trim()
                                .equalsIgnoreCase(normalizedUniversity)
                );

        if (duplicate) {
            throw new DuplicateResourceException(
                    DoctorConstants.DUPLICATE_QUALIFICATION
            );
        }
    }
}
