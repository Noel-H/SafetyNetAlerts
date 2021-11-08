package com.noelh.safetynetalerts.json;

import com.noelh.safetynetalerts.json.jsonparser.*;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class JsonManagement {

    private String jsonData="";

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
        data.setPersons(personListFilter2(rawData));
        data.setFireStations(fireStationListFilter2(rawData));
        return data;
    }

    private List<FireStation> sortFireStationList(List<FireStation> filteredFireStationList) {
        List<FireStation> sortedFireStationList = new ArrayList<>();
        int station = 0;
        for (FireStation elem : filteredFireStationList) {
            FireStation fireStation = new FireStation();
            List<String> fireStationAdressList = new ArrayList<>();
            station = elem.getStation();
            fireStation.setStation(station);
            for (FireStation elem2 : filteredFireStationList) {
                if (station == elem2.getStation()){
                    fireStationAdressList.add(elem2.getAddress().get(0));
                }
            }
            LinkedHashSet<String> hashSetAdress = new LinkedHashSet<>(fireStationAdressList);
            List<String> fireStationAdressListWithoutDuplicate = new ArrayList<>(hashSetAdress);
            fireStation.setAddress(fireStationAdressListWithoutDuplicate);
            sortedFireStationList.add(fireStation);
        }
        LinkedHashSet<FireStation> hashSet = new LinkedHashSet<>(sortedFireStationList);
        List<FireStation> sortedFireStationListWithoutDuplicate = new ArrayList<>(hashSet);
        return sortedFireStationListWithoutDuplicate;
    }

    private List<FireStation> fireStationListFilter2(ParserJson rawData) {
        List<FireStation> fireStationList = new ArrayList<>();
        for (FireStationJson elem: rawData.getFirestations()) {
            FireStation fireStation = new FireStation();
            List<String> fireStationAdressList = new ArrayList<>();
            int stationInInt = Integer.parseInt(elem.getStation());
            fireStationAdressList.add(elem.getAddress());
            fireStation.setStation(stationInInt);
            fireStation.setAddress(fireStationAdressList);
            fireStationList.add(fireStation);
        }
        return sortFireStationList(fireStationList);
    }

    private List<Person> personListFilter2(ParserJson rawData) throws ParseException {

        String sDate1;
        SimpleDateFormat formatter1 = new SimpleDateFormat("MM/dd/yyyy");
        Date date1 = null;
        List<Person> personList = new ArrayList<Person>();
        Iterator iterator = rawData.getPersons().iterator();

        int i = 0;

        while(iterator.hasNext()){
            Person person = new Person();
            String FirstName=rawData.getPersons().get(i).getFirstName();
            String LastName=rawData.getPersons().get(i).getLastName();
            sDate1 = findBirthdayByFirstNameAndLastName(rawData, FirstName, LastName);
            if(sDate1 != null){
                date1 = formatter1.parse(sDate1);
            }

            person.setFirstName(FirstName);
            person.setLastName(LastName);
            person.setBirthdate(date1);
            person.setAddress(rawData.getPersons().get(i).getAddress());
            person.setCity(rawData.getPersons().get(i).getCity());
            person.setZip(rawData.getPersons().get(i).getZip());
            person.setPhone(rawData.getPersons().get(i).getPhone());
            person.setEmail(rawData.getPersons().get(i).getEmail());
            person.setMedicalRecord(findMedicalRecordByFirstNameAndLastName(rawData, FirstName, LastName));
            personList.add(person);
            i++;
            iterator.next();
        }
        return personList;
    }

    private MedicalRecord findMedicalRecordByFirstNameAndLastName(ParserJson rawData, String firstName, String lastName) {
        MedicalRecord medicalRecord = new MedicalRecord();
        for (MedicalRecordJson elem : rawData.getMedicalrecords()) {
            if (elem.getFirstName().equals(firstName) && elem.getLastName().equals(lastName)){
                medicalRecord.setMedications(elem.getMedications());
                medicalRecord.setAllergies(elem.getAllergies());
                return medicalRecord;
            }
        }
        return null;
    }

    private String findBirthdayByFirstNameAndLastName(ParserJson rawData, String firstName, String lastName) {
        for (MedicalRecordJson elem : rawData.getMedicalrecords()) {
            if (elem.getFirstName().equals(firstName) && elem.getLastName().equals(lastName)){
                return elem.getBirthdate();
            }
        }
        return null;
    }
}
