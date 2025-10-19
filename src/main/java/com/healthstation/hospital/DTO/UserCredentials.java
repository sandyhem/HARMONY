package com.healthstation.hospital.DTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCredentials {
    private String firstName;
    private String passwordHash;
}
