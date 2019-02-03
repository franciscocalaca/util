package com.franciscocalaca.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public abstract class UtilStream {


   public static final void copiarInputStream(InputStream in, OutputStream out) throws FalhaException {
      try {
         byte buffer[] = new byte[8192];
         int bytesRead = -1;
         while ((bytesRead = in.read(buffer)) != -1){
            out.write(buffer, 0, bytesRead);
         }
      } catch (IOException e) {
         Log.error(e.getMessage(), e);
         throw new FalhaException(e.getMessage(), e);
      }
   }


   public static byte[] lerInputStream(InputStream in) throws FalhaException {
      try {
         ByteArrayOutputStream bytesStream = new ByteArrayOutputStream();
         byte buffer[] = new byte[8192];
         int bytesRead = -1;
         while ((bytesRead = in.read(buffer)) != -1) {
            bytesStream.write(buffer, 0, bytesRead);
         }
         return bytesStream.toByteArray();
      } catch (IOException e) {
         Log.error(e.getMessage(), e);
         throw new FalhaException(e.getMessage(), e);
      }
   }

}