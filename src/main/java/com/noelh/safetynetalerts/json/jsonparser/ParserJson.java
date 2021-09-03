package com.noelh.safetynetalerts.json.jsonparser;

import lombok.Data;

import java.util.List;

@Data
public class ParserJson {
    private List<PersonJson> persons;
    private List<FireStationJson> firestations;
    private List<MedicalRecordJson> medicalrecords;
}
