package org.aapsasha.businessLogic.InputOutput.OutputConvert.Writing.TXT;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class TXTWriter {
    public static File write(File file, List<String> results){

        try(FileWriter writer = new FileWriter(file, false))
        {
            results.forEach(str -> {
                try {
                    writer.write(str);
                    writer.write("\n");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

            writer.flush();
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }
        return file;
    }
}
