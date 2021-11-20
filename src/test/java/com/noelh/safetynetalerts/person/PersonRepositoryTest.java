package com.noelh.safetynetalerts.person;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class PersonRepositoryTest {

    @Autowired
    private PersonRepository personRepositoryUnderTest;

    @Test
    public void findByFirstNameAndLastNameTest_shouldFindThisPerson() {
        Person person = new Person();
        person.setFirstName("Slydeno");
        person.setLastName("Hixo");
        personRepositoryUnderTest.save(person);
        Optional<Person> expected = personRepositoryUnderTest.findByFirstNameAndLastName("Slydeno","Hixo");
        assertThat(expected).isNotEmpty();
    }

    @Test
    public void findByFirstNameAndLastNameTest_shouldNotFindThisPerson() {
        Optional<Person> expected = personRepositoryUnderTest.findByFirstNameAndLastName("Slydeno","Hixo");
        assertThat(expected).isEmpty();
    }
}