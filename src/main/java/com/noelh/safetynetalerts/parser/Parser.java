package com.noelh.safetynetalerts.parser;

import com.noelh.safetynetalerts.firestation.FireStation;
import com.noelh.safetynetalerts.person.Person;
import lombok.Data;

import java.util.List;

@Data
public class Parser {

    private List<Person> persons;

    private List<FireStation> fireStations;
}
