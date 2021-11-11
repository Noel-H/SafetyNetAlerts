package com.noelh.safetynetalerts.medicalrecord.dto;

import lombok.Data;

import javax.persistence.ElementCollection;
import java.util.List;

@Data
public class MedicalRecordAddRequest {

    @ElementCollection
    private List<String> medications;

    @ElementCollection
    private List<String> allergies;
}
