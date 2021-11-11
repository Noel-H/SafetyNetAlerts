package com.noelh.safetynetalerts.person;

import com.noelh.safetynetalerts.medicalrecord.MedicalRecord;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String firstName;
    private String lastName;
    private Date birthdate;
    private String address;
    private String city;
    private String zip;
    private String phone;
    private String email;

    @OneToOne(cascade = CascadeType.ALL)
    private MedicalRecord medicalRecord;

    public Person(Person p) {
        id = p.id;
        firstName = p.firstName;
        lastName = p.lastName;
        birthdate = p.birthdate;
        address = p.address;
        city = p.city;
        zip = p.zip;
        phone = p.phone;
        email = p.email;
        medicalRecord = new MedicalRecord(p.medicalRecord);
    }
}
