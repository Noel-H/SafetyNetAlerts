package com.noelh.safetynetalerts.medicalrecord;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * MedicalRecord Repository
 */
@Repository
public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {
}
