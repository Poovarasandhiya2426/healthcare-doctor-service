
package com.healthcare.doctor.mapper;

import com.healthcare.doctor.dto.request.DoctorAvailabilityRequest;
import com.healthcare.doctor.dto.response.DoctorAvailabilityResponse;
import com.healthcare.doctor.entity.Doctor;
import com.healthcare.doctor.entity.DoctorAvailability;

import org.springframework.stereotype.Component;

@Component
public class DoctorAvailabilityMapper {

    // Request DTO to Entity
    public DoctorAvailability toEntity(
            DoctorAvailabilityRequest request,
            Doctor doctor) {

        return DoctorAvailability.builder()
                .doctor(doctor)
                .dayOfWeek(request.getDayOfWeek())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .consultationMode(request.getConsultationMode())
                .slotDurationMinutes(
                        request.getSlotDurationMinutes())
                .active(true)
                .build();
    }

    // Entity to Response DTO
    public DoctorAvailabilityResponse toResponse(
            DoctorAvailability availability) {

        return DoctorAvailabilityResponse.builder()
                .id(availability.getId())
                .doctorId(availability.getDoctor().getId())
                .dayOfWeek(availability.getDayOfWeek())
                .startTime(availability.getStartTime())
                .endTime(availability.getEndTime())
                .consultationMode(
                        availability.getConsultationMode())
                .slotDurationMinutes(
                        availability.getSlotDurationMinutes())
                .active(availability.getActive())
                .createdAt(availability.getCreatedAt())
                .updatedAt(availability.getUpdatedAt())
                .build();
    }
}
