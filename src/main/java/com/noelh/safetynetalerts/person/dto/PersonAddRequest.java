package com.noelh.safetynetalerts.person.dto;

import lombok.Data;

import java.util.Date;

@Data
public class PersonAddRequest {
    private String firstName;
    private String lastName;
    private Date birthdate;
    private String address;
    private String city;
    private String zip;
    private String phone;
    private String email;

    public PersonAddRequest(String firstName, String lastName, Date birthdate, String address, String city, String zip, String phone, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.address = address;
        this.city = city;
        this.zip = zip;
        this.phone = phone;
        this.email = email;
    }
}
