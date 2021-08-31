package com.noelh.safetynetalerts.json;

import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStreamReader;

public class JsonManagement {
    private static JsonManagement instance;

    private String jsonData="";

    public static JsonManagement getInstance(){
        if (instance == null){
            instance = new JsonManagement();
        }
        return instance;
    }

    private JsonManagement(){}

    public String loadJsonData(Resource jsonDataFile) throws IOException {
        try(InputStreamReader inputStreamReader = new InputStreamReader(jsonDataFile.getInputStream())){
            int data = inputStreamReader.read();
            while(data != -1) {
                jsonData = jsonData+(char)data;
                data = inputStreamReader.read();
            }
            return jsonData;
        }
    }
}
