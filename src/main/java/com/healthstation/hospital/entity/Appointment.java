package com.healthstation.hospital.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "appointments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID appointmentId;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "department_id", nullable = true)
    private Department department;

    private LocalDate appointmentDate;

    private LocalTime appointmentStartTime;
    private LocalTime appointmentEndTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AppointmentStatus status;

    @Enumerated(EnumType.STRING)
    private ConsultationType consultationType;

    @Lob
    private String remarks;

    private Integer roomId;
    private String virtualMeetingLink;

    @Enumerated(EnumType.STRING)
    private ConsultationDoctorType consultationDoctorType;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public enum AppointmentStatus {
        SCHEDULED, COMPLETED, CANCELLED, NO_SHOW
    }

    public enum ConsultationType {
        IN_PERSON, ONLINE
    }

    public enum ConsultationDoctorType {
        GENERAL, SPECIALIST
    }
}
