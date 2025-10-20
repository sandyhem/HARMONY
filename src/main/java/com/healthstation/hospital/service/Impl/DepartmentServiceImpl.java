package com.healthstation.hospital.service.Impl;


import com.healthstation.hospital.DTO.DepartmentDTO;
import com.healthstation.hospital.entity.Department;
import com.healthstation.hospital.entity.Doctor;
import com.healthstation.hospital.repository.DepartmentRepository;
import com.healthstation.hospital.repository.DoctorRepository;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class DepartmentServiceImpl {
    private final DepartmentRepository departmentRepository;
    private final DoctorRepository doctorRepository;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository, DoctorRepository doctorRepository) {
        this.departmentRepository = departmentRepository;
        this.doctorRepository = doctorRepository;
    }

    public DepartmentDTO createDepartment(DepartmentDTO departmentDTO) {
        Department dept = new Department();

        dept.setName(departmentDTO.getName());
        dept.setDescription(departmentDTO.getDescription());
        dept.setFloor(departmentDTO.getFloor());

        dept.setPhone(departmentDTO.getPhone());
        dept.setEmail(departmentDTO.getEmail());
        dept.setCreatedAt(LocalDateTime.now());

        dept.setUpdatedAt(LocalDateTime.now());
        dept.setActive(true);
        try {
            if (departmentDTO.getHeadDoctorId() != null) {
                Doctor headDoctor = doctorRepository.findById(departmentDTO.getHeadDoctorId())
                        .orElseThrow(() -> new RuntimeException("Doctor not found"));
                dept.setHeadDoctor(headDoctor);
            }
        } catch (RuntimeException e) {
            throw new RuntimeException("Head Doctor not found!", e);
        }
        Department savedDept = departmentRepository.save(dept);
        DepartmentDTO savedDeptDTO = getDepartmentDTO(savedDept);
        return savedDeptDTO;
    }

    public DepartmentDTO getDepartmentById(UUID id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found with ID: " + id));
        return getDepartmentDTO(department);
    }

    public List<DepartmentDTO> getAllDepartments() {
        return departmentRepository.findAll()
                .stream()
                .map(DepartmentServiceImpl::getDepartmentDTO)
                .toList();
    }

    public DepartmentDTO updateDepartment(UUID id, DepartmentDTO dto) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found with ID: " + id));

        if (dto.getName() != null) department.setName(dto.getName());
        if (dto.getDescription() != null) department.setDescription(dto.getDescription());
        if (dto.getFloor() != null) department.setFloor(dto.getFloor());
        if (dto.getPhone() != null) department.setPhone(dto.getPhone());
        if (dto.getEmail() != null) department.setEmail(dto.getEmail());

        if (dto.getHeadDoctorId() != null) {
            Doctor doctor = doctorRepository.findById(dto.getHeadDoctorId())
                    .orElseThrow(() -> new RuntimeException("Doctor not found with ID: " + dto.getHeadDoctorId()));
            department.setHeadDoctor(doctor);
        }

        Department updated = departmentRepository.save(department);
        return getDepartmentDTO(updated);
    }

    public DepartmentDTO updateHeadDoctor(UUID departmentId, UUID doctorId) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new RuntimeException("Department not found with ID: " + departmentId));

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found with ID: " + doctorId));

        department.setHeadDoctor(doctor);
        Department updated = departmentRepository.save(department);
        DepartmentDTO deptdto = getDepartmentDTO(updated);
        return deptdto;
    }

//    public void deleteDepartment(UUID id) {
//        Department department = departmentRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Department not found with ID: " + id));
//        departmentRepository.delete(department);
//    }

    private static @NotNull DepartmentDTO getDepartmentDTO(Department savedDept) {
        DepartmentDTO savedDeptDTO = new DepartmentDTO();
        savedDeptDTO.setDepartmentId(String.valueOf(savedDept.getDepartmentId()));
        savedDeptDTO.setName(savedDept.getName());
        savedDeptDTO.setDescription(savedDept.getDescription());
        savedDeptDTO.setFloor(savedDept.getFloor());
        savedDeptDTO.setPhone(savedDept.getPhone());
        savedDeptDTO.setEmail(savedDept.getEmail());
        if (savedDept.getHeadDoctor() != null) {
            savedDeptDTO.setHeadDoctorId(savedDept.getHeadDoctor().getDoctorId());
        }
        return savedDeptDTO;
    }


}



