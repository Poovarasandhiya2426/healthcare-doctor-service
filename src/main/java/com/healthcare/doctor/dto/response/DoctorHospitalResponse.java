
package com.healthcare.doctor.dto.response;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoctorHospitalResponse {

    private Long id;

    private Long doctorId;

    private String doctorName;

    private Long hospitalId;

    private String department;

    private LocalDate joiningDate;

    private BigDecimal consultationFee;

    private Boolean primaryHospital;

    private Boolean active;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
