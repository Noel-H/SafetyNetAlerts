package com.noelh.safetynetalerts.json;

import com.jsoniter.JsonIterator;
import com.noelh.safetynetalerts.json.jsonparser.*;
import com.noelh.safetynetalerts.service.FireStationService;
import com.noelh.safetynetalerts.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;


@Component
public class JsonInitializer implements CommandLineRunner {
    @Value("${jsonfile}")
    Resource jsonDataFile;

    @Autowired
    private PersonService personService;

    @Autowired
    private FireStationService fireStationService;



    @Override
    public void run(String... args) throws Exception {
        JsonManagement jsonManagement = JsonManagement.getInstance();
        jsonManagement.loadJsonData(jsonDataFile);
        ParserJson rawData = JsonIterator.deserialize(jsonManagement.getJsonData(), ParserJson.class);
        Parser data = jsonManagement.dataConverter(rawData);
        fireStationService.addFireStations(data.getFirestations());
        personService.addPersons(data.getPersons());
    }
}
