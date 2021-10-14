package com.noelh.safetynetalerts.json;

import com.noelh.safetynetalerts.json.jsonparser.*;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

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
        data.setPersons(personListFilter2(rawData));
        data.setFirestations(fireStationListFilter2(rawData));
        //data.setFirestations(fireStationListFilter2StreamAlternative(rawData.getFirestations()));
        //data.setMedicalrecords(medicalRecordListFilter(rawData));
        return data;
    }

    private List<FireStation> fireStationListFilter2Alternative(List<FireStationJson> rawData) {
        // Passer d'une liste d'associations station/address à une liste de stations unique
        List<FireStation> fireStations = new ArrayList<>();
        for (FireStationJson elemJson : rawData) {
            // Vérifier que la station de elem existe dans fireStations
            boolean alreadyHere = false;
            for (FireStation elem : fireStations) {
                if (elem.getStation() == Integer.parseInt(elemJson.getStation())) {
                    alreadyHere = true;
                }
            }
            if (!alreadyHere) {
                FireStation f = new FireStation();
                f.setStation(Integer.parseInt(elemJson.getStation()));
                f.setAddress(new ArrayList<>());
                fireStations.add(f);
            }
        }
        // Remplir les stations unique des addresses associées
        for (FireStation fireStation : fireStations) {
            for (FireStationJson fireStationJson : rawData) {
                if (Integer.parseInt(fireStationJson.getStation()) == fireStation.getStation()) {
                    fireStation.getAddress().add(fireStationJson.getAddress());
                }
            }
        }
        return fireStations;
    }

    private List<FireStation> fireStationListFilter2StreamAlternative(List<FireStationJson> rawData) {
        // Passer d'une liste d'associations station/address à une liste de stations unique
        List<FireStation> fireStations = rawData
                .stream()
                .map(fireStationJson -> {
                    FireStation f = new FireStation();
                    f.setStation(Integer.parseInt(fireStationJson.getStation()));
                    return f;
                })
                .distinct()
                .collect(Collectors.toList());
        // Remplir les stations unique des addresses associées
        fireStations.forEach(fireStation -> {
            fireStation.setAddress(rawData
                    .stream()
                    .filter(fireStationJson -> Integer.parseInt(fireStationJson.getStation()) == fireStation.getStation())
                    .map(fireStationJson -> fireStationJson.getAddress())
                    .distinct()
                    .collect(Collectors.toList()));
        });
        return fireStations;
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

/*
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
    }*/

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
