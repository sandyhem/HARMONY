package com.healthstation.hospital.service.Impl;

import com.healthstation.hospital.DTO.UserDTO;
import com.healthstation.hospital.entity.User;
import com.healthstation.hospital.exceptions.ResourceNotFoundException;
import com.healthstation.hospital.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl{
    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO createUser(UserDTO userDTO){
        userDTO.setPasswordHash(passwordEncoder.encode(userDTO.getPasswordHash()));
        User user = new User();
        BeanUtils.copyProperties(userDTO,user);
        User savedUser = userRepository.save(user);

        UserDTO savedUserDTO = new UserDTO();
        BeanUtils.copyProperties(savedUser, savedUserDTO);
        return savedUserDTO;
    }



    public UserDTO getUserById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow();
        return toDTO(user);
    }


    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }


    public void deleteUser(UUID id) {
        if (!userRepository.existsById(id))
            throw new ResourceNotFoundException("User not found");
        userRepository.deleteById(id);
    }

    private UserDTO toDTO(User user) {
        UserDTO dto = new UserDTO();
        BeanUtils.copyProperties(user, dto);
        return dto;
    }

}
