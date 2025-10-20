package com.healthstation.hospital.DTO;

import com.healthstation.hospital.entity.Patient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PatientDTO {
    private String patientId;
    private String knownAllergies;
    private String existingConditions;
    private String medications;
    private String insuranceId;
    private String preferredLanguages;
    private Patient.GenderPreference preferredGenderDoctor;
    private Patient.ConsultationType preferredConsultationType;
    private Patient.BloodGroup bloodGroup;
    private UUID userId;
}
