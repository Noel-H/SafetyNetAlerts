package com.noelh.safetynetalerts.url;

import com.noelh.safetynetalerts.firestation.FireStation;
import com.noelh.safetynetalerts.firestation.FireStationService;
import com.noelh.safetynetalerts.url.dto.FireStationUrlResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Slf4j
@Service
public class UrlService {

    @Autowired
    private FireStationService fireStationService;

    public FireStationUrlResponse getPersonsListByStationId(Long stationId) {
        FireStation fireStation = fireStationService.getFireStation(stationId);
        FireStationUrlResponse fireStationUrlResponse = new FireStationUrlResponse();
        fireStationUrlResponse.setNbChild(5);
        return fireStationUrlResponse;
    }
}
