package com.noelh.safetynetalerts.url.dto;

import com.noelh.safetynetalerts.medicalrecord.MedicalRecord;
import lombok.Data;

@Data
public class PersonInfoUrlResponse {
    private String firstName;
    private String lastName;
    private String address;
    private int age;
    private String email;
    private MedicalRecord medicalRecord;

    public PersonInfoUrlResponse(String firstName, String lastName, String address, int ageByBirthdate, String email, MedicalRecord medicalRecord) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.address = address;
    age = ageByBirthdate;
    this.email = email;
    this.medicalRecord = medicalRecord;
    }
}
