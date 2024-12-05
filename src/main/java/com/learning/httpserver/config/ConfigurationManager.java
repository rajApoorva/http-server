package com.learning.httpserver.config;

import com.fasterxml.jackson.databind.JsonNode;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.learning.httpserver.utils.Json;
public class ConfigurationManager {
    private static ConfigurationManager myConfigurationManager;
    public  static Configuration myCurrentConfiguration;
    private ConfigurationManager() {

    }

    public static ConfigurationManager getInstance() {
        if(myConfigurationManager == null){
            myConfigurationManager = new ConfigurationManager();
        }
        return myConfigurationManager;
    }

    public void loadConfigurationFile (String filePath) throws IOException {
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(filePath);
        } catch (FileNotFoundException e) {
            throw new HttpConfigurationException(e);
        }
        StringBuffer sb = new StringBuffer();
        int i;
        try{
            while(( i = fileReader.read()) != -1) {
                sb.append((char)i);
            }
        } catch (IOException e) {
            throw new HttpConfigurationException(e);
        }
        JsonNode conf = null;
        try{
            conf = Json.parse(sb.toString());
        } catch (IOException e) {
            throw new HttpConfigurationException(e);
        }

        myCurrentConfiguration = Json.fromJson(conf, Configuration.class);
    }

    public Configuration getCurrentConfiguration() throws IOException {
        if (myCurrentConfiguration == null) {
            throw new HttpConfigurationException ();
        }
        return myCurrentConfiguration;
    }
}
