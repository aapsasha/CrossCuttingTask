package org.aapsasha.businessLogic.InputOutput.OutputConvert.Packing.ZIP;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipPacker {
    public static File pack(File file) {
        Pattern pattern = Pattern.compile("^(.+)\\.(.+)$");
        Matcher matcher = pattern.matcher(file.getName());
        boolean matchFound = matcher.find();
        File result = new File(matcher.group(1) + ".zip");
        try(ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(result));
            FileInputStream fis= new FileInputStream(file))
        {
            ZipEntry entry1=new ZipEntry(file.getName());
            zout.putNextEntry(entry1);
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            zout.write(buffer);
            zout.closeEntry();
        }
        catch(Exception ex){

            System.out.println(ex.getMessage());
        }
        return result;
    }
}
