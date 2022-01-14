package com.noelh.safetynetalerts.json;

import com.noelh.safetynetalerts.firestation.FireStation;
import com.noelh.safetynetalerts.firestation.FireStationJson;
import com.noelh.safetynetalerts.medicalrecord.MedicalRecord;
import com.noelh.safetynetalerts.medicalrecord.MedicalRecordJson;
import com.noelh.safetynetalerts.parser.Parser;
import com.noelh.safetynetalerts.parser.ParserJson;
import com.noelh.safetynetalerts.person.Person;
import com.noelh.safetynetalerts.person.PersonJson;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Manage json data
 */
public class JsonManagement {

    private String jsonData="";

    /**
     * Get a json data
     * @return a String who contains the json data
     */
    public String getJsonData() {
        return jsonData;
    }

    /**
     * Load a json data
     * @param jsonDataFile is th json data who'll be loaded
     * @throws IOException if the data can't be read
     */
    public void loadJsonData(Resource jsonDataFile) throws IOException {
        try(InputStreamReader inputStreamReader = new InputStreamReader(jsonDataFile.getInputStream())){
            int data = inputStreamReader.read();
            while(data != -1) {
                jsonData = jsonData+(char)data;
                data = inputStreamReader.read();
            }
        }
    }

    /**
     * Convert the data
     * @param rawData is the original data
     * @return the modified data
     * @throws ParseException if the data can't be parsed
     */
    public Parser dataConverter(ParserJson rawData) throws ParseException {
        Parser data = new Parser();
        data.setPersons(personListFilter2(rawData));
        data.setFireStations(fireStationListFilter2(rawData));
        return data;
    }

    /**
     * Sort a list of firestation
     * @param filteredFireStationList is the list of firestation
     * @return a sorted list of firestation
     */
    private List<FireStation> sortFireStationList(List<FireStation> filteredFireStationList) {
        List<FireStation> sortedFireStationList = new ArrayList<>();
        int station;
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
        return new ArrayList<>(hashSet);
    }

    /**
     *  Filter a list of firestation
     * @param rawData is an original json data
     * @return a filtered list of firestation
     */
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

    /**
     * Filter a list of person
     * @param rawData is an original json data
     * @return a filtered list of person
     * @throws ParseException if the data can't be parsed
     */
    private List<Person> personListFilter2(ParserJson rawData) throws ParseException {

        String sDate1;
        SimpleDateFormat formatter1 = new SimpleDateFormat("MM/dd/yyyy");
        Date date1 = null;
        List<Person> personList = new ArrayList<>();
        Iterator<PersonJson> iterator = rawData.getPersons().iterator();

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

    /**
     * Find a medical record by a firstame and lastname
     * @param rawData is an original json data
     * @param firstName is the firstname who'll be looked for
     * @param lastName is the lastname who'll be looked for
     * @return a medical record
     */
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

    /**
     * Find a birthday by a firstname and lastname
     * @param rawData is an original json data
     * @param firstName is the firstname who'll be looked for
     * @param lastName is the lastname who'll be looked for
     * @return a String of the birthday
     */
    private String findBirthdayByFirstNameAndLastName(ParserJson rawData, String firstName, String lastName) {
        for (MedicalRecordJson elem : rawData.getMedicalrecords()) {
            if (elem.getFirstName().equals(firstName) && elem.getLastName().equals(lastName)){
                return elem.getBirthdate();
            }
        }
        return null;
    }
}
