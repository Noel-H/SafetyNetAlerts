package com.noelh.safetynetalerts.json.jsonparser;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class MedicalRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ElementCollection
    private List<String> medications;

    @ElementCollection
    private List<String> allergies;
}
