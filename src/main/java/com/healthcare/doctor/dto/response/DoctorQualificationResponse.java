
package com.healthcare.doctor.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoctorQualificationResponse {

    private Long id;

    private Long doctorId;

    private String doctorName;

    private String degree;

    private String university;

    private Integer yearOfCompletion;

    private String certificateUrl;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
