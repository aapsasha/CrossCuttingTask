package org.aapsasha.businessLogic.InputOutput.InputConvert.Decrypting;

import com.rockaport.alice.Alice;
import com.rockaport.alice.AliceContext;
import com.rockaport.alice.AliceContextBuilder;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AliceDecrypter {
    private int passwordID;
    public AliceDecrypter(){
        passwordID = 1;
    }

    public File decrypt(File file, List<File> passwordList) throws IOException {
        Pattern pattern = Pattern.compile("^(.*)(.{3})\\.(.+)$");
        Matcher matcher = pattern.matcher(file.getName());
        boolean matchFound = matcher.find();
        if(!matchFound){
            return file;
        }
        String password = null;
        if(passwordList != null){
            for(File passFile: passwordList){
                if(passFile.getName().equals("password" + passwordID + ".txt")){
                    try(BufferedReader reader = new BufferedReader(new FileReader(passFile))) {
                        password = reader.readLine();
                        break;
                    } catch (FileNotFoundException e){
                        System.err.println("no password found");
                        throw new RuntimeException();
                    }
                }
            }
        }
        else{
            try(BufferedReader reader = new BufferedReader(new FileReader("password" + passwordID + ".txt"))) {
                password = reader.readLine();
            } catch (FileNotFoundException e){
                System.err.println("no password found");
                throw new RuntimeException();
            }
        }
        File tmpResult = null;
        try{
            switch ((matcher.group(2))) {
                case "AES": {
                    tmpResult = decryptAES(file, password);
                    break;
                }
                case "DES": {
                    tmpResult = decryptDES(file, password);
                    break;
                }
                default:
                    return file;
            }
        } catch (IOException | GeneralSecurityException e){
            return file;
        }

        passwordID++;
        File result = new File(matcher.group(1) + "." + matcher.group(3));
        FileUtils.copyFile(tmpResult, result);
        return result;
    }
    public File decryptAES(File file,String password) throws GeneralSecurityException, IOException {
        File tmp;

        Alice alice = new Alice(new AliceContextBuilder().build());
        tmp = new File("tmp" + file.getName());
        alice.decrypt(file, tmp, password.toCharArray());
        return tmp;
    }
    public File decryptDES(File file,String password) throws GeneralSecurityException, IOException {
        File tmp;

        Alice alice = new Alice(new AliceContextBuilder()
                .setAlgorithm(AliceContext.Algorithm.DES)
                .setMode(AliceContext.Mode.CBC) // or AliceContext.Mode.CTR
                .setIvLength(8)
                .build());

        tmp = new File("tmp" + file.getName());
        alice.decrypt(file, tmp, password.toCharArray());
        return tmp;
    }
}
