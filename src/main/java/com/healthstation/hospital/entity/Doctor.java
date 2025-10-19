package com.healthstation.hospital.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table (name = "doctors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID doctorId;

    private int experience;
    private String qualification;
    @Column(nullable = false)
    private String specialization;

    private String languagesSpoken;

    @Enumerated(EnumType.STRING)
    private ConsultationType consultationType;

    private Integer maxPatientsPerDay;

    @Enumerated(EnumType.STRING)
    private DoctorStatus status;

    private String licenseNumber;
    private LocalDate licenseExpiry;
    private BigDecimal rating;
    private LocalDate dateOfJoining;
    private LocalTime availableFrom;
    private LocalTime availableTo;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    public enum ConsultationType {
        IN_PERSON,
        ONLINE
    }
    public enum DoctorStatus {
        ACTIVE,
        INACTIVE,
        RETIRED
    }

}
