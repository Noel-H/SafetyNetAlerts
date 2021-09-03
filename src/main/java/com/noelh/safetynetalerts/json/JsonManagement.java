package com.noelh.safetynetalerts.json;

import com.noelh.safetynetalerts.json.jsonparser.*;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class JsonManagement {
    private static JsonManagement instance;

    private String jsonData="";

    public static JsonManagement getInstance(){
        if (instance == null){
            instance = new JsonManagement();
        }
        return instance;
    }

    private JsonManagement(){}

    public String getJsonData() {
        return jsonData;
    }

    public String loadJsonData(Resource jsonDataFile) throws IOException {
        try(InputStreamReader inputStreamReader = new InputStreamReader(jsonDataFile.getInputStream())){
            int data = inputStreamReader.read();
            while(data != -1) {
                jsonData = jsonData+(char)data;
                data = inputStreamReader.read();
            }
            return jsonData;
        }
    }

    public Parser dataConverter(ParserJson rawData) throws ParseException {
        Parser data = new Parser();
        data.setPersons(personListFilter(rawData));
        data.setFirestations(fireStationListFilter(rawData));
        data.setMedicalrecords(medicalRecordListFilter(rawData));
        return data;
    }

    private List<MedicalRecord> medicalRecordListFilter(ParserJson rawData) {
        List<MedicalRecord> medicalRecordList = new ArrayList<MedicalRecord>();
        Iterator iterator = rawData.getMedicalrecords().iterator();

        int i = 0;
        while (iterator.hasNext()){
            MedicalRecord medicalRecord = new MedicalRecord();

            medicalRecord.setMedications(rawData.getMedicalrecords().get(i).getMedications());
            medicalRecord.setAllergies(rawData.getMedicalrecords().get(i).getAllergies());
            medicalRecordList.add(medicalRecord);
            i++;
            iterator.next();
        }
        return medicalRecordList;
    }

    private List<FireStation> fireStationListFilter(ParserJson rawData) {
        List<FireStation> fireStationList = new ArrayList<FireStation>();
        Iterator iterator = rawData.getFirestations().iterator();

        int i = 0;
        while (iterator.hasNext()){
            int stationInInt = Integer.parseInt(rawData.getFirestations().get(i).getStation());
            FireStation fireStation = new FireStation();

            fireStation.setAddress(rawData.getFirestations().get(i).getAddress());
            fireStation.setStation(stationInInt);
            fireStationList.add(fireStation);
            i++;
            iterator.next();
        }
        return fireStationList;
    }

    private List<Person> personListFilter(ParserJson rawData) throws ParseException {

        String sDate1;
        SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy");
        Date date1;
        List<Person> personList = new ArrayList<Person>();
        Iterator iterator = rawData.getPersons().iterator();

        int i = 0;

        while(iterator.hasNext()){
            sDate1 = rawData.getMedicalrecords().get(i).getBirthdate();
            date1 = formatter1.parse(sDate1);
            Person person = new Person();

            person.setFirstName(rawData.getPersons().get(i).getFirstName());
            person.setLastName(rawData.getPersons().get(i).getLastName());
            person.setBirthdate(date1);
            person.setAddress(rawData.getPersons().get(i).getAddress());
            person.setCity(rawData.getPersons().get(i).getCity());
            person.setZip(rawData.getPersons().get(i).getZip());
            person.setPhone(rawData.getPersons().get(i).getPhone());
            person.setEmail(rawData.getPersons().get(i).getEmail());
            personList.add(person);
            i++;
            iterator.next();
        }
        return personList;
    }
}
