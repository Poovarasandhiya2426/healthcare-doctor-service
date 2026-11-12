
package com.healthcare.doctor.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoctorHospitalRequest {

    @NotNull(message = "Doctor ID is required")
    @Positive(message = "Doctor ID must be positive")
    private Long doctorId;

    @NotNull(message = "Hospital ID is required")
    @Positive(message = "Hospital ID must be positive")
    private Long hospitalId;

    @Size(max = 150, message = "Department must not exceed 150 characters")
    private String department;

    @PastOrPresent(message = "Joining date cannot be in the future")
    private LocalDate joiningDate;

    @DecimalMin(
            value = "0.00",
            inclusive = true,
            message = "Consultation fee cannot be negative"
    )
    private BigDecimal consultationFee;

    private Boolean primaryHospital;

    private Boolean active;
}
