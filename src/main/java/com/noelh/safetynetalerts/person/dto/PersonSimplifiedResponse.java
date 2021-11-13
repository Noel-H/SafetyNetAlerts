package com.noelh.safetynetalerts.person.dto;

import lombok.Data;

@Data
public class PersonSimplifiedResponse {

    private String firstName;
    private String lastName;
    private String address;
    private String phone;
}
