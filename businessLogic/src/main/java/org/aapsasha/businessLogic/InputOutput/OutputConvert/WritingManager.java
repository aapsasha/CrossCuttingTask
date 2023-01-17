package org.aapsasha.businessLogic.InputOutput.OutputConvert;

import org.aapsasha.businessLogic.InputOutput.FileExtensions;
import org.aapsasha.businessLogic.InputOutput.OutputConvert.Writing.JSON.JSONWriter;
import org.aapsasha.businessLogic.InputOutput.OutputConvert.Writing.TXT.TXTWriter;
import org.aapsasha.businessLogic.InputOutput.OutputConvert.Writing.XML.XMLWriter;

import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.aapsasha.businessLogic.InputOutput.FileExtensions.*;

public class WritingManager {
    public WritingManager(){}
    public FileExtensions getFileType(String type) throws IllegalArgumentException{
        return switch(type){
            case "json" -> json;
            case "txt" -> txt;
            case "xml" -> xml;
            default -> throw new IllegalArgumentException("wrong file type");
        };
    }
    public File write(List<String> resultsList, FileExtensions type) throws IOException, XMLStreamException {

        File resultFile = new File("results." + type.name());
        return switch(type){
            case zip -> null;//never happening
            case jar -> null;//never happening
            case txt -> TXTWriter.write(resultFile, resultsList);
            case json -> JSONWriter.write(resultFile, resultsList);
            case xml -> XMLWriter.write(resultFile, resultsList);
        };
    }
}
