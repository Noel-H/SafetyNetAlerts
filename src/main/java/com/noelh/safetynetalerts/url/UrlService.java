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

@Slf4j
@Service
public class UrlService {

    @Autowired
    private FireStationService fireStationService;

    @Autowired
    private PersonService personService;

//    public FireStationUrlResponse getPersonsListByStationIdWithoutStream(Long stationId) throws NoSuchElementException {
//        FireStation fireStation = fireStationService.getFireStation(stationId);
//
//        List<String> stationAddress = fireStation.getAddress();
//        List<Person> personList = personService.getPersons();
//
//        List<Person> urlPersonListResponse = new ArrayList<>();
//        int nbAdult = 0;
//        int nbChild = 0;
//        for (Person p:personList) {
//            if (stationAddress.contains(p.getAddress())){
//                 LocalDate birthdayDate = new java.sql.Date(p.getBirthdate().getTime()).toLocalDate();
//                Period period = Period.between(birthdayDate, LocalDate.now());
//                if (period.getYears() <= 18){
//                    nbChild++;
//                } else {
//                    nbAdult++;
//                }
//                urlPersonListResponse.add(p);
//            }
//        }
//
//        List<PersonSimplifiedResponse> urlPersonSimplfiedListResponse = new ArrayList<>();
//        for (Person p: urlPersonListResponse) {
//            PersonSimplifiedResponse personSimplifiedResponse = new PersonSimplifiedResponse();
//            personSimplifiedResponse.setFirstName(p.getFirstName());
//            personSimplifiedResponse.setLastName(p.getLastName());
//            personSimplifiedResponse.setAddress(p.getAddress());
//            personSimplifiedResponse.setPhone(p.getPhone());
//            urlPersonSimplfiedListResponse.add(personSimplifiedResponse);
//        }
//
//        FireStationUrlResponse fireStationUrlResponse = new FireStationUrlResponse();
//        fireStationUrlResponse.setUrlPersonList(urlPersonSimplfiedListResponse);
//        fireStationUrlResponse.setNbAdult(nbAdult);
//        fireStationUrlResponse.setNbChild(nbChild);
//
//        return fireStationUrlResponse;
//    }

//    public FireStationUrlResponse getPersonsListByStationIdOriginal(Long stationId) throws NoSuchElementException {
//
//        List<String> stationAddress = fireStationService.getFireStation(stationId).getAddress();
//        List<Person> personSimplifiedResponseList = personService.getPersons().stream()
//                .filter(person -> stationAddress.contains(person.getAddress()))
//                .collect(Collectors.toList());
//
//        int nbAdult = 0;
//        int nbChild = 0;
//        for (Person p: personSimplifiedResponseList) {
//            LocalDate birthdayDate = new java.sql.Date(p.getBirthdate().getTime()).toLocalDate();
//            Period period = Period.between(birthdayDate, LocalDate.now());
//            if (period.getYears() <= 18){
//                nbChild++;
//            } else {
//                nbAdult++;
//            }
//        }
//
//        List<PersonSimplifiedResponse> urlPersonSimplfiedListResponse = new ArrayList<>();
//        for (Person p: personSimplifiedResponseList) {
//            PersonSimplifiedResponse personSimplifiedResponse = new PersonSimplifiedResponse();
//            personSimplifiedResponse.setFirstName(p.getFirstName());
//            personSimplifiedResponse.setLastName(p.getLastName());
//            personSimplifiedResponse.setAddress(p.getAddress());
//            personSimplifiedResponse.setPhone(p.getPhone());
//            urlPersonSimplfiedListResponse.add(personSimplifiedResponse);
//        }
//
//        FireStationUrlResponse fireStationUrlResponse = new FireStationUrlResponse();
//        fireStationUrlResponse.setUrlPersonList(urlPersonSimplfiedListResponse);
//        fireStationUrlResponse.setNbAdult(nbAdult);
//        fireStationUrlResponse.setNbChild(nbChild);
//
//        return fireStationUrlResponse;
//    }

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

    private int getAgeByBirthdate(Date birthdayDate){
        LocalDate birthdayLocalDate = new java.sql.Date(birthdayDate.getTime()).toLocalDate();
        Period period = Period.between(birthdayLocalDate, LocalDate.now());
        return period.getYears();
    }

    private boolean isAnAdult(int age){
        return age > 18;
    }

//    public List<ChildAlertUrlResponse> getChildListByAddress(String address) {
//        List<Person> personList = personService.getPersons().stream()
//                .filter(person -> address.equals(person.getAddress()))
//                .collect(Collectors.toList());
//
//        List<Person> childList = new ArrayList<>();
//        for (Person p: personList) {
//            LocalDate birthdayDate = new java.sql.Date(p.getBirthdate().getTime()).toLocalDate();
//            Period period = Period.between(birthdayDate, LocalDate.now());
//            if (period.getYears() <= 18){
//                childList.add(p);
//            }
//        }
//
//        List<ChildAlertUrlResponse> childAlertUrlResponseList = new ArrayList<>();
//        for (Person p: childList) {
//            ChildAlertUrlResponse childAlertUrlResponse = new ChildAlertUrlResponse();
//            childAlertUrlResponse.setFirstName(p.getFirstName());
//            childAlertUrlResponse.setLastName(p.getLastName());
//            LocalDate birthdayDate = new java.sql.Date(p.getBirthdate().getTime()).toLocalDate();
//            Period period = Period.between(birthdayDate, LocalDate.now());
//            childAlertUrlResponse.setAge(period.getYears());
//            childAlertUrlResponse.setHouseholdMember(personService.getPersons().stream()
//                            .filter(person -> person.getAddress().equals(p.getAddress()))
//                    .collect(Collectors.toList()));
//            childAlertUrlResponseList.add(childAlertUrlResponse);
//        }
//
//        return childAlertUrlResponseList;
//    }

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

    public List<PhoneUrlResponse> getPhonesByStationId(Long stationId) throws NoSuchElementException{
        return personService.getPersons().stream()
                .filter(person -> fireStationService.getFireStation(stationId).getAddress()
                        .contains(person.getAddress()))
                .map(person -> new PhoneUrlResponse(
                        person.getPhone()))
                .collect(Collectors.toList());
    }

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

    public Map<String, List<FloodStationUrlResponse>> getPersonsGroupedByAddress(List<Long> stationIdList) {
        List<FireStation> fireStationList = new ArrayList<>();

        for (long l : stationIdList) {
            fireStationList.add(fireStationService.getFireStation(l));
        }
        List<String> addressList = fireStationList.stream()
                .flatMap(fireStation -> fireStation.getAddress().stream())
                .collect(Collectors.toList());

        Map<String, List<FloodStationUrlResponse>> floodStationUrlResponseList = personService.getPersons().stream()
                .filter(person -> addressList.contains(person.getAddress()))
                .map(person -> new FloodStationUrlResponse(
                        person.getAddress(),
                        person.getFirstName(),
                        person.getLastName(),
                        person.getPhone(),
                        getAgeByBirthdate(person.getBirthdate()),
                        person.getMedicalRecord()))
                .collect(Collectors.groupingBy(FloodStationUrlResponse::getAddress));
        return floodStationUrlResponseList;
    }

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
}
