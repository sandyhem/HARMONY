package com.healthstation.hospital.DTO;

import com.healthstation.hospital.entity.Appointment;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppointmentDTO {
    private UUID appointmentId;
    private UUID doctorId;
    private UUID patientId;
    private UUID departmentId;
    private LocalDate appointmentDate;
    private LocalTime appointmentStartTime;
    private LocalTime appointmentEndTime;
    private Appointment.AppointmentStatus status;
    private Appointment.ConsultationType consultationType;
    private String remarks;
    private Integer roomId;
    private String virtualMeetingLink;
    private Appointment.ConsultationDoctorType consultationDoctorType;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
