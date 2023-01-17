package org.aapsasha.businessLogic.InputOutput.InputConvert.Unpacking.Jar;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

public class JarUnpacker {
    public static File unpack(File file) {
        File result = null;
        try(JarInputStream jin = new JarInputStream(new FileInputStream(file)))
        {
            JarEntry entry;
            String name;
            long size;

            while((entry=jin.getNextJarEntry())!=null){

                name = entry.getName();
                size=entry.getSize();

                result = new File(name);
                FileOutputStream fout = new FileOutputStream(result);
                for (int c = jin.read(); c != -1; c = jin.read()) {
                    fout.write(c);
                }
                fout.flush();
                jin.closeEntry();
                fout.close();
            }

        }
        catch(Exception ex){

            System.out.println(ex.getMessage());
        }
        return result;
    }
}
