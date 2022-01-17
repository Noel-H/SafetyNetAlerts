package com.noelh.safetynetalerts.json;

import com.jsoniter.JsonIterator;
import com.noelh.safetynetalerts.firestation.FireStationService;
import com.noelh.safetynetalerts.parser.Parser;
import com.noelh.safetynetalerts.parser.ParserJson;
import com.noelh.safetynetalerts.person.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

/**
 * Load json data into the database
 */
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
        JsonManagement jsonManagement = new JsonManagement();
        jsonManagement.loadJsonData(jsonDataFile);
        ParserJson rawData = JsonIterator.deserialize(jsonManagement.getJsonData(), ParserJson.class);
        Parser data = jsonManagement.dataConverter(rawData);
        fireStationService.addFireStations(data.getFireStations());
        personService.addPersons(data.getPersons());
    }
}
