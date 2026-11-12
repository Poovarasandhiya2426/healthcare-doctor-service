
package com.healthcare.doctor.mapper;

import com.healthcare.doctor.dto.request.DoctorRegistrationRequest;
import com.healthcare.doctor.dto.request.DoctorUpdateRequest;
import com.healthcare.doctor.dto.response.DoctorResponse;
import com.healthcare.doctor.dto.response.DoctorSummaryResponse;
import com.healthcare.doctor.entity.Doctor;
import com.healthcare.doctor.entity.Specialization;
import com.healthcare.doctor.enums.DoctorStatus;
import com.healthcare.doctor.exception.ResourceNotFoundException;
import com.healthcare.doctor.repository.SpecializationRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DoctorMapper {

    private final SpecializationRepository specializationRepository;

    // Registration request to entity
    public Doctor toEntity(DoctorRegistrationRequest request) {

        Specialization specialization =
                findActiveSpecialization(request.getSpecializationId());

        return Doctor.builder()
                .identityUserId(request.getIdentityUserId())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .dateOfBirth(request.getDateOfBirth())
                .gender(request.getGender())
                .specialization(specialization)
                .licenseNumber(request.getLicenseNumber())
                .yearsOfExperience(request.getYearsOfExperience())
                .consultationFee(request.getConsultationFee())
                .profileImageUrl(request.getProfileImageUrl())
                .biography(request.getBiography())
                .status(DoctorStatus.ACTIVE)
                .build();
    }

    // Update existing doctor entity
    public void updateEntity(
            DoctorUpdateRequest request,
            Doctor doctor) {

        doctor.setFirstName(request.getFirstName());
        doctor.setLastName(request.getLastName());
        doctor.setEmail(request.getEmail());
        doctor.setPhoneNumber(request.getPhoneNumber());
        doctor.setDateOfBirth(request.getDateOfBirth());
        doctor.setGender(request.getGender());

        if (request.getSpecializationId() != null) {
            doctor.setSpecialization(
                    findActiveSpecialization(request.getSpecializationId()));
        }

        doctor.setYearsOfExperience(request.getYearsOfExperience());
        doctor.setConsultationFee(request.getConsultationFee());
        doctor.setProfileImageUrl(request.getProfileImageUrl());
        doctor.setBiography(request.getBiography());
    }

    // Entity to full response
    public DoctorResponse toResponse(Doctor doctor) {

        return DoctorResponse.builder()
                .id(doctor.getId())
                .identityUserId(doctor.getIdentityUserId())
                .firstName(doctor.getFirstName())
                .lastName(doctor.getLastName())
                .fullName(
                        doctor.getFirstName() + " "
                                + doctor.getLastName())
                .email(doctor.getEmail())
                .phoneNumber(doctor.getPhoneNumber())
                .dateOfBirth(doctor.getDateOfBirth())
                .gender(doctor.getGender())
                .specializationId(getSpecializationId(doctor))
                .specializationName(getSpecializationName(doctor))
                .licenseNumber(doctor.getLicenseNumber())
                .yearsOfExperience(doctor.getYearsOfExperience())
                .consultationFee(doctor.getConsultationFee())
                .profileImageUrl(doctor.getProfileImageUrl())
                .biography(doctor.getBiography())
                .status(doctor.getStatus())
                .createdAt(doctor.getCreatedAt())
                .updatedAt(doctor.getUpdatedAt())
                .build();
    }

    // Entity to summary response
    public DoctorSummaryResponse toSummaryResponse(Doctor doctor) {

        return DoctorSummaryResponse.builder()
                .id(doctor.getId())
                .fullName(
                        doctor.getFirstName() + " "
                                + doctor.getLastName())
                .email(doctor.getEmail())
                .phoneNumber(doctor.getPhoneNumber())
                .specializationId(getSpecializationId(doctor))
                .specializationName(getSpecializationName(doctor))
                .yearsOfExperience(doctor.getYearsOfExperience())
                .consultationFee(doctor.getConsultationFee())
                .status(doctor.getStatus())
                .build();
    }

    // Find active specialization
    private Specialization findActiveSpecialization(Long specializationId) {

        if (specializationId == null) {
            return null;
        }

        return specializationRepository.findById(specializationId)
                .filter(specialization ->
                        Boolean.TRUE.equals(specialization.getActive()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Active specialization not found with ID: "
                                + specializationId));
    }

    // Extract specialization ID
    private Long getSpecializationId(Doctor doctor) {

        return doctor.getSpecialization() != null
                ? doctor.getSpecialization().getId()
                : null;
    }

    // Extract specialization name
    private String getSpecializationName(Doctor doctor) {

        return doctor.getSpecialization() != null
                ? doctor.getSpecialization().getName()
                : null;
    }
}
