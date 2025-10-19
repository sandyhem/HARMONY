package com.healthstation.hospital.DTO;

import com.healthstation.hospital.entity.User;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserOut {
    private UUID userId;
    private String email;
    private String firstName;
    private String lastName;
    private User.Role role;
    private User.Gender gender;
    private LocalDate dob;
    private String phone;
    private String address;
    private String city;
    private String state;
    private String zipCode;
    private String emergencyContactName;
    private String emergencyContactPhone;
    private LocalDateTime createdAt;

}
