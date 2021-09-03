package com.noelh.safetynetalerts.json.jsonparser;

import lombok.Data;

import java.util.List;

@Data
public class Parser {
    private List<Person> persons;
    private List<FireStation> firestations;
    private List<MedicalRecord> medicalrecords;
}
