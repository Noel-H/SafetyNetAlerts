package com.noelh.safetynetalerts.url.dto;

import com.noelh.safetynetalerts.firestation.dto.FireStationNumberResponse;
import lombok.Data;

import java.util.List;

@Data
public class FireUrlWithStationNumberResponse {
    List<FireStationNumberResponse> stations;
    List<FireUrlResponse> fireUrlResponseList;
}
