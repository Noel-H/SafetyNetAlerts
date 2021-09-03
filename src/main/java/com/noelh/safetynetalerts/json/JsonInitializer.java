package com.noelh.safetynetalerts.json;

import com.jsoniter.JsonIterator;
import com.noelh.safetynetalerts.json.jsonparser.Parser;
import com.noelh.safetynetalerts.json.jsonparser.ParserJson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;


@Component
public class JsonInitializer implements CommandLineRunner {
    @Value("${jsonfile}")
    Resource jsonDataFile;

    @Override
    public void run(String... args) throws Exception {
        JsonManagement jsonManagement = JsonManagement.getInstance();
        jsonManagement.loadJsonData(jsonDataFile);
        ParserJson rawData = JsonIterator.deserialize(jsonManagement.getJsonData(), ParserJson.class);
        Parser data = jsonManagement.dataConverter(rawData);
    }
}
