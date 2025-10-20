package com.healthstation.hospital.repository;

import com.healthstation.hospital.entity.Department;
import com.healthstation.hospital.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, UUID> {
}
