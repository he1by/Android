package com.company;


import java.io.File;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.BitSet;

/*
 * Created by He1by on 24/2/2017.
 */
public class CryptoSTB34 {

    private static final long MASK =0xDC2BE1997FE0D8AEL;

 /*   public static byte addBitsToByte(BitSet bitSet, int offset){
        byte value=0;
        for(int i=offset;(i<bitSet.length()&&((i+offset)<8));++i){
            value+=bitSet.get(i)?(1<<i);
        }
        return value;
    }*/


    public static byte[] getHash(byte[] x, int l, int indexOfLastEl){
        int len =1536-4*l;
        x[x.length]=Byte.parseByte("40",16);
        if(x.length<len/8){
            return Arrays.copyOf(x,len/8);
        }
        if(x.length>len/8){
            int res =x.length%len/8;
            return Arrays.copyOf(x,len/8*(res+1));
        }
        return null;
    }

    private  static long[] reverseArray(long[] array)
    {
        for(int i=0;i<array.length;i++){
            array[i]=Long.reverseBytes(array[i]);
        }
        return array;
    }

    public static long[] bash_s(long w0, long w1,long w2,int m1,int n1,int m2, int n2){
        long t0=RotHi64(w0,m1);
        w0=w0^w1^w2;
        long t1=w1^RotHi64(w0,n1);
        w1=t0^t1;
        w2 ^=RotHi64(w2,m2)^RotHi64(t1,n2);
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
        long c = 0x3BF5080AC8BA94B1L;
        for (int i = 0; i < 24; i++) {
          int  n1 = 53;
          int m1 = 8;
          int n2 = 1;
          int m2 = 14;
            for (int j = 0; j <= 7; j++) {
                long[] buffer = bash_s(s[j], s[8 + j], s[16 + j], m1, n1, m2, n2);
                s[j] = buffer[0];
                s[8 + j] = buffer[1];
                s[16 + j] = buffer[2];
                n1 = (7 * n1) % 64;
                n2 = (7 * n2) % 64;
                m1 = (7 * m1) % 64;
                m2 = (7 * m2) % 64;
            }
            long[] newArray = { s[15], s[10], s[9], s[12], s[11], s[14],
                                s[13], s[8], s[17], s[16], s[19], s[18],
                                s[21], s[20], s[23], s[22], s[6], s[3],
                                s[0], s[5], s[2], s[7], s[4], s[1]};
            newArray[23] ^= c;
            s = newArray.clone();
            if((c&1)==0){
                c=c>>>1;
            }else c=(c>>>1)^ MASK;
        }
        return reverseArray(s);
    }

   private static long RotHi64(long val, int amount){
       long ShLi;
       long ShHi;
       for(int i=0;i<amount;i++){
           ShHi=val << 1;
           ShLi=val >>> 63;
           val= ShHi^ShLi;
       }
       return val;
   }
}
