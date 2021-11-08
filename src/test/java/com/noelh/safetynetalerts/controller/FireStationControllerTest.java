package com.noelh.safetynetalerts.controller;

import com.noelh.safetynetalerts.json.JsonInitializer;
import com.noelh.safetynetalerts.json.jsonparser.FireStation;
import com.noelh.safetynetalerts.json.jsonparser.Person;
import com.noelh.safetynetalerts.service.FireStationService;
import com.noelh.safetynetalerts.web.controller.FireStationController;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;




@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = FireStationController.class)
public class FireStationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JsonInitializer jsonInitializer;

    @MockBean
    private FireStationService fireStationService;

    @BeforeEach
    public void setup() throws Exception {
        doNothing().when(jsonInitializer).run();
    }

    @Test
    public void getFireStationsTest_shouldReturnOk() throws Exception {
        //Given que j'essai d'obtenir la liste des stations
        when(fireStationService.getFireStations()).thenReturn(new ArrayList<>());
        //When je demande la liste
        mockMvc.perform(get("/firestations"))
                //Then je reçois un 200
                .andExpect(status().isOk());
    }

    @Test
    public void getFireStationTest_shouldReturnOk() throws Exception {
        //Given que j'éssaie d'obtenir une firestation grace à un id
        when(fireStationService.getFireStation(anyLong())).thenReturn(new FireStation());
        //When je fais la demande
        mockMvc.perform(get("/firestations/1"))
                //Then je reçois un ok
                .andExpect(status().isOk());
    }

    @Test
    public void getFireStationTest_shouldReturnNotFound() throws Exception {
        //Given que j'éssaie d'obtenir une firestation grace à un id érroné
        when(fireStationService.getFireStation(anyLong())).thenThrow(new NoSuchElementException());
        //When je fais la demande
        mockMvc.perform(get("/firestations/1"))
                //Then je reçois un ok
                .andExpect(status().isNotFound());
    }

    @Test
    public void addFireStationTest_shouldReturnOk() throws Exception {
        //Given que j'éssaie d'ajouter une firestation
        when(fireStationService.addFireStation(any())).thenReturn(new FireStation());
        //When je fais la demande
        mockMvc.perform(post("/firestations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                //Then je reçois un ok
                .andExpect(status().isOk());
    }

    @Test
    public void updateFireStationTest_shouldReturnOk() throws Exception {
        //Given que j'éssaie de modifier une firestation
        FireStation f = new FireStation();
        when(fireStationService.getFireStation(anyLong())).thenReturn(f);
        when(fireStationService.updateFireStation(eq(f),any())).thenReturn(new FireStation());
        //When je fais la demande
        mockMvc.perform(put("/firestations/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                //Then je reçois un ok
                .andExpect(status().isOk());
    }

    @Test
    public void updateFireStationTest_shouldReturnNotFound() throws Exception {
        //Given que j'éssaie d'ajouter une firestation
        when(fireStationService.getFireStation(anyLong())).thenThrow(new NoSuchElementException());
        //When je fais la demande
        mockMvc.perform(put("/firestations/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                //Then je reçois un ok
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteFireStationTest_shouldReturnOk() throws Exception {
        //Given que j'éssaie de modifier une firestation
        FireStation f = new FireStation();
        when(fireStationService.getFireStation(anyLong())).thenReturn(f);
        when(fireStationService.deleteFireStation(eq(f))).thenReturn(new FireStation());
        //When je fais la demande
        mockMvc.perform(delete("/firestations/1"))
                //Then je reçois un ok
                .andExpect(status().isOk());
    }

    @Test
    public void deleteFireStationTest_shouldReturnNotFound() throws Exception {
        //Given que j'éssaie de modifier une firestation
        when(fireStationService.getFireStation(anyLong())).thenThrow(new NoSuchElementException());
        //When je fais la demande
        mockMvc.perform(delete("/firestations/1"))
                //Then je reçois un ok
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteFireStationAddressTest_shouldReturnOk() throws Exception {
        //Given que j'éssaie de modifier une firestation
        FireStation f = new FireStation();
        when(fireStationService.getFireStation(anyLong())).thenReturn(f);
        when(fireStationService.deleteFireStationAddress(eq(f), any())).thenReturn(new FireStation());
        //When je fais la demande
        mockMvc.perform(delete("/firestations/1/address")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                //Then je reçois un ok
                .andExpect(status().isOk());
    }

    @Test
    public void deleteFireStationAddressTest_shouldReturnNotFound() throws Exception {
        //Given que j'éssaie de modifier une firestation
        when(fireStationService.getFireStation(anyLong())).thenThrow(new NoSuchElementException());
        //When je fais la demande
        mockMvc.perform(delete("/firestations/1/address")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                //Then je reçois un ok
                .andExpect(status().isNotFound());
    }
}
