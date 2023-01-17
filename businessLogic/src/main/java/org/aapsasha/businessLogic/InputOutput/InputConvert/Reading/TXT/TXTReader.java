package org.aapsasha.businessLogic.InputOutput.InputConvert.Reading.TXT;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TXTReader {
    public static List<String> read(File file) throws IOException {
        List<String> stringList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file)))  {
            String line = reader.readLine();
            while (line != null) {
                stringList.add(line);
                line = reader.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return stringList;
    }
}
