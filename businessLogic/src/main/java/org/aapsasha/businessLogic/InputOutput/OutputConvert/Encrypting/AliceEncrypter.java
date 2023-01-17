package org.aapsasha.businessLogic.InputOutput.OutputConvert.Encrypting;

import com.rockaport.alice.Alice;
import com.rockaport.alice.AliceContextBuilder;
import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AliceEncrypter {

    private int passwordID;

    public AliceEncrypter(){
        passwordID = 1;
    }

    public File encryptAES(File file){
        try(BufferedReader reader = new BufferedReader(new FileReader("Password" + passwordID + ".txt"))){
            String line = reader.readLine();
            Alice alice = new Alice(new AliceContextBuilder().build());
            File tmp = new File("tmp" + file.getName());
            alice.encrypt(file,tmp, line.toCharArray());

            Pattern pattern = Pattern.compile("^(.*)\\.(.+)$");
            Matcher matcher = pattern.matcher(file.getName());
            boolean matchFound = matcher.find();
            File result =  new File (matcher.group(1) + "AES." + matcher.group(2));
            FileUtils.copyFile(tmp,result);

            passwordID++;
            return result;
        } catch (GeneralSecurityException | IOException e) {
            return file;
        }
    }

    public File encryptAES(File file, String password){
        try{
            Alice alice = new Alice(new AliceContextBuilder().build());
            File tmp = new File("tmp" + file.getName());
            alice.encrypt(file,tmp, password.toCharArray());

            Pattern pattern = Pattern.compile("^(.*)\\.(.+)$");
            Matcher matcher = pattern.matcher(file.getName());
            boolean matchFound = matcher.find();
            File result =  new File (matcher.group(1) + "AES." + matcher.group(2));
            FileUtils.copyFile(tmp,result);

            passwordID++;
            return result;
        } catch (GeneralSecurityException | IOException e) {
            return file;
        }
    }
    public File encryptDES(File file){
        try(BufferedReader reader = new BufferedReader(new FileReader("password" + passwordID + ".txt"))){
            String line = reader.readLine();
            Alice alice = new Alice(new AliceContextBuilder().build());
            File tmp = new File("tmp" + file.getName());
            alice.encrypt(file,tmp, line.toCharArray());

            Pattern pattern = Pattern.compile("^(.*)\\.(.+)$");
            Matcher matcher = pattern.matcher(file.getName());
            boolean matchFound = matcher.find();
            File result = new File (matcher.group(1) + "DES." + matcher.group(2));
            FileUtils.copyFile(tmp, result);

            passwordID++;
            return result;
        } catch (GeneralSecurityException | IOException e) {
            return file;
        }
    }

    public File encryptDES(File file,String password){
        try{
            Alice alice = new Alice(new AliceContextBuilder().build());
            File tmp = new File("tmp" + file.getName());
            alice.encrypt(file,tmp, password.toCharArray());

            Pattern pattern = Pattern.compile("^(.*)\\.(.+)$");
            Matcher matcher = pattern.matcher(file.getName());
            boolean matchFound = matcher.find();
            File result = new File (matcher.group(1) + "DES." + matcher.group(2));
            FileUtils.copyFile(tmp, result);

            passwordID++;
            return result;
        } catch (GeneralSecurityException | IOException e) {
            return file;
        }
    }
}
