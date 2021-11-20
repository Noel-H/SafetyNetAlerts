package com.noelh.safetynetalerts.url;

import com.noelh.safetynetalerts.firestation.FireStation;
import com.noelh.safetynetalerts.firestation.FireStationService;
import com.noelh.safetynetalerts.firestation.dto.FireStationNumberResponse;
import com.noelh.safetynetalerts.medicalrecord.MedicalRecord;
import com.noelh.safetynetalerts.person.Person;
import com.noelh.safetynetalerts.person.PersonService;
import com.noelh.safetynetalerts.person.dto.PersonSimplifiedResponse;
import com.noelh.safetynetalerts.url.dto.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UrlServiceTest {

    @Mock
    private PersonService personService;

    @Mock
    private FireStationService fireStationService;

    @InjectMocks
    private UrlService urlService;

    @Test
    public void getPersonsListByStationIdTest_Should_Return_FireStationUrlResponse() {
        List<String> addressList = new ArrayList<>();
        addressList.add("01 Test St");
        addressList.add("02 Test St");
        addressList.add("03 Test St");
        addressList.add("04 Test St");
        addressList.add("05 Test St");

        FireStation fireStation = new FireStation();
        fireStation.setId(1);
        fireStation.setStation(1);
        fireStation.setAddress(addressList);

        Date date = new Date();
        date.setTime(978303600000L); // 01/01/2001
        Date date1 = new Date();
        date1.setTime(1355266800000L); // 12/12/2012

        Person person = new Person();
        person.setId(1);
        person.setFirstName("John");
        person.setLastName("Doe");
        person.setBirthdate(date);
        person.setAddress("01 Test St");
        person.setCity("Test city");
        person.setZip("12345");
        person.setPhone("0102030405");
        person.setEmail("john.doe@email.com");

        Person person1 = new Person();
        person1.setId(2);
        person1.setFirstName("Peter");
        person1.setLastName("Parker");
        person1.setBirthdate(date1);
        person1.setAddress("02 Test St");
        person1.setCity("Test city");
        person1.setZip("99999");
        person1.setPhone("0909090909");
        person1.setEmail("peter.parker@email.com");

        List<Person> personList = new ArrayList<>();
        personList.add(person);
        personList.add(person1);

        PersonSimplifiedResponse personSimplifiedResponse = new PersonSimplifiedResponse(
                "John",
                "Doe",
                "01 Test St",
                "0102030405");

        PersonSimplifiedResponse personSimplifiedResponse1 = new PersonSimplifiedResponse(
                "Peter",
                "Parker",
                "02 Test St",
                "0909090909");

        List<PersonSimplifiedResponse> personSimplifiedResponseList = new ArrayList<>();
        personSimplifiedResponseList.add(personSimplifiedResponse);
        personSimplifiedResponseList.add(personSimplifiedResponse1);

        FireStationUrlResponse expectedValue = new FireStationUrlResponse(1,1,personSimplifiedResponseList);

        when(fireStationService.getFireStation(1L)).thenReturn(fireStation);
        when(personService.getPersons()).thenReturn(personList);

        FireStationUrlResponse result = urlService.getPersonsListByStationId(1L);
        assertThat(result).isEqualTo(expectedValue);
    }

    @Test
    public void getPersonsListByStationIdTest_Should_Throw_NoSuchElementException() {
        when(fireStationService.getFireStation(1L)).thenThrow(new NoSuchElementException());

        assertThatThrownBy(() -> urlService.getPersonsListByStationId(1L))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    public void getChildListByAddressTest_Should_Return_ChildAlertUrlResponseList() {
        Date date = new Date();
        date.setTime(978303600000L); // 01/01/2001
        Date date1 = new Date();
        date1.setTime(1355266800000L); // 12/12/2012
        Date date2 = new Date();
        date2.setTime(652831200000L); // 09/09/1990

        Person person = new Person();
        person.setId(1);
        person.setFirstName("John");
        person.setLastName("Doe");
        person.setBirthdate(date);
        person.setAddress("01 Test St");
        person.setCity("Test city");
        person.setZip("12345");
        person.setPhone("0102030405");
        person.setEmail("john.doe@email.com");

        Person person1 = new Person();
        person1.setId(2);
        person1.setFirstName("Peter");
        person1.setLastName("Parker");
        person1.setBirthdate(date1);
        person1.setAddress("02 Test St");
        person1.setCity("Test city");
        person1.setZip("99999");
        person1.setPhone("0909090909");
        person1.setEmail("peter.parker@email.com");

        Person person2 = new Person();
        person2.setId(3);
        person2.setFirstName("Lara");
        person2.setLastName("Croft");
        person2.setBirthdate(date2);
        person2.setAddress("02 Test St");
        person2.setCity("Test city");
        person2.setZip("99999");
        person2.setPhone("0808080808");
        person2.setEmail("lara.croft@email.com");

        List<Person> personList = new ArrayList<>();
        personList.add(person);
        personList.add(person1);
        personList.add(person2);

        List<Person> personList1 = new ArrayList<>();
        personList1.add(person1);
        personList1.add(person2);

        ChildAlertUrlResponse childAlertUrlResponse = new ChildAlertUrlResponse(
                "Peter",
                "Parker",
                8,
                personList1);

        List<ChildAlertUrlResponse> expectedValue = new ArrayList<>();
        expectedValue.add(childAlertUrlResponse);
        when(personService.getPersons()).thenReturn(personList);
        List<ChildAlertUrlResponse> result = urlService.getChildListByAddress("02 Test St");
        assertThat(result).isEqualTo(expectedValue);
    }

    @Test
    public void getChildListByAddressTest_Should_Throw_NoSuchElementException() {
        Date date = new Date();
        date.setTime(978303600000L); // 01/01/2001
        Date date1 = new Date();
        date1.setTime(1355266800000L); // 12/12/2012

        Person person = new Person();
        person.setId(1);
        person.setFirstName("John");
        person.setLastName("Doe");
        person.setBirthdate(date);
        person.setAddress("01 Test St");
        person.setCity("Test city");
        person.setZip("12345");
        person.setPhone("0102030405");
        person.setEmail("john.doe@email.com");

        Person person1 = new Person();
        person1.setId(2);
        person1.setFirstName("Peter");
        person1.setLastName("Parker");
        person1.setBirthdate(date1);
        person1.setAddress("02 Test St");
        person1.setCity("Test city");
        person1.setZip("99999");
        person1.setPhone("0909090909");
        person1.setEmail("peter.parker@email.com");

        List<Person> personList = new ArrayList<>();
        personList.add(person);
        personList.add(person1);
        when(personService.getPersons()).thenReturn(personList);
        assertThatThrownBy(()-> urlService.getChildListByAddress("11 Test St"))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    public void getPhonesByStationIdTest_Should_Return_PhoneUrlResponseList() {
        Date date = new Date();
        date.setTime(978303600000L); // 01/01/2001
        Date date1 = new Date();
        date1.setTime(1355266800000L); // 12/12/2012
        Date date2 = new Date();
        date2.setTime(652831200000L); // 09/09/1990

        Person person = new Person();
        person.setId(1);
        person.setFirstName("John");
        person.setLastName("Doe");
        person.setBirthdate(date);
        person.setAddress("01 Test St");
        person.setCity("Test city");
        person.setZip("12345");
        person.setPhone("0102030405");
        person.setEmail("john.doe@email.com");

        Person person1 = new Person();
        person1.setId(2);
        person1.setFirstName("Peter");
        person1.setLastName("Parker");
        person1.setBirthdate(date1);
        person1.setAddress("99 Wrong Av");
        person1.setCity("Test city");
        person1.setZip("99999");
        person1.setPhone("0909090909");
        person1.setEmail("peter.parker@email.com");

        Person person2 = new Person();
        person2.setId(3);
        person2.setFirstName("Lara");
        person2.setLastName("Croft");
        person2.setBirthdate(date2);
        person2.setAddress("02 Test St");
        person2.setCity("Test city");
        person2.setZip("99999");
        person2.setPhone("0808080808");
        person2.setEmail("lara.croft@email.com");

        List<Person> personList = new ArrayList<>();
        personList.add(person);
        personList.add(person1);
        personList.add(person2);

        List<String> addressList = new ArrayList<>();
        addressList.add("01 Test St");
        addressList.add("02 Test St");
        addressList.add("03 Test St");
        addressList.add("04 Test St");
        addressList.add("05 Test St");

        FireStation fireStation = new FireStation();
        fireStation.setId(1);
        fireStation.setStation(1);
        fireStation.setAddress(addressList);

        PhoneUrlResponse phoneUrlResponse = new PhoneUrlResponse("0102030405");
        PhoneUrlResponse phoneUrlResponse1 = new PhoneUrlResponse("0808080808");

        List<PhoneUrlResponse> expectedValue = new ArrayList<>();
        expectedValue.add(phoneUrlResponse);
        expectedValue.add(phoneUrlResponse1);

        when(personService.getPersons()).thenReturn(personList);
        when(fireStationService.getFireStation(1L)).thenReturn(fireStation);
        List<PhoneUrlResponse> result = urlService.getPhonesByStationId(1L);
        assertThat(result).isEqualTo(expectedValue);
    }

    @Test
    public void getPhonesByStationIdTest_Should_Throw_NoSuchElementException() {
        Date date = new Date();
        date.setTime(978303600000L); // 01/01/2001
        Date date1 = new Date();
        date1.setTime(1355266800000L); // 12/12/2012
        Date date2 = new Date();
        date2.setTime(652831200000L); // 09/09/1990

        Person person = new Person();
        person.setId(1);
        person.setFirstName("John");
        person.setLastName("Doe");
        person.setBirthdate(date);
        person.setAddress("01 Test St");
        person.setCity("Test city");
        person.setZip("12345");
        person.setPhone("0102030405");
        person.setEmail("john.doe@email.com");

        Person person1 = new Person();
        person1.setId(2);
        person1.setFirstName("Peter");
        person1.setLastName("Parker");
        person1.setBirthdate(date1);
        person1.setAddress("99 Wrong Av");
        person1.setCity("Test city");
        person1.setZip("99999");
        person1.setPhone("0909090909");
        person1.setEmail("peter.parker@email.com");

        Person person2 = new Person();
        person2.setId(3);
        person2.setFirstName("Lara");
        person2.setLastName("Croft");
        person2.setBirthdate(date2);
        person2.setAddress("02 Test St");
        person2.setCity("Test city");
        person2.setZip("99999");
        person2.setPhone("0808080808");
        person2.setEmail("lara.croft@email.com");

        List<Person> personList = new ArrayList<>();
        personList.add(person);
        personList.add(person1);
        personList.add(person2);

        when(personService.getPersons()).thenReturn(personList);
        when(fireStationService.getFireStation(1L)).thenThrow(new NoSuchElementException());
        assertThatThrownBy(() -> urlService.getPhonesByStationId(1L))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    public void getPersonAndStationNumberByAddressTest_Should_Return_FireUrlWithStationNumberResponse() {
        Date date = new Date();
        date.setTime(978303600000L); // 01/01/2001
        Date date1 = new Date();
        date1.setTime(1355266800000L); // 12/12/2012
        Date date2 = new Date();
        date2.setTime(652831200000L); // 09/09/1990

        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setAllergies(Arrays.asList("Pomme","Ananas"));
        medicalRecord.setMedications(Arrays.asList("Doliprane:500mg","hydrapermazol:100mg"));

        MedicalRecord medicalRecord1 = new MedicalRecord();
        medicalRecord1.setAllergies(Arrays.asList("Banane","Poire"));
        medicalRecord1.setMedications(Arrays.asList("Doliprane:1000mg",""));

        MedicalRecord medicalRecord2 = new MedicalRecord();
        medicalRecord2.setAllergies(Arrays.asList("Kiwi","Fraise"));
        medicalRecord2.setMedications(Arrays.asList("aznol:350mg","hydrapermazol:100mg"));

        Person person = new Person();
        person.setId(1);
        person.setFirstName("John");
        person.setLastName("Doe");
        person.setBirthdate(date);
        person.setAddress("01 Test St");
        person.setCity("Test city");
        person.setZip("12345");
        person.setPhone("0102030405");
        person.setEmail("john.doe@email.com");
        person.setMedicalRecord(medicalRecord);

        Person person1 = new Person();
        person1.setId(2);
        person1.setFirstName("Peter");
        person1.setLastName("Parker");
        person1.setBirthdate(date1);
        person1.setAddress("99 Wrong Av");
        person1.setCity("Test city");
        person1.setZip("99999");
        person1.setPhone("0909090909");
        person1.setEmail("peter.parker@email.com");
        person1.setMedicalRecord(medicalRecord1);

        Person person2 = new Person();
        person2.setId(3);
        person2.setFirstName("Lara");
        person2.setLastName("Croft");
        person2.setBirthdate(date2);
        person2.setAddress("02 Test St");
        person2.setCity("Test city");
        person2.setZip("99999");
        person2.setPhone("0808080808");
        person2.setEmail("lara.croft@email.com");
        person2.setMedicalRecord(medicalRecord2);

        List<Person> personList = new ArrayList<>();
        personList.add(person);
        personList.add(person1);
        personList.add(person2);

        FireStation fireStation = new FireStation();
        fireStation.setId(1);
        fireStation.setStation(1);
        fireStation.setAddress(Arrays.asList("01 Test St","02 Test St","03 Test St","04 Test St"));

        FireStation fireStation1 = new FireStation();
        fireStation1.setId(2);
        fireStation1.setStation(2);
        fireStation1.setAddress(Arrays.asList("05 Test St","06 Test St","07 Test St","08 Test St"));

        FireStation fireStation2 = new FireStation();
        fireStation2.setId(3);
        fireStation2.setStation(3);
        fireStation2.setAddress(Arrays.asList("09 Test St","10 Test St","11 Test St","12 Test St"));

        List<FireStation> fireStationList = new ArrayList<>();
        fireStationList.add(fireStation);
        fireStationList.add(fireStation1);
        fireStationList.add(fireStation2);

        FireStationNumberResponse fireStationNumberResponse = new FireStationNumberResponse(1);

        FireUrlResponse fireUrlResponse = new FireUrlResponse(
                "John",
                "Doe",
                "0102030405",
                20,
                medicalRecord);


        List<FireStationNumberResponse> fireStationNumberResponses = new ArrayList<>();
        fireStationNumberResponses.add(fireStationNumberResponse);

        List<FireUrlResponse> fireUrlResponseList = new ArrayList<>();
        fireUrlResponseList.add(fireUrlResponse);

        FireUrlWithStationNumberResponse expectedValue = new FireUrlWithStationNumberResponse();
        expectedValue.setStations(fireStationNumberResponses);
        expectedValue.setFireUrlResponseList(fireUrlResponseList);
        when(personService.getPersons()).thenReturn(personList);
        when(fireStationService.getFireStations()).thenReturn(fireStationList);
        FireUrlWithStationNumberResponse result = urlService.getPersonAndStationNumberByAddress("01 Test St");
        assertThat(result).isEqualTo(expectedValue);
    }

    @Test
    public void getPersonAndStationNumberByAddressTest_Should_Throw_NoSuchElementException() {
        List<Person> personList = new ArrayList<>();
        when(personService.getPersons()).thenReturn(personList);
        assertThatThrownBy(() -> urlService.getPersonAndStationNumberByAddress("01 Test St"))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    public void getPersonsGroupedByAddressTest_Should_Return_MapList() {
        Date date = new Date();
        date.setTime(978303600000L); // 01/01/2001
        Date date1 = new Date();
        date1.setTime(1355266800000L); // 12/12/2012
        Date date2 = new Date();
        date2.setTime(652831200000L); // 09/09/1990

        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setAllergies(Arrays.asList("Pomme","Ananas"));
        medicalRecord.setMedications(Arrays.asList("Doliprane:500mg","hydrapermazol:100mg"));

        MedicalRecord medicalRecord1 = new MedicalRecord();
        medicalRecord1.setAllergies(Arrays.asList("Banane","Poire"));
        medicalRecord1.setMedications(Arrays.asList("Doliprane:1000mg",""));

        MedicalRecord medicalRecord2 = new MedicalRecord();
        medicalRecord2.setAllergies(Arrays.asList("Kiwi","Fraise"));
        medicalRecord2.setMedications(Arrays.asList("aznol:350mg","hydrapermazol:100mg"));

        Person person = new Person();
        person.setId(1);
        person.setFirstName("John");
        person.setLastName("Doe");
        person.setBirthdate(date); // 01/01/2001 - 20 ans
        person.setAddress("01 Test St");
        person.setCity("Test city");
        person.setZip("12345");
        person.setPhone("0102030405");
        person.setEmail("john.doe@email.com");
        person.setMedicalRecord(medicalRecord);

        Person person1 = new Person();
        person1.setId(2);
        person1.setFirstName("Peter");
        person1.setLastName("Parker");
        person1.setBirthdate(date1); // 12/12/2012 - 8 ans
        person1.setAddress("99 Wrong Av");
        person1.setCity("Test city");
        person1.setZip("99999");
        person1.setPhone("0909090909");
        person1.setEmail("peter.parker@email.com");
        person1.setMedicalRecord(medicalRecord1);

        Person person2 = new Person();
        person2.setId(3);
        person2.setFirstName("Lara");
        person2.setLastName("Croft");
        person2.setBirthdate(date2); // 09/09/1990 - 31 ans
        person2.setAddress("08 Test St");
        person2.setCity("Test city");
        person2.setZip("99999");
        person2.setPhone("0808080808");
        person2.setEmail("lara.croft@email.com");
        person2.setMedicalRecord(medicalRecord2);

        List<Person> personList = new ArrayList<>();
        personList.add(person);
        personList.add(person1);
        personList.add(person2);
        FireStation fireStation = new FireStation();
        fireStation.setId(1);
        fireStation.setStation(1);
        fireStation.setAddress(Arrays.asList("01 Test St","02 Test St","03 Test St","04 Test St"));

        FireStation fireStation1 = new FireStation();
        fireStation1.setId(2);
        fireStation1.setStation(2);
        fireStation1.setAddress(Arrays.asList("05 Test St","06 Test St","07 Test St","08 Test St"));

        FireStation fireStation2 = new FireStation();
        fireStation2.setId(3);
        fireStation2.setStation(3);
        fireStation2.setAddress(Arrays.asList("09 Test St","10 Test St","11 Test St","12 Test St"));

        FloodStationUrlResponse floodStationUrlResponse = new FloodStationUrlResponse(
                "01 Test St",
                "John",
                "Doe",
                "0102030405",
                20,
                medicalRecord
        );
        List<FloodStationUrlResponse> floodStationUrlResponseList = new ArrayList<>();
        floodStationUrlResponseList.add(floodStationUrlResponse);

        FloodStationUrlResponse floodStationUrlResponse1 = new FloodStationUrlResponse(
                "08 Test St",
                "Lara",
                "Croft",
                "0808080808",
                31,
                medicalRecord2
        );
        List<FloodStationUrlResponse> floodStationUrlResponseList1 = new ArrayList<>();
        floodStationUrlResponseList1.add(floodStationUrlResponse1);

        Map<String, List<FloodStationUrlResponse>> expectedValue = new HashMap<>();
        expectedValue.put("01 Test St",floodStationUrlResponseList);
        expectedValue.put("08 Test St",floodStationUrlResponseList1);

        when(fireStationService.getFireStation(1L)).thenReturn(fireStation);
        when(fireStationService.getFireStation(2L)).thenReturn(fireStation1);
        when(personService.getPersons()).thenReturn(personList);
        Map<String, List<FloodStationUrlResponse>> result = urlService.getPersonsGroupedByAddress(Arrays.asList(1L,2L));
        assertThat(result).isEqualTo(expectedValue);
    }

    @Test
    public void getPersonsGroupedByAddressTest_Should_Throw_NoSuchElementException() {
        FireStation fireStation = new FireStation();
        fireStation.setId(1);
        fireStation.setStation(1);
        fireStation.setAddress(Arrays.asList("01 Test St","02 Test St","03 Test St","04 Test St"));

        when(fireStationService.getFireStation(1L)).thenReturn(fireStation);
        when(fireStationService.getFireStation(5L)).thenThrow(new NoSuchElementException());
        assertThatThrownBy(() -> urlService.getPersonsGroupedByAddress(Arrays.asList(1L,5L)))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    public void getPersonInfoByFirstNameAndLastNameTest_Should_Return_PersonInfoUrlResponseList() {
        Date date = new Date();
        date.setTime(978303600000L); // 01/01/2001
        Date date1 = new Date();
        date1.setTime(1355266800000L); // 12/12/2012
        Date date2 = new Date();
        date2.setTime(652831200000L); // 09/09/1990

        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setAllergies(Arrays.asList("Pomme","Ananas"));
        medicalRecord.setMedications(Arrays.asList("Doliprane:500mg","hydrapermazol:100mg"));

        MedicalRecord medicalRecord1 = new MedicalRecord();
        medicalRecord1.setAllergies(Arrays.asList("Banane","Poire"));
        medicalRecord1.setMedications(Arrays.asList("Doliprane:1000mg",""));

        MedicalRecord medicalRecord2 = new MedicalRecord();
        medicalRecord2.setAllergies(Arrays.asList("Kiwi","Fraise"));
        medicalRecord2.setMedications(Arrays.asList("aznol:350mg","hydrapermazol:100mg"));

        Person person = new Person();
        person.setId(1);
        person.setFirstName("John");
        person.setLastName("Doe");
        person.setBirthdate(date); // 01/01/2001 - 20 ans
        person.setAddress("01 Test St");
        person.setCity("Test city");
        person.setZip("12345");
        person.setPhone("0102030405");
        person.setEmail("john.doe@email.com");
        person.setMedicalRecord(medicalRecord);

        Person person1 = new Person();
        person1.setId(2);
        person1.setFirstName("Peter");
        person1.setLastName("Parker");
        person1.setBirthdate(date1); // 12/12/2012 - 8 ans
        person1.setAddress("99 Wrong Av");
        person1.setCity("Test city");
        person1.setZip("99999");
        person1.setPhone("0909090909");
        person1.setEmail("peter.parker@email.com");
        person1.setMedicalRecord(medicalRecord1);

        Person person2 = new Person();
        person2.setId(3);
        person2.setFirstName("Lara");
        person2.setLastName("Croft");
        person2.setBirthdate(date2); // 09/09/1990 - 31 ans
        person2.setAddress("08 Test St");
        person2.setCity("Test city");
        person2.setZip("99999");
        person2.setPhone("0808080808");
        person2.setEmail("lara.croft@email.com");
        person2.setMedicalRecord(medicalRecord2);

        List<Person> personList = new ArrayList<>();
        personList.add(person);
        personList.add(person1);
        personList.add(person2);

        PersonInfoUrlResponse personInfoUrlResponse = new PersonInfoUrlResponse(
                "John",
                "Doe",
                "01 Test St",
                20,
                "john.doe@email.com",
                medicalRecord
        );

        List<PersonInfoUrlResponse> expectedValue = new ArrayList<>();
        expectedValue.add(personInfoUrlResponse);
        when(personService.getPersons()).thenReturn(personList);
        List<PersonInfoUrlResponse> result = urlService.getPersonInfoByFirstNameAndLastName("John", "Doe");
        assertThat(result).isEqualTo(expectedValue);
    }

    @Test
    public void getPersonInfoByFirstNameAndLastNameTest_Should_Throw_NoSuchElementException() {
        Date date = new Date();
        date.setTime(978303600000L); // 01/01/2001
        Date date1 = new Date();
        date1.setTime(1355266800000L); // 12/12/2012
        Date date2 = new Date();
        date2.setTime(652831200000L); // 09/09/1990

        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setAllergies(Arrays.asList("Pomme","Ananas"));
        medicalRecord.setMedications(Arrays.asList("Doliprane:500mg","hydrapermazol:100mg"));

        MedicalRecord medicalRecord1 = new MedicalRecord();
        medicalRecord1.setAllergies(Arrays.asList("Banane","Poire"));
        medicalRecord1.setMedications(Arrays.asList("Doliprane:1000mg",""));

        MedicalRecord medicalRecord2 = new MedicalRecord();
        medicalRecord2.setAllergies(Arrays.asList("Kiwi","Fraise"));
        medicalRecord2.setMedications(Arrays.asList("aznol:350mg","hydrapermazol:100mg"));

        Person person = new Person();
        person.setId(1);
        person.setFirstName("Max");
        person.setLastName("Doe");
        person.setBirthdate(date); // 01/01/2001 - 20 ans
        person.setAddress("01 Test St");
        person.setCity("Test city");
        person.setZip("12345");
        person.setPhone("0102030405");
        person.setEmail("john.doe@email.com");
        person.setMedicalRecord(medicalRecord);

        Person person1 = new Person();
        person1.setId(2);
        person1.setFirstName("Peter");
        person1.setLastName("Parker");
        person1.setBirthdate(date1); // 12/12/2012 - 8 ans
        person1.setAddress("99 Wrong Av");
        person1.setCity("Test city");
        person1.setZip("99999");
        person1.setPhone("0909090909");
        person1.setEmail("peter.parker@email.com");
        person1.setMedicalRecord(medicalRecord1);

        Person person2 = new Person();
        person2.setId(3);
        person2.setFirstName("Lara");
        person2.setLastName("Payne");
        person2.setBirthdate(date2); // 09/09/1990 - 31 ans
        person2.setAddress("08 Test St");
        person2.setCity("Test city");
        person2.setZip("99999");
        person2.setPhone("0808080808");
        person2.setEmail("lara.croft@email.com");
        person2.setMedicalRecord(medicalRecord2);

        List<Person> personList = new ArrayList<>();
        personList.add(person);
        personList.add(person1);
        personList.add(person2);
        when(personService.getPersons()).thenReturn(personList);
        assertThatThrownBy(() -> urlService.getPersonInfoByFirstNameAndLastName("Max", "Payne"))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    public void getMailsByCityTest_Should_Return_CommunityEmailUrlResponseList() {
        Date date = new Date();
        date.setTime(978303600000L); // 01/01/2001
        Date date1 = new Date();
        date1.setTime(1355266800000L); // 12/12/2012
        Date date2 = new Date();
        date2.setTime(652831200000L); // 09/09/1990

        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setAllergies(Arrays.asList("Pomme","Ananas"));
        medicalRecord.setMedications(Arrays.asList("Doliprane:500mg","hydrapermazol:100mg"));

        MedicalRecord medicalRecord1 = new MedicalRecord();
        medicalRecord1.setAllergies(Arrays.asList("Banane","Poire"));
        medicalRecord1.setMedications(Arrays.asList("Doliprane:1000mg",""));

        MedicalRecord medicalRecord2 = new MedicalRecord();
        medicalRecord2.setAllergies(Arrays.asList("Kiwi","Fraise"));
        medicalRecord2.setMedications(Arrays.asList("aznol:350mg","hydrapermazol:100mg"));

        Person person = new Person();
        person.setId(1);
        person.setFirstName("Max");
        person.setLastName("Doe");
        person.setBirthdate(date); // 01/01/2001 - 20 ans
        person.setAddress("01 Test St");
        person.setCity("Test city");
        person.setZip("12345");
        person.setPhone("0102030405");
        person.setEmail("john.doe@email.com");
        person.setMedicalRecord(medicalRecord);

        Person person1 = new Person();
        person1.setId(2);
        person1.setFirstName("Peter");
        person1.setLastName("Parker");
        person1.setBirthdate(date1); // 12/12/2012 - 8 ans
        person1.setAddress("99 Wrong Av");
        person1.setCity("Wrong city");
        person1.setZip("99999");
        person1.setPhone("0909090909");
        person1.setEmail("peter.parker@email.com");
        person1.setMedicalRecord(medicalRecord1);

        Person person2 = new Person();
        person2.setId(3);
        person2.setFirstName("Lara");
        person2.setLastName("Payne");
        person2.setBirthdate(date2); // 09/09/1990 - 31 ans
        person2.setAddress("08 Test St");
        person2.setCity("Test city");
        person2.setZip("99999");
        person2.setPhone("0808080808");
        person2.setEmail("lara.croft@email.com");
        person2.setMedicalRecord(medicalRecord2);

        List<Person> personList = new ArrayList<>();
        personList.add(person);
        personList.add(person1);
        personList.add(person2);
        CommunityEmailUrlResponse communityEmailUrlResponse = new CommunityEmailUrlResponse("john.doe@email.com");
        CommunityEmailUrlResponse communityEmailUrlResponse1 = new CommunityEmailUrlResponse("lara.croft@email.com");

        List<CommunityEmailUrlResponse> expectedValue = new ArrayList<>();
        expectedValue.add(communityEmailUrlResponse);
        expectedValue.add(communityEmailUrlResponse1);
        when(personService.getPersons()).thenReturn(personList);
        List<CommunityEmailUrlResponse> result = urlService.getMailsByCity("Test city");
        assertThat(result).isEqualTo(expectedValue);
    }

    @Test
    public void getMailsByCityTest_Should_Throw_NoSuchElementException() {
        Date date = new Date();
        date.setTime(978303600000L); // 01/01/2001
        Date date1 = new Date();
        date1.setTime(1355266800000L); // 12/12/2012
        Date date2 = new Date();
        date2.setTime(652831200000L); // 09/09/1990

        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setAllergies(Arrays.asList("Pomme","Ananas"));
        medicalRecord.setMedications(Arrays.asList("Doliprane:500mg","hydrapermazol:100mg"));

        MedicalRecord medicalRecord1 = new MedicalRecord();
        medicalRecord1.setAllergies(Arrays.asList("Banane","Poire"));
        medicalRecord1.setMedications(Arrays.asList("Doliprane:1000mg",""));

        MedicalRecord medicalRecord2 = new MedicalRecord();
        medicalRecord2.setAllergies(Arrays.asList("Kiwi","Fraise"));
        medicalRecord2.setMedications(Arrays.asList("aznol:350mg","hydrapermazol:100mg"));

        Person person = new Person();
        person.setId(1);
        person.setFirstName("Max");
        person.setLastName("Doe");
        person.setBirthdate(date); // 01/01/2001 - 20 ans
        person.setAddress("01 Test St");
        person.setCity("Test city");
        person.setZip("12345");
        person.setPhone("0102030405");
        person.setEmail("john.doe@email.com");
        person.setMedicalRecord(medicalRecord);

        Person person1 = new Person();
        person1.setId(2);
        person1.setFirstName("Peter");
        person1.setLastName("Parker");
        person1.setBirthdate(date1); // 12/12/2012 - 8 ans
        person1.setAddress("99 Wrong Av");
        person1.setCity("Wrong city");
        person1.setZip("99999");
        person1.setPhone("0909090909");
        person1.setEmail("peter.parker@email.com");
        person1.setMedicalRecord(medicalRecord1);

        Person person2 = new Person();
        person2.setId(3);
        person2.setFirstName("Lara");
        person2.setLastName("Payne");
        person2.setBirthdate(date2); // 09/09/1990 - 31 ans
        person2.setAddress("08 Test St");
        person2.setCity("Test city");
        person2.setZip("99999");
        person2.setPhone("0808080808");
        person2.setEmail("lara.croft@email.com");
        person2.setMedicalRecord(medicalRecord2);

        List<Person> personList = new ArrayList<>();
        personList.add(person);
        personList.add(person1);
        personList.add(person2);
        when(personService.getPersons()).thenReturn(personList);
        assertThatThrownBy(() -> urlService.getMailsByCity("High city"))
                .isInstanceOf(NoSuchElementException.class);
    }
}