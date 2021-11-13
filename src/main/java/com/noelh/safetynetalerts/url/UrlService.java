package com.noelh.safetynetalerts.url;

import com.noelh.safetynetalerts.firestation.FireStation;
import com.noelh.safetynetalerts.firestation.FireStationService;
import com.noelh.safetynetalerts.person.Person;
import com.noelh.safetynetalerts.person.PersonService;
import com.noelh.safetynetalerts.person.dto.PersonSimplifiedResponse;
import com.noelh.safetynetalerts.url.dto.FireStationUrlResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
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

    public FireStationUrlResponse getPersonsListByStationIdWithoutStream(Long stationId) throws NoSuchElementException {
        FireStation fireStation = fireStationService.getFireStation(stationId);

        List<String> stationAddress = fireStation.getAddress();
        List<Person> personList = personService.getPersons();

        List<Person> urlPersonListResponse = new ArrayList<>();
        int nbAdult = 0;
        int nbChild = 0;
        for (Person p:personList) {
            if (stationAddress.contains(p.getAddress())){
                 LocalDate birthdayDate = new java.sql.Date(p.getBirthdate().getTime()).toLocalDate();
                Period period = Period.between(birthdayDate, LocalDate.now());
                if (period.getYears() <= 18){
                    nbChild++;
                } else {
                    nbAdult++;
                }
                urlPersonListResponse.add(p);
            }
        }

        List<PersonSimplifiedResponse> urlPersonSimplfiedListResponse = new ArrayList<>();
        for (Person p: urlPersonListResponse) {
            PersonSimplifiedResponse personSimplifiedResponse = new PersonSimplifiedResponse();
            personSimplifiedResponse.setFirstName(p.getFirstName());
            personSimplifiedResponse.setLastName(p.getLastName());
            personSimplifiedResponse.setAddress(p.getAddress());
            personSimplifiedResponse.setPhone(p.getPhone());
            urlPersonSimplfiedListResponse.add(personSimplifiedResponse);
        }

        FireStationUrlResponse fireStationUrlResponse = new FireStationUrlResponse();
        fireStationUrlResponse.setUrlPersonList(urlPersonSimplfiedListResponse);
        fireStationUrlResponse.setNbAdult(nbAdult);
        fireStationUrlResponse.setNbChild(nbChild);

        return fireStationUrlResponse;
    }

    public FireStationUrlResponse getPersonsListByStationId(Long stationId) throws NoSuchElementException {

        List<String> stationAddress = fireStationService.getFireStation(stationId).getAddress();
        List<Person> personSimplifiedResponseList = personService.getPersons().stream()
                .filter(person -> stationAddress.contains(person.getAddress()))
                .collect(Collectors.toList());

        int nbAdult = 0;
        int nbChild = 0;
        for (Person p: personSimplifiedResponseList) {
            LocalDate birthdayDate = new java.sql.Date(p.getBirthdate().getTime()).toLocalDate();
            Period period = Period.between(birthdayDate, LocalDate.now());
            if (period.getYears() <= 18){
                nbChild++;
            } else {
                nbAdult++;
            }
        }

        List<PersonSimplifiedResponse> urlPersonSimplfiedListResponse = new ArrayList<>();
        for (Person p: personSimplifiedResponseList) {
            PersonSimplifiedResponse personSimplifiedResponse = new PersonSimplifiedResponse();
            personSimplifiedResponse.setFirstName(p.getFirstName());
            personSimplifiedResponse.setLastName(p.getLastName());
            personSimplifiedResponse.setAddress(p.getAddress());
            personSimplifiedResponse.setPhone(p.getPhone());
            urlPersonSimplfiedListResponse.add(personSimplifiedResponse);
        }

        FireStationUrlResponse fireStationUrlResponse = new FireStationUrlResponse();
        fireStationUrlResponse.setUrlPersonList(urlPersonSimplfiedListResponse);
        fireStationUrlResponse.setNbAdult(nbAdult);
        fireStationUrlResponse.setNbChild(nbChild);

        return fireStationUrlResponse;
    }
}
