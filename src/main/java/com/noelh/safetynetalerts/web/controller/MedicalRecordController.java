package com.noelh.safetynetalerts.web.controller;

import com.noelh.safetynetalerts.json.jsonparser.MedicalRecord;
import com.noelh.safetynetalerts.service.MedicalRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequestMapping("/medicalrecords")
public class MedicalRecordController {

    @Autowired
    private MedicalRecordService medicalRecordService;

    @GetMapping("")
    public List<MedicalRecord> getMedicalRecords(){
        log.info("GET /medicalrecords/");
        return medicalRecordService.getMedicalRecords();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicalRecord> getMedicalRecord(@PathVariable("id") Long id){
        log.info("GET /medicalrecords/"+ id);
        try {
            return ResponseEntity.ok(medicalRecordService.getMedicalRecord(id));
        } catch (NoSuchElementException e){
            log.error("GET /medicalrecords/" + id + " - ERROR : " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}
