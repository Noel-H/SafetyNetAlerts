package com.noelh.safetynetalerts.firestation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = FireStationController.class)
public class FireStationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FireStationService fireStationService;

    @Test
    public void getFireStationsTest_shouldReturnOk() throws Exception {
        when(fireStationService.getFireStations()).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/firestations"))
                .andExpect(status().isOk());
    }

    @Test
    public void getFireStationTest_shouldReturnOk() throws Exception {
        when(fireStationService.getFireStation(anyLong())).thenReturn(new FireStation());
        mockMvc.perform(get("/firestations/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void getFireStationTest_shouldReturnNotFound() throws Exception {
        when(fireStationService.getFireStation(anyLong())).thenThrow(new NoSuchElementException());
        mockMvc.perform(get("/firestations/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void addFireStationTest_shouldReturnOk() throws Exception {
        when(fireStationService.addFireStation(any())).thenReturn(new FireStation());
        mockMvc.perform(post("/firestations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk());
    }

    @Test
    public void updateFireStationTest_shouldReturnOk() throws Exception {
        FireStation f = new FireStation();
        when(fireStationService.getFireStation(anyLong())).thenReturn(f);
        when(fireStationService.updateFireStation(eq(f),any())).thenReturn(new FireStation());
        mockMvc.perform(put("/firestations/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk());
    }

    @Test
    public void updateFireStationTest_shouldReturnNotFound() throws Exception {
        when(fireStationService.getFireStation(anyLong())).thenThrow(new NoSuchElementException());
        mockMvc.perform(put("/firestations/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteFireStationTest_shouldReturnOk() throws Exception {
        FireStation f = new FireStation();
        when(fireStationService.getFireStation(anyLong())).thenReturn(f);
        when(fireStationService.deleteFireStation(eq(f))).thenReturn(new FireStation());
        mockMvc.perform(delete("/firestations/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteFireStationTest_shouldReturnNotFound() throws Exception {
        when(fireStationService.getFireStation(anyLong())).thenThrow(new NoSuchElementException());
        mockMvc.perform(delete("/firestations/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteFireStationAddressTest_shouldReturnOk() throws Exception {
        FireStation f = new FireStation();
        when(fireStationService.getFireStation(anyLong())).thenReturn(f);
        when(fireStationService.deleteFireStationAddress(eq(f), any())).thenReturn(new FireStation());
        mockMvc.perform(delete("/firestations/1/address")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteFireStationAddressTest_shouldReturnNotFound() throws Exception {
        when(fireStationService.getFireStation(anyLong())).thenThrow(new NoSuchElementException());
        mockMvc.perform(delete("/firestations/1/address")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isNotFound());
    }
}
