
package com.healthcare.doctor.dto.request;

import com.healthcare.doctor.constants.DoctorConstants;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoctorQualificationRequest {

    @NotNull(message = DoctorConstants.DOCTOR_ID_REQUIRED)
    @Positive(message = DoctorConstants.INVALID_DOCTOR_ID)
    private Long doctorId;

    @NotBlank(message = DoctorConstants.DEGREE_REQUIRED)
    @Size(
            max = 150,
            message = DoctorConstants.DEGREE_MAX_LENGTH
    )
    private String degree;

    @NotBlank(message = DoctorConstants.UNIVERSITY_REQUIRED)
    @Size(
            max = 200,
            message = DoctorConstants.UNIVERSITY_MAX_LENGTH
    )
    private String university;

    @NotNull(message = DoctorConstants.COMPLETION_YEAR_REQUIRED)
    @Min(
            value = 1900,
            message = DoctorConstants.INVALID_COMPLETION_YEAR
    )
    private Integer yearOfCompletion;

    @Size(
            max = 500,
            message = DoctorConstants.CERTIFICATE_URL_MAX_LENGTH
    )
    private String certificateUrl;
}
