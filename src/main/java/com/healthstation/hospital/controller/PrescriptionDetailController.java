package com.healthstation.hospital.controller;

import com.healthstation.hospital.DTO.PrescriptionDetailDTO;
import com.healthstation.hospital.service.Impl.PrescriptionDetailServiceImpl;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/prescription-details")
public class PrescriptionDetailController {

    private final PrescriptionDetailServiceImpl detailService;

    public PrescriptionDetailController(PrescriptionDetailServiceImpl detailService) {
        this.detailService = detailService;
    }

    @PostMapping
    public ResponseEntity<?> createPrescriptionDetail(@RequestBody PrescriptionDetailDTO dto) {
        try {
            PrescriptionDetailDTO created = detailService.createPrescriptionDetail(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPrescriptionDetailById(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(detailService.getPrescriptionDetailById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<PrescriptionDetailDTO>> getAllPrescriptionDetails() {
        return ResponseEntity.ok(detailService.getAllPrescriptionDetails());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePrescriptionDetail(@PathVariable UUID id, @RequestBody PrescriptionDetailDTO dto) {
        try {
            return ResponseEntity.ok(detailService.updatePrescriptionDetail(id, dto));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePrescriptionDetail(@PathVariable UUID id) {
        try {
            detailService.deletePrescriptionDetail(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
