package com.noelh.safetynetalerts.url;

import com.noelh.safetynetalerts.url.dto.FireStationUrlResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
