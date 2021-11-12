package com.noelh.safetynetalerts.person;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PersonRepositoryTest {

    @Autowired
    private PersonRepository personRepositoryUnderTest;

    @Test
    void findByFirstNameAndLastName_shouldFindThisPerson() {
        //given
        Person person = new Person();
        person.setFirstName("Slydeno");
        person.setLastName("Hixo");
        personRepositoryUnderTest.save(person);
        //when
        Optional<Person> expected = personRepositoryUnderTest.findByFirstNameAndLastName("Slydeno","Hixo");
        //then
        assertThat(expected).isNotEmpty();
    }

    @Test
    void findByFirstNameAndLastName_shouldNotFindThisPerson() {
        //given
        //when
        Optional<Person> expected = personRepositoryUnderTest.findByFirstNameAndLastName("Slydeno","Hixo");
        //then
        assertThat(expected).isEmpty();
    }
}