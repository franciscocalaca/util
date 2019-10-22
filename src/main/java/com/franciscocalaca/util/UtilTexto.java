package com.franciscocalaca.util;

import java.text.Normalizer;

/**
 * Utilidades gerais para trabalhos com texto
 * @author chico
 *
 */
public abstract class UtilTexto {

   /**
    * Remove caracteres de um texto informado. Exemplo: removerCaracter("teste", {'e'}) retorna tst
    * 
    * @param texto texto a ter os caracteres removidos
    * @param caracteresRemover os caracteres que serao removidos do texto
    * @return retorna o texto com os caracteres informados removidos
    */
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

   /**
    * substitui em um texto especificado todos os caracateres informados no array de char por uma String
    * informada em substituirPor
    * 
    * @param texto
    * @param caracteres
    * @param substituirPor
    * @return
    */
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

   /**
    * Remove aspas em qualquer posicao de um texto
    * @param texto
    * @return
    */
   public static String removerAspas(String texto) {
      return removerCaracter(texto, new char[] { '"' });
   }

   /**
    * retorna o metodo get equivalente ao atributo informado. Exemplo: 
    * getMetodoGet(endereco) retornara getEndereco
    * @param atributo
    * @return
    */
   public static String getMetodoGet(String atributo) {
      StringBuffer attr = new StringBuffer(atributo);
      char c = attr.charAt(0);
      attr.replace(0, 1, Character.toString(c).toUpperCase());
      return "get" + attr.toString();
   }

   /**
    * retorna o metodo set equivalente ao atributo informado. Exemplo: 
    * getMetodoGet(endereco) retornara setEndereco
    * @param atributo
    * @return
    */
   public static String getMetodoSet(String atributo) {
      StringBuffer attr = new StringBuffer(atributo);
      char c = attr.charAt(0);
      attr.replace(0, 1, Character.toString(c).toUpperCase());
      return "set" + attr.toString();
   }

   /**
    * Completa espacos em branco em uma string.
    * @param texto
    * @param tamanhoTotal
    * @param formatarEsquerda quando true o texto sera formatado a esquerda.
    * false o texto sera formatado a direita
    * @return
    */
   public static String completarEspacoBranco(String texto, int tamanhoTotal, boolean formatarEsquerda){
      return String.format("%"+(formatarEsquerda ? "-" : "") + tamanhoTotal + "s", texto);
   }

   /**
    * Remove aspas do inicio e do final do texto. Aspas no meio do texto nao sao removidas
    * @param texto
    * @return
    */
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

   /**
    * Completa o texto com o caracter c informado na quantidade tamanhoTotal 
    * @param c
    * @param tamanhoTotal
    * @param texto
    * @return
    */
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


   /**
    * Remove os acentos do texto informado
    * @param texto
    * @return
    */
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

   /**
    * remove caracteres nao alfanumericos de um texto. Obs.: não remove os acentos
    * @param texto
    * @return
    */
   public static String removerCaracteresNaoAlfaNumericos(String texto){
      return texto.replaceAll("[^A-Za-zÀ-ú0-9 ]", "");
   }

   /**
    * Trunca um texto para um tamanho maximo especificado.
    * <pre>
    * String retorno = truncarTamanhoMaximo("teste", 10)
    * 
    * retorno sera "teste"
    * 
    * 
    * String retorno = truncarTamanhoMaximo("testando", 5)
    * 
    * retorno será "testa"
    * </pre>
    * 
    * @param texto
    * @param tamanho
    * @return
    */
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

   /**
    * Formata um texto com uma mascara específica
    * Exemplo de uso:
    * <pre>
    * String cpfFormatado = getValueMaskFormat("###.###.###-##", "12345678912", true);
    * </pre>
    * @param mascara
    * @param texto
    * @param retornaVazio
    * @return
    */
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
