
package com.healthcare.doctor.dto.request;

import com.healthcare.doctor.constants.DoctorConstants;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SpecializationRequest {

    @NotBlank(
            message = DoctorConstants.SPECIALIZATION_NAME_REQUIRED
    )
    @Size(
            max = 100,
            message = DoctorConstants.SPECIALIZATION_NAME_MAX_LENGTH
    )
    private String name;

    @Size(
            max = 500,
            message = DoctorConstants.SPECIALIZATION_DESCRIPTION_MAX_LENGTH
    )
    private String description;
}
