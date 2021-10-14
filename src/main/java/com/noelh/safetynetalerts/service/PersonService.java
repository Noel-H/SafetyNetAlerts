package com.noelh.safetynetalerts.service;

import com.noelh.safetynetalerts.json.jsonparser.MedicalRecord;
import com.noelh.safetynetalerts.json.jsonparser.Person;
import com.noelh.safetynetalerts.json.jsonparser.PersonRepository;
import com.noelh.safetynetalerts.web.dto.PersonAddRequest;
import com.noelh.safetynetalerts.web.dto.PersonUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public List<Person> addPersons(List<Person> persons) {
        return personRepository.saveAll(persons);
    }

    public List<Person> getPersons(){
        return personRepository.findAll();
    }

    public Person getPerson(long id) throws NoSuchElementException {
        return personRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Person id " + id + " not found"));
    }

    public Person getPerson(String firstName, String lastName) {
        return personRepository.findByFirstNameAndLastName(firstName, lastName)
                .orElseThrow(() -> new NoSuchElementException("There is no person called " + firstName + " " + lastName));
    }

    public Person addPerson(PersonAddRequest person) {
        return personRepository.save(Person.builder()
                .firstName(person.getFirstName())
                .lastName(person.getLastName())
                .birthdate(person.getBirthdate())
                .address(person.getAddress())
                .city(person.getCity())
                .zip(person.getZip())
                .phone(person.getPhone())
                .email(person.getEmail())
                .build());
    }

    public Person updatePerson(Person person, PersonUpdateRequest updatedPerson) {
        return personRepository.save(Person.builder()
                .id(person.getId())
                .firstName(person.getFirstName())
                .lastName(person.getLastName())
                .birthdate(updatedPerson.getBirthdate() == null ? person.getBirthdate() : updatedPerson.getBirthdate())
                .address(updatedPerson.getAddress() == null ? person.getAddress() : updatedPerson.getAddress())
                .city(updatedPerson.getCity() == null ? person.getCity() : updatedPerson.getCity())
                .zip(updatedPerson.getZip() == null ? person.getZip() : updatedPerson.getZip())
                .phone(updatedPerson.getPhone() == null ? person.getPhone() :updatedPerson.getPhone())
                .email(updatedPerson.getEmail() == null ? person.getEmail() : updatedPerson.getEmail())
                .build());
    }

    public Person deletePerson(Person person) {
        Person result = Person.builder()
                .id(person.getId())
                .firstName(person.getFirstName())
                .lastName(person.getLastName())
                .birthdate(person.getBirthdate())
                .address(person.getAddress())
                .city(person.getCity())
                .zip(person.getZip())
                .phone(person.getPhone())
                .email(person.getEmail())
                .build();
        personRepository.delete(person);
        return result;
    }

}
