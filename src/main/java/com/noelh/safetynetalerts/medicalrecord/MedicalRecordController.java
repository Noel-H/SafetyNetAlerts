package com.noelh.safetynetalerts.medicalrecord;

import com.noelh.safetynetalerts.medicalrecord.dto.MedicalRecordAddRequest;
import com.noelh.safetynetalerts.medicalrecord.dto.MedicalRecordUpdateRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Contains medical record possible response
 */
@Slf4j
@RestController
@RequestMapping("/medicalrecords")
public class MedicalRecordController {

    @Autowired
    private MedicalRecordService medicalRecordService;

    /**
     * Get a list of Medical Record
     * @return a list of medical record
     */
    @GetMapping("")
    public List<MedicalRecord> getMedicalRecords(){
        log.info("GET /medicalrecords");
        return medicalRecordService.getMedicalRecords();
    }

    /**
     * Get a medical record by an id
     * @param id is the id of the medical record
     * @return a response entity ok or not found if a NoSuchElementException is caught
     */
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

    /**
     * Add a medical record
     * @param id is the id who'll get a medical record
     * @param medicalRecordAddRequest is the medical record who'll be added
     * @return a response entity ok or bad request if an IllegalArgumentException is caught or not found if a NoSuchElementException is caught
     */
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

    /**
     * Update a medical record
     * @param id is the id of the medical record who'll be updated
     * @param medicalRecordUpdateRequest is the medical record update info
     * @return a response entity ok or not found if a NoSuchElementException is caught
     */
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

    /**
     * Delete a medical record
     * @param id is the id of the medical record who'll be deleted
     * @return a response entity ok or not found if a NoSuchElementException is caught
     */
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
