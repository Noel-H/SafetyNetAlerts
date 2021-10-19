package com.noelh.safetynetalerts.service;

import com.noelh.safetynetalerts.json.jsonparser.MedicalRecord;
import com.noelh.safetynetalerts.json.jsonparser.MedicalRecordRepository;
import com.noelh.safetynetalerts.json.jsonparser.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

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
}
