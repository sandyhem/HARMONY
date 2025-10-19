package com.healthstation.hospital.service;

import com.healthstation.hospital.DTO.UserDTO;
import com.healthstation.hospital.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


public interface UserService {
    UserDTO createUser(UserDTO userDTO);

    UserDTO getUserById(UUID id);

    List<UserDTO> getAllUsers();

    void deleteUser(UUID id);
}
