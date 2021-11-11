package com.noelh.safetynetalerts.medicalrecord;

import com.noelh.safetynetalerts.medicalrecord.dto.MedicalRecordAddRequest;
import com.noelh.safetynetalerts.medicalrecord.dto.MedicalRecordUpdateRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MedicalRecordServiceTest {

    @Mock
    private MedicalRecordRepository medicalRecordRepository;

    @InjectMocks
    private MedicalRecordService medicalRecordService;

    @Test
    public void getMedicalRecordsTest_shouldReturnListOfMedicalRecord(){
        when(medicalRecordRepository.findAll()).thenReturn(new ArrayList<>());
        medicalRecordService.getMedicalRecords();
        verify(medicalRecordRepository, times(1)).findAll();
    }

    @Test
    public void getMedicalRecordTest_shouldReturnMedicalRecord(){
        MedicalRecord medicalRecord = new MedicalRecord();
        when(medicalRecordRepository.findById(1L)).thenReturn(Optional.of(medicalRecord));
        MedicalRecord result = medicalRecordService.getMedicalRecord(1L);
        assertEquals(medicalRecord,result);
    }

    @Test
    public void getMecicalRecordTest_shouldThrowException(){
        when(medicalRecordRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> medicalRecordService.getMedicalRecord(1L));

    }

    @Test
    public void addMedicalRecordTest_shouldReturnMedicalRecord(){
        MedicalRecord mr = new MedicalRecord();
        mr.setId(1L);
        mr.setAllergies(new ArrayList<>());
        mr.setMedications(new ArrayList<>());
        MedicalRecordAddRequest mrAR = new MedicalRecordAddRequest();
        when(medicalRecordRepository.save(any())).thenReturn(new MedicalRecord());

        medicalRecordService.addMedicalRecord(mr, mrAR);

        verify(medicalRecordRepository, times(1)).save(any());
    }

    @Test
    public void addMedicalRecordTest_shouldThrowException(){
        MedicalRecord mr = new MedicalRecord();
        mr.setId(1L);
        mr.setAllergies(Arrays.asList("nicillian", "crevettes"));
        mr.setMedications(new ArrayList<>());
        MedicalRecordAddRequest mrAR = new MedicalRecordAddRequest();

        assertThrows(IllegalArgumentException.class, () -> medicalRecordService.addMedicalRecord(mr, mrAR));
    }

    @Test
    public void addMedicalRecordTest_shouldThrowExceptionButAllergiesIsEmpty(){
        MedicalRecord mr = new MedicalRecord();
        List<String> med = new ArrayList<>();
        med.add("Doliprane:500mg");

        mr.setId(1L);
        mr.setAllergies(new ArrayList<>());
        mr.setMedications(med);

        MedicalRecordAddRequest mrAR = new MedicalRecordAddRequest();

        assertThrows(IllegalArgumentException.class, () -> medicalRecordService.addMedicalRecord(mr, mrAR));
    }

    @Test
    public void updateMedicalRecord_shouldReturnMedicalRecord(){
        MedicalRecord mr = new MedicalRecord();
        MedicalRecord mrUpdated = new MedicalRecord();
        MedicalRecordUpdateRequest mrUR = new MedicalRecordUpdateRequest();
        when(medicalRecordRepository.save(mr)).thenReturn(mrUpdated);
        medicalRecordService.updateMedicalRecord(mr, mrUR);
        verify(medicalRecordRepository, times(1)).save(any());
    }

    @Test
    public void deleteMedicalRecordTest_shouldReturnMedicalRecord(){

        MedicalRecord mr = new MedicalRecord();
        List<String> allergies = new ArrayList<>();
        allergies.add("shrimp");
        allergies.add("nicillian");
        List<String> medications = new ArrayList<>();
        medications.add("tradoxidine:400mg");
        mr.setAllergies(allergies);
        mr.setMedications(medications);

        when(medicalRecordRepository.save(any())).thenReturn(new MedicalRecord());

        medicalRecordService.deleteMedicalRecord(mr);
        assertTrue(mr.getAllergies().isEmpty());
        assertTrue(mr.getMedications().isEmpty());
        verify(medicalRecordRepository,times(1)).save(any());
    }

    @Test
    public void deleteMedicalRecordTest_shouldReturnMedicalRecordButMedicalRecordIsEmpty(){

        MedicalRecord mr = new MedicalRecord();
        List<String> allergies = new ArrayList<>();
        List<String> medications = new ArrayList<>();
        mr.setAllergies(allergies);
        mr.setMedications(medications);

        when(medicalRecordRepository.save(any())).thenReturn(new MedicalRecord());

        medicalRecordService.deleteMedicalRecord(mr);
        assertTrue(mr.getAllergies().isEmpty());
        assertTrue(mr.getMedications().isEmpty());
        verify(medicalRecordRepository,times(1)).save(any());
    }

    @Test
    public void deleteMedicalRecordTest_shouldReturnMedicalRecordButMedicationsIsEmpty(){

        MedicalRecord mr = new MedicalRecord();
        List<String> allergies = new ArrayList<>();
        allergies.add("shrimp");
        allergies.add("nicillian");
        List<String> medications = new ArrayList<>();
        mr.setAllergies(allergies);
        mr.setMedications(medications);

        when(medicalRecordRepository.save(any())).thenReturn(new MedicalRecord());

        medicalRecordService.deleteMedicalRecord(mr);
        assertTrue(mr.getAllergies().isEmpty());
        assertTrue(mr.getMedications().isEmpty());
        verify(medicalRecordRepository,times(1)).save(any());
    }
}
