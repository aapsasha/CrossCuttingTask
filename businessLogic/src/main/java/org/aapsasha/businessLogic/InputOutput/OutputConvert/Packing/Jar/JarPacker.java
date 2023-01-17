package org.aapsasha.businessLogic.InputOutput.OutputConvert.Packing.Jar;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JarPacker {
    public static File pack(File file) {
        Pattern pattern = Pattern.compile("^(.+)\\.(.+)$");
        Matcher matcher = pattern.matcher(file.getName());
        boolean matchFound = matcher.find();
        File result = new File(matcher.group(1) + ".jar");
        try(JarOutputStream jout = new JarOutputStream(new FileOutputStream(result));
            FileInputStream fis= new FileInputStream(file))
        {
            JarEntry entry1=new JarEntry(file.getName());
            jout.putNextEntry(entry1);
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            jout.write(buffer);
            jout.closeEntry();
        }
        catch(Exception ex){

            System.out.println(ex.getMessage());
        }
        return result;
    }
}
