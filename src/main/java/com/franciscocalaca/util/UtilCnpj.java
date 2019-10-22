package com.franciscocalaca.util;

public abstract class UtilCnpj {

   @Deprecated
   /**
    * Utilize UtilCpfCnpj.formatarMascaraCpfCnpj
    * @param cpfStr
    * @return
    */
   public static String mascararCnpj(String cnpjStr){
      String cnpj = UtilTexto.removerCaracteresNaoAlfaNumericos(cnpjStr);
      return UtilTexto.formatarComMascara("##.###.###/####-##", cnpj, true);
   }

   // 02998301000181
   @Deprecated
   /**
    * Utilize UtilCpfCnpj.validar
    * @param cpfStr
    * @return
    */
   public static boolean validarCnpj(String cnpj) {
      try {
         int soma = 0; 
         int dig;

         String cnpjStr = UtilTexto.removerCaracter(cnpj, new char[]{'.','-','/'});


         if (cnpjStr.length() != 14){
            return false;
         }

         String cnpj_calc = cnpjStr.substring(0, 12);

         char[] cnpjArray = cnpjStr.toCharArray();

         for (int i = 0; i < 4; i++){
            if (cnpjArray[i] - 48 >= 0 && cnpjArray[i] - 48 <= 9){
               soma += (cnpjArray[i] - 48) * (6 - (i + 1));
            }
         }

         for (int i = 0; i < 8; i++){
            if (cnpjArray[i + 4] - 48 >= 0 && cnpjArray[i + 4] - 48 <= 9){
               soma += (cnpjArray[i + 4] - 48) * (10 - (i + 1));
            }
         }

         dig = 11 - (soma % 11);

         cnpj_calc += (dig == 10 || dig == 11) ? "0" : Integer.toString(dig);

         soma = 0;
         for (int i = 0; i < 5; i++){
            if (cnpjArray[i] - 48 >= 0 && cnpjArray[i] - 48 <= 9){
               soma += (cnpjArray[i] - 48) * (7 - (i + 1));
            }
         }

         for (int i = 0; i < 8; i++){
            if (cnpjArray[i + 5] - 48 >= 0 && cnpjArray[i + 5] - 48 <= 9){
               soma += (cnpjArray[i + 5] - 48) * (10 - (i + 1));
            }
         }

         dig = 11 - (soma % 11);
         cnpj_calc += (dig == 10 || dig == 11) ? "0" : Integer.toString(dig);

         return cnpjStr.equals(cnpj_calc);
      } catch (RuntimeException e) {
         return false;
      }
   }

}