package com.noelh.safetynetalerts.firestation.dto;

import lombok.Data;

@Data
public class FireStationNumberResponse {
    private int station;

    public FireStationNumberResponse(int station) {
        this.station = station;
    }
}
