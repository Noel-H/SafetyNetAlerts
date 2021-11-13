package com.noelh.safetynetalerts.url.dto;

import com.noelh.safetynetalerts.person.Person;
import lombok.Data;

import javax.persistence.ElementCollection;
import java.util.List;

@Data
public class FireStationUrlResponse {

    private int nbAdult;

    private int nbChild;

    @ElementCollection
    private List<Person> urlPersonList;
}
