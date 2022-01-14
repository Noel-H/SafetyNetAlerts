package com.noelh.safetynetalerts.firestation;

import com.noelh.safetynetalerts.firestation.dto.FireStationAddRequest;
import com.noelh.safetynetalerts.firestation.dto.FireStationUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * Contains Firestation related feature
 */
@Service
public class FireStationService {

    @Autowired
    private FireStationRepository fireStationRepository;

    /**
     * Add a list of firestation
     * @param fireStations is a liste of firestation
     * @return a list of firestation and save it with the fireStationRepository
     */
    public List<FireStation> addFireStations(List<FireStation> fireStations) {
        return fireStationRepository.saveAll(fireStations);
    }

    /**
     * Get a list of firestation
     * @return a list of firestation
     */
    public List<FireStation> getFireStations() {
        return fireStationRepository.findAll();
    }

    /**
     * Get a firestation by an id
     * @param id is the id of a firestation
     * @return a firestation
     * @throws NoSuchElementException if the id is not found
     */
    public FireStation getFireStation(long id) throws NoSuchElementException {
        return fireStationRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("FireStation id " + id + " not found"));
    }

    /**
     * Add a firestation
     * @param fireStation is the firestation who'll be added
     * @return a firestation and save it with the fireStationRepository
     */
    public FireStation addFireStation(FireStationAddRequest fireStation) {
        return fireStationRepository.save(FireStation.builder()
                .station(fireStation.getStation())
                .address(fireStation.getAddress())
                .build());
    }

    /**
     * Update a firestation
     * @param fireStation is the firestation who'll be updated
     * @param fireStationUpdateRequest is the updated info of the firestation
     * @return the updated firestation
     */
    public FireStation updateFireStation(FireStation fireStation, FireStationUpdateRequest fireStationUpdateRequest) {
        fireStation.setStation(fireStationUpdateRequest.getStation());
        return fireStationRepository.save(fireStation);
    }

    /**
     * Delete a firestation
     * @param firestation is the firestation who'll be deleted
     * @return the deleted firestation
     */
    public FireStation deleteFireStation(FireStation firestation) {
        FireStation result = FireStation.builder()
                .id(firestation.getId())
                .station(firestation.getStation())
                .address(new ArrayList<>(firestation.getAddress()))
                .build();
        fireStationRepository.delete(firestation);
        return result;
    }

    /**
     * Delete an address from a firestation
     * @param firestation is the firestation who'll be modified
     * @param address is the address who'll be deleted
     * @return the modified firestation
     * @throws NoSuchElementException  if the address is not found
     */
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
