package com.noelh.safetynetalerts.web.controller;

import com.noelh.safetynetalerts.json.jsonparser.FireStation;
import com.noelh.safetynetalerts.json.jsonparser.MedicalRecord;
import com.noelh.safetynetalerts.service.MedicalRecordService;
import com.noelh.safetynetalerts.service.PersonService;
import com.noelh.safetynetalerts.web.dto.MedicalRecordAddRequest;
import com.noelh.safetynetalerts.web.dto.MedicalRecordUpdateRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/{id}")
    public ResponseEntity<MedicalRecord> addMedicalRecord(@PathVariable("id") Long id, @RequestBody MedicalRecordAddRequest medicalRecordAddRequest){
        log.info("POST /medicalrecords/" + id);
        try {
            return ResponseEntity.ok(medicalRecordService.addMedicalRecord(medicalRecordService.getMedicalRecord(id),medicalRecordAddRequest));
        } catch (IllegalArgumentException e) {
            log.error("POST /medicalrecords/" + id + " - ERROR : " + e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (NoSuchElementException e) {
            log.error("POST /medicalrecords/" + id + " - ERROR : " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicalRecord> updateMedicalRecord(@PathVariable("id") Long id, @RequestBody MedicalRecordUpdateRequest medicalRecordUpdateRequest){
        log.info("PUT /medicalrecords/" + id);
        try {
            return ResponseEntity.ok(medicalRecordService.updateMedicalRecord(medicalRecordService.getMedicalRecord(id),medicalRecordUpdateRequest));
        } catch (NoSuchElementException e) {
            log.error("PUT /medicalrecords/" + id + " - ERROR : " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MedicalRecord> deleteMedicalRecord(@PathVariable("id") Long id){
        log.info("DELETE /medicalrecords/" +id);
        try{
            return ResponseEntity.ok(medicalRecordService.deleteMedicalRecord(medicalRecordService.getMedicalRecord(id)));
        } catch (NoSuchElementException e) {
            log.error("DELETE /medicalrecords/" + id + " - ERROR : " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }


}
