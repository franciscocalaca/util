package com.franciscocalaca.util; 

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
/**
 *Classe para trabalho com numeros por extenso. Exemplo de uso:
 * <pre>
      public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Sintax : ...Extenso (numero)");
            return;
        }
        UtilExtenso teste = new UtilExtenso(new BigDecimal(args[0]));
        System.out.println("Numero  : "
                + (new DecimalFormat().format(Double.valueOf(args[0]))));
        System.out.println("Extenso : " + teste.toString());
    }
    </pre>

 */

public class UtilExtenso {
   
   private ArrayList<Integer> nro;
   
   private BigInteger num;

   private String qualificadores[][] = { { "centavo", "centavos" },
         { "", "" }, { "mil", "mil" }, { "milhão", "milhões" },
         { "bilhão", "bilhões" }, { "trilhão", "trilhões" },
         { "quatrilhão", "quatrilhões" }, { "quintilhão", "quintilhões" },
         { "sextilhão", "sextilhões" }, { "septilhão", "septilhões" } };
   private String numeros[][] = {
         { "zero", "um", "dois", "três", "quatro", "cinco", "seis", "sete",
            "oito", "nove", "dez", "onze", "doze", "treze", "quatorze",
            "quinze", "dezesseis", "dezessete", "dezoito", "dezenove" },
            { "vinte", "trinta", "quarenta", "cinquenta", "sessenta",
               "setenta", "oitenta", "noventa" },
               { "cem", "cento", "duzentos", "trezentos", "quatrocentos",
                  "quinhentos", "seiscentos", "setecentos", "oitocentos",
               "novecentos" } };

   public UtilExtenso() {
      this.nro = new ArrayList<Integer>();
   }

   /**
    * Cria um objeto a partir de um valor BigDecimal
    * @param dec valor para colocar por extenso
    */
   public UtilExtenso(BigDecimal dec) {
      this();
      setNumero(dec);
   }

   /**
    * Cria um objeto a partir de um valor double
    * 
    * @param dec valor para colocar por extenso
    */
   public UtilExtenso(double dec) {
      this();
      setNumero(dec);
   }

   /**
    * Sets the Number attribute of the Extenso object
    * 
    * @param dec The new Number value
    */
   public final void setNumero(BigDecimal dec) {
      // Converte para inteiro arredondando os centavos
      this.num = dec.setScale(2, BigDecimal.ROUND_HALF_UP).multiply(
            BigDecimal.valueOf(100)).toBigInteger();

      // Adiciona valores
      this.nro.clear();
      if (this.num.equals(BigInteger.ZERO)) {
         // Centavos
         this.nro.add(0);
         // Valor
         this.nro.add(0);
      } else {
         // Adiciona centavos
         addRemainder(100);

         // Adiciona grupos de 1000
         while (!this.num.equals(BigInteger.ZERO)) {
            addRemainder(1000);
         }
      }
   }

   public void setNumero(double dec) {
      setNumero(new BigDecimal(dec));
   }

   public String toString() {
      StringBuffer buf = new StringBuffer();

      int ct;

      for (ct = this.nro.size() - 1; ct > 0; ct--) {
         // Se ja existe texto e o atual não é zero
         if (buf.length() > 0 && !isGrupoZero(ct)) {
            buf.append(" e ");
         }
         buf.append(numToString(this.nro.get(ct).intValue(), ct));
      }
      if (buf.length() > 0) {
         if (ehUnicoGrupo()){
            buf.append(" de ");
         }
         while (buf.toString().endsWith(" ")){
            buf.setLength(buf.length() - 1);
         }
         if (isPrimeiroGrupoUm()){
            buf.insert(0, "h");
         }
         if (this.nro.size() == 2 && this.nro.get(1).intValue() == 1) {
            buf.append(getUnidadeSingular());
         } else {
            buf.append(getUnidadePlural());
         }
         if (this.nro.get(0).intValue() != 0) {
            buf.append(" e ");
         }
      }
      if (this.nro.get(0).intValue() != 0) {
         buf.append(numToString(this.nro.get(0).intValue(), 0));
      }
      return buf.toString();
   }

   protected String getUnidadePlural() {
      return " reais";
   }

   protected String getUnidadeSingular() {
      return " real";
   }

   private boolean isPrimeiroGrupoUm() {
      if (this.nro.get(this.nro.size() - 1).intValue() == 1){
         return true;
      }else{
         return false;
      }
   }

   /**
    * Adds a feature to the Remainder attribute of the Extenso object
    * 
    * @param divisor
    *            The feature to be added to the Remainder attribute
    */
   private void addRemainder(int divisor) {
      // Encontra newNum[0] = num modulo divisor, newNum[1] = num dividido
      // divisor
      BigInteger[] newNum = this.num.divideAndRemainder(BigInteger.valueOf(divisor));

      // Adiciona modulo
      this.nro.add(newNum[1].intValue());

      // Altera numero
      this.num = newNum[0];
   }

   /**
    * Description of the Method
    * 
    * @return Description of the Returned Value
    */
   private boolean ehUnicoGrupo() {
      if (this.nro.size() <= 3){
         return false;
      }
      if (!isGrupoZero(1) && !isGrupoZero(2)){
         return false;
      }
      boolean hasOne = false;
      for (int i = 3; i < this.nro.size(); i++) {
         if (this.nro.get(i).intValue() != 0) {
            if (hasOne){
               return false;
            }
            hasOne = true;
         }
      }
      return true;
   }

   private boolean isGrupoZero(int ps) {
      if (ps <= 0 || ps >= this.nro.size()){
         return true;
      }else{
         return this.nro.get(ps).intValue() == 0;
      }
   }

   /**
    * Description of the Method
    * 
    * @param numero
    *            Description of Parameter
    * @param escala
    *            Description of Parameter
    * @return Description of the Returned Value
    */
   private String numToString(int numero, int escala) {
      int unidade = (numero % 10);
      int dezena = (numero % 100); //* nao pode dividir por 10 pois verifica
      // de 0..19
      int centena = (numero / 100);
      StringBuffer buf = new StringBuffer();

      if (numero != 0) {
         if (centena != 0) {
            if (dezena == 0 && centena == 1) {
               buf.append(this.numeros[2][0]);
            } else {
               buf.append(this.numeros[2][centena]);
            }
         }

         if ((buf.length() > 0) && (dezena != 0)) {
            buf.append(" e ");
         }
         if (dezena > 19) {
            dezena /= 10;
            buf.append(this.numeros[1][dezena - 2]);
            if (unidade != 0) {
               buf.append(" e ");
               buf.append(this.numeros[0][unidade]);
            }
         } else if (centena == 0 || dezena != 0) {
            buf.append(this.numeros[0][dezena]);
         }

         buf.append(" ");
         if (numero == 1) {
            buf.append(this.qualificadores[escala][0]);
         } else {
            buf.append(this.qualificadores[escala][1]);
         }
      }
      return buf.toString();
   }
}