
package com.healthcare.doctor.dto.response;

import com.healthcare.doctor.enums.ConsultationMode;
import com.healthcare.doctor.enums.WeekDay;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoctorAvailabilityResponse {

    private Long id;

    private Long doctorId;

    private WeekDay dayOfWeek;

    private LocalTime startTime;

    private LocalTime endTime;

    private ConsultationMode consultationMode;

    private Integer slotDurationMinutes;

    private Boolean active;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
