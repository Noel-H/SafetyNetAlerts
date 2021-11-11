package com.noelh.safetynetalerts.parser;

import com.noelh.safetynetalerts.firestation.FireStationJson;
import com.noelh.safetynetalerts.medicalrecord.MedicalRecordJson;
import com.noelh.safetynetalerts.person.PersonJson;
import lombok.Data;

import java.util.List;

@Data
public class ParserJson {
    private List<PersonJson> persons;
    private List<FireStationJson> firestations;
    private List<MedicalRecordJson> medicalrecords;
}
