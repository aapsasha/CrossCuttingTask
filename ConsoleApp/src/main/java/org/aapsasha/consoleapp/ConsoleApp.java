package org.aapsasha.consoleapp;

import org.aapsasha.businessLogic.Calculation.CalculationContext;
import org.aapsasha.businessLogic.Calculation.WithoutRegEx.ExpressionParser;
import org.aapsasha.businessLogic.InputOutput.FileExtensions;
import org.aapsasha.businessLogic.InputOutput.InputConvert.ReadingManager;
import org.aapsasha.businessLogic.InputOutput.OutputConvert.Encrypting.AliceEncrypter;
import org.aapsasha.businessLogic.InputOutput.OutputConvert.Packing.Jar.JarPacker;
import org.aapsasha.businessLogic.InputOutput.OutputConvert.Packing.ZIP.ZipPacker;
import org.aapsasha.businessLogic.InputOutput.OutputConvert.WritingManager;

import javax.xml.stream.XMLStreamException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConsoleApp {
    public static void main(String[] args) throws IOException, XMLStreamException {

        System.out.println("Enter input File full name");
        Scanner consoleScan = new Scanner(System.in);
        File expressionsFile = new File(consoleScan.next());

        ReadingManager readManager = new ReadingManager();
        List<String> expressionsList = readManager.read(expressionsFile,null);

        CalculationContext context = new CalculationContext();
        context.setStrategy(new ExpressionParser());
        List<String> resultList =new ArrayList<>();
        for(String i : expressionsList){
            resultList.add(context.calculateExpression(i));
        }
        File resultFile =null;
        while(true){
            System.out.println("enter output file type, allowed types: xml,json,txt");
            String typeString = consoleScan.next();
            WritingManager writingManager = new WritingManager();
            FileExtensions fileType;
            try{
                fileType = writingManager.getFileType(typeString);
            } catch (IllegalArgumentException e){
                System.out.println("wrong file type!");
                continue;
            }
            resultFile = writingManager.write(resultList,fileType);
            break;
        }

        AliceEncrypter aliceEncrypter= new AliceEncrypter();
        while(true) {
            System.out.println("do you want to convert file? y/n");
            String answer = consoleScan.next();
            if ("n".equals(answer)) {
                System.out.println("result file name: " + resultFile.getName());
                break;
            }
            if ("y".equals(answer)) {
                while (true) {
                    System.out.println("Encrypt or pack? e/p");
                    String action = consoleScan.next();
                    if ("e".equals(action)) {
                        while(true){
                            System.out.println("algorithm AES or DES? a/d");
                            String encryptType = consoleScan.next();
                            if("a".equals(encryptType)){
                                resultFile = aliceEncrypter.encryptAES(resultFile);
                                break;
                            }else if("d".equals(encryptType)){
                                resultFile = aliceEncrypter.encryptDES(resultFile);
                                break;
                            }
                        }
                        break;
                    } else if ("p".equals(action)) {
                        while (true) {
                            System.out.println("zip or jar? z/j");
                            String packType = consoleScan.next();
                            if ("z".equals(packType)) {
                                resultFile = ZipPacker.pack(resultFile);
                                break;
                            } else if ("j".equals(packType)) {
                                resultFile = JarPacker.pack(resultFile);
                                break;
                            }
                        }
                        break;
                    }
                }
            }
        }
    }
}
