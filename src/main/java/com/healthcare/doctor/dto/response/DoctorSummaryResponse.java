
package com.healthcare.doctor.dto.response;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoctorSummaryResponse {

    private Long id;

    private String fullName;

    private String email;

    private String phoneNumber;

    private Long specializationId;

    private Integer yearsOfExperience;

    private BigDecimal consultationFee;

    private String status;
}
