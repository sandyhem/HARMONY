package com.healthstation.hospital.service.Impl;

import com.healthstation.hospital.DTO.PatientDTO;
import com.healthstation.hospital.entity.Patient;
import com.healthstation.hospital.entity.User;
import com.healthstation.hospital.repository.PatientRepository;
import com.healthstation.hospital.repository.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PatientServiceImpl {

    private final PatientRepository patientRepository;
    private final UserRepository userRepository;

    public PatientServiceImpl(PatientRepository patientRepository, UserRepository userRepository) {
        this.patientRepository = patientRepository;
        this.userRepository = userRepository;
    }


    public PatientDTO createPatient(PatientDTO patientDTO) {
            Patient patient = new Patient();
            patient.setKnownAllergies(patientDTO.getKnownAllergies());
            patient.setExistingConditions(patientDTO.getExistingConditions());
            patient.setMedications(patientDTO.getMedications());

            patient.setInsuranceId(patientDTO.getInsuranceId());
            patient.setPreferredLanguages(patientDTO.getPreferredLanguages());
            patient.setPreferredGenderDoctor(patientDTO.getPreferredGenderDoctor());

            patient.setPreferredConsultationType(patientDTO.getPreferredConsultationType());
            patient.setBloodGroup(patientDTO.getBloodGroup());
            patient.setActive(true);
            // Fetch User entity from DB
            if(patientDTO.getUserId() != null){
                User user = userRepository.findById(patientDTO.getUserId())
                        .orElseThrow(() -> new RuntimeException("User not found"));
                patient.setUser(user);
            } else {
                throw new RuntimeException("UserId is required");
            }

            Patient savedpatient= patientRepository.save(patient);
        PatientDTO savedPatientDTO = getPatientDTO(savedpatient);
        return savedPatientDTO;
    }
    public Optional<PatientDTO> getPatientById(UUID id) {
        try {
            return patientRepository.findById(id)
                    .map(PatientServiceImpl::getPatientDTO);
        } catch (Exception e) {
            throw new RuntimeException("Error fetching patient by ID", e);
        }
    }
    public List<PatientDTO> getPatients(){
        try{
          return patientRepository.findAll().stream().map(PatientServiceImpl::getPatientDTO).toList();
        } catch (RuntimeException e) {
            throw new RuntimeException("Error Fetching the patients",e);
        }
    }


    public PatientDTO updatePatient(UUID id, PatientDTO updatedPatientDTO) {
        // 1️⃣ Find existing patient
        Patient existingPatient = patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found with ID: " + id));

        // 2️⃣ Update only provided fields (null-safe updates)
        if (updatedPatientDTO.getKnownAllergies() != null)
            existingPatient.setKnownAllergies(updatedPatientDTO.getKnownAllergies());

        if (updatedPatientDTO.getExistingConditions() != null)
            existingPatient.setExistingConditions(updatedPatientDTO.getExistingConditions());

        if (updatedPatientDTO.getMedications() != null)
            existingPatient.setMedications(updatedPatientDTO.getMedications());

        if (updatedPatientDTO.getInsuranceId() != null)
            existingPatient.setInsuranceId(updatedPatientDTO.getInsuranceId());

        if (updatedPatientDTO.getPreferredLanguages() != null)
            existingPatient.setPreferredLanguages(updatedPatientDTO.getPreferredLanguages());

        if (updatedPatientDTO.getPreferredGenderDoctor() != null)
            existingPatient.setPreferredGenderDoctor(updatedPatientDTO.getPreferredGenderDoctor());

        if (updatedPatientDTO.getPreferredConsultationType() != null)
            existingPatient.setPreferredConsultationType(updatedPatientDTO.getPreferredConsultationType());

        if (updatedPatientDTO.getBloodGroup() != null)
            existingPatient.setBloodGroup(updatedPatientDTO.getBloodGroup());

        // Optional: allow updating linked user (only if user exists)
        if (updatedPatientDTO.getUserId() != null) {
            User user = userRepository.findById(updatedPatientDTO.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found for given userId"));
            existingPatient.setUser(user);
        }

        // 3️⃣ Save and return updated patient
        Patient savedPatient = patientRepository.save(existingPatient);
        return getPatientDTO(savedPatient);
    }

    private static @NotNull PatientDTO getPatientDTO(Patient savedpatient) {
        PatientDTO savedPatientDTO = new PatientDTO();
        savedPatientDTO.setKnownAllergies(savedpatient.getKnownAllergies());
        savedPatientDTO.setPatientId(String.valueOf(savedpatient.getPatientId()));
        savedPatientDTO.setExistingConditions(savedpatient.getExistingConditions());
        savedPatientDTO.setMedications(savedpatient.getMedications());
        savedPatientDTO.setInsuranceId(savedpatient.getInsuranceId());
        savedPatientDTO.setPreferredLanguages(savedpatient.getPreferredLanguages());
        savedPatientDTO.setPreferredGenderDoctor(savedpatient.getPreferredGenderDoctor());
        savedPatientDTO.setPreferredConsultationType(savedpatient.getPreferredConsultationType());
        savedPatientDTO.setBloodGroup(savedpatient.getBloodGroup());
        savedPatientDTO.setUserId(savedpatient.getUser().getUserId());
        return savedPatientDTO;
    }
}
