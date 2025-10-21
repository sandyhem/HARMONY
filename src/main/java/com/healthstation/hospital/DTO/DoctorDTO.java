package com.healthstation.hospital.DTO;

import com.healthstation.hospital.entity.Doctor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoctorDTO {
    private UUID doctorId;
    private int experience;
    private String qualification;
    private String specialization;
    private String languagesSpoken;
    private Doctor.ConsultationType consultationType;
    private Integer maxPatientsPerDay;
    private Doctor.DoctorStatus status;
    private String licenseNumber;
    private LocalDate licenseExpiry;
    private BigDecimal rating;
    private LocalDate dateOfJoining;
    private LocalTime availableFrom;
    private LocalTime availableTo;
    private UUID userId;
    private UUID departmentId;
}
