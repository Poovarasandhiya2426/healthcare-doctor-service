
package com.healthcare.doctor.serviceImpl;

import com.healthcare.doctor.constants.DoctorConstants;

import com.healthcare.doctor.dto.request.DoctorHospitalRequest;
import com.healthcare.doctor.dto.response.DoctorHospitalResponse;

import com.healthcare.doctor.entity.Doctor;
import com.healthcare.doctor.entity.DoctorHospital;

import com.healthcare.doctor.exception.BadRequestException;
import com.healthcare.doctor.exception.DuplicateResourceException;
import com.healthcare.doctor.exception.ResourceNotFoundException;

import com.healthcare.doctor.mapper.DoctorHospitalMapper;

import com.healthcare.doctor.repository.DoctorHospitalRepository;
import com.healthcare.doctor.repository.DoctorRepository;

import com.healthcare.doctor.service.DoctorHospitalService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DoctorHospitalServiceImpl
        implements DoctorHospitalService {

    private final DoctorHospitalRepository hospitalRepository;

    private final DoctorRepository doctorRepository;

    private final DoctorHospitalMapper hospitalMapper;

    // ==========================================
    // CREATE HOSPITAL MAPPING
    // ==========================================

    @Override
    public DoctorHospitalResponse createHospitalMapping(
            DoctorHospitalRequest request) {

        validateRequest(request);

        Doctor doctor = findDoctorById(request.getDoctorId());

        validateDuplicateMapping(
                request.getDoctorId(),
                request.getHospitalId(),
                null
        );

        boolean primaryHospital =
                Boolean.TRUE.equals(request.getPrimaryHospital());

        if (primaryHospital) {
            validatePrimaryHospital(request.getDoctorId(), null);
        }

        DoctorHospital doctorHospital =
                hospitalMapper.toEntity(request, doctor);

        DoctorHospital savedMapping =
                hospitalRepository.save(doctorHospital);

        return hospitalMapper.toResponse(savedMapping);
    }

    // ==========================================
    // GET MAPPING BY ID
    // ==========================================

    @Override
    @Transactional(readOnly = true)
    public DoctorHospitalResponse getHospitalMappingById(
            Long mappingId) {

        DoctorHospital mapping = findMappingById(mappingId);

        return hospitalMapper.toResponse(mapping);
    }

    // ==========================================
    // GET HOSPITALS BY DOCTOR ID
    // ==========================================

    @Override
    @Transactional(readOnly = true)
    public List<DoctorHospitalResponse> getHospitalsByDoctorId(
            Long doctorId) {

        findDoctorById(doctorId);

        return hospitalRepository.findByDoctorId(doctorId)
                .stream()
                .map(hospitalMapper::toResponse)
                .toList();
    }

    // ==========================================
    // GET DOCTORS BY HOSPITAL ID
    // ==========================================

    @Override
    @Transactional(readOnly = true)
    public List<DoctorHospitalResponse> getDoctorsByHospitalId(
            Long hospitalId) {

        validateHospitalId(hospitalId);

        return hospitalRepository.findByHospitalId(hospitalId)
                .stream()
                .map(hospitalMapper::toResponse)
                .toList();
    }

    // ==========================================
    // GET ACTIVE HOSPITALS BY DOCTOR ID
    // ==========================================

    @Override
    @Transactional(readOnly = true)
    public List<DoctorHospitalResponse> getActiveHospitalsByDoctorId(
            Long doctorId) {

        findDoctorById(doctorId);

        return hospitalRepository.findByDoctorIdAndActiveTrue(doctorId)
                .stream()
                .map(hospitalMapper::toResponse)
                .toList();
    }

    // ==========================================
    // UPDATE HOSPITAL MAPPING
    // ==========================================

    @Override
    public DoctorHospitalResponse updateHospitalMapping(
            Long mappingId,
            DoctorHospitalRequest request) {

        validateRequest(request);

        DoctorHospital mapping = findMappingById(mappingId);

        Long existingDoctorId = mapping.getDoctor().getId();

        if (!existingDoctorId.equals(request.getDoctorId())) {
            throw new BadRequestException(
                    DoctorConstants.HOSPITAL_MAPPING_DOCTOR_CHANGE_NOT_ALLOWED
            );
        }

        if (!mapping.getHospitalId().equals(request.getHospitalId())) {
            throw new BadRequestException(
                    DoctorConstants.HOSPITAL_MAPPING_HOSPITAL_CHANGE_NOT_ALLOWED
            );
        }

        if (Boolean.TRUE.equals(request.getPrimaryHospital())) {
            validatePrimaryHospital(existingDoctorId, mappingId);
        }

        hospitalMapper.updateEntity(mapping, request);

        DoctorHospital updatedMapping =
                hospitalRepository.save(mapping);

        return hospitalMapper.toResponse(updatedMapping);
    }

    // ==========================================
    // DEACTIVATE HOSPITAL MAPPING
    // ==========================================

    @Override
    public void deactivateHospitalMapping(Long mappingId) {

        DoctorHospital mapping = findMappingById(mappingId);

        if (Boolean.FALSE.equals(mapping.getActive())) {
            throw new BadRequestException(
                    DoctorConstants.HOSPITAL_MAPPING_ALREADY_INACTIVE
            );
        }

        mapping.setActive(false);
        mapping.setPrimaryHospital(false);

        hospitalRepository.save(mapping);
    }

    // ==========================================
    // DELETE HOSPITAL MAPPING
    // ==========================================

    @Override
    public void deleteHospitalMapping(Long mappingId) {

        DoctorHospital mapping = findMappingById(mappingId);

        hospitalRepository.delete(mapping);
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
                                "Doctor not found with ID: " + doctorId
                        )
                );
    }

    // ==========================================
    // FIND MAPPING BY ID
    // ==========================================

    private DoctorHospital findMappingById(Long mappingId) {

        if (mappingId == null || mappingId <= 0) {
            throw new BadRequestException(
                    DoctorConstants.INVALID_HOSPITAL_MAPPING_ID
            );
        }

        return hospitalRepository.findById(mappingId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                DoctorConstants.HOSPITAL_MAPPING_NOT_FOUND
                                        + " with ID: " + mappingId
                        )
                );
    }

    // ==========================================
    // VALIDATE REQUEST
    // ==========================================

    private void validateRequest(DoctorHospitalRequest request) {

        if (request == null) {
            throw new BadRequestException(
                    DoctorConstants.HOSPITAL_MAPPING_REQUEST_EMPTY
            );
        }

        if (request.getDoctorId() == null
                || request.getDoctorId() <= 0) {
            throw new BadRequestException(
                    DoctorConstants.INVALID_DOCTOR_ID
            );
        }

        validateHospitalId(request.getHospitalId());

        if (request.getJoiningDate() != null
                && request.getJoiningDate().isAfter(LocalDate.now())) {
            throw new BadRequestException(
                    DoctorConstants.INVALID_JOINING_DATE
            );
        }

        BigDecimal fee = request.getConsultationFee();

        if (fee != null && fee.compareTo(BigDecimal.ZERO) < 0) {
            throw new BadRequestException(
                    DoctorConstants.INVALID_CONSULTATION_FEE
            );
        }
    }

    // ==========================================
    // VALIDATE HOSPITAL ID
    // ==========================================

    private void validateHospitalId(Long hospitalId) {

        if (hospitalId == null || hospitalId <= 0) {
            throw new BadRequestException(
                    DoctorConstants.INVALID_HOSPITAL_ID
            );
        }
    }

    // ==========================================
    // VALIDATE DUPLICATE MAPPING
    // ==========================================

    private void validateDuplicateMapping(
            Long doctorId,
            Long hospitalId,
            Long excludeMappingId) {

        boolean duplicate;

        if (excludeMappingId == null) {
            duplicate = hospitalRepository
                    .existsByDoctorIdAndHospitalId(
                            doctorId,
                            hospitalId
                    );
        } else {
            duplicate = hospitalRepository
                    .existsByDoctorIdAndHospitalIdAndIdNot(
                            doctorId,
                            hospitalId,
                            excludeMappingId
                    );
        }

        if (duplicate) {
            throw new DuplicateResourceException(
                    DoctorConstants.DUPLICATE_HOSPITAL_MAPPING
            );
        }
    }

    // ==========================================
    // VALIDATE PRIMARY HOSPITAL
    // ==========================================

    private void validatePrimaryHospital(
            Long doctorId,
            Long excludeMappingId) {

        hospitalRepository
                .findByDoctorIdAndPrimaryHospitalTrue(doctorId)
                .ifPresent(existing -> {

                    boolean isDifferentMapping =
                            excludeMappingId == null
                                    || !existing.getId()
                                    .equals(excludeMappingId);

                    if (isDifferentMapping) {
                        throw new DuplicateResourceException(
                                DoctorConstants.PRIMARY_HOSPITAL_EXISTS
                        );
                    }
                });
    }
}
