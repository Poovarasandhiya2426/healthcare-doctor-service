
package com.healthcare.doctor.service;

import com.healthcare.doctor.dto.request.SpecializationRequest;
import com.healthcare.doctor.dto.response.SpecializationResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SpecializationService {

    SpecializationResponse createSpecialization(
            SpecializationRequest request);

    SpecializationResponse getSpecializationById(Long id);

    Page<SpecializationResponse> getAllSpecializations(
            Pageable pageable);

    SpecializationResponse updateSpecialization(
            Long id,
            SpecializationRequest request);

    void deactivateSpecialization(Long id);

    Page<SpecializationResponse> searchSpecializations(
            String keyword,
            Pageable pageable);
}
