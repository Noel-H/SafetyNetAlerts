package com.noelh.safetynetalerts.service;

import com.noelh.safetynetalerts.json.jsonparser.MedicalRecord;
import com.noelh.safetynetalerts.json.jsonparser.Person;
import com.noelh.safetynetalerts.json.jsonparser.PersonRepository;
import com.noelh.safetynetalerts.web.dto.PersonAddRequest;
import com.noelh.safetynetalerts.web.dto.PersonUpdateRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonService personService;

    @Test
    public void getPersonsTest_shouldReturnListOfPerson(){
        when(personRepository.findAll()).thenReturn(new ArrayList<>());
        personService.getPersons();
        verify(personRepository, times(1)).findAll();
    }

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

    @Test
    public void getPersonByFirstNameAndLastName_souldReturnPerson(){
        Person p = new Person();
        when(personRepository.findByFirstNameAndLastName(anyString(),anyString())).thenReturn(Optional.of(p));
        Person result = personService.getPerson(anyString(),anyString());
        assertEquals(p, result);
    }

    @Test
    public void getPersonByFirstNameAndLastName_souldThrowException(){
        when(personRepository.findByFirstNameAndLastName(anyString(),anyString())).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> personService.getPerson(anyString(),anyString()));

    }

    @Test
    public void addPerson_shouldReturnPerson(){
        Person p = new Person();
        PersonAddRequest pAR = new PersonAddRequest();
        when(personRepository.save(any())).thenReturn(p);
        personService.addPerson(pAR);
        verify(personRepository, times(1)).save(any());
    }

    //ternaire non testé
    @Test
    public void updatePerson_shouldReturnPerson(){
        Person p = new Person();
        Person updatedPerson = new Person();
        PersonUpdateRequest pUR = new PersonUpdateRequest();
        when(personRepository.save(any())).thenReturn(updatedPerson);
        personService.updatePerson(p, pUR);
        verify(personRepository, times(1)).save(any());
    }


    @Test
    public void deletePersonTest_shouldReturnPerson(){
        MedicalRecord m = new MedicalRecord();
        m.setMedications(new ArrayList<>());
        m.setAllergies(new ArrayList<>());
        Person p = new Person();
        p.setFirstName("David");
        p.setMedicalRecord(m);
        doNothing().when(personRepository).delete(any());

        Person result = personService.deletePerson(p);

        assertEquals("David", result.getFirstName());
        verify(personRepository, times(1)).delete(any());
    }
}
