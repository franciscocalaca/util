package com.franciscocalaca.util;

import java.text.Normalizer;

/**
 * Utilidades gerais para trabalhos com texto
 * @author chico
 *
 */
public abstract class UtilTexto {

   public static String removerCaracter(String texto, char ... caracteresRemover) {
      if(texto == null){
         return "";
      }
      char[] novoTexto = texto.toCharArray();
      StringBuffer resp = new StringBuffer();
      for (char c : novoTexto) {
         boolean remover = false;

         for (char cremov : caracteresRemover) {
            if (c == cremov) {
               remover = true;
            }
         }

         if (!remover) {
            resp.append(c);
         }
      }
      return resp.toString();
   }

   public static String substituirCaracteres(String texto, char [] caracteres, String substituirPor){
      if(texto == null){
         return "";
      }
      char[] novoTexto = texto.toCharArray();
      StringBuffer resp = new StringBuffer();
      for (char c : novoTexto) {
         boolean substituir = false;

         for (char cremov : caracteres) {
            if (c == cremov) {
               substituir = true;
            }
         }

         if (substituir) {
            resp.append(substituirPor);
         }else{
            resp.append(c);
         }
      }
      return resp.toString();
   }

   public static String removerAspas(String texto) {
      return removerCaracter(texto, new char[] { '"' });
   }

   public static String getMetodoGet(String atributo) {
      StringBuffer attr = new StringBuffer(atributo);
      char c = attr.charAt(0);
      attr.replace(0, 1, Character.toString(c).toUpperCase());
      return "get" + attr.toString();
   }

   public static String getMetodoSet(String atributo) {
      StringBuffer attr = new StringBuffer(atributo);
      char c = attr.charAt(0);
      attr.replace(0, 1, Character.toString(c).toUpperCase());
      return "set" + attr.toString();
   }

   public static String completarEspacoBranco(String texto, int tamanhoTotal, boolean formatarEsquerda){
      return String.format("%"+(formatarEsquerda ? "-" : "") + tamanhoTotal + "s", texto);
   }

   public static String removerAspasInicioFinal(String texto){
      if(texto != null && texto.length() > 0){
         char [] t = texto.toCharArray();
         int inicio = 0;
         int fim = t.length - 1;
         if(t[inicio] == '"'){
            inicio ++;
         }

         if(t[fim] == '"'){
            fim --;
         }

         char [] resp = new char[fim - inicio + 1];
         for(int i = inicio; i <= fim; i++){
            resp[i - inicio] = t[i]; 
         }
         return new String(resp);
      }else{
         return "";
      }
   }

   public static String completarStringEsquerda(char c, int tamanhoTotal, String texto){
      StringBuffer sb = new StringBuffer();
      int qtdCompletar = tamanhoTotal - texto.length();
      if(qtdCompletar > 0){
         for(int i = 0; i < qtdCompletar; i++){
            sb.append(c);
         }
         sb.append(texto);
         return sb.toString();
      }
      return texto;
   }


   public static String removerAcentos(String texto) {
      if (texto == null) {
         return "";
      } else {
         String textoSemEspaco = texto.trim();
         textoSemEspaco = Normalizer.normalize(textoSemEspaco, Normalizer.Form.NFD);
         textoSemEspaco = textoSemEspaco.replaceAll("[^\\p{ASCII}]", "");
         return textoSemEspaco;
      }
   }

   public static String removerCaracteresNaoAlfaNumericos(String texto){
      return texto.replaceAll("[^A-Za-zÀ-ú0-9 ]", "");
   }

   public static String truncarTamanhoMaximo(String texto, int tamanho){
      if(texto != null){

         if(texto.length() > tamanho){
            return texto.substring(0, tamanho);
         }else{
            return texto;
         }
      }else{
         return null;
      }
   }

   public static String formatarComMascara(String mascaraFormatar, String textoFormatar, boolean retornaVazio) {
      String texto = textoFormatar;
      String mascara = mascaraFormatar;
      /*
       * Verifica se se foi configurado para nao retornar a mascara se a string
       * for nulo ou vazia se nao retorna somente a mascara.
       */
      if (retornaVazio && texto == null){
         return "";
      }else if(texto == null){
         return null;
      }

      String caracteresRemover = mascara.replaceAll("#", "");
      char [] cr = caracteresRemover.toCharArray();
      texto = removerCaracter(texto, cr);

      /*
       * Formata valor com a mascara passada
       */
      for (int i = 0; i < texto.length(); i++) {
         mascara = mascara.replaceFirst("#", texto.substring(i, i + 1));
      }

      /*
       * Subistitui por string vazia os digitos restantes da mascara quando o
       * valor passado é menor que a mascara
       */
      return mascara.replaceAll("#", "");
   }

   public static String removerEspacosDuplos(String texto){
      return texto == null ? null : texto.replaceAll("\\s+", " ").trim();
   }

	public static String removerCaracteresRegex(String regexPattern, String texto){
		String remover = texto.replaceAll(String.format("[%s]", regexPattern), "");
		String ret = UtilTexto.removerCaracter(texto, remover.toCharArray());
		ret = UtilTexto.removerEspacosDuplos(ret);
		return ret.toString();
	}

}
