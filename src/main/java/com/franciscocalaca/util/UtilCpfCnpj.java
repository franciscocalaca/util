package com.franciscocalaca.util;

public abstract class UtilCpfCnpj {

   @SuppressWarnings("deprecation")
   public static boolean validar(String cpfCnpj){
      if(cpfCnpj == null){
         return false;
      }else{
         String cpfCnpjNum = retirarMascara(cpfCnpj);
         if(cpfCnpjNum.length() == 11){
            return UtilCpf.validarCpf(cpfCnpjNum);
         }else if(cpfCnpjNum.length() == 14){
            return UtilCnpj.validarCnpj(cpfCnpjNum);
         }else{
            return false;
         }
      }
   }

   @SuppressWarnings("deprecation")
   public static boolean isCpf(String cpfCnpj, boolean validar){
      if(cpfCnpj == null){
         return false;
      }else{
         String cpfCnpjNum = retirarMascara(cpfCnpj);
         if(cpfCnpjNum.length() == 11){
            return validar ? UtilCpf.validarCpf(cpfCnpjNum) : true;
         }else{
            return false;
         }
      }
   }

   @SuppressWarnings("deprecation")
   public static boolean isCnpj(String cpfCnpj, boolean validar){
      if(cpfCnpj == null){
         return false;
      }else{
         String cpfCnpjNum = retirarMascara(cpfCnpj);
         if(cpfCnpjNum.length() == 14){
            return validar ? UtilCnpj.validarCnpj(cpfCnpjNum) : true;
         }else{
            return false;
         }
      }
   }

   public static String formatarMascaraCpfCnpj(String cpfCnpj){
      if(cpfCnpj == null){
         return cpfCnpj;
      }
      String cpfCnpjNum = retirarMascara(cpfCnpj);
      if(cpfCnpjNum.length() == 11){
         return UtilTexto.formatarComMascara("###.###.###-##", cpfCnpj, true);
      }else if(cpfCnpjNum.length() == 14){
         return UtilTexto.formatarComMascara("##.###.###/####-##", cpfCnpj, true);
      }else{
         return cpfCnpj;
      }
   }

   public static String retirarMascara(String cpfCnpj) { 
      return cpfCnpj.replaceAll("[^0-9]", "");
   }



}
