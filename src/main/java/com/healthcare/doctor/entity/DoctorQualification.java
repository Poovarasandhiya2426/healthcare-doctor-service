
package com.healthcare.doctor.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "doctor_qualifications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoctorQualification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "doctor_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_doctor_qualification_doctor")
    )
    private Doctor doctor;

    @Column(nullable = false, length = 150)
    private String degree;

    @Column(nullable = false, length = 200)
    private String university;

    @Column(name = "year_of_completion", nullable = false)
    private Integer yearOfCompletion;

    @Column(name = "certificate_url", length = 500)
    private String certificateUrl;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", insertable = false, updatable = false)
    private LocalDateTime updatedAt;
}
