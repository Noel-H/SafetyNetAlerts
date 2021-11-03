package com.noelh.safetynetalerts.service;

import com.noelh.safetynetalerts.json.jsonparser.FireStation;
import com.noelh.safetynetalerts.json.jsonparser.FireStationRepository;
import com.noelh.safetynetalerts.json.jsonparser.Person;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FireStationServiceTest {

    @Mock
    private FireStationRepository fireStationRepository;

    @InjectMocks
    private FireStationService fireStationService;

    @Test
    public void test() {
        // GIVEN Je veux récuperer une firestation avec un id spécifique
        FireStation fs = new FireStation();
        when(fireStationRepository.findById(anyLong())).thenReturn(Optional.of(fs));

        // WHEN J'appelle la méthode en lui donnant un id
        FireStation result = fireStationService.getFireStation(1L);

        // THEN Je reçois la firestation avec l'id correspondante
        assertEquals(fs, result);
    }

    @Test
    public void testError() {
        // GIVEN Je veux récuperer une firestation avec un id qui n'existe pas
        when(fireStationRepository.findById(anyLong())).thenReturn(Optional.empty());

        // WHEN J'appelle la méthode en lui donnant un id
        // THEN Je reçois une NoSuchElementException
        assertThrows(NoSuchElementException.class, () -> fireStationService.getFireStation(1L));
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
}
