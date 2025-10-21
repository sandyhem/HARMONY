package com.healthstation.hospital.service.Impl;

import com.healthstation.hospital.DTO.AppointmentDTO;
import com.healthstation.hospital.entity.Appointment;
import com.healthstation.hospital.entity.Department;
import com.healthstation.hospital.entity.Doctor;
import com.healthstation.hospital.entity.Patient;
import com.healthstation.hospital.repository.AppointmentRepository;
import com.healthstation.hospital.repository.DepartmentRepository;
import com.healthstation.hospital.repository.DoctorRepository;
import com.healthstation.hospital.repository.PatientRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AppointmentServiceImpl {

    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final DepartmentRepository departmentRepository;

    public AppointmentServiceImpl(AppointmentRepository appointmentRepository,
                                  DoctorRepository doctorRepository,
                                  PatientRepository patientRepository,
                                  DepartmentRepository departmentRepository) {
        this.appointmentRepository = appointmentRepository;
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
        this.departmentRepository = departmentRepository;
    }

    // ✅ CREATE Appointment
    public AppointmentDTO createAppointment(AppointmentDTO dto) {
        Appointment appointment = new Appointment();

        mapDtoToEntity(dto, appointment);

        Doctor doctor = doctorRepository.findById(dto.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Doctor not found with ID: " + dto.getDoctorId()));
        Patient patient = patientRepository.findById(dto.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient not found with ID: " + dto.getPatientId()));

        appointment.setDoctor(doctor);
        appointment.setPatient(patient);

        if (dto.getDepartmentId() != null) {
            Department department = departmentRepository.findById(dto.getDepartmentId())
                    .orElseThrow(() -> new RuntimeException("Department not found with ID: " + dto.getDepartmentId()));
            appointment.setDepartment(department);
        }

        Appointment saved = appointmentRepository.save(appointment);
        return convertToDTO(saved);
    }

    // ✅ READ - Get Appointment by ID
    public AppointmentDTO getAppointmentById(UUID id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found with ID: " + id));
        return convertToDTO(appointment);
    }

    // ✅ READ - Get All Appointments
    public List<AppointmentDTO> getAllAppointments() {
        return appointmentRepository.findAll()
                .stream()
                .map(AppointmentServiceImpl::convertToDTO)
                .toList();
    }

    // ✅ UPDATE Appointment
    public AppointmentDTO updateAppointment(UUID id, AppointmentDTO dto) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found with ID: " + id));

        mapDtoToEntity(dto, appointment);

        if (dto.getDoctorId() != null) {
            Doctor doctor = doctorRepository.findById(dto.getDoctorId())
                    .orElseThrow(() -> new RuntimeException("Doctor not found with ID: " + dto.getDoctorId()));
            appointment.setDoctor(doctor);
        }

        if (dto.getPatientId() != null) {
            Patient patient = patientRepository.findById(dto.getPatientId())
                    .orElseThrow(() -> new RuntimeException("Patient not found with ID: " + dto.getPatientId()));
            appointment.setPatient(patient);
        }

        if (dto.getDepartmentId() != null) {
            Department department = departmentRepository.findById(dto.getDepartmentId())
                    .orElseThrow(() -> new RuntimeException("Department not found with ID: " + dto.getDepartmentId()));
            appointment.setDepartment(department);
        }

        Appointment updated = appointmentRepository.save(appointment);
        return convertToDTO(updated);
    }

    // ✅ DELETE Appointment
    public void deleteAppointment(UUID id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found with ID: " + id));
        appointmentRepository.delete(appointment);
    }

    // 🔹 Utility: Map DTO → Entity
    private void mapDtoToEntity(AppointmentDTO dto, Appointment appointment) {
        if (dto.getAppointmentDate() != null) appointment.setAppointmentDate(dto.getAppointmentDate());
        if (dto.getAppointmentStartTime() != null) appointment.setAppointmentStartTime(dto.getAppointmentStartTime());
        if (dto.getAppointmentEndTime() != null) appointment.setAppointmentEndTime(dto.getAppointmentEndTime());
        if (dto.getStatus() != null) appointment.setStatus(dto.getStatus());
        if (dto.getConsultationType() != null) appointment.setConsultationType(dto.getConsultationType());
        if (dto.getRemarks() != null) appointment.setRemarks(dto.getRemarks());
        if (dto.getRoomId() != null) appointment.setRoomId(dto.getRoomId());
        if (dto.getVirtualMeetingLink() != null) appointment.setVirtualMeetingLink(dto.getVirtualMeetingLink());
        if (dto.getConsultationDoctorType() != null) appointment.setConsultationDoctorType(dto.getConsultationDoctorType());
    }

    // 🔹 Utility: Entity → DTO
    private static @NotNull AppointmentDTO convertToDTO(Appointment a) {
        AppointmentDTO dto = new AppointmentDTO();
        dto.setAppointmentId(a.getAppointmentId());
        dto.setAppointmentDate(a.getAppointmentDate());
        dto.setAppointmentStartTime(a.getAppointmentStartTime());
        dto.setAppointmentEndTime(a.getAppointmentEndTime());
        dto.setStatus(a.getStatus());
        dto.setConsultationType(a.getConsultationType());
        dto.setRemarks(a.getRemarks());
        dto.setRoomId(a.getRoomId());
        dto.setVirtualMeetingLink(a.getVirtualMeetingLink());
        dto.setConsultationDoctorType(a.getConsultationDoctorType());
        dto.setCreatedAt(a.getCreatedAt());
        dto.setUpdatedAt(a.getUpdatedAt());

        if (a.getDoctor() != null) dto.setDoctorId(a.getDoctor().getDoctorId());
        if (a.getPatient() != null) dto.setPatientId(a.getPatient().getPatientId());
        if (a.getDepartment() != null) dto.setDepartmentId(a.getDepartment().getDepartmentId());

        return dto;
    }
}
