package com.noelh.safetynetalerts.web.controller;

import com.noelh.safetynetalerts.json.jsonparser.FireStation;
import com.noelh.safetynetalerts.service.FireStationService;
import com.noelh.safetynetalerts.web.dto.FireStationAddRequest;
import com.noelh.safetynetalerts.web.dto.FireStationDeleteAdressRequest;
import com.noelh.safetynetalerts.web.dto.FireStationUpdateRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequestMapping("/firestations")
public class FireStationController {

    @Autowired
    private FireStationService fireStationService;

    @GetMapping("")
    public List<FireStation> getFireStations(){
        log.info("GET /firestations/");
        return fireStationService.getFirestations();
    }

    @GetMapping("/{id}")
    public ResponseEntity<FireStation> getFireStation(@PathVariable("id") Long id){
        log.info("GET /firestations/" +id);
        try {
            return ResponseEntity.ok(fireStationService.getFirestation(id));
        } catch (NoSuchElementException e) {
            log.error("GET /firestations/" + id + " - ERROR : " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("")
    public ResponseEntity<FireStation> addFireStation(@RequestBody FireStationAddRequest firestation){
        log.info("POST /firestations/");
        return ResponseEntity.ok(fireStationService.addFireStation(firestation));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FireStation> updateFireStation(@PathVariable("id") Long id,@RequestBody FireStationUpdateRequest fireStationUpdateRequest){
        log.info("PUT /firestations/" + id);
        try {
            return ResponseEntity.ok(fireStationService.updateFireStation(fireStationService.getFirestation(id), fireStationUpdateRequest));
        } catch (NoSuchElementException e) {
            log.error("PUT /firestations/" + id + " - ERROR : " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<FireStation> deleteFireStation(@PathVariable("id") Long id){
        log.info("DELETE /firestations/" +id);
        try{
            return ResponseEntity.ok(fireStationService.deleteFireStation(fireStationService.getFirestation(id)));
        } catch (NoSuchElementException e) {
            log.error("DELETE /firestations/" + id + " - ERROR : " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}/adress")
    public ResponseEntity<FireStation> deleteFireStationAddress(@PathVariable("id") Long id, @RequestBody FireStationDeleteAdressRequest fireStationDeleteAdressRequest) {
        log.info("DELETE /firestations/" + id + "/adress : " +fireStationDeleteAdressRequest.getAddress());
        try {
            return ResponseEntity.ok(fireStationService.deleteFireStationAddress(fireStationService.getFirestation(id), fireStationDeleteAdressRequest.getAddress()));
        } catch (NoSuchElementException e) {
            log.error("DELETE /firestations/" + id +"/adress : " +fireStationDeleteAdressRequest.getAddress() +" - ERROR : " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}
