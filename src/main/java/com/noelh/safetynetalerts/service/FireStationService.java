package com.noelh.safetynetalerts.service;

import com.noelh.safetynetalerts.json.jsonparser.FireStation;
import com.noelh.safetynetalerts.json.jsonparser.FireStationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FireStationService {

    @Autowired
    private FireStationRepository fireStationRepository;

    public List<FireStation> addFireStations(List<FireStation> firestations) {
        return fireStationRepository.saveAll(firestations);
    }
}
