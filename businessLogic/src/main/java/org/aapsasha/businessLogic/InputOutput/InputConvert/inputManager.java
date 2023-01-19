package org.aapsasha.businessLogic.InputOutput.InputConvert;

import org.aapsasha.businessLogic.InputOutput.FileExtensions;
import org.aapsasha.businessLogic.InputOutput.InputConvert.Decrypting.AliceDecrypter;
import org.aapsasha.businessLogic.InputOutput.InputConvert.Reading.JSON.JSONReader;
import org.aapsasha.businessLogic.InputOutput.InputConvert.Reading.TXT.TXTReader;
import org.aapsasha.businessLogic.InputOutput.InputConvert.Reading.XML.XMLReader;
import org.aapsasha.businessLogic.InputOutput.InputConvert.Unpacking.Jar.JarUnpacker;
import org.aapsasha.businessLogic.InputOutput.InputConvert.Unpacking.ZIP.ZipUnpacker;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.aapsasha.businessLogic.InputOutput.FileExtensions.*;

public class inputManager {

    AliceDecrypter aliceDecrypter;

    public inputManager(){
        aliceDecrypter = new AliceDecrypter();
    }


    public File createCopy(File file) throws IOException {
        File copy = new File("copy" + file.getName());
        FileUtils.copyFile(file, copy);
        return copy;
    }

    FileExtensions getFileExtension(File file) {
        Pattern pattern = Pattern.compile("^.*\\.(.+)$");
        Matcher matcher = pattern.matcher(file.getName());
        boolean matchFound = matcher.find();
        String Type = matcher.group(1);
        return switch(Type){
            case "json" -> json;
            case "txt" -> txt;
            case "xml" -> xml;
            case "zip" -> zip;
            case "jar" -> jar;
            default -> throw new IllegalArgumentException("wrong file type");
        };
    }
    public List<String> read(File file,List<File> passwordList) throws IOException {
        file = aliceDecrypter.decrypt(file, passwordList);

        return switch (getFileExtension(file)){
            case json -> JSONReader.read(file);
            case txt -> TXTReader.read(file);
            case xml -> XMLReader.read(file);
            case zip -> read(ZipUnpacker.unpack(file),passwordList);
            case jar -> read(JarUnpacker.unpack(file),passwordList);
        };
    }
}
