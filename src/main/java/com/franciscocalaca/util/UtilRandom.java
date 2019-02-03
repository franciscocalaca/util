package com.franciscocalaca.util;

import java.util.Random;

public class UtilRandom {

   private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";  
   
   public static String getStringRandom(int qtd) {
      int size = CHARACTERS.length();
      Random rand = new Random();
      StringBuffer sb = new StringBuffer();
      for (int i = 0; i < qtd; i++) {
         sb.append(CHARACTERS.charAt(rand.nextInt(size)));
      }
      return sb.toString();
   }
   
}
