package com.noelh.safetynetalerts.json.jsonparser;

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
}
