package com.noelh.safetynetalerts.url.dto;

import lombok.Data;

@Data
public class CommunityEmailUrlResponse {
    private String email;

    public CommunityEmailUrlResponse(String email) {
        this.email = email;
    }
}
