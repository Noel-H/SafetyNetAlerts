package com.noelh.safetynetalerts.json;

import com.noelh.safetynetalerts.firestation.FireStation;
import com.noelh.safetynetalerts.firestation.FireStationJson;
import com.noelh.safetynetalerts.medicalrecord.MedicalRecord;
import com.noelh.safetynetalerts.medicalrecord.MedicalRecordJson;
import com.noelh.safetynetalerts.parser.Parser;
import com.noelh.safetynetalerts.parser.ParserJson;
import com.noelh.safetynetalerts.person.Person;
import com.noelh.safetynetalerts.person.PersonJson;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
class JsonManagementTest {

    JsonManagement jsonManagement = new JsonManagement();

    @Test
    public void dataConverterTest_shouldReturn_Parser() throws ParseException {
        ParserJson parserJson = new ParserJson();

        List<PersonJson> personJsonList = new ArrayList<>();
        List<FireStationJson> fireStationJsonList = new ArrayList<>();
        List<MedicalRecordJson> medicalRecordJsonList = new ArrayList<>();

        PersonJson personJson = new PersonJson();
        personJson.setFirstName("John");
        personJson.setLastName("Doe");
        personJson.setAddress("42 Test St");
        personJson.setCity("Paris");
        personJson.setZip("12345");
        personJson.setPhone("801-803-8004");
        personJson.setEmail("john.doe@email.com");
        personJsonList.add(personJson);
        parserJson.setPersons(personJsonList);

        FireStationJson fireStationJson = new FireStationJson();
        fireStationJson.setStation("1");
        fireStationJson.setAddress("42 Test St");
        fireStationJsonList.add(fireStationJson);
        parserJson.setFirestations(fireStationJsonList);

        MedicalRecordJson medicalRecordJson = new MedicalRecordJson();

        List<String> medications = new ArrayList<>();
        medications.add("doliprane:1000mg");
        medications.add("senzu:500mg");

        List<String> allergies = new ArrayList<>();
        allergies.add("lactose");
        allergies.add("noix");

        medicalRecordJson.setFirstName("John");
        medicalRecordJson.setLastName("Doe");
        medicalRecordJson.setBirthdate("01/01/2001");
        medicalRecordJson.setMedications(medications);
        medicalRecordJson.setAllergies(allergies);
        medicalRecordJsonList.add(medicalRecordJson);
        parserJson.setMedicalrecords(medicalRecordJsonList);

        Parser parser = new Parser();

        List<Person> personList = new ArrayList<>();
        List<FireStation> fireStationList = new ArrayList<>();

        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setMedications(medications);
        medicalRecord.setAllergies(allergies);

        Person person = new Person();
        Date birthdayDate = new SimpleDateFormat("MM/dd/yyyy").parse("01/01/2001");

        person.setFirstName("John");
        person.setLastName("Doe");
        person.setBirthdate(birthdayDate);
        person.setAddress("42 Test St");
        person.setCity("Paris");
        person.setZip("12345");
        person.setPhone("801-803-8004");
        person.setEmail("john.doe@email.com");
        person.setMedicalRecord(medicalRecord);
        personList.add(person);
        parser.setPersons(personList);

        FireStation fireStation = new FireStation();
        List<String> addressList = new ArrayList<>();
        addressList.add("42 Test St");
        fireStation.setStation(1);
        fireStation.setAddress(addressList);
        fireStationList.add(fireStation);
        parser.setFireStations(fireStationList);

        Parser returnedValue = jsonManagement.dataConverter(parserJson);

        assertThat(returnedValue).isEqualTo(parser);
    }
}