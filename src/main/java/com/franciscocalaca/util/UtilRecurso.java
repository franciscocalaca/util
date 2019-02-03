package com.franciscocalaca.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;


public abstract class UtilRecurso {

   public static ClassLoader getClassLoader(){
      ClassLoader loader = Thread.currentThread().getContextClassLoader();
      if(loader == null){
         loader = ClassLoader.getSystemClassLoader();
      }
      return loader;
   }

   public static String getCaminhoRecurso(){
      return getClassLoader().getResource("").getPath();
   }

   public static String getCaminhoRecurso(String pacoteClasse){
      StringBuffer sb = new StringBuffer(getCaminhoRecurso());
      sb.append(convertePacoteDiretorio(pacoteClasse));
      return sb.toString();
   }

   public static String getCaminhoRecurso(String pacoteClasse, String extensao){
      StringBuffer sb = new StringBuffer(getCaminhoRecurso(pacoteClasse));
      sb.append(".").append(extensao);
      return sb.toString();
   }

   public static String convertePacoteDiretorio(String pacote){
      StringBuffer sb = new StringBuffer(pacote);
      for(int i = 0; i < sb.length(); i++){
         char caracter = sb.charAt(i);
         if(caracter == '.'){
            sb.replace(i, i+1, "/");
         }
      }
      return sb.toString();
   }

   public static InputStream getInputStreamRecursoDiretorio(String pacoteClasse) {
      try {
         return getClassLoader().getResourceAsStream(pacoteClasse);
      } catch (Exception e) {
         Log.error(e.getMessage(), e);
         throw new FalhaException(e.getMessage(), e);
      }
   }

   public static OutputStream getOutputStreamRecurso(String pacoteClasse) throws FalhaException{
      File file = new File(getCaminhoRecurso(pacoteClasse));
      try {
         return new FileOutputStream(file);
      } catch (FileNotFoundException e) {
         Log.error("erro ao buscar propriedades", e);
         throw new FalhaException(e.getMessage(), e);
      }
   }

   public static OutputStream getOutputStreamRecurso(String pacoteClasse, String extensao) throws FalhaException{
      File file = new File(getCaminhoRecurso(pacoteClasse));
      try {
         return new FileOutputStream(file);
      } catch (FileNotFoundException e) {
         Log.error("erro ao buscar propriedades", e);
         throw new FalhaException(e.getMessage(), e);
      }
   }

   public static Properties getProperties(String pacoteArquivo) throws FalhaException{
      Properties prop = new Properties();
      InputStream is = getInputStreamRecursoDiretorio(pacoteArquivo);
      try {
         if(is != null){
            prop.load(is);
            is.close();
         }else{
            throw new FalhaException("Arquivo '" + pacoteArquivo + ".properties' não encontrado.");
         }
      } catch (IOException e) {
         Log.error("erro ao buscar propriedades: " + pacoteArquivo, e);
         throw new FalhaException(e.getMessage(), e);
      } catch (RuntimeException e) {
         Log.error("erro ao buscar propriedades: " + pacoteArquivo, e);
         throw new FalhaException(e.getMessage(), e);
      }
      return prop;        
   }
}
