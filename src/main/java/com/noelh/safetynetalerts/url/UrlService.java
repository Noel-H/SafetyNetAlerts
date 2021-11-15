package com.noelh.safetynetalerts.url;

import com.noelh.safetynetalerts.firestation.FireStationService;
import com.noelh.safetynetalerts.person.Person;
import com.noelh.safetynetalerts.person.PersonService;
import com.noelh.safetynetalerts.person.dto.PersonSimplifiedResponse;
import com.noelh.safetynetalerts.url.dto.ChildAlertUrlResponse;
import com.noelh.safetynetalerts.url.dto.FireStationUrlResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
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

    public List<ChildAlertUrlResponse> getChildListByAddress(String address) {
        return personService.getPersons().stream()
                .filter(person -> person.getAddress().equals(address))
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
}
