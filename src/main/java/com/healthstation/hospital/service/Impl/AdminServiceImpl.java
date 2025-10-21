package com.healthstation.hospital.service.Impl;

import com.healthstation.hospital.DTO.AdminDTO;
import com.healthstation.hospital.entity.Admin;
import com.healthstation.hospital.entity.Department;
import com.healthstation.hospital.entity.User;
import com.healthstation.hospital.repository.AdminRepository;
import com.healthstation.hospital.repository.DepartmentRepository;
import com.healthstation.hospital.repository.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AdminServiceImpl {

    private final AdminRepository adminRepository;
    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;

    public AdminServiceImpl(AdminRepository adminRepository,
                            UserRepository userRepository,
                            DepartmentRepository departmentRepository) {
        this.adminRepository = adminRepository;
        this.userRepository = userRepository;
        this.departmentRepository = departmentRepository;
    }

    // ✅ CREATE
    public AdminDTO createAdmin(AdminDTO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found: " + dto.getUserId()));

        Department department = null;
        if (dto.getDepartmentId() != null) {
            department = departmentRepository.findById(dto.getDepartmentId())
                    .orElseThrow(() -> new RuntimeException("Department not found: " + dto.getDepartmentId()));
        }

        Admin admin = Admin.builder()
                .user(user)
                .department(department)
                .roleLevel(dto.getRoleLevel())
                .build();

        Admin saved = adminRepository.save(admin);
        return convertToDTO(saved);
    }

    // ✅ READ by ID
    public AdminDTO getAdminById(UUID id) {
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Admin not found: " + id));
        return convertToDTO(admin);
    }

    // ✅ READ all
    public List<AdminDTO> getAllAdmins() {
        return adminRepository.findAll()
                .stream()
                .map(AdminServiceImpl::convertToDTO)
                .toList();
    }

    // ✅ UPDATE
    public AdminDTO updateAdmin(UUID id, AdminDTO dto) {
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Admin not found: " + id));

        if (dto.getRoleLevel() != null) admin.setRoleLevel(dto.getRoleLevel());

        if (dto.getUserId() != null) {
            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found: " + dto.getUserId()));
            admin.setUser(user);
        }

        if (dto.getDepartmentId() != null) {
            Department department = departmentRepository.findById(dto.getDepartmentId())
                    .orElseThrow(() -> new RuntimeException("Department not found: " + dto.getDepartmentId()));
            admin.setDepartment(department);
        }

        Admin updated = adminRepository.save(admin);
        return convertToDTO(updated);
    }

    // ✅ DELETE
    public void deleteAdmin(UUID id) {
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Admin not found: " + id));
        adminRepository.delete(admin);
    }

    // ✅ Assign Department (for dynamic updates)
    public AdminDTO assignDepartment(UUID adminId, UUID departmentId) {
        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() -> new RuntimeException("Admin not found: " + adminId));

        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new RuntimeException("Department not found: " + departmentId));

        admin.setDepartment(department);
        Admin updated = adminRepository.save(admin);
        return convertToDTO(updated);
    }

    // 🔹 Utility: Entity → DTO
    private static @NotNull AdminDTO convertToDTO(Admin a) {
        return AdminDTO.builder()
                .adminId(a.getAdminId())
                .userId(a.getUser().getUserId())
                .departmentId(a.getDepartment() != null ? a.getDepartment().getDepartmentId() : null)
                .roleLevel(a.getRoleLevel())
                .build();
    }
}
