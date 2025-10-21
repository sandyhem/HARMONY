package com.healthstation.hospital.service.Impl;

import com.healthstation.hospital.DTO.DoctorDTO;
import com.healthstation.hospital.entity.Department;
import com.healthstation.hospital.entity.Doctor;
import com.healthstation.hospital.entity.User;
import com.healthstation.hospital.repository.DepartmentRepository;
import com.healthstation.hospital.repository.DoctorRepository;
import com.healthstation.hospital.repository.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class DoctorServiceImpl {

    private final DoctorRepository doctorRepository;
    private final DepartmentRepository departmentRepository;
    private final UserRepository userRepository;

    public DoctorServiceImpl(DoctorRepository doctorRepository, DepartmentRepository departmentRepository, UserRepository userRepository) {
        this.doctorRepository = doctorRepository;
        this.departmentRepository = departmentRepository;
        this.userRepository = userRepository;
    }

    public DoctorDTO createDoctor(DoctorDTO dto) {
        Doctor doctor = new Doctor();

        doctor.setExperience(dto.getExperience());
        doctor.setQualification(dto.getQualification());
        doctor.setSpecialization(dto.getSpecialization());
        doctor.setLanguagesSpoken(dto.getLanguagesSpoken());
        doctor.setConsultationType(dto.getConsultationType());
        doctor.setMaxPatientsPerDay(dto.getMaxPatientsPerDay());
        doctor.setStatus(dto.getStatus());
        doctor.setLicenseNumber(dto.getLicenseNumber());
        doctor.setLicenseExpiry(dto.getLicenseExpiry());
        doctor.setRating(dto.getRating());
        doctor.setDateOfJoining(dto.getDateOfJoining());
        doctor.setAvailableFrom(dto.getAvailableFrom());
        doctor.setAvailableTo(dto.getAvailableTo());

        // --- Set relationships ---
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + dto.getUserId()));
        Department department = departmentRepository.findById(dto.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Department not found with ID: " + dto.getDepartmentId()));

        doctor.setUser(user);
        doctor.setDepartment(department);

        Doctor saved = doctorRepository.save(doctor);
        return convertToDTO(saved);
    }

    public DoctorDTO getDoctorById(UUID id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found with ID: " + id));
        return convertToDTO(doctor);
    }

    public List<DoctorDTO> getAllDoctors() {
        return doctorRepository.findAll()
                .stream()
                .map(DoctorServiceImpl::convertToDTO)
                .toList();
    }

    public DoctorDTO updateDoctor(UUID id, DoctorDTO dto) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found with ID: " + id));

        // --- Update fields if present ---
        if (dto.getExperience() != 0) doctor.setExperience(dto.getExperience());
        if (dto.getQualification() != null) doctor.setQualification(dto.getQualification());
        if (dto.getSpecialization() != null) doctor.setSpecialization(dto.getSpecialization());
        if (dto.getLanguagesSpoken() != null) doctor.setLanguagesSpoken(dto.getLanguagesSpoken());
        if (dto.getConsultationType() != null) doctor.setConsultationType(dto.getConsultationType());
        if (dto.getMaxPatientsPerDay() != null) doctor.setMaxPatientsPerDay(dto.getMaxPatientsPerDay());
        if (dto.getStatus() != null) doctor.setStatus(dto.getStatus());
        if (dto.getLicenseNumber() != null) doctor.setLicenseNumber(dto.getLicenseNumber());
        if (dto.getLicenseExpiry() != null) doctor.setLicenseExpiry(dto.getLicenseExpiry());
        if (dto.getRating() != null) doctor.setRating(dto.getRating());
        if (dto.getDateOfJoining() != null) doctor.setDateOfJoining(dto.getDateOfJoining());
        if (dto.getAvailableFrom() != null) doctor.setAvailableFrom(dto.getAvailableFrom());
        if (dto.getAvailableTo() != null) doctor.setAvailableTo(dto.getAvailableTo());

        // --- Update relationships if changed ---
        if (dto.getUserId() != null) {
            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found with ID: " + dto.getUserId()));
            doctor.setUser(user);
        }

        if (dto.getDepartmentId() != null) {
            Department department = departmentRepository.findById(dto.getDepartmentId())
                    .orElseThrow(() -> new RuntimeException("Department not found with ID: " + dto.getDepartmentId()));
            doctor.setDepartment(department);
        }

        Doctor updated = doctorRepository.save(doctor);
        return convertToDTO(updated);
    }

    public void deleteDoctor(UUID id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found with ID: " + id));
        doctorRepository.delete(doctor);
    }

    private static @NotNull DoctorDTO convertToDTO(Doctor doctor) {
        DoctorDTO dto = new DoctorDTO();
        dto.setDoctorId(doctor.getDoctorId());
        dto.setExperience(doctor.getExperience());
        dto.setQualification(doctor.getQualification());
        dto.setSpecialization(doctor.getSpecialization());
        dto.setLanguagesSpoken(doctor.getLanguagesSpoken());
        dto.setConsultationType(doctor.getConsultationType());
        dto.setMaxPatientsPerDay(doctor.getMaxPatientsPerDay());
        dto.setStatus(doctor.getStatus());
        dto.setLicenseNumber(doctor.getLicenseNumber());
        dto.setLicenseExpiry(doctor.getLicenseExpiry());
        dto.setRating(doctor.getRating());
        dto.setDateOfJoining(doctor.getDateOfJoining());
        dto.setAvailableFrom(doctor.getAvailableFrom());
        dto.setAvailableTo(doctor.getAvailableTo());
        dto.setUserId(doctor.getUser().getUserId());
        dto.setDepartmentId(doctor.getDepartment().getDepartmentId());
        return dto;
    }
}
