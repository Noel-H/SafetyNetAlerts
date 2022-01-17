package com.noelh.safetynetalerts.url;

import com.noelh.safetynetalerts.firestation.FireStation;
import com.noelh.safetynetalerts.firestation.FireStationService;
import com.noelh.safetynetalerts.firestation.dto.FireStationNumberResponse;
import com.noelh.safetynetalerts.person.Person;
import com.noelh.safetynetalerts.person.PersonService;
import com.noelh.safetynetalerts.person.dto.PersonSimplifiedResponse;
import com.noelh.safetynetalerts.url.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Contains URL related feature
 */
@Slf4j
@Service
public class UrlService {

    @Autowired
    private FireStationService fireStationService;

    @Autowired
    private PersonService personService;

    /**
     * Get a list of person by a station id
     * @param stationId is the station id
     * @return the number of adult and child and then a list of person who have
     * the same address as one of the station list address in the same object
     * @throws NoSuchElementException if the station id is incorrect
     */
    public FireStationUrlResponse getPersonsListByStationId(Long stationId) throws NoSuchElementException {
        List<String> stationAddress = fireStationService.getFireStation(stationId).getAddress();
        List<Person> personListForThisStation = personService.getPersons().stream()
                .filter(person -> stationAddress.contains(person.getAddress()))
                .collect(Collectors.toList());

        int nbAdult = (int)personListForThisStation.stream()
                .filter(person -> isAnAdult(getAgeByBirthdate(person.getBirthdate())))
                .count();
        int nbChild = (int)personListForThisStation.stream()
                .filter(person -> !isAnAdult(getAgeByBirthdate(person.getBirthdate())))
                .count();

        List<PersonSimplifiedResponse> result = personListForThisStation.stream()
                .map(person -> new PersonSimplifiedResponse(
                        person.getFirstName(),
                        person.getLastName(),
                        person.getAddress(),
                        person.getPhone()))
                .collect(Collectors.toList());

        return new FireStationUrlResponse(nbAdult,nbChild,result);
    }

    /**
     * Get age by a birthday date
     * @param birthdayDate is a birthdate
     * @return today age
     */
    private int getAgeByBirthdate(Date birthdayDate){
        LocalDate birthdayLocalDate = new java.sql.Date(birthdayDate.getTime()).toLocalDate();
        Period period = Period.between(birthdayLocalDate, LocalDate.now());
        return period.getYears();
    }

    /**
     * Check if an age > 18
     * @param age is the age
     * @return true if age > 18
     */
    private boolean isAnAdult(int age){
        return age > 18;
    }

    /**
     * Get a list of person under 18 years old by an address
     * @param address is an address
     * @return a list of person under 18 years old
     * @throws NoSuchElementException if the address is not found
     */
    public List<ChildAlertUrlResponse> getChildListByAddress(String address) throws NoSuchElementException{
        List<Person> personList = personService.getPersons().stream()
                .filter(person -> person.getAddress().equals(address))
                .collect(Collectors.toList());

        if (personList.isEmpty()){
            throw new NoSuchElementException("Address " + address +" not found");
        }

        return personList.stream()
                .filter(person -> !isAnAdult(getAgeByBirthdate(person.getBirthdate())))
                .map(person -> new ChildAlertUrlResponse(
                        person.getFirstName(),
                        person.getLastName(),
                        getAgeByBirthdate(person.getBirthdate()),
                        personService.getPersons().stream()
                                .filter(person1 -> person1.getAddress().equals(address))
                                .collect(Collectors.toList())))
                .collect(Collectors.toList());
    }

    /**
     * Get phones number by station id
     * @param stationId is a station id
     * @return a list of phone number
     * @throws NoSuchElementException if the station id is not found
     */
    public List<PhoneUrlResponse> getPhonesByStationId(Long stationId) throws NoSuchElementException{
        return personService.getPersons().stream()
                .filter(person -> fireStationService.getFireStation(stationId).getAddress()
                        .contains(person.getAddress()))
                .map(person -> new PhoneUrlResponse(
                        person.getPhone()))
                .collect(Collectors.toList());
    }

    /**
     * Get a person and a station number by an address
     * @param address is the address
     * @return person info and station number
     */
    public FireUrlWithStationNumberResponse getPersonAndStationNumberByAddress(String address) {
        List<Person> personList = personService.getPersons().stream()
                .filter(person -> person.getAddress().equals(address))
                .collect(Collectors.toList());

        if (personList.isEmpty()){
            throw new NoSuchElementException("Address " + address +" not found");
        }

        List<FireUrlResponse> fireUrlResponseList = personService.getPersons().stream()
                .filter(person -> person.getAddress().equals(address))
                .map(person -> new FireUrlResponse(
                        person.getFirstName(),
                        person.getLastName(),
                        person.getPhone(),
                        getAgeByBirthdate(person.getBirthdate()),
                        person.getMedicalRecord()))
                .collect(Collectors.toList());

        List<FireStationNumberResponse> stations = fireStationService.getFireStations().stream()
                .filter(fireStation -> fireStation.getAddress().contains(address))
                .map(fireStation -> new FireStationNumberResponse(
                        fireStation.getStation()))
                .collect(Collectors.toList());

        FireUrlWithStationNumberResponse fireUrlWithStationNumberResponse = new FireUrlWithStationNumberResponse();
        fireUrlWithStationNumberResponse.setStations(stations);
        fireUrlWithStationNumberResponse.setFireUrlResponseList(fireUrlResponseList);
        return fireUrlWithStationNumberResponse;
    }

    /**
     * Get persons grouped by an address
     * @param stationIdList is a list of station id
     * @return persons info grouped by address
     */
    public Map<String, List<FloodStationUrlResponse>> getPersonsGroupedByAddress(List<Long> stationIdList) {
        List<FireStation> fireStationList = new ArrayList<>();

        for (long l : stationIdList) {
            fireStationList.add(fireStationService.getFireStation(l));
        }
        List<String> addressList = fireStationList.stream()
                .flatMap(fireStation -> fireStation.getAddress().stream())
                .collect(Collectors.toList());

        return personService.getPersons().stream()
                .filter(person -> addressList.contains(person.getAddress()))
                .map(person -> new FloodStationUrlResponse(
                        person.getAddress(),
                        person.getFirstName(),
                        person.getLastName(),
                        person.getPhone(),
                        getAgeByBirthdate(person.getBirthdate()),
                        person.getMedicalRecord()))
                .collect(Collectors.groupingBy(FloodStationUrlResponse::getAddress));
    }

    /**
     * Get person info by a firstname and lastname
     * @param firstName is the person firstname
     * @param lastName is the person lastname
     * @return a list of person with the same firstname and lastname
     */
    public List<PersonInfoUrlResponse> getPersonInfoByFirstNameAndLastName(String firstName, String lastName) {
        List<PersonInfoUrlResponse> personInfoUrlResponseList = personService.getPersons().stream()
                .filter(person -> person.getFirstName().equals(firstName)&& person.getLastName().equals(lastName))
                .map(person -> new PersonInfoUrlResponse(
                        person.getFirstName(),
                        person.getLastName(),
                        person.getAddress(),
                        getAgeByBirthdate(person.getBirthdate()),
                        person.getEmail(),
                        person.getMedicalRecord()))
                .collect(Collectors.toList());

        if (personInfoUrlResponseList.isEmpty()){
            throw new NoSuchElementException("FirstName " +firstName+ " and lastName "+lastName+ " not found");
        }

        return personInfoUrlResponseList;
    }

    /**
     * Get mail address by city name
     * @param city is the city
     * @return a list of mail
     */
    public List<CommunityEmailUrlResponse> getMailsByCity(String city) {
        List<CommunityEmailUrlResponse> communityEmailUrlResponseList = personService.getPersons().stream()
                .filter(person -> person.getCity().equals(city))
                .map(person -> new CommunityEmailUrlResponse(
                        person.getEmail()))
                .collect(Collectors.toList());

        if (communityEmailUrlResponseList.isEmpty()){
            throw new NoSuchElementException("No result for "+city);
        }
        return communityEmailUrlResponseList;
    }
}
