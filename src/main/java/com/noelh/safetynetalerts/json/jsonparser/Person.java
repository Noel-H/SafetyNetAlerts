package com.noelh.safetynetalerts.json.jsonparser;

import lombok.Data;

import java.util.Date;

@Data
public class Person {
    private String firstName;
    private String lastName;
    private Date birthdate;
    private String address;
    private String city;
    private String zip;
    private String phone;
    private String email;
    private MedicalRecord medicalRecord;
}
