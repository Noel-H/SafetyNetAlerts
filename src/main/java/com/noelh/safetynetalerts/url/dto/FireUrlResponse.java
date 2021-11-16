package com.noelh.safetynetalerts.url.dto;

import com.noelh.safetynetalerts.medicalrecord.MedicalRecord;
import lombok.Data;

@Data
public class FireUrlResponse {
    private String firstName;
    private String lastName;
    private String phone;
    private int age;
    private MedicalRecord medicalRecord;

    public FireUrlResponse(String firstName, String lastName, String phone, int ageByBirthdate, MedicalRecord medicalRecord) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        age = ageByBirthdate;
        this.medicalRecord = medicalRecord;
    }
}
