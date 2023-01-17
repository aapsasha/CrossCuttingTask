package org.aapsasha.businessLogic.InputOutput.OutputConvert.Writing.JSON;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.ListIterator;

public class JSONWriter {
    public static File write(File file, List<String> resultList) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ListIterator<String> it =resultList.listIterator();
        FileWriter writer = new FileWriter(file, false);
        writer.write("[\n");
        while(it.hasNext()){
            String json = objectMapper.writeValueAsString(it.next());
            writer.write(json);
            if(it.hasNext()){
                writer.write(",");
            }
            writer.write("\n");
        }
        writer.write("]");
        writer.flush();
        return file;
    }
}
