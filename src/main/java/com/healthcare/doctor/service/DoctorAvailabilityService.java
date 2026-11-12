
package com.healthcare.doctor.service;

import com.healthcare.doctor.dto.request.DoctorAvailabilityRequest;
import com.healthcare.doctor.dto.response.DoctorAvailabilityResponse;

import java.util.List;

public interface DoctorAvailabilityService {

    // Create doctor availability
    DoctorAvailabilityResponse createAvailability(
            DoctorAvailabilityRequest request);

    // Get availability by ID
    DoctorAvailabilityResponse getAvailabilityById(
            Long availabilityId);

    // Get all availability records for a doctor
    List<DoctorAvailabilityResponse> getAvailabilityByDoctorId(
            Long doctorId);

    // Get availability by doctor and weekday
    List<DoctorAvailabilityResponse> getAvailabilityByDoctorAndDay(
            Long doctorId,
            String dayOfWeek);

    // Update doctor availability
    DoctorAvailabilityResponse updateAvailability(
            Long availabilityId,
            DoctorAvailabilityRequest request);

    // Deactivate doctor availability
    DoctorAvailabilityResponse deactivateAvailability(
            Long availabilityId);
}
