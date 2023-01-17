package org.aapsasha.businessLogic.InputOutput.InputConvert.Reading.JSON;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JSONReader {


    public static List<String> read(File file) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<JSONHelpClass> tmp =  objectMapper.readValue(file, new TypeReference<>(){});
        List<String> data = new ArrayList<>();
        for(JSONHelpClass str : tmp){
            data.add(str.expression);
        }
        return data;
    }
}