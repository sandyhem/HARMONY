package com.healthstation.hospital.service.Impl;

import com.healthstation.hospital.DTO.PrescriptionDTO;
import com.healthstation.hospital.entity.*;
import com.healthstation.hospital.repository.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class PrescriptionServiceImpl {

    private final PrescriptionRepository prescriptionRepository;
    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;

    public PrescriptionServiceImpl(PrescriptionRepository prescriptionRepository,
                                   AppointmentRepository appointmentRepository,
                                   DoctorRepository doctorRepository,
                                   PatientRepository patientRepository) {
        this.prescriptionRepository = prescriptionRepository;
        this.appointmentRepository = appointmentRepository;
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
    }

    // ✅ CREATE
    public PrescriptionDTO createPrescription(PrescriptionDTO dto) {
        Prescription prescription = new Prescription();

        Appointment appointment = appointmentRepository.findById(dto.getAppointmentId())
                .orElseThrow(() -> new RuntimeException("Appointment not found: " + dto.getAppointmentId()));

        Doctor doctor = doctorRepository.findById(dto.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Doctor not found: " + dto.getDoctorId()));

        Patient patient = patientRepository.findById(dto.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient not found: " + dto.getPatientId()));

        prescription.setAppointment(appointment);
        prescription.setDoctor(doctor);
        prescription.setPatient(patient);
        prescription.setDateIssued(dto.getDateIssued());
        prescription.setNotes(dto.getNotes());
        prescription.setCreatedAt(LocalDateTime.now());
        prescription.setUpdatedAt(LocalDateTime.now());
        Prescription saved = prescriptionRepository.save(prescription);
        return convertToDTO(saved);
    }

    // ✅ READ by ID
    public PrescriptionDTO getPrescriptionById(UUID id) {
        Prescription prescription = prescriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prescription not found: " + id));
        return convertToDTO(prescription);
    }

    // ✅ READ all
    public List<PrescriptionDTO> getAllPrescriptions() {
        return prescriptionRepository.findAll()
                .stream()
                .map(PrescriptionServiceImpl::convertToDTO)
                .toList();
    }

    // ✅ UPDATE
    public PrescriptionDTO updatePrescription(UUID id, PrescriptionDTO dto) {
        Prescription prescription = prescriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prescription not found: " + id));

        if (dto.getDateIssued() != null) prescription.setDateIssued(dto.getDateIssued());
        if (dto.getNotes() != null) prescription.setNotes(dto.getNotes());

        if (dto.getAppointmentId() != null) {
            Appointment appointment = appointmentRepository.findById(dto.getAppointmentId())
                    .orElseThrow(() -> new RuntimeException("Appointment not found: " + dto.getAppointmentId()));
            prescription.setAppointment(appointment);
        }

        if (dto.getDoctorId() != null) {
            Doctor doctor = doctorRepository.findById(dto.getDoctorId())
                    .orElseThrow(() -> new RuntimeException("Doctor not found: " + dto.getDoctorId()));
            prescription.setDoctor(doctor);
        }

        if (dto.getPatientId() != null) {
            Patient patient = patientRepository.findById(dto.getPatientId())
                    .orElseThrow(() -> new RuntimeException("Patient not found: " + dto.getPatientId()));
            prescription.setPatient(patient);
        }

        Prescription updated = prescriptionRepository.save(prescription);
        return convertToDTO(updated);
    }

    // ✅ DELETE
    public void deletePrescription(UUID id) {
        Prescription prescription = prescriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prescription not found: " + id));
        prescriptionRepository.delete(prescription);
    }

    // 🔹 Utility: Entity → DTO
    private static @NotNull PrescriptionDTO convertToDTO(Prescription p) {
        return PrescriptionDTO.builder()
                .prescriptionId(p.getPrescriptionId())
                .appointmentId(p.getAppointment().getAppointmentId())
                .doctorId(p.getDoctor().getDoctorId())
                .patientId(p.getPatient().getPatientId())
                .dateIssued(p.getDateIssued())
                .notes(p.getNotes())
                .createdAt(p.getCreatedAt())
                .updatedAt(p.getUpdatedAt())
                .build();
    }
}
