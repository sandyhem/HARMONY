package com.healthstation.hospital.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DepartmentDTO {
    private String name;
    private String description;
    private String floor;
    private String phone;
    private String email;
    private UUID headDoctorId;
}

