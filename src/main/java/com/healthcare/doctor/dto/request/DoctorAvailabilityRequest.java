
package com.healthcare.doctor.dto.request;

import com.healthcare.doctor.constants.DoctorConstants;
import com.healthcare.doctor.enums.ConsultationMode;
import com.healthcare.doctor.enums.WeekDay;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

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

    @NotNull(message = DoctorConstants.DOCTOR_ID_REQUIRED)
    @Positive(message = DoctorConstants.INVALID_DOCTOR_ID)
    private Long doctorId;

    @NotNull(message = DoctorConstants.DAY_OF_WEEK_REQUIRED)
    private WeekDay dayOfWeek;

    @NotNull(message = DoctorConstants.START_TIME_REQUIRED)
    private LocalTime startTime;

    @NotNull(message = DoctorConstants.END_TIME_REQUIRED)
    private LocalTime endTime;

    @NotNull(message = DoctorConstants.CONSULTATION_MODE_REQUIRED)
    private ConsultationMode consultationMode;

    @NotNull(message = DoctorConstants.SLOT_DURATION_REQUIRED)
    @Min(
            value = 5,
            message = DoctorConstants.MIN_SLOT_DURATION
    )
    @Max(
            value = 240,
            message = DoctorConstants.MAX_SLOT_DURATION
    )
    private Integer slotDurationMinutes;

    @AssertTrue(message = DoctorConstants.INVALID_AVAILABILITY_TIME_RANGE)
    public boolean isValidTimeRange() {

        if (startTime == null || endTime == null) {
            return true;
        }

        return endTime.isAfter(startTime);
    }
}
