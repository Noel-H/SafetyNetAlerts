package com.noelh.safetynetalerts.controller;

import com.noelh.safetynetalerts.json.jsonparser.MedicalRecord;
import com.noelh.safetynetalerts.service.MedicalRecordService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
public class MedicalRecordControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MedicalRecordService medicalRecordService;

    @Test
    public void getMedicalRecordsTest_shouldReturnOk() throws Exception {
        when(medicalRecordService.getMedicalRecords()).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/medicalrecords"))
                .andExpect(status().isOk());
    }

    @Test
    public void getMedicalRecordTest_shouldReturnOk() throws Exception {
        when(medicalRecordService.getMedicalRecord(anyLong())).thenReturn(new MedicalRecord());
        mockMvc.perform(get("/medicalrecords/1")).andExpect(status().isOk());
    }

    @Test
    public void getMedicalRecordTest_shouldReturnNotFound() throws Exception {
        when(medicalRecordService.getMedicalRecord(anyLong())).thenThrow(new NoSuchElementException());
        mockMvc.perform(get("/medicalrecords/1")).andExpect(status().isNotFound());
    }

    @Test
    public void addMedicalRecordTest_shouldReturnOk() throws Exception {
        MedicalRecord mr = new MedicalRecord();
        when(medicalRecordService.getMedicalRecord(anyLong())).thenReturn(mr);
        when(medicalRecordService.addMedicalRecord(eq(mr), any())).thenReturn(new MedicalRecord());
        mockMvc.perform(post("/medicalrecords/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isOk());
    }

    @Test
    public void addMedicalRecordTest_shouldReturnBadRequest() throws Exception {
        MedicalRecord mr = new MedicalRecord();
        when(medicalRecordService.getMedicalRecord(anyLong())).thenReturn(mr);
        when(medicalRecordService.addMedicalRecord(eq(mr), any())).thenThrow(new IllegalArgumentException());
        mockMvc.perform(post("/medicalrecords/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void addMedicalRecordTest_shouldReturnNotFound() throws Exception {
        when(medicalRecordService.getMedicalRecord(anyLong())).thenThrow(new NoSuchElementException());
        mockMvc.perform(post("/medicalrecords/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateMedicalRecordTest_shouldReturnOk() throws Exception {
        MedicalRecord mr = new MedicalRecord();
        when(medicalRecordService.getMedicalRecord(anyLong())).thenReturn(mr);
        when(medicalRecordService.updateMedicalRecord(eq(mr), any())).thenReturn(new MedicalRecord());
        mockMvc.perform(put("/medicalrecords/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk());
    }

    @Test
    public void updateMedicalRecordTest_shouldReturnNotFound() throws Exception {
        when(medicalRecordService.getMedicalRecord(anyLong())).thenThrow(new NoSuchElementException());
        mockMvc.perform(put("/medicalrecords/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteMedicalRecordTest_shouldReturnOk() throws Exception {
        MedicalRecord mr = new MedicalRecord();
        when(medicalRecordService.getMedicalRecord(anyLong())).thenReturn(mr);
        when(medicalRecordService.deleteMedicalRecord(eq(mr))).thenReturn(new MedicalRecord());
        mockMvc.perform(delete("/medicalrecords/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteMedicalRecordTest_shouldReturnNotFound() throws Exception {
        when(medicalRecordService.getMedicalRecord(anyLong())).thenThrow(new NoSuchElementException());
        mockMvc.perform(delete("/medicalrecords/1"))
                .andExpect(status().isNotFound());
    }

}
