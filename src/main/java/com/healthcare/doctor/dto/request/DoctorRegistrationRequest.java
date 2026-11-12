
package com.healthcare.doctor.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoctorRegistrationRequest {

    @NotNull(message = "Identity user ID is required")
    private Long identityUserId;

    @NotBlank(message = "First name is required")
    @Size(max = 100)
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(max = 100)
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[0-9]{10,15}$",
            message = "Phone number must contain 10 to 15 digits")
    private String phoneNumber;

    private LocalDate dateOfBirth;

    private String gender;

    private Long specializationId;

    @NotBlank(message = "License number is required")
    private String licenseNumber;

    @Min(value = 0, message = "Experience cannot be negative")
    private Integer yearsOfExperience;

    @DecimalMin(value = "0.0",
            message = "Consultation fee cannot be negative")
    private BigDecimal consultationFee;

    private String profileImageUrl;

    private String biography;
}
