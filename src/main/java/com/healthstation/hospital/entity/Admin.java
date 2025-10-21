package com.healthstation.hospital.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Entity
@Table(name = "admins")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID adminId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleLevel roleLevel;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;   // FK → User.user_id

    @ManyToOne
    @JoinColumn(name = "department_id", nullable = true)
    private Department department;  // Optional FK

    public enum RoleLevel {
        SUPER_ADMIN,
        MANAGER,
        STAFF
    }
}
