package com.noelh.safetynetalerts.url.dto;

import com.noelh.safetynetalerts.person.Person;
import lombok.Data;

import javax.persistence.ElementCollection;
import java.util.List;

@Data
public class ChildAlertUrlResponse {

    private String firstName;
    private String lastName;
    private int age;

    @ElementCollection
    private List<Person> householdMember;
}
