
package com.healthcare.doctor.mapper;

import com.healthcare.doctor.dto.request.SpecializationRequest;
import com.healthcare.doctor.dto.response.SpecializationResponse;
import com.healthcare.doctor.entity.Specialization;

import org.springframework.stereotype.Component;

@Component
public class SpecializationMapper {

    // Request to Entity
    public Specialization toEntity(SpecializationRequest request) {

        return Specialization.builder()
                .name(request.getName().trim())
                .description(request.getDescription())
                .active(true)
                .build();
    }

    // Update existing Entity
    public void updateEntity(
            SpecializationRequest request,
            Specialization specialization) {

        specialization.setName(request.getName().trim());
        specialization.setDescription(request.getDescription());
    }

    // Entity to Response
    public SpecializationResponse toResponse(
            Specialization specialization) {

        return SpecializationResponse.builder()
                .id(specialization.getId())
                .name(specialization.getName())
                .description(specialization.getDescription())
                .active(specialization.getActive())
                .createdAt(specialization.getCreatedAt())
                .updatedAt(specialization.getUpdatedAt())
                .build();
    }
}
