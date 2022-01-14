package com.noelh.safetynetalerts.url;

import com.noelh.safetynetalerts.url.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Contains URL related possible response
 */
@Slf4j
@RestController
@RequestMapping("/")
public class UrlController {

    @Autowired
    private UrlService urlService;

    /**
     * Get a list of persons by a station id
     * @param stationId is the station id
     * @return a response entity ok or not found if a NoSuchElementException is caught
     */
    @GetMapping("firestations/")
    public ResponseEntity<FireStationUrlResponse> getPersonsListByStationId(@RequestParam(value = "stationId") Long stationId){
        log.info("GET /firestations/?stationId=" + stationId);
        try {
            return ResponseEntity.ok(urlService.getPersonsListByStationId(stationId));
        } catch (NoSuchElementException e){
            log.error("GET /firestations/?stationId=" + stationId + " - ERROR : " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }


    /**
     * Get a list of child by an address
     * @param address is the address
     * @return a response entity ok or not found if a NoSuchElementException is caught
     */
    @GetMapping("childAlert/")
    public ResponseEntity<List<ChildAlertUrlResponse>> getChildListByAddress(@RequestParam(value = "address") String address){
        log.info("GET /childAlert/?address=" + address);
        try {
            return ResponseEntity.ok(urlService.getChildListByAddress(address));
        } catch (NoSuchElementException e){
            log.error("GET /childAlert/?address=" + address + " - ERROR : " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Get phones number by station id
     * @param stationId is the station id
     * @return a response entity ok or not found if a NoSuchElementException is caught
     */
    @GetMapping("phoneAlert/")
    public ResponseEntity<List<PhoneUrlResponse>> getPhonesByStationId(@RequestParam(value = "firestation") Long stationId){
        log.info("GET /phoneAlert/?firestation=" + stationId);
        try {
            return ResponseEntity.ok(urlService.getPhonesByStationId(stationId));
        } catch (NoSuchElementException e){
            log.error("GET /phoneAlert/?firestation=" + stationId + " - ERROR : " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Get a person and station number by an address
     * @param address is the address
     * @return a response entity ok or not found if a NoSuchElementException is caught
     */
    @GetMapping("fire/")
    public ResponseEntity<FireUrlWithStationNumberResponse> getPersonAndStationNumberByAdress(@RequestParam(value = "address") String address){
        log.info("GET /fire/?address=" + address);
        try {
            return ResponseEntity.ok(urlService.getPersonAndStationNumberByAddress(address));
        } catch (NoSuchElementException e){
            log.error("GET /fire/?address=" + address + " - ERROR : " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Get a list of persons grouped by address
     * @param stationIdList is the list of station id
     * @return a response entity ok or not found if a NoSuchElementException is caught
     */
    @GetMapping("flood/stations/")
    public ResponseEntity<Map<String, List<FloodStationUrlResponse>>> getPersonsGroupedByAddress(@RequestParam(value = "stations") List<Long> stationIdList){
        log.info("GET /flood/stations/?stations=" + stationIdList);
        try {
            return ResponseEntity.ok(urlService.getPersonsGroupedByAddress(stationIdList));
        } catch (NoSuchElementException e){
            log.error("GET /flood/stations/?stations=" + stationIdList + " - ERROR : " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Get a person info by his firstname and lastname
     * @param firstName is the firstname
     * @param lastName is the lastname
     * @return a response entity ok or not found if a NoSuchElementException is caught
     */
    @GetMapping("personInfo/")
    public ResponseEntity<List<PersonInfoUrlResponse>> getPersonInfoByFirstNameAndLastName(@RequestParam(value = "firstName") String firstName, @RequestParam(value = "lastName") String lastName){
        log.info("GET /personInfo/?firstName="+firstName+"&lastName="+lastName);
        try {
            return ResponseEntity.ok(urlService.getPersonInfoByFirstNameAndLastName(firstName, lastName));
        } catch (NoSuchElementException e){
            log.error("GET /personInfo/?firstName="+firstName+"&lastName="+lastName + " - ERROR : " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Get a list of mail address by a city
     * @param city is the city
     * @return a response entity ok or not found if a NoSuchElementException is caught
     */
    @GetMapping("communityEmail/")
    public ResponseEntity<List<CommunityEmailUrlResponse>> getMailsByCity(@RequestParam(value = "city") String city){
        log.info("GET /communityEmail/?city=" +city);
        try {
            return ResponseEntity.ok(urlService.getMailsByCity(city));
        } catch (NoSuchElementException e){
            log.error("GET /communityEmail/?city=" +city + " - ERROR : " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}
