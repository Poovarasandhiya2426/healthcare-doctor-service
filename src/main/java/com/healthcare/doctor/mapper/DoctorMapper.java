
package com.healthcare.doctor.mapper;

import com.healthcare.doctor.dto.request.DoctorRegistrationRequest;
import com.healthcare.doctor.dto.request.DoctorUpdateRequest;
import com.healthcare.doctor.dto.response.DoctorResponse;
import com.healthcare.doctor.dto.response.DoctorSummaryResponse;
import com.healthcare.doctor.entity.Doctor;

import org.springframework.stereotype.Component;

@Component
public class DoctorMapper {

    // Registration Request DTO to Entity
    public Doctor toEntity(DoctorRegistrationRequest request) {

        return Doctor.builder()
                .identityUserId(request.getIdentityUserId())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .dateOfBirth(request.getDateOfBirth())
                .gender(request.getGender())
                .specializationId(request.getSpecializationId())
                .licenseNumber(request.getLicenseNumber())
                .yearsOfExperience(request.getYearsOfExperience())
                .consultationFee(request.getConsultationFee())
                .profileImageUrl(request.getProfileImageUrl())
                .biography(request.getBiography())
                .status("ACTIVE")
                .build();
    }

    // Update Request DTO to Existing Entity
    public void updateEntity(
            DoctorUpdateRequest request,
            Doctor doctor) {

        doctor.setFirstName(request.getFirstName());
        doctor.setLastName(request.getLastName());
        doctor.setEmail(request.getEmail());
        doctor.setPhoneNumber(request.getPhoneNumber());
        doctor.setDateOfBirth(request.getDateOfBirth());
        doctor.setGender(request.getGender());
        doctor.setSpecializationId(request.getSpecializationId());
        doctor.setYearsOfExperience(request.getYearsOfExperience());
        doctor.setConsultationFee(request.getConsultationFee());
        doctor.setProfileImageUrl(request.getProfileImageUrl());
        doctor.setBiography(request.getBiography());
    }

    // Entity to Doctor Response DTO
    public DoctorResponse toResponse(Doctor doctor) {

        String fullName = doctor.getFirstName() + " "
                + doctor.getLastName();

        return DoctorResponse.builder()
                .id(doctor.getId())
                .identityUserId(doctor.getIdentityUserId())
                .firstName(doctor.getFirstName())
                .lastName(doctor.getLastName())
                .fullName(fullName)
                .email(doctor.getEmail())
                .phoneNumber(doctor.getPhoneNumber())
                .dateOfBirth(doctor.getDateOfBirth())
                .gender(doctor.getGender())
                .specializationId(doctor.getSpecializationId())
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

    // Entity to Summary Response DTO
    public DoctorSummaryResponse toSummaryResponse(Doctor doctor) {

        String fullName = doctor.getFirstName() + " "
                + doctor.getLastName();

        return DoctorSummaryResponse.builder()
                .id(doctor.getId())
                .fullName(fullName)
                .email(doctor.getEmail())
                .phoneNumber(doctor.getPhoneNumber())
                .specializationId(doctor.getSpecializationId())
                .yearsOfExperience(doctor.getYearsOfExperience())
                .consultationFee(doctor.getConsultationFee())
                .status(doctor.getStatus())
                .build();
    }
}
