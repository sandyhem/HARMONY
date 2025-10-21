package com.healthstation.hospital.repository;

import com.healthstation.hospital.entity.PrescriptionDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PrescriptionDetailRepository extends JpaRepository<PrescriptionDetail, UUID> {
}
