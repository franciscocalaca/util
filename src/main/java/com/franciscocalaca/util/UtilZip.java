package com.franciscocalaca.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class UtilZip {
   
   public static void compress(InputStream is, OutputStream os) {
      try {
         String sourceFile = "file";
         ZipOutputStream zipOut = new ZipOutputStream(os);
         ZipEntry zipEntry = new ZipEntry(sourceFile);
         zipOut.putNextEntry(zipEntry);
         final byte[] bytes = new byte[1024];
         int length;
         while((length = is.read(bytes)) >= 0) {
             zipOut.write(bytes, 0, length);
         }
         zipOut.close();
      } catch (IOException e) {
         Log.error(e);
      }
   }

   private static boolean contains(String [] array, String file) {
      for (int i = 0; i < array.length; i++) {
         String str = array[i].toLowerCase();
         if(file.contains(str)) {
            return true;
         }
      }
      return false;
   }
   
   public static void compressDir(File dir, OutputStream os, String ... exclusions) {
      try {
         ZipOutputStream zos = new ZipOutputStream(os);
         String path = dir.getAbsolutePath();
         for(File file : UtilFile.listFiles(dir)){
            if(!contains(exclusions, file.getAbsolutePath())) {
               String name = file.getAbsolutePath().substring(path.length() + 1);
               name = name.replace("\\", "/");
               ZipEntry ze= new ZipEntry(name);
               zos.putNextEntry(ze);
               FileInputStream in = new FileInputStream(file);
               byte[] buffer = new byte[1024];
               int len;
               while ((len = in.read(buffer)) > 0) {
                  zos.write(buffer, 0, len);
               }
               in.close();
               zos.closeEntry();
            }
         }
         zos.close();
      } catch (IOException e) {
         Log.error(e);
      }      
   }
   
}
