
package com.healthcare.doctor.service;

import com.healthcare.doctor.dto.request.DoctorHospitalRequest;
import com.healthcare.doctor.dto.response.DoctorHospitalResponse;

import java.util.List;

public interface DoctorHospitalService {

    // Create doctor-hospital mapping
    DoctorHospitalResponse createHospitalMapping(
            DoctorHospitalRequest request
    );

    // Get mapping by ID
    DoctorHospitalResponse getHospitalMappingById(
            Long mappingId
    );

    // Get all hospitals mapped to a doctor
    List<DoctorHospitalResponse> getHospitalsByDoctorId(
            Long doctorId
    );

    // Get all doctors mapped to a hospital
    List<DoctorHospitalResponse> getDoctorsByHospitalId(
            Long hospitalId
    );

    // Get active hospital mappings of a doctor
    List<DoctorHospitalResponse> getActiveHospitalsByDoctorId(
            Long doctorId
    );

    // Update hospital mapping
    DoctorHospitalResponse updateHospitalMapping(
            Long mappingId,
            DoctorHospitalRequest request
    );

    // Deactivate hospital mapping
    void deactivateHospitalMapping(
            Long mappingId
    );

    // Delete hospital mapping
    void deleteHospitalMapping(
            Long mappingId
    );
}
