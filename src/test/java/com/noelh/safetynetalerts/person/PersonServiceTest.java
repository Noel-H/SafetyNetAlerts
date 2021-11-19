package com.noelh.safetynetalerts.person;

import com.noelh.safetynetalerts.medicalrecord.MedicalRecord;
import com.noelh.safetynetalerts.person.dto.PersonAddRequest;
import com.noelh.safetynetalerts.person.dto.PersonUpdateRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonService personService;

    @Test
    public void getPersonsTest_shouldReturnListOfPerson(){
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
        when(personRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> personService.getPerson(1));
    }

    @Test
    public void getPersonByFirstNameAndLastNameTest_souldReturnPerson(){
        Person p = new Person();
        when(personRepository.findByFirstNameAndLastName("John","Doe")).thenReturn(Optional.of(p));
        Person result = personService.getPerson("John","Doe");
        assertEquals(p, result);
    }

    @Test
    public void getPersonByFirstNameAndLastNameTest_souldThrowException(){
        when(personRepository.findByFirstNameAndLastName("John","Doe")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> personService.getPerson("John","Doe"))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    public void addPersonTest_shouldReturnPerson(){
        Date date = new Date();
        date.setTime(978303600000L); // 01/01/2001
        PersonAddRequest pAR = new PersonAddRequest(
                "John",
                "Doe",
                date,
                "01 Test St",
                "Test city",
                "12345",
                "0102030405",
                "john.doe@email.com");

        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setMedications(new ArrayList<>());
        medicalRecord.setAllergies(new ArrayList<>());

        Person person = new Person();
        person.setFirstName("John");
        person.setLastName("Doe");
        person.setBirthdate(date);
        person.setAddress("01 Test St");
        person.setCity("Test city");
        person.setZip("12345");
        person.setPhone("0102030405");
        person.setEmail("john.doe@email.com");
        person.setMedicalRecord(medicalRecord);


        when(personRepository.save(person)).thenReturn(new Person());
        personService.addPerson(pAR);
        verify(personRepository, times(1)).save(person);
    }

    @Test
    public void updatePersonTest_shouldReturnPerson(){
        Date date = new Date();
        date.setTime(978303600000L); // 01/01/2001

        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setMedications(new ArrayList<>());
        medicalRecord.setAllergies(new ArrayList<>());

        Person person = new Person();
        person.setId(1L);
        person.setFirstName("John");
        person.setLastName("Doe");
        person.setBirthdate(date);
        person.setAddress("01 Test St");
        person.setCity("Test city");
        person.setZip("12345");
        person.setPhone("0102030405");
        person.setEmail("john.doe@email.com");
        person.setMedicalRecord(medicalRecord);

        Date date2 = new Date();
        date2.setTime(652831200000L); // 09/09/1990

        PersonUpdateRequest pUR = new PersonUpdateRequest();
        pUR.setBirthdate(date2);
        pUR.setAddress("02 High city");
        pUR.setCity("High city");
        pUR.setZip("77777");
        pUR.setPhone("0707070707");
        pUR.setEmail("alias@email.com");

        Person person2 = new Person();
        person2.setId(1L);
        person2.setFirstName("John");
        person2.setLastName("Doe");
        person2.setBirthdate(date2);
        person2.setAddress("02 High city");
        person2.setCity("High city");
        person2.setZip("77777");
        person2.setPhone("0707070707");
        person2.setEmail("alias@email.com");
        person2.setMedicalRecord(medicalRecord);

        when(personRepository.save(person2)).thenReturn(new Person());

        personService.updatePerson(person, pUR);
        verify(personRepository, times(1)).save(person2);
    }

    @Test
    public void updatePersonTest_shouldReturnPersonWithoutChange(){
        Date date = new Date();
        date.setTime(978303600000L); // 01/01/2001

        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setMedications(new ArrayList<>());
        medicalRecord.setAllergies(new ArrayList<>());

        Person person = new Person();
        person.setId(1L);
        person.setFirstName("John");
        person.setLastName("Doe");
        person.setBirthdate(date);
        person.setAddress("01 Test St");
        person.setCity("Test city");
        person.setZip("12345");
        person.setPhone("0102030405");
        person.setEmail("john.doe@email.com");
        person.setMedicalRecord(medicalRecord);

        Date date2 = new Date();
        date2.setTime(652831200000L); // 09/09/1990

        PersonUpdateRequest pUR = new PersonUpdateRequest();

        Person person2 = new Person();
        person2.setId(1L);
        person2.setFirstName("John");
        person2.setLastName("Doe");
        person2.setBirthdate(date);
        person2.setAddress("01 Test St");
        person2.setCity("Test city");
        person2.setZip("12345");
        person2.setPhone("0102030405");
        person2.setEmail("john.doe@email.com");
        person2.setMedicalRecord(medicalRecord);

        when(personRepository.save(person2)).thenReturn(new Person());

        personService.updatePerson(person, pUR);
        verify(personRepository, times(1)).save(person2);
    }

    @Test
    public void deletePersonTest_shouldReturnPerson(){
        Date date = new Date();
        date.setTime(978303600000L); // 01/01/2001

        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setMedications(new ArrayList<>());
        medicalRecord.setAllergies(new ArrayList<>());

        Person person = new Person();
        person.setId(1L);
        person.setFirstName("John");
        person.setLastName("Doe");
        person.setBirthdate(date);
        person.setAddress("01 Test St");
        person.setCity("Test city");
        person.setZip("12345");
        person.setPhone("0102030405");
        person.setEmail("john.doe@email.com");
        person.setMedicalRecord(medicalRecord);
        doNothing().when(personRepository).delete(person);

        personService.deletePerson(person);

        verify(personRepository, times(1)).delete(person);
    }
}
