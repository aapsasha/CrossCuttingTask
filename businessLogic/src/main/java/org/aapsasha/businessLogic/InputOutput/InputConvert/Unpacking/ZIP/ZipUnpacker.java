package org.aapsasha.businessLogic.InputOutput.InputConvert.Unpacking.ZIP;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZipUnpacker {
    public static File unpack(File file){
        File result = null;
        try(ZipInputStream zin = new ZipInputStream(new FileInputStream(file)))
        {
            ZipEntry entry;
            String name;
            long size;
            while((entry=zin.getNextEntry())!=null){

                name = entry.getName();
                size=entry.getSize();

                result = new File(name);
                FileOutputStream fout = new FileOutputStream(result);
                for (int c = zin.read(); c != -1; c = zin.read()) {
                    fout.write(c);
                }
                fout.flush();
                zin.closeEntry();
                fout.close();

            }
        }
        catch(Exception ex){

            System.out.println(ex.getMessage());
        }
        return result;
    }

}
