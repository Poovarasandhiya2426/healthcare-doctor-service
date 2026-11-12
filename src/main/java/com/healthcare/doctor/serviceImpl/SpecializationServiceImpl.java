
package com.healthcare.doctor.serviceImpl;

import com.healthcare.doctor.dto.request.SpecializationRequest;
import com.healthcare.doctor.dto.response.SpecializationResponse;
import com.healthcare.doctor.entity.Specialization;
import com.healthcare.doctor.exception.BadRequestException;
import com.healthcare.doctor.exception.DuplicateResourceException;
import com.healthcare.doctor.exception.ResourceNotFoundException;
import com.healthcare.doctor.mapper.SpecializationMapper;
import com.healthcare.doctor.repository.SpecializationRepository;
import com.healthcare.doctor.service.SpecializationService;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class SpecializationServiceImpl implements SpecializationService {

    private final SpecializationRepository specializationRepository;
    private final SpecializationMapper specializationMapper;

    @Override
    public SpecializationResponse createSpecialization(
            SpecializationRequest request) {

        if (specializationRepository.existsByNameIgnoreCase(
                request.getName().trim())) {
            throw new DuplicateResourceException(
                    "Specialization name already exists");
        }

        Specialization specialization =
                specializationMapper.toEntity(request);

        Specialization savedSpecialization =
                specializationRepository.save(specialization);

        return specializationMapper.toResponse(savedSpecialization);
    }

    @Override
    @Transactional(readOnly = true)
    public SpecializationResponse getSpecializationById(Long id) {

        Specialization specialization = findSpecializationById(id);

        return specializationMapper.toResponse(specialization);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SpecializationResponse> getAllSpecializations(
            Pageable pageable) {

        return specializationRepository.findByActiveTrue(pageable)
                .map(specializationMapper::toResponse);
    }

    @Override
    public SpecializationResponse updateSpecialization(
            Long id,
            SpecializationRequest request) {

        Specialization specialization = findSpecializationById(id);

        String newName = request.getName().trim();

        specializationRepository.findByNameIgnoreCase(newName)
                .filter(existing -> !existing.getId().equals(id))
                .ifPresent(existing -> {
                    throw new DuplicateResourceException(
                            "Specialization name already exists");
                });

        specializationMapper.updateEntity(request, specialization);

        Specialization updatedSpecialization =
                specializationRepository.save(specialization);

        return specializationMapper.toResponse(updatedSpecialization);
    }

    @Override
    public void deactivateSpecialization(Long id) {

        Specialization specialization = findSpecializationById(id);

        if (!Boolean.TRUE.equals(specialization.getActive())) {
            throw new BadRequestException(
                    "Specialization is already inactive");
        }

        specialization.setActive(false);

        specializationRepository.save(specialization);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SpecializationResponse> searchSpecializations(
            String keyword,
            Pageable pageable) {

        if (keyword == null || keyword.isBlank()) {
            throw new BadRequestException(
                    "Search keyword is required");
        }

        return specializationRepository
                .findByNameContainingIgnoreCaseAndActiveTrue(
                        keyword.trim(), pageable)
                .map(specializationMapper::toResponse);
    }

    private Specialization findSpecializationById(Long id) {

        if (id == null || id <= 0) {
            throw new BadRequestException(
                    "Valid specialization ID is required");
        }

        return specializationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Specialization not found with ID: " + id));
    }
}
