package com.noelh.safetynetalerts.firestation;

import com.noelh.safetynetalerts.firestation.dto.FireStationAddRequest;
import com.noelh.safetynetalerts.firestation.dto.FireStationDeleteAddressRequest;
import com.noelh.safetynetalerts.firestation.dto.FireStationUpdateRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Contains firestation possible response
 */
@Slf4j
@RestController
@RequestMapping("/firestations")
public class FireStationController {

    @Autowired
    private FireStationService fireStationService;

    /**
     * Get a list of firestation
     * @return a list of firestation
     */
    @GetMapping("")
    public List<FireStation> getFireStations(){
        log.info("GET /firestations");
        return fireStationService.getFireStations();
    }

    /**
     * Get a firestation by an id
     * @param id is the firestation id
     * @return a response entity ok or not found if a NoSuchElementException is caught
     */
    @GetMapping("/{id}")
    public ResponseEntity<FireStation> getFireStation(@PathVariable("id") Long id){
        log.info("GET /firestations/" +id);
        try {
            return ResponseEntity.ok(fireStationService.getFireStation(id));
        } catch (NoSuchElementException e) {
            log.error("GET /firestations/" + id + " - ERROR : " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Add a firestation
     * @param firestation is the firestation who'll be added
     * @return a response entity ok for the added firestation
     */
    @PostMapping("")
    public ResponseEntity<FireStation> addFireStation(@RequestBody FireStationAddRequest firestation){
        log.info("POST /firestations/");
        return ResponseEntity.ok(fireStationService.addFireStation(firestation));
    }

    /**
     * Update a firestation
     * @param id is the id of the firestation who'll be updated
     * @param fireStationUpdateRequest is the info of the firestation who'll be updated
     * @return a response entity ok or not found if a NoSuchElementException is caught
     */
    @PutMapping("/{id}")
    public ResponseEntity<FireStation> updateFireStation(@PathVariable("id") Long id,@RequestBody FireStationUpdateRequest fireStationUpdateRequest){
        log.info("PUT /firestations/" + id);
        try {
            return ResponseEntity.ok(fireStationService.updateFireStation(fireStationService.getFireStation(id), fireStationUpdateRequest));
        } catch (NoSuchElementException e) {
            log.error("PUT /firestations/" + id + " - ERROR : " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Delete a firestation
     * @param id is the id of the firestation who'll be deleted
     * @return a response entity ok or not found if a NoSuchElementException is caught
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<FireStation> deleteFireStation(@PathVariable("id") Long id){
        log.info("DELETE /firestations/" +id);
        try{
            return ResponseEntity.ok(fireStationService.deleteFireStation(fireStationService.getFireStation(id)));
        } catch (NoSuchElementException e) {
            log.error("DELETE /firestations/" + id + " - ERROR : " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Delete an address from a firestation
     * @param id is the id of the firestation who'll get modified
     * @param fireStationDeleteAddressRequest is the info of the address who need to be deleted
     * @return a response entity ok or not found if a NoSuchElementException is caught
     */
    @DeleteMapping("/{id}/address")
    public ResponseEntity<FireStation> deleteFireStationAddress(@PathVariable("id") Long id, @RequestBody FireStationDeleteAddressRequest fireStationDeleteAddressRequest) {
        log.info("DELETE /firestations/" + id + "/address : " + fireStationDeleteAddressRequest.getAddress());
        try {
            return ResponseEntity.ok(fireStationService.deleteFireStationAddress(fireStationService.getFireStation(id), fireStationDeleteAddressRequest.getAddress()));
        } catch (NoSuchElementException e) {
            log.error("DELETE /firestations/" + id +"/address : " + fireStationDeleteAddressRequest.getAddress() +" - ERROR : " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}
