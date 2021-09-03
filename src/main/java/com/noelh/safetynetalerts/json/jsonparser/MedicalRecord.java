package com.noelh.safetynetalerts.json.jsonparser;

import lombok.Data;

import java.util.List;

@Data
public class MedicalRecord {
    private List<String> medications;
    private List<String> allergies;
}
