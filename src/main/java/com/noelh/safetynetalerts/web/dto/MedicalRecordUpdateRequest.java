package com.noelh.safetynetalerts.web.dto;

import lombok.Data;

import javax.persistence.ElementCollection;
import java.util.List;

@Data
public class MedicalRecordUpdateRequest {

    @ElementCollection
    private List<String> medications;

    @ElementCollection
    private List<String> allergies;
}
