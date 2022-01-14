package com.noelh.safetynetalerts.person;

import com.noelh.safetynetalerts.medicalrecord.MedicalRecord;
import com.noelh.safetynetalerts.person.dto.PersonAddRequest;
import com.noelh.safetynetalerts.person.dto.PersonUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Get persons related feature
 */
@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    /**
     * Add a list persons
     * @param persons is a list of person
     * @return will save the data
     */
    public List<Person> addPersons(List<Person> persons) {
        return personRepository.saveAll(persons);
    }

    /**
     * Get a list of person
     * @return a list of person
     */
    public List<Person> getPersons(){
        return personRepository.findAll();
    }

    /**
     * Get a person by his id
     * @param id is the id of a person
     * @return a Person
     * @throws NoSuchElementException if the id don't exist
     */
    public Person getPerson(long id) throws NoSuchElementException {
        return personRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Person id " + id + " not found"));
    }

    /**
     * Get a person by his firstname and his lastname
     * @param firstName is the person firstname
     * @param lastName is the person lasttname
     * @return a Person or a NoSuchElementException if the person is not found
     */
    public Person getPerson(String firstName, String lastName) {
        return personRepository.findByFirstNameAndLastName(firstName, lastName)
                .orElseThrow(() -> new NoSuchElementException("There is no person called " + firstName + " " + lastName));
    }

    /**
     * Add a person
     * @param person is the person
     * @return a Person and save it to the personRepository
     */
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
                .medicalRecord(MedicalRecord.builder()
                        .medications(new ArrayList<>())
                        .allergies(new ArrayList<>())
                        .build())
                .build());
    }

    /**
     * Update a person
     * @param person is the person who will be updated
     * @param updatedPerson is the update of that person
     * @return a Person and save it to the personRepository
     */
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
                .medicalRecord(person.getMedicalRecord())
                .build());
    }

    /**
     * Delete a person
     * @param person is the person
     * @return the person
     */
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
                .medicalRecord(MedicalRecord.builder()
                        .id(person.getMedicalRecord().getId())
                        .medications(new ArrayList<>(person.getMedicalRecord().getMedications()))
                        .allergies(new ArrayList<>(person.getMedicalRecord().getAllergies()))
                        .build())
                .build();
        personRepository.delete(person);
        return result;
    }
}
