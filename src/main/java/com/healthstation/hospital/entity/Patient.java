package com.healthstation.hospital.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "patients")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID patientId;

    @Lob
    private String knownAllergies;

    @Lob
    private String existingConditions;

    @Lob
    private String medications;

    private String insuranceId;
    private String preferredLanguages;

    @Enumerated(EnumType.STRING)
    private GenderPreference preferredGenderDoctor;

    @Enumerated(EnumType.STRING)
    private ConsultationType preferredConsultationType;

    private boolean isActive;

    @Enumerated(EnumType.STRING)
    private BloodGroup bloodGroup;

    public enum GenderPreference{
        MALE, FEMALE, ANY
    }

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public enum ConsultationType{
        IN_PERSON,ONLINE,BOTH
    }

    public enum BloodGroup{
        A_POS,A_NEG,AB_POS,O_POS,
        B_POS,B_NEG,AB_NEG,O_NEG,OTHERS
    }

}
