package org.aapsasha.consoleapp;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;

public class ConsoleApp {
    public static void main(String[] args) throws IOException, XMLStreamException {
        ParserApplication app = new ParserApplication();
        app.run();
    }
}
