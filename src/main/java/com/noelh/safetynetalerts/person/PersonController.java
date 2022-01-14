package com.noelh.safetynetalerts.person;

import com.noelh.safetynetalerts.person.dto.PersonAddRequest;
import com.noelh.safetynetalerts.person.dto.PersonDeleteRequest;
import com.noelh.safetynetalerts.person.dto.PersonUpdateRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Contains person related possible response
 */
@Slf4j
@RestController
@RequestMapping("/persons")
public class PersonController {
    @Autowired
    private PersonService personService;

    /**
     * Get a list of person
     * @return a list of person
     */
    @GetMapping("")
    public List<Person> getPersons(){
        log.info("GET /persons");
        return personService.getPersons();
    }

    /**
     * Get a person by his id
     * @param id is the person id
     * @return a response entity ok or not found if a NoSuchElementException is caught
     */
    @GetMapping("/{id}")
    public ResponseEntity<Person> getPerson(@PathVariable("id") Long id){
        log.info("GET /persons/" + id);
        try {
            return ResponseEntity.ok(personService.getPerson(id));
        } catch (NoSuchElementException e) {
            log.error("GET /persons/" + id + " - ERROR : " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Add a person
     * @param person is the person
     * @return a response entity ok
     */
    @PostMapping("")
    public ResponseEntity<Person> addPerson(@RequestBody PersonAddRequest person){
        log.info("POST /persons/ : " + person.getFirstName() + " " + person.getLastName());
        return ResponseEntity.ok(personService.addPerson(person));
    }

    /**
     * Update a person
     * @param id is the person id
     * @param person is the updated person info
     * @return a response entity ok or not found if a NoSuchElementException is caught
     */
    @PutMapping("/{id}")
    public ResponseEntity<Person> updatePerson(@PathVariable("id") Long id,@RequestBody PersonUpdateRequest person){
        log.info("PUT /persons/" + id);
        try {
            return ResponseEntity.ok(personService.updatePerson(personService.getPerson(id), person));
        } catch (NoSuchElementException e) {
            log.error("PUT /persons/" + id + " - ERROR : " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Delete a person
     * @param id is the person id
     * @return a response entity ok or not found if a NoSuchElementException is caught
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Person> deletePerson(@PathVariable("id") Long id){
        log.info("DELETE /persons/" + id);
        try {
            return ResponseEntity.ok(personService.deletePerson(personService.getPerson(id)));
        } catch (NoSuchElementException e) {
            log.error("DELETE /persons/" + id +" - ERROR : " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Delete a person by his firstname and lastname
     * @param person is the PersonDeleteRequest who contains a firstname and lastname of a person
     * @return a response entity ok or not found if a NoSuchElementException is caught
     */
    @DeleteMapping("")
    public ResponseEntity<Person> deletePersonByName(@RequestBody PersonDeleteRequest person){
        log.info("DELETE /persons/ : " + person.getFirstName() + " " + person.getLastName());
        try {
            return ResponseEntity.ok(personService.deletePerson(personService.getPerson(person.getFirstName(), person.getLastName())));
        } catch (NoSuchElementException e) {
            log.error("DELETE /persons/ : " + person.getFirstName() + " " + person.getLastName() + " - ERROR : " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}
