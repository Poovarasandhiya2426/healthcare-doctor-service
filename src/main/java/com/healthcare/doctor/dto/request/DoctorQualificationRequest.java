
package com.healthcare.doctor.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.Year;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoctorQualificationRequest {

    @NotNull(message = "Doctor ID is required")
    private Long doctorId;

    @NotBlank(message = "Degree is required")
    @Size(max = 150, message = "Degree must not exceed 150 characters")
    private String degree;

    @NotBlank(message = "University is required")
    @Size(max = 200, message = "University must not exceed 200 characters")
    private String university;

    @NotNull(message = "Year of completion is required")
    @Min(value = 1900, message = "Invalid completion year")
    @Max(value = 2100, message = "Invalid completion year")
    private Integer yearOfCompletion;

    @Size(max = 500, message = "Certificate URL must not exceed 500 characters")
    private String certificateUrl;
}
