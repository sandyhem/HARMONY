package com.healthstation.hospital.service.Impl;

import com.healthstation.hospital.DTO.PrescriptionDetailDTO;
import com.healthstation.hospital.entity.Prescription;
import com.healthstation.hospital.entity.PrescriptionDetail;
import com.healthstation.hospital.repository.PrescriptionDetailRepository;
import com.healthstation.hospital.repository.PrescriptionRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PrescriptionDetailServiceImpl {

    private final PrescriptionDetailRepository detailRepository;
    private final PrescriptionRepository prescriptionRepository;

    public PrescriptionDetailServiceImpl(PrescriptionDetailRepository detailRepository,
                                         PrescriptionRepository prescriptionRepository) {
        this.detailRepository = detailRepository;
        this.prescriptionRepository = prescriptionRepository;
    }

    // ✅ CREATE
    public PrescriptionDetailDTO createPrescriptionDetail(PrescriptionDetailDTO dto) {
        Prescription prescription = prescriptionRepository.findById(dto.getPrescriptionId())
                .orElseThrow(() -> new RuntimeException("Prescription not found: " + dto.getPrescriptionId()));

        PrescriptionDetail detail = new PrescriptionDetail();
        detail.setPrescription(prescription);
        detail.setMedicineName(dto.getMedicineName());
        detail.setDosage(dto.getDosage());
        detail.setFrequency(dto.getFrequency());
        detail.setDuration(dto.getDuration());
        detail.setInstructions(dto.getInstructions());

        PrescriptionDetail saved = detailRepository.save(detail);
        return convertToDTO(saved);
    }

    // ✅ READ by ID
    public PrescriptionDetailDTO getPrescriptionDetailById(UUID id) {
        PrescriptionDetail detail = detailRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prescription detail not found: " + id));
        return convertToDTO(detail);
    }

    // ✅ READ all
    public List<PrescriptionDetailDTO> getAllPrescriptionDetails() {
        return detailRepository.findAll()
                .stream()
                .map(PrescriptionDetailServiceImpl::convertToDTO)
                .toList();
    }

    // ✅ UPDATE
    public PrescriptionDetailDTO updatePrescriptionDetail(UUID id, PrescriptionDetailDTO dto) {
        PrescriptionDetail detail = detailRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prescription detail not found: " + id));

        if (dto.getMedicineName() != null) detail.setMedicineName(dto.getMedicineName());
        if (dto.getDosage() != null) detail.setDosage(dto.getDosage());
        if (dto.getFrequency() != null) detail.setFrequency(dto.getFrequency());
        if (dto.getDuration() != null) detail.setDuration(dto.getDuration());
        if (dto.getInstructions() != null) detail.setInstructions(dto.getInstructions());

        if (dto.getPrescriptionId() != null) {
            Prescription prescription = prescriptionRepository.findById(dto.getPrescriptionId())
                    .orElseThrow(() -> new RuntimeException("Prescription not found: " + dto.getPrescriptionId()));
            detail.setPrescription(prescription);
        }

        PrescriptionDetail updated = detailRepository.save(detail);
        return convertToDTO(updated);
    }

    // ✅ DELETE
    public void deletePrescriptionDetail(UUID id) {
        PrescriptionDetail detail = detailRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prescription detail not found: " + id));
        detailRepository.delete(detail);
    }

    // 🔹 Utility: Entity → DTO
    private static @NotNull PrescriptionDetailDTO convertToDTO(PrescriptionDetail detail) {
        return PrescriptionDetailDTO.builder()
                .prescriptionDetailId(detail.getPrescriptionDetailId())
                .prescriptionId(detail.getPrescription().getPrescriptionId())
                .medicineName(detail.getMedicineName())
                .dosage(detail.getDosage())
                .frequency(detail.getFrequency())
                .duration(detail.getDuration())
                .instructions(detail.getInstructions())
                .build();
    }
}
