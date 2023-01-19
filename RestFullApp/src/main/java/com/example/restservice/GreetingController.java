package com.example.restservice;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.stream.XMLStreamException;

@RestController
public class GreetingController {

    private static final String template = "result file: %s!";
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/parsing")
    public Parsing parsing(@RequestParam(value = "inputFileName", defaultValue = "TXTtest.txt") String inputFileName,
                           @RequestParam(value = "outputFileType", defaultValue = "txt") String outputFileType,
                           @RequestParam(value = "convert", defaultValue = "") String convert) throws XMLStreamException, IOException {

        ParserApplication parserApplication = new ParserApplication();
        File result = parserApplication.run(inputFileName,outputFileType,convert);
        return new Parsing(counter.incrementAndGet(), String.format(template,result.getName()));
    }
}