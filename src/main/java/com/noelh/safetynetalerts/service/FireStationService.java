package com.noelh.safetynetalerts.service;

import com.noelh.safetynetalerts.json.jsonparser.FireStation;
import com.noelh.safetynetalerts.json.jsonparser.FireStationRepository;
import com.noelh.safetynetalerts.web.dto.FireStationAddRequest;
import com.noelh.safetynetalerts.web.dto.FireStationUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class FireStationService {

    @Autowired
    private FireStationRepository fireStationRepository;

    public List<FireStation> addFireStations(List<FireStation> fireStations) {
        return fireStationRepository.saveAll(fireStations);
    }

    public List<FireStation> getFireStations() {
        return fireStationRepository.findAll();
    }

    /**
     * Renvoie la firestation associée à l'id donnée
     * @param id l'id de la firestation
     * @return la firestation
     * @throws NoSuchElementException si il n'y a pas de firestation avec l'id donnée
     */
    public FireStation getFireStation(long id) throws NoSuchElementException {
        return fireStationRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("FireStation id " + id + " not found"));
    }

    public FireStation addFireStation(FireStationAddRequest fireStation) {
        return fireStationRepository.save(FireStation.builder()
                .station(fireStation.getStation())
                .address(fireStation.getAddress())
                .build());
    }

    public FireStation updateFireStation(FireStation fireStation, FireStationUpdateRequest fireStationUpdateRequest) {
        fireStation.setStation(fireStationUpdateRequest.getStation());
        return fireStationRepository.save(fireStation);
    }

    public FireStation deleteFireStation(FireStation firestation) {
        FireStation result = FireStation.builder()
                .id(firestation.getId())
                .station(firestation.getStation())
                .address(new ArrayList<>(firestation.getAddress()))
                .build();
        fireStationRepository.delete(firestation);
        return result;
    }

    public FireStation deleteFireStationAddress(FireStation firestation, String address) throws NoSuchElementException {
        if(firestation.getAddress().stream().noneMatch(str -> (str.equals(address)))){
            throw new NoSuchElementException("Address " + address + " not found");
        }else {
            List<String> result = firestation.getAddress().stream()
                    .filter(e -> !e.equals(address))
                    .collect(Collectors.toList());
            firestation.setAddress(result);
            return fireStationRepository.save(firestation);
        }
    }
}
