package com.noelh.safetynetalerts.url.dto;

import lombok.Data;

@Data
public class PhoneUrlResponse {
    private String phone;

    public PhoneUrlResponse(String phone) {
        this.phone=phone;
    }
}
