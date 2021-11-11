package com.noelh.safetynetalerts.service;

import com.noelh.safetynetalerts.json.jsonparser.MedicalRecord;
import com.noelh.safetynetalerts.json.jsonparser.MedicalRecordRepository;
import com.noelh.safetynetalerts.web.dto.MedicalRecordAddRequest;
import com.noelh.safetynetalerts.web.dto.MedicalRecordUpdateRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
public class MedicalRecordService {

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    public List<MedicalRecord> getMedicalRecords() {
        return medicalRecordRepository.findAll();
    }

    public MedicalRecord getMedicalRecord(Long id) throws NoSuchElementException {
        return medicalRecordRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("MedicalRecord id " + id + " not found"));
    }

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

    public MedicalRecord updateMedicalRecord(MedicalRecord medicalRecord, MedicalRecordUpdateRequest medicalRecordUpdateRequest) {
        return medicalRecordRepository.save(MedicalRecord.builder()
                .id(medicalRecord.getId())
                .medications(medicalRecordUpdateRequest.getMedications())
                .allergies(medicalRecordUpdateRequest.getAllergies())
                .build());
    }

    public MedicalRecord deleteMedicalRecord(MedicalRecord medicalRecord){
        if (medicalRecord.getMedications().isEmpty() && medicalRecord.getAllergies().isEmpty()) {
            log.warn("MedicalRecord id {} already empty", medicalRecord.getId());
        }
        medicalRecord.setMedications(new ArrayList<>());
        medicalRecord.setAllergies(new ArrayList<>());
        return medicalRecordRepository.save(medicalRecord);
    }
}
