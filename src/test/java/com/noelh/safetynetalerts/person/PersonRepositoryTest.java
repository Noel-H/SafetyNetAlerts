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
    public void findByFirstNameAndLastNameTest_shouldNotFindThisPerson() {
        //given
        //when
        Optional<Person> expected = personRepositoryUnderTest.findByFirstNameAndLastName("Slydeno","Hixo");
        //then
        assertThat(expected).isEmpty();
    }
}