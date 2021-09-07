package com.noelh.safetynetalerts.json.jsonparser;

import lombok.Data;

import java.util.List;

@Data
public class FireStation {
    private List<String> address;
    private int station;
}
