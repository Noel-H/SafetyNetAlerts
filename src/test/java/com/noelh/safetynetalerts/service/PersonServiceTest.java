package com.noelh.safetynetalerts.service;

import com.noelh.safetynetalerts.json.jsonparser.Person;
import com.noelh.safetynetalerts.json.jsonparser.PersonRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonService personService;

    @Test
    public void getPersonTest_shouldReturnPerson() {
        Person p = new Person();
        when(personRepository.findById(1L)).thenReturn(Optional.of(p));
        Person result = personService.getPerson(1);
        assertEquals(p, result);
    }

    @Test
    public void getPersonTest_shouldThrowException() {
        //GIVEN Je prepare mon repository a renvoyer un optional vide
        when(personRepository.findById(1L)).thenReturn(Optional.empty());

        //WHEN j'appelle personService.getPerson()
        //THEN je recois une NoSuchElementException
        assertThrows(NoSuchElementException.class, () -> personService.getPerson(1));
    }
}
