package com.healthstation.hospital.service.Impl;


import com.healthstation.hospital.DTO.DepartmentDTO;
import com.healthstation.hospital.entity.Department;
import com.healthstation.hospital.repository.DepartmentRepository;
import com.healthstation.hospital.repository.DoctorRepository;
import org.springframework.stereotype.Service;

@Service
public class DepartmentServiceImpl {
    private final DepartmentRepository departmentRepository;
    private final DoctorRepository doctorRepository;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository, DoctorRepository doctorRepository) {
        this.departmentRepository = departmentRepository;
        this.doctorRepository = doctorRepository;
    }

//    public DepartmentDTO createDepartment(DepartmentDTO departmentDTO) {
//        Department dept = new Department();
//        dept.setName(departmentDTO.getName());
//        dept.setDescription(departmentDTO.getDescription());
//        dept.setFloor(departmentDTO.getFloor());
//
//        return null;
//    }
}
