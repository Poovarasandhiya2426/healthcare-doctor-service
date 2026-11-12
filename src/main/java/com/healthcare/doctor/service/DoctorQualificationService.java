
package com.healthcare.doctor.service;

import com.healthcare.doctor.dto.request.DoctorQualificationRequest;
import com.healthcare.doctor.dto.response.DoctorQualificationResponse;

import java.util.List;

public interface DoctorQualificationService {

    DoctorQualificationResponse createQualification(
            DoctorQualificationRequest request
    );

    DoctorQualificationResponse getQualificationById(
            Long qualificationId
    );

    List<DoctorQualificationResponse> getQualificationsByDoctorId(
            Long doctorId
    );

    DoctorQualificationResponse updateQualification(
            Long qualificationId,
            DoctorQualificationRequest request
    );

    void deleteQualification(
            Long qualificationId
    );
}
