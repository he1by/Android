package com.company;


import java.io.*;
import java.lang.reflect.Array;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.BitSet;

/*
 * Created by He1by on 24/2/2017.
 */
public class CryptoSTB34 {

    /*  int t=0;
       int lenght = 1536 - 4 * l;
       message =Arrays.copyOf(reverseArray(message),4);
       do {
           t++;
       } while ((message.length + 2 + t) % lenght != 0);
       long[] buffer = Arrays.copyOf(message,24);//(message.length*64+2+t)/64);
       buffer[message.length]=0x4000000000000000L;
       long[] s=new long[24];
       s[23]=Long.parseLong(String.valueOf(4/l),10);
       for(int i=0;i<buffer.length-1;i=i+8){
           for(int n=0;n<lenght/64;n++){
                  s[n] = buffer[n+i];
           }
           s = Arrays.copyOf(bash_f(s),24);
       }
       return Arrays.copyOf(s,2*l/64);*/
    private static final long MASK =0xDC2BE1997FE0D8AEL;// магическая константа

    /*  int t=0;
      long[] s= new long[24];
      for(int i=0;i<23;i++){
          s[i]=0x00;//00000000000000L;
      }
      s[23]=0x0000000000000100L;//00000000000000L;
      int len=1536-4*l;
      do {
          t++;
      }while((message.length*64+2+t)%len!=0);
      int count=0;
      long[] buffer = new long [16];
      for(int i=0;i<message.length;i++){
          if(i<message.length)
          buffer[i]=message[i];
          count++;
      }
      buffer[count]=0x0000000000000001L;
      for(int i=0;i<message.length;i+=8){
          for(int j=0;j<len/64;j++) {
              s[j] = buffer[j + i];
          }
        s=bash_f(s);
      }
      return new long[]{s[0],s[1],s[2],s[3]};*/
    private static long[] convertByteToLong(byte[]array){
        //функция перевода массива byte в массив long
        ByteBuffer byteBuffer= ByteBuffer.wrap(array);
        long[]result=new long[byteBuffer.capacity()/8];
        for(int i=0;i<byteBuffer.capacity()/8;i++){
            result[i]=byteBuffer.getLong(i);
        }
        return result;
    }
    public static long[] getHash(long[] message, int l) {
        //алгоритм получения хэша из сообщения message(попытка переделать для потока)
        int myL=(1536-4*l)/64;
        if (message.length != myL) {
            int i = message.length;
            long[]bufer = Arrays.copyOf(message,myL);
            long r=0x0000000000000100L;
            bufer[i] = r;
            for(int count=i;count<myL;count++){
                if(bufer[count]!=0x00L){
                    bufer[count] =0x00L;
                }
            }
        }
        long[]s=new long[24];
        s[23]=Long.reverseBytes(l/4);
        return null;
    }

    public   static long[] reverseArray(long[] array)
    {   //функция 'переворачивания' бит в массиве
        for(int i=0;i<array.length;i++){
            array[i]=Long.reverseBytes(array[i]);
        }
        return array;
    }

    public static long[] bash_s(long w0, long w1,long w2,int m1,int n1,int m2, int n2){
        long t0=rotHi64(w0,m1);
        w0=w0^w1^w2;
        long t1=w1^rotHi64(w0,n1);
        w1=t0^t1;
        w2 ^=rotHi64(w2,m2)^rotHi64(t1,n2);
        t0=~w2;
        t1=w0|w2;
        long t2=w0&w1;
        t0 |=w1;
        w1 ^=t1;
        w2^=t2;
        w0^=t0;
        return  new long[]{w0,w1,w2};
   }

    public static long[] bash_f(long[] s) {
        long c = 0x3BF5080AC8BA94B1L;//магическая константа
        //цикл проходи по сообщению , которое всегда длинной s
        for (int i = 0; i < 24; i++) {
          int  n1 = 53;
          int m1 = 8;
          int n2 = 1;
          int m2 = 14;
            for (int j = 0; j <= 7; j++) {
                long[] buffer = bash_s(s[j], s[8 + j], s[16 + j], m1, n1, m2, n2);//магические перестановки
                s[j] = buffer[0];
                s[8 + j] = buffer[1];
                s[16 + j] = buffer[2];
                n1 = (7 * n1) & 63;
                n2 = (7 * n2) & 63;
                m1 = (7 * m1) & 63;
                m2 = (7 * m2) & 63;
            }
            long[] bufferArray = { s[15], s[10], s[9], s[12], s[11], s[14],
                                s[13], s[8], s[17], s[16], s[19], s[18],
                                s[21], s[20], s[23], s[22], s[6], s[3],
                                s[0], s[5], s[2], s[7], s[4], s[1]};//были проблемы с переопределением , пришлось делать так
            bufferArray[23] ^= c;
            s = bufferArray.clone();
            if((c&1)==0){
                c=c>>>1;
            }else c=(c>>>1)^ MASK;
        }
        return s;
    }

   private static long rotHi64(long val, int amount){//реализация сдвигов (т.к массив "перевёрнутый")
       long ShLi;
       long ShHi;
       for(int i=0;i<amount;i++){
           //сдвиг влево
           ShHi=val << 1;
           //сдвиг вправо
           ShLi=val >>> 63;
           val= ShHi^ShLi;
       }
       return val;
   }
}
