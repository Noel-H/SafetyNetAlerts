package com.noelh.safetynetalerts.json.jsonparser;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MedicalRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ElementCollection
    private List<String> medications;

    @ElementCollection
    private List<String> allergies;

    public MedicalRecord(MedicalRecord mr) {
        id = mr.id;
        medications = new ArrayList<>(mr.medications);
        allergies = new ArrayList<>(mr.allergies);
    }
}
