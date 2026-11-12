
package com.healthcare.doctor.dto.request;

import com.healthcare.doctor.enums.ConsultationMode;
import com.healthcare.doctor.enums.WeekDay;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.AssertTrue;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoctorAvailabilityRequest {

    @NotNull(message = "Doctor ID is required")
    private Long doctorId;

    @NotNull(message = "Day of week is required")
    private WeekDay dayOfWeek;

    @NotNull(message = "Start time is required")
    private LocalTime startTime;

    @NotNull(message = "End time is required")
    private LocalTime endTime;

    @NotNull(message = "Consultation mode is required")
    private ConsultationMode consultationMode;

    @NotNull(message = "Slot duration is required")
    @Min(value = 5, message = "Minimum slot duration is 5 minutes")
    @Max(value = 240, message = "Maximum slot duration is 240 minutes")
    private Integer slotDurationMinutes;

    @AssertTrue(message = "End time must be after start time")
    public boolean isValidTimeRange() {
        if (startTime == null || endTime == null) {
            return true;
        }
        return endTime.isAfter(startTime);
    }
}
