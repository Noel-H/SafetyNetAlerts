package com.noelh.safetynetalerts.firestation;

import com.noelh.safetynetalerts.firestation.dto.FireStationAddRequest;
import com.noelh.safetynetalerts.firestation.dto.FireStationUpdateRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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
    public void updateFireStationTest_shouldReturnFireStation(){
        FireStation fs = new FireStation();
        FireStation fsUpdated = new FireStation();
        FireStationUpdateRequest fsUR = new FireStationUpdateRequest();
        when(fireStationRepository.save(any())).thenReturn(fsUpdated);
        fireStationService.updateFireStation(fs, fsUR);
        verify(fireStationRepository, times(1)).save(any());
    }

    @Test
    public void deleteFireStationTest_shouldReturnFireStation(){
        FireStation firestation = new FireStation();
        List<String> address = new ArrayList<>();
        firestation.setAddress(address);

        doNothing().when(fireStationRepository).delete(any());

        FireStation result = fireStationService.deleteFireStation(firestation);
        verify(fireStationRepository, times(1)).delete(any());
        assertEquals(result, firestation);
    }

    @Test
    public void deleteFireStationAddressTest_shouldReturnFireStation(){
        FireStation fireStation = new FireStation();
        List<String> addressList = new ArrayList<>();
        addressList.add("01 Address Test");
        fireStation.setAddress(addressList);
        String address = "01 Address Test";

        when(fireStationRepository.save(any())).thenReturn(new FireStation());

        fireStationService.deleteFireStationAddress(fireStation,address);
        assertFalse(fireStation.getAddress().contains(address));
        verify(fireStationRepository, times(1)).save(any());
    }

    @Test
    public void deleteFireStationAddressTest_shouldThrowException(){
        FireStation fireStation = new FireStation();
        List<String> addressList = new ArrayList<>();
        fireStation.setAddress(addressList);
        String address = "01 Address Test";

        assertThrows(NoSuchElementException.class, () -> fireStationService.deleteFireStationAddress(fireStation,address));
    }
}
