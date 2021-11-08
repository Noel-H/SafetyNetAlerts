package com.noelh.safetynetalerts.service;

import com.noelh.safetynetalerts.json.jsonparser.FireStation;
import com.noelh.safetynetalerts.json.jsonparser.FireStationRepository;
import com.noelh.safetynetalerts.web.dto.FireStationAddRequest;
import com.noelh.safetynetalerts.web.dto.FireStationUpdateRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FireStationServiceTest {

    @Mock
    private FireStationRepository fireStationRepository;

    @InjectMocks
    private FireStationService fireStationService;

    @Test
    public void getFireStationsTest_shouldReturnListOfFireStation(){
        when(fireStationRepository.findAll()).thenReturn(new ArrayList<>());
        fireStationService.getFireStations();
        verify(fireStationRepository, times(1)).findAll();
    }

    @Test
    public void getFireStationTest_shouldReturnFireStation(){
        FireStation fireStation = new FireStation();
        when(fireStationRepository.findById(1L)).thenReturn(Optional.of(fireStation));
        FireStation result = fireStationService.getFireStation(1);
        assertEquals(fireStation,result);
    }

    @Test
    public void getFireStationTest_shouldThrowException(){
        when(fireStationRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> fireStationService.getFireStation(1));

    }

    @Test
    public void addFireStationTest_shouldReturnFireStation(){
        FireStation fs = new FireStation();
        FireStationAddRequest fsA = new FireStationAddRequest();
        when(fireStationRepository.save(any())).thenReturn(fs);
        fireStationService.addFireStation(fsA);
        verify(fireStationRepository, times(1)).save(any());
    }

    @Test
    public void updateFireStation_shouldReturnFireStation(){
        FireStation fs = new FireStation();
        FireStation fsUpdated = new FireStation();
        FireStationUpdateRequest fsUR = new FireStationUpdateRequest();
        when(fireStationRepository.save(any())).thenReturn(fsUpdated);
        fireStationService.updateFireStation(fs, fsUR);
        verify(fireStationRepository, times(1)).save(any());
    }
}
