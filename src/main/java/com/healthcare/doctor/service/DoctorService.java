
package com.healthcare.doctor.service;

import com.healthcare.doctor.dto.request.DoctorRegistrationRequest;
import com.healthcare.doctor.dto.request.DoctorUpdateRequest;
import com.healthcare.doctor.dto.response.DoctorResponse;
import com.healthcare.doctor.dto.response.DoctorSummaryResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DoctorService {

    // Register a new doctor
    DoctorResponse registerDoctor(DoctorRegistrationRequest request);

    // Get doctor by ID
    DoctorResponse getDoctorById(Long doctorId);

    // Get doctor by email
    DoctorResponse getDoctorByEmail(String email);

    // Get all doctors with pagination
    Page<DoctorSummaryResponse> getAllDoctors(Pageable pageable);

    // Update doctor details
    DoctorResponse updateDoctor(
            Long doctorId,
            DoctorUpdateRequest request
    );

    // Deactivate doctor
    DoctorResponse deactivateDoctor(Long doctorId);

    // Search doctors
    Page<DoctorSummaryResponse> searchDoctors(
            String keyword,
            Pageable pageable
    );

    // Get doctors by specialization
    Page<DoctorSummaryResponse> getDoctorsBySpecialization(
            Long specializationId,
            Pageable pageable
    );

    // Get doctors by status
    Page<DoctorSummaryResponse> getDoctorsByStatus(
            String status,
            Pageable pageable
    );
}
