
package com.healthcare.doctor.dto.request;

import com.healthcare.doctor.constants.DoctorConstants;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoctorUpdateRequest {

    @NotBlank(message = DoctorConstants.FIRST_NAME_REQUIRED)
    @Size(max = 100)
    private String firstName;

    @NotBlank(message = DoctorConstants.LAST_NAME_REQUIRED)
    @Size(max = 100)
    private String lastName;

    @Email(message = DoctorConstants.INVALID_EMAIL_FORMAT)
    @NotBlank(message = DoctorConstants.EMAIL_REQUIRED)
    private String email;

    @Pattern(
            regexp = "^[0-9]{10,15}$",
            message = DoctorConstants.INVALID_PHONE_NUMBER
    )
    @NotBlank(message = DoctorConstants.PHONE_NUMBER_REQUIRED)
    private String phoneNumber;

    private LocalDate dateOfBirth;

    private String gender;

    private Long specializationId;

    @Min(
            value = 0,
            message = DoctorConstants.INVALID_EXPERIENCE
    )
    private Integer yearsOfExperience;

    @DecimalMin(
            value = "0.0",
            message = DoctorConstants.DOCTOR_INVALID_CONSULTATION_FEE
    )
    private BigDecimal consultationFee;

    private String profileImageUrl;

    private String biography;
}
