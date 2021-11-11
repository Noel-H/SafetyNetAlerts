package com.noelh.safetynetalerts.medicalrecord;

import lombok.Data;

import java.util.List;

@Data
public class MedicalRecordJson {
    private String firstName;
    private String lastName;
    private String birthdate;
    private List<String> medications;
    private List<String> allergies;
}
