
package com.healthcare.doctor.mapper;

import com.healthcare.doctor.dto.request.DoctorQualificationRequest;
import com.healthcare.doctor.dto.response.DoctorQualificationResponse;
import com.healthcare.doctor.entity.Doctor;
import com.healthcare.doctor.entity.DoctorQualification;

import org.springframework.stereotype.Component;

@Component
public class DoctorQualificationMapper {

    // ==========================================
    // REQUEST TO ENTITY
    // ==========================================

    public DoctorQualification toEntity(
            DoctorQualificationRequest request,
            Doctor doctor) {

        return DoctorQualification.builder()
                .doctor(doctor)
                .degree(request.getDegree().trim())
                .university(request.getUniversity().trim())
                .yearOfCompletion(request.getYearOfCompletion())
                .certificateUrl(normalizeCertificateUrl(
                        request.getCertificateUrl()
                ))
                .build();
    }

    // ==========================================
    // UPDATE ENTITY
    // ==========================================

    public void updateEntity(
            DoctorQualification qualification,
            DoctorQualificationRequest request) {

        qualification.setDegree(request.getDegree().trim());
        qualification.setUniversity(request.getUniversity().trim());
        qualification.setYearOfCompletion(request.getYearOfCompletion());
        qualification.setCertificateUrl(
                normalizeCertificateUrl(request.getCertificateUrl())
        );
    }

    // ==========================================
    // ENTITY TO RESPONSE
    // ==========================================

    public DoctorQualificationResponse toResponse(
            DoctorQualification qualification) {

        Doctor doctor = qualification.getDoctor();

        String doctorName = doctor.getFirstName() + " "
                + doctor.getLastName();

        return DoctorQualificationResponse.builder()
                .id(qualification.getId())
                .doctorId(doctor.getId())
                .doctorName(doctorName)
                .degree(qualification.getDegree())
                .university(qualification.getUniversity())
                .yearOfCompletion(qualification.getYearOfCompletion())
                .certificateUrl(qualification.getCertificateUrl())
                .createdAt(qualification.getCreatedAt())
                .updatedAt(qualification.getUpdatedAt())
                .build();
    }

    // ==========================================
    // NORMALIZE CERTIFICATE URL
    // ==========================================

    private String normalizeCertificateUrl(String certificateUrl) {

        if (certificateUrl == null || certificateUrl.isBlank()) {
            return null;
        }

        return certificateUrl.trim();
    }
}
