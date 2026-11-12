
package com.healthcare.doctor.service;

import com.healthcare.doctor.dto.request.DoctorRegistrationRequest;
import com.healthcare.doctor.dto.request.DoctorUpdateRequest;
import com.healthcare.doctor.dto.response.DoctorResponse;
import com.healthcare.doctor.dto.response.DoctorSummaryResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DoctorService {

    DoctorResponse registerDoctor(DoctorRegistrationRequest request);

    DoctorResponse getDoctorById(Long doctorId);

    DoctorResponse getDoctorByEmail(String email);

    Page<DoctorSummaryResponse> getAllDoctors(Pageable pageable);

    DoctorResponse updateDoctor(
            Long doctorId,
            DoctorUpdateRequest request);

    DoctorResponse deactivateDoctor(Long doctorId);

    Page<DoctorSummaryResponse> searchDoctors(
            String keyword,
            Pageable pageable);

    Page<DoctorSummaryResponse> getDoctorsBySpecialization(
            Long specializationId,
            Pageable pageable);

    Page<DoctorSummaryResponse> getDoctorsByStatus(
            String status,
            Pageable pageable);
}
