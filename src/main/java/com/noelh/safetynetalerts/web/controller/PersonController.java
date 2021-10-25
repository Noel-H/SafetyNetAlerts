package com.noelh.safetynetalerts.web.controller;

import com.noelh.safetynetalerts.json.jsonparser.Person;
import com.noelh.safetynetalerts.service.PersonService;
import com.noelh.safetynetalerts.web.dto.PersonAddRequest;
import com.noelh.safetynetalerts.web.dto.PersonDeleteRequest;
import com.noelh.safetynetalerts.web.dto.PersonUpdateRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequestMapping("/persons")
public class PersonController {
    @Autowired
    private PersonService personService;

    @GetMapping("")
    public List<Person> getPersons(){
        log.info("GET /persons/");
        return personService.getPersons();
    }

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

    @PostMapping("")
    public ResponseEntity<Person> addPerson(@RequestBody PersonAddRequest person){
        log.info("POST /persons/ : " + person.getFirstName() + " " + person.getLastName());
        return ResponseEntity.ok(personService.addPerson(person));
    }

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
