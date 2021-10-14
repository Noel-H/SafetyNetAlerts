package com.noelh.safetynetalerts.json.jsonparser;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class FireStation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int station;

    @ElementCollection
    private List<String> address;

}
