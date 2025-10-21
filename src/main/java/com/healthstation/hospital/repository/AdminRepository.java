package com.healthstation.hospital.repository;

import com.healthstation.hospital.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AdminRepository extends JpaRepository<Admin,UUID> {

}
