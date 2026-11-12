
package com.healthcare.doctor.mapper;

import com.healthcare.doctor.dto.request.DoctorHospitalRequest;
import com.healthcare.doctor.dto.response.DoctorHospitalResponse;
import com.healthcare.doctor.entity.Doctor;
import com.healthcare.doctor.entity.DoctorHospital;

import org.springframework.stereotype.Component;

@Component
public class DoctorHospitalMapper {

    // ==========================================
    // REQUEST DTO TO ENTITY
    // ==========================================

    public DoctorHospital toEntity(
            DoctorHospitalRequest request,
            Doctor doctor) {

        return DoctorHospital.builder()
                .doctor(doctor)
                .hospitalId(request.getHospitalId())
                .department(request.getDepartment())
                .joiningDate(request.getJoiningDate())
                .consultationFee(request.getConsultationFee())
                .primaryHospital(
                        request.getPrimaryHospital() != null
                                ? request.getPrimaryHospital()
                                : false
                )
                .active(
                        request.getActive() != null
                                ? request.getActive()
                                : true
                )
                .build();
    }

    // ==========================================
    // UPDATE EXISTING ENTITY
    // ==========================================

    public void updateEntity(
            DoctorHospital doctorHospital,
            DoctorHospitalRequest request) {

        doctorHospital.setDepartment(request.getDepartment());
        doctorHospital.setJoiningDate(request.getJoiningDate());
        doctorHospital.setConsultationFee(request.getConsultationFee());

        if (request.getPrimaryHospital() != null) {
            doctorHospital.setPrimaryHospital(
                    request.getPrimaryHospital()
            );
        }

        if (request.getActive() != null) {
            doctorHospital.setActive(request.getActive());
        }
    }

    // ==========================================
    // ENTITY TO RESPONSE DTO
    // ==========================================

    public DoctorHospitalResponse toResponse(
            DoctorHospital doctorHospital) {

        Doctor doctor = doctorHospital.getDoctor();

        String doctorName = doctor.getFirstName() + " "
                + doctor.getLastName();

        return DoctorHospitalResponse.builder()
                .id(doctorHospital.getId())
                .doctorId(doctor.getId())
                .doctorName(doctorName)
                .hospitalId(doctorHospital.getHospitalId())
                .department(doctorHospital.getDepartment())
                .joiningDate(doctorHospital.getJoiningDate())
                .consultationFee(doctorHospital.getConsultationFee())
                .primaryHospital(doctorHospital.getPrimaryHospital())
                .active(doctorHospital.getActive())
                .createdAt(doctorHospital.getCreatedAt())
                .updatedAt(doctorHospital.getUpdatedAt())
                .build();
    }
}
