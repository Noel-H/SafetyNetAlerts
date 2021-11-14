package com.noelh.safetynetalerts.url.dto;

import com.noelh.safetynetalerts.person.dto.PersonSimplifiedResponse;
import lombok.Data;

import javax.persistence.ElementCollection;
import java.util.List;

@Data
public class FireStationUrlResponse {

    private int nbAdult;

    private int nbChild;

    @ElementCollection
    private List<PersonSimplifiedResponse> urlPersonList;

    public FireStationUrlResponse(int nbAdult, int nbChild, List<PersonSimplifiedResponse> urlPersonList) {
        this.nbAdult = nbAdult;
        this.nbChild = nbChild;
        this.urlPersonList = urlPersonList;
    }
}
