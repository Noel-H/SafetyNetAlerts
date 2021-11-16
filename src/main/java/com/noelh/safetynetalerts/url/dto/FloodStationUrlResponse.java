package com.noelh.safetynetalerts.url.dto;

import com.noelh.safetynetalerts.medicalrecord.MedicalRecord;
import lombok.Data;

@Data
public class FloodStationUrlResponse {
    private String address;
    private String firstname;
    private String lastName;
    private String phone;
    private int age;
    private MedicalRecord medicalRecord;

    public FloodStationUrlResponse(String address, String firstName, String lastName, String phone, int ageByBirthdate, MedicalRecord medicalRecord) {
        this.address = address;
        this.firstname = firstName;
        this.lastName = lastName;
        this.phone = phone;
        age = ageByBirthdate;
        this.medicalRecord = medicalRecord;
    }
}
