package com.healthstation.hospital.DTO;

import com.healthstation.hospital.entity.Admin;
import lombok.*;

import java.util.UUID;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminDTO {
    private UUID adminId;
    private UUID userId;
    private UUID departmentId;
    private Admin.RoleLevel roleLevel;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
