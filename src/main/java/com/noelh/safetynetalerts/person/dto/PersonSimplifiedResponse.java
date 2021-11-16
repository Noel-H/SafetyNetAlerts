package com.noelh.safetynetalerts.person.dto;

import lombok.Data;

@Data
public class PersonSimplifiedResponse {

    private String firstName;
    private String lastName;
    private String address;
    private String phone;

    public PersonSimplifiedResponse(String firstName, String lastName, String address, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phone = phone;
    }
}
