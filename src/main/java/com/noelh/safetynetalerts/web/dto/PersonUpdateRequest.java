package com.noelh.safetynetalerts.web.dto;

import lombok.Data;

import java.util.Date;

@Data
public class PersonUpdateRequest {
    private Date birthdate;
    private String address;
    private String city;
    private String zip;
    private String phone;
    private String email;
}
