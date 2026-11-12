
package com.healthcare.doctor.dto.response;

import com.healthcare.doctor.enums.DoctorStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoctorResponse {

    private Long id;

    private Long identityUserId;

    private String firstName;

    private String lastName;

    private String fullName;

    private String email;

    private String phoneNumber;

    private LocalDate dateOfBirth;

    private String gender;

    private Long specializationId;

    private String specializationName;

    private String licenseNumber;

    private Integer yearsOfExperience;

    private BigDecimal consultationFee;

    private String profileImageUrl;

    private String biography;

    private DoctorStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
