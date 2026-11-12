
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
public class DoctorUpdateRequest {

    @NotBlank(message = "First name is required")
    @Size(max = 100)
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(max = 100)
    private String lastName;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    @Pattern(regexp = "^[0-9]{10,15}$",
            message = "Invalid phone number")
    @NotBlank(message = "Phone number is required")
    private String phoneNumber;

    private LocalDate dateOfBirth;

    private String gender;

    private Long specializationId;

    @Min(value = 0)
    private Integer yearsOfExperience;

    @DecimalMin(value = "0.0")
    private BigDecimal consultationFee;

    private String profileImageUrl;

    private String biography;
}
