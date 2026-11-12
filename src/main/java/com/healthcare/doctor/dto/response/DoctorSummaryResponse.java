
package com.healthcare.doctor.dto.response;

import com.healthcare.doctor.enums.DoctorStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    private String specializationName;

    private Integer yearsOfExperience;

    private BigDecimal consultationFee;

    private DoctorStatus status;
}
