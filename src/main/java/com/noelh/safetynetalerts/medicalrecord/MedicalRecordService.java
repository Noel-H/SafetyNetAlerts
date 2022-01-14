package com.noelh.safetynetalerts.medicalrecord;

import com.noelh.safetynetalerts.medicalrecord.dto.MedicalRecordAddRequest;
import com.noelh.safetynetalerts.medicalrecord.dto.MedicalRecordUpdateRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Contains Medical Record related feature
 */
@Slf4j
@Service
public class MedicalRecordService {

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    /**
     * Get a list of medical record
     * @return a list of medical record
     */
    public List<MedicalRecord> getMedicalRecords() {
        return medicalRecordRepository.findAll();
    }

    /**
     * Get a medical record by his id
     * @param id is the id
     * @return a MedicalRecord
     * @throws NoSuchElementException if the id is not found
     */
    public MedicalRecord getMedicalRecord(Long id) throws NoSuchElementException {
        return medicalRecordRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("MedicalRecord id " + id + " not found"));
    }

    /**
     * Add a MedicalRecord
     * @param medicalRecord is the medical record spot
     * @param medicalRecordAddRequest is the medical record who will be added
     * @return a medical record and save it to the medicalRecordRepository
     * @throws IllegalArgumentException if the medical record spot is not empty
     */
    public MedicalRecord addMedicalRecord(MedicalRecord medicalRecord, MedicalRecordAddRequest medicalRecordAddRequest) throws IllegalArgumentException {
        if (medicalRecord.getMedications().isEmpty() && medicalRecord.getAllergies().isEmpty()) {
            return medicalRecordRepository.save(MedicalRecord.builder()
                    .id(medicalRecord.getId())
                    .medications(medicalRecordAddRequest.getMedications())
                    .allergies(medicalRecordAddRequest.getAllergies())
                    .build());
        } else {
            throw new IllegalArgumentException("MedicalRecord id " + medicalRecord.getId() + " already exist");
        }

    }

    /**
     * Update a medical record
     * @param medicalRecord is the medical record who'll be updated
     * @param medicalRecordUpdateRequest is the info who need to be updated
     * @return the updated medical record and save it to the medicalRecordRepository
     */
    public MedicalRecord updateMedicalRecord(MedicalRecord medicalRecord, MedicalRecordUpdateRequest medicalRecordUpdateRequest) {
        return medicalRecordRepository.save(MedicalRecord.builder()
                .id(medicalRecord.getId())
                .medications(medicalRecordUpdateRequest.getMedications())
                .allergies(medicalRecordUpdateRequest.getAllergies())
                .build());
    }

    /**
     * Delete a medical record
     * @param medicalRecord is the medical record who'll be deleted
     * @return the deleted medical record
     */
    public MedicalRecord deleteMedicalRecord(MedicalRecord medicalRecord){
        if (medicalRecord.getMedications().isEmpty() && medicalRecord.getAllergies().isEmpty()) {
            log.warn("MedicalRecord id {} already empty", medicalRecord.getId());
        }
        medicalRecord.setMedications(new ArrayList<>());
        medicalRecord.setAllergies(new ArrayList<>());
        return medicalRecordRepository.save(medicalRecord);
    }
}
