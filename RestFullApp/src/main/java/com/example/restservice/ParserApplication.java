package com.example.restservice;

import org.aapsasha.businessLogic.Calculation.CalculationContext;
import org.aapsasha.businessLogic.Calculation.Library.ExpressionParser;
import org.aapsasha.businessLogic.InputOutput.FileExtensions;
import org.aapsasha.businessLogic.InputOutput.InputConvert.inputManager;
import org.aapsasha.businessLogic.InputOutput.OutputConvert.Encrypting.AliceEncrypter;
import org.aapsasha.businessLogic.InputOutput.OutputConvert.Packing.Jar.JarPacker;
import org.aapsasha.businessLogic.InputOutput.OutputConvert.Packing.ZIP.ZipPacker;
import org.aapsasha.businessLogic.InputOutput.OutputConvert.WritingManager;

import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ParserApplication {

    org.aapsasha.businessLogic.InputOutput.InputConvert.inputManager inputManager;

    CalculationContext context;

    File resultFile;

    WritingManager writingManager;

    AliceEncrypter aliceEncrypter;

    public ParserApplication(){
        inputManager = new inputManager();
        context = new CalculationContext();
        context.setStrategy(new ExpressionParser());
        resultFile = null;
        writingManager = new WritingManager();
        aliceEncrypter = new AliceEncrypter();
    }
    public File run(String fileName,String outputFileType,String convert) throws IOException, XMLStreamException {

        List<String> expressions = inputManager.read(new File(fileName),null);

        List<String> resultList =new ArrayList<>();
        for(String i : expressions){
            resultList.add(context.calculateExpression(i));
        }

        FileExtensions fileType = writingManager.getFileType(outputFileType);
        resultFile = writingManager.write(resultList,fileType);

        converting(convert);

        return resultFile;
    }
    private void converting(String convert){

        int pointer = 3;
        while(pointer <= convert.length()){
            String currentConvert = convert.substring(pointer- 3,pointer);
            resultFile = switch(currentConvert){
                case "zip" -> ZipPacker.pack(resultFile);
                case "jar" -> JarPacker.pack(resultFile);
                case "AES" -> aliceEncrypter.encryptAES(resultFile);
                case "DES" -> aliceEncrypter.encryptDES(resultFile);
                default -> resultFile;
            };
            pointer +=3;
        }
    }
}
