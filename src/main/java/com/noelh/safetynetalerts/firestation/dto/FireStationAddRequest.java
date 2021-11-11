package com.noelh.safetynetalerts.firestation.dto;

import lombok.Data;

import javax.persistence.ElementCollection;
import java.util.List;

@Data
public class FireStationAddRequest {
    private int station;

    @ElementCollection
    private List<String> address;
}
