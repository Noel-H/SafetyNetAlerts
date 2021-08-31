package com.noelh.safetynetalerts.json;

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
        System.out.println(jsonManagement.loadJsonData(jsonDataFile));
    }
}
