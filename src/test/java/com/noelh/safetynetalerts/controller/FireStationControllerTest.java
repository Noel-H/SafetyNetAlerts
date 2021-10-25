package com.noelh.safetynetalerts.controller;

import com.noelh.safetynetalerts.service.FireStationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@SpringBootTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
public class FireStationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FireStationService fireStationService;

    @Test
    public void getFireStationsTest_shouldReturnOk() throws Exception {
        //Given que j'essai d'obtenir la liste des stations
        when(fireStationService.getFireStations()).thenReturn(new ArrayList<>());
        //When je demande la liste
        mockMvc.perform(get("/firestations"))
        //Then je reçois un 200
                .andExpect(status().isOk());
    }
}
