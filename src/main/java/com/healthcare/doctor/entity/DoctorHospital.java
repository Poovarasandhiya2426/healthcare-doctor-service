
package com.healthcare.doctor.entity;

import jakarta.persistence.*;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "doctor_hospitals",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_doctor_hospital",
                        columnNames = {"doctor_id", "hospital_id"}
                )
        },
        indexes = {
                @Index(
                        name = "idx_doctor_hospitals_doctor_id",
                        columnList = "doctor_id"
                ),
                @Index(
                        name = "idx_doctor_hospitals_hospital_id",
                        columnList = "hospital_id"
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoctorHospital {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Doctor relationship
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "doctor_id",
            nullable = false,
            foreignKey = @ForeignKey(
                    name = "fk_doctor_hospital_doctor"
            )
    )
    private Doctor doctor;

    // External Hospital Service ID
    @Column(name = "hospital_id", nullable = false)
    private Long hospitalId;

    @Column(length = 150)
    private String department;

    @Column(name = "joining_date")
    private LocalDate joiningDate;

    @Column(
            name = "consultation_fee",
            precision = 10,
            scale = 2
    )
    private BigDecimal consultationFee;

    @Builder.Default
    @Column(name = "primary_hospital", nullable = false)
    private Boolean primaryHospital = false;

    @Builder.Default
    @Column(nullable = false)
    private Boolean active = true;

    @Column(
            name = "created_at",
            insertable = false,
            updatable = false
    )
    private LocalDateTime createdAt;

    @Column(
            name = "updated_at",
            insertable = false,
            updatable = false
    )
    private LocalDateTime updatedAt;
}
