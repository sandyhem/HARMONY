package com.healthstation.hospital.DTO;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrescriptionDTO {
    private UUID prescriptionId;
    private UUID appointmentId;
    private UUID doctorId;
    private UUID patientId;
    private LocalDate dateIssued;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
