package com.healthstation.hospital.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "departments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID departmentId;

    private String name;
    private String description;

    private String floor;
    private String phone;
    private String email;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

//    true if dept is operational.
    private boolean isActive;

    @ManyToOne
    @JoinColumn(name = "head_doctor_id", nullable = true)
    private Doctor headDoctor;
}
