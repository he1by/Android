package com.company;

import com.sun.jna.platform.win32.WinDef;

import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.nio.ByteBuffer;
import java.sql.DataTruncation;
import java.util.Random;

/**
 * Created by He1by on 24/2/2017.
 */
public class CryptoSTB34 {
    public static String W0 = "B194BAC80A08F53B";
    public String W1 = "E12BDC1AE28257EC";
    public String W2 = "E9DEE72C8F0C0FA6";
    public static int m1=8;
    public static int n1=53;
    public static int m2=14;
    public static int n2=1;
    //Всё ок
    private static long toLongFromByte(byte[] array)
    {
//        return DatatypeConverter.printLong();
    }
    private static String toHexString(byte[] array){
        return DatatypeConverter.printHexBinary(array);
    }
    //Всё ок
    private static byte[] toByteArray(String s){
        return DatatypeConverter.parseHexBinary(s);
    }
   public static byte[] hash_s(){//byte[] W0,byte[] W1,byte W2 , int m1,int n1 ,int m2, int n2){
      // 8,53,14,1
       byte[] T0 = new byte[16];
       byte[] T1 = new byte[16];
       byte[] T2 = new byte[16];
        return null;
      //  return RotHi(toByteArray(W0));
   }
   public static String  test(){

       String buffer=toHexString(hash_s());
       return buffer;
    }
    //Тут всё правильно
   private static byte[] Logic_XOR(byte[] left,byte[] right){
       if(left.length==right.length){
           byte[] result = new byte[left.length];
           for(int i =0;i<left.length;i++)
           result[i]= (byte) (left[i] ^ right[i]);
           return result;
       }
       return new byte[10];
   }
  /* private static byte[] checkDown (byte[] left,byte[] right){
       if(left.length==right.length){
           byte[] result = new byte[left.length];
           for(int i=0;i<left.length;i++){
               result[i]=(byte) (((left[i]*right[i])+left[i]+right[i]) | 2);
           }
           return result;
          // F9619BFF77AFFFF3
       }
       return new byte[0];
   }*/
 /*  private static byte[] RotHi(byte[]u){
       int n=u.length;
       byte[] buffer = new byte[n];
       for(int i=0;i<u.length;i++) {
           buffer[n] = (byte) ((u[i] << m1) | (u[i] >>> (32 - m1)));
           n--;
       }
          // value << 5 | value >>> 27;
       return buffer;
   }*/

   private static long RotHi64(long val, int amount){
       long res;
       res = (val << amount) | (val >>> (64 - amount));
       return (res);
   }

   private static byte[] ShLo(byte[] u){
       for(int i=0;i<u.length;i++) {
           u[i]= (byte) ((int)(u[i]/2));
       }return u;
   }
   private static byte[] ShHi(byte[]u){
        for(int i=0;i<u.length;i++) {
            u[i]= (byte) (u[i]*2);
        }return u;
   }
}
