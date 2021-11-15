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

@Slf4j
@RestController
@RequestMapping("/")
public class UrlController {

    @Autowired
    private UrlService urlService;

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
}
