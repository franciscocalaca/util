package com.franciscocalaca.util;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Classe utilizada para manipular datas
 * @author Francisco
 *
 */
public final class DateTime extends Date implements Cloneable {

   private static final long serialVersionUID = 1L;

   private final Date date;

   /**
    * Instancia um objeto à partir de uma String. Ex.: new Data("dd/MM/yyyy", "01/02/2014")
    * @param formato format of the date
    * @param data date to import
    * @throws ParseException when string date is in wrong format
    */
   public DateTime(String formato, String data) throws ParseException{
      this(new SimpleDateFormat(formato).parse(data));
   }

   /**
    * Instancia um objeto à partri de um Date
    * @param date date to create
    */
   public DateTime(Date date) {
      this.date = date;
   }

   /**
    * Instancia um objeto à partir de um Calendar
    * @param cal calendar to convert
    */
   public DateTime(Calendar cal) {
      this(cal.getTime());
   }

   /**
    * Instancia um objeto à partir de um dia, mes e ano informados
    * @param dia day of the date
    * @param mes month of the date
    * @param ano year of the date
    */
   public DateTime(int dia, int mes, int ano){
      Calendar cal = Calendar.getInstance();
      cal.set(ano, mes - 1, dia);
      cal.set(Calendar.HOUR_OF_DAY, 0);
      cal.set(Calendar.MINUTE, 0);
      cal.set(Calendar.SECOND, 0);
      cal.set(Calendar.MILLISECOND, 0);
      date = cal.getTime();
   }

   /**
    * Instancia uma data à partir de uma data atual. Utiliza new Date() para criar esta data.
    */
   public DateTime(){
      this(new Date());
   }


   /**
    * Adiciona hora em uma data. Não altera o objeto no qual é chamado. A data alterada é retornada no método.
    * @param qtdHour qtd de hour
    * @return the hour added
    */
   public DateTime addHour(int qtdHour){
      Calendar cal = Calendar.getInstance();
      cal.setTime(date);
      int hour = cal.get(Calendar.HOUR_OF_DAY);
      cal.set(Calendar.HOUR_OF_DAY, hour + qtdHour);
      return new DateTime(cal);
   }

   /**
    * Adiciona dia em uma data. Não altera o objeto no qual é chamado. A data alterada é retornada no método.
    * @param qtdDias qtd de dias
    * @return the day added
    */
   public DateTime addDay(int qtdDias){
      Calendar cal = Calendar.getInstance();
      cal.setTime(date);
      int dia = cal.get(Calendar.DAY_OF_YEAR);
      cal.set(Calendar.DAY_OF_YEAR, dia + qtdDias);
      return new DateTime(cal);
   }

   /**
    * Adiciona mês em uma data. Não altera o objeto no qual é chamado. A data alterada é retornada no método.
    * @param qtdMes qtd de mes
    * @return the month added
    */
   public DateTime addMonth(int qtdMes){
      Calendar cal = Calendar.getInstance();
      cal.setTime(date);
      int mes = cal.get(Calendar.MONTH);
      cal.set(Calendar.MONTH, mes + qtdMes);
      return new DateTime(cal);
   }

   /**
    * Retorna um objeto Data sem as informações de tempo: hora, minuto, segundo, etc
    * @return the date without time
    */
   public DateTime getDateWithoutTime(){
      Calendar cal = Calendar.getInstance();
      cal.setTime(date);
      cal.set(Calendar.HOUR_OF_DAY, 0);
      cal.set(Calendar.MINUTE, 0);
      cal.set(Calendar.SECOND, 0);
      cal.set(Calendar.MILLISECOND, 0);
      return new DateTime(cal);
   }

   /**
    * Calcula a diferença de dias entre até a dataCalc
    * @param dataCalc date to calc
    * @return the diff days
    */
   public int getDiffDays(DateTime dataCalc){
      Date dataInicio = getDateWithoutTime();
      Date dataFim = dataCalc.getDateWithoutTime();

      long t1 = dataInicio.getTime();
      long t2 = dataFim.getTime();

      long dif = t2 - t1;

      //86400000 = 1000 * 60 * 60 * 24
      long dias = dif/86400000;

      return (int) dias;
   }

   public DateTime getTomorow(){
      return addDay(1);
   }

   public DateTime getYesterday(){
      return addDay(-1);
   }

   /**
    * Retorna a data que representa o inicio do mês. Ex.: Data = 09/10/2014, retornará a data: 01/10/2014
    * @return the first day of the month
    */
   public DateTime getMonthFirstDay(){
      Calendar cal = Calendar.getInstance();
      cal.setTime(date);
      cal.set(Calendar.DAY_OF_MONTH, 1);
      return new DateTime(cal);
   }

   /**
    * Retorna a data que representa o inicio do mês. Ex.: Data = 09/10/2014, retornará a data: 31/10/2014
    * @return the last day of the month
    */
   public DateTime getMonthLastDay(){
      DateTime inicio = getMonthFirstDay();
      return inicio.addMonth(1).addDay(-1);
   }

   /**
    * Retorna a data que representa o inicio do ano. Ex.: Data = 09/10/2014, retornará a data: 01/01/2014
    * @return the first day of the year
    */
   public DateTime getYearFirstDay(){
      Calendar cal = Calendar.getInstance();
      cal.setTime(date);
      cal.set(Calendar.DAY_OF_YEAR, 1);
      return new DateTime(cal);
   }

   /**
    * Retorna a data que representa o fim do ano. Ex.: Data = 09/10/2014, retornará a data: 31/12/2014
    * @return the last day of the year
    */
   public DateTime getYearLastDay(){
      DateTime inicio = getYearFirstDay();
      return inicio.addDay(30).addMonth(11);
   }

   /**
    * Retorna uma instancia de Calendar que representa a data
    * @return date as Calendar
    */
   public Calendar getCalendar(){
      Calendar cal = Calendar.getInstance();
      cal.setTime(date);
      return cal;
   }

   /**
    * Retorna a data formatada à partir do formato especificado.
    * @param formato format of the date
    * @return date as String
    */
   public String getFormated(String formato){
      SimpleDateFormat sdf = new SimpleDateFormat(formato);
      return sdf.format(date);
   }

   /**
    * Retorna o objeto java.util.Date gerenciado por esta Data
    * @return the Date instance
    */
   public Date getDateInstance() {
      return date;
   }

   /**
    * Retorna o objeto java.sql.Date gerenciado por esta Data, ideal para lidar com JDBC 
    * @return the java.sql.Date instance
    */
   public java.sql.Date getDateSql(){
      return new java.sql.Date(date.getTime());
   }

   /**
    * Retorna o objeto java.sql.Time gerenciado por esta Data, ideal para lidar com JDBC 
    * @return the java.sql.Time instance
    */
   public Time getTimeSql(){
      return new Time(date.getTime());
   }

   /**
    * Retorna o objeto java.sql.TimestampSql gerenciado por esta Data, ideal para lidar com JDBC 
    * @return the java.sql.Timestamp instance
    */
   public Timestamp getTimestampSql(){
      return new Timestamp(date.getTime());
   }

   /**
    * Retorna o dia da data. Ex.: 26/05/2014 retornará 26
    * @return the day of the date
    */
   public int getDay(){
      return getCalendar().get(Calendar.DAY_OF_MONTH);
   }

   /**
    * Retorna o dia da data. Ex.: 26/05/2014 retornará 5
    * @return the month of the date
    */
   public int getMonth(){
      return getCalendar().get(Calendar.MONTH) + 1;
   }

   /**
    * Retorna o dia da data. Ex.: 26/05/2014 retornará 2014
    * @return the year of the date
    */
   public int getYear(){
      return getCalendar().get(Calendar.YEAR);
   }

   /**
    * Metodo que sobrescreve o de mesmo nome da classe Date, utilizado para manter compatibilidade.
    * @return the time in milliseconds
    */
   @Override
   public long getTime() {
      return date.getTime();
   }

   /**
    * Metodo que sobrescreve o de mesmo nome da classe Date, utilizado para manter compatibilidade.
    * @return true if date is after
    */
   @Override
   public boolean after(Date when) {
      return date.after(when);
   }

   /**
    * Metodo que sobrescreve o de mesmo nome da classe Date, utilizado para manter compatibilidade.
    * @return comprateTo
    */
   @Override
   public int compareTo(Date anotherDate) {
      return date.compareTo(anotherDate);
   }

   /**
    * Metodo que sobrescreve o de mesmo nome da classe Date, utilizado para manter compatibilidade.
    */
   @Override
   public boolean before(Date when) {
      return date.before(when);
   }

   /**
    * Metodo que sobrescreve o de mesmo nome da classe Date, utilizado para manter compatibilidade.
    */
   @Override
   public DateTime clone() {
      return new DateTime(new Date(date.getTime()));
   }

   /**
    * Metodo que sobrescreve o de mesmo nome da classe Date, utilizado para manter compatibilidade.
    */
   @Override
   public boolean equals(Object obj) {
      return date.equals(obj);
   }

   /**
    * Metodo que sobrescreve o de mesmo nome da classe Date, utilizado para manter compatibilidade.
    */
   @Override
   public int hashCode() {
      return date.hashCode();
   }

   @Override
   public void setTime(long time) {
      date.setTime(time);
   }

   /**
    * Metodo que sobrescreve o de mesmo nome da classe Date, utilizado para manter compatibilidade.
    */
   @Override
   public String toString() {
      return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss Z").format(date);
   }

}
