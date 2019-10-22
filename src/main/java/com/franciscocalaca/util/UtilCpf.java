package com.franciscocalaca.util;

public abstract class UtilCpf extends Object {

   @Deprecated
   /**
    * Utilize UtilCpfCnpj.formatarMascaraCpfCnpj
    * @param cpfStr
    * @return
    */
   public static String mascararCpf(String cpfStr){
      String cpf = UtilTexto.removerCaracteresNaoAlfaNumericos(cpfStr);
      return UtilTexto.formatarComMascara("###.###.###-##", cpf, true);
   }

   private static String calcularDigitoVerificador(String num) {
      int primDig, segDig;
      int soma = 0, peso = 10;
      for (int i = 0; i < num.length(); i++){
         soma += Integer.parseInt(num.substring(i, i + 1)) * peso--;
      }

      if (soma % 11 == 0 || soma % 11 == 1){
         primDig = 0;
      } else{
         primDig = 11 - (soma % 11);
      }

      soma = 0;
      peso = 11;
      for (int i = 0; i < num.length(); i++){
         soma += Integer.parseInt(num.substring(i, i + 1)) * peso--;
      }

      soma += primDig * 2;
      if (soma % 11 == 0 | soma % 11 == 1){
         segDig = 0;
      } else{
         segDig = 11 - (soma % 11);
      }

      return String.valueOf(primDig) + String.valueOf(segDig);
   }

   public static String gerarCpf() {
      StringBuffer iniciais = new StringBuffer();
      int numero;
      for (int i = 0; i < 9; i++) {
         numero = (int) (Math.random() * 10);
         iniciais.append(String.valueOf(numero));
      }
      iniciais.append(calcularDigitoVerificador(iniciais.toString()));
      return iniciais.toString();
   }

   @Deprecated
   /**
    * Utilize UtilCpfCnpj.validar
    * @param cpfStr
    * @return
    */
   public static boolean validarCpf(String cpf) {
      try {
         String cpfSemMascara = UtilTexto.removerCaracter(cpf, new char[]{'.', '-'});

         if (cpfSemMascara.length() != 11) {
            return false;
         }

         String numDig = cpfSemMascara.substring(0, 9);
         return calcularDigitoVerificador(numDig).equals(cpfSemMascara.substring(9, 11));
      } catch (Exception e) {
         return false;
      }
   }


}