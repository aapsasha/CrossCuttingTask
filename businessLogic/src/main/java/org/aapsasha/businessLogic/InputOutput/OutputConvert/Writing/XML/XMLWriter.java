package org.aapsasha.businessLogic.InputOutput.OutputConvert.Writing.XML;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class XMLWriter {
    public static File write(File file, List<String> results) throws IOException, XMLStreamException {
        XMLOutputFactory output = XMLOutputFactory.newInstance();
        XMLStreamWriter writer = output.createXMLStreamWriter(new FileWriter(file));
        writer.writeStartDocument("1.0");
        writer.writeStartElement("expressionsResults");
        for (String result : results) {
            writer.writeStartElement("result");
            writer.writeCharacters(result);
            writer.writeEndElement();
        }
        writer.writeEndElement();
        writer.writeEndDocument();
        writer.flush();
        writer.close();
        return file;
    }
}
