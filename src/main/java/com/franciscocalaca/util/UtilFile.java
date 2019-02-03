package com.franciscocalaca.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class UtilFile {

   private static void fillFileList(List<File> files, File node){
      if(node.isFile()){
         files.add(node.getAbsoluteFile());
      }

      if(node.isDirectory()){
         for(File subNode : node.listFiles()) {
            fillFileList(files, subNode);
         }
      }
   }
   
   public static List<File> listFiles(File node){
      List<File> result = new ArrayList<File>();
      fillFileList(result, node);
      return result;
   }

}
