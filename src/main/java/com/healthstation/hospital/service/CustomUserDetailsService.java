package com.healthstation.hospital.service;

import com.healthstation.hospital.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;


@Component
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    public UserRepository getUserRepository() {
        return userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.healthstation.hospital.entity.User user = userRepository.findByFirstName(username).orElseThrow(
        );
        // assuming your entity has a field like `private String role;`
        String roleName = String.valueOf(user.getRole()); // e.g., "DOCTOR", "ADMIN", "NURSE"

        // Spring Security expects roles prefixed with "ROLE_"
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + roleName.toUpperCase());

        return new org.springframework.security.core.userdetails.User(
                user.getFirstName(),          // username
                user.getPasswordHash(),       // password
                Collections.singleton(authority) // roles/authorities
        );

//        return new User(user.getFirstName(), user.getPasswordHash(), Collections.singleton(new SimpleGrantedAuthority("DOCTOR")));
    }

}
