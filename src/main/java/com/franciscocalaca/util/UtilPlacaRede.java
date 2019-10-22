package com.franciscocalaca.util;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public abstract class UtilPlacaRede {
   
   public static List<InterfaceRede> getInterfaces() throws FalhaException{
      List<InterfaceRede> interfaces = new ArrayList<InterfaceRede>();
      try {  
         Enumeration<NetworkInterface> redes = NetworkInterface.getNetworkInterfaces();
         
         while(redes.hasMoreElements()){
            NetworkInterface netInt = redes.nextElement();
            InterfaceRede intRede = new InterfaceRede();
            interfaces.add(intRede);
            intRede.nome = netInt.getDisplayName();
            intRede.endereco = netInt.getInterfaceAddresses().toString();
            if(netInt.getHardwareAddress() != null){
               byte [] macAddressBytes = netInt.getHardwareAddress();
               String macAddress =  String.format ( "%1$02x-%2$02x-%3$02x-%4$02x-%5$02x-%6$02x",
                     macAddressBytes[0], macAddressBytes[1],
                     macAddressBytes[2], macAddressBytes[3],
                     macAddressBytes[4], macAddressBytes[5] ).toUpperCase();
               intRede.mac = macAddress;
            }
         }
         
         return interfaces;         
      } catch (SocketException e) {  
         Log.error("erro ao obter interfaces de rede: SocketException", e);
         throw new FalhaException(e.getMessage(), e);
      } catch (Exception e) {  
    	  Log.error("erro ao obter interfaces de rede: Exception", e);
         throw new FalhaException(e.getMessage(), e);
      }          
   }  

   public static class InterfaceRede{
      private String nome;
      private String endereco;
      private String mac;
      public String getNome() {
         return nome;
      }
      public String getEndereco() {
         return endereco;
      }
      public String getMac() {
         return mac;
      }
      @Override
      public String toString() {
         return "InterfaceRede [endereco=" + endereco + ", mac=" + mac + ", nome=" + nome + "]";
      }

   }
   

}
