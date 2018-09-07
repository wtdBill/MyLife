/**
 * Copyright (c) 2013 NewSenceNetwork.Co.Ltd.
 * <p>
 * All rights reserved.
 */
package com.example.ypp.mylife.Utils;

import android.text.TextUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.math.BigInteger;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Xutao
 */
public class MD5Utils {

    private static char md5Chars[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
            'e', 'f'};

    private static final String[] md5String = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    private static MessageDigest messagedigest;

    public static String encode(String var0) {
        String var1 = null;

        try {
            var1 = new String(var0);
            var1 = byteArrayToHexString(MessageDigest.getInstance("MD5").digest(var1.getBytes()));
        } catch (Exception var2) {
            ;
        }

        return var1;
    }

    public static String byteArrayToHexString(byte[] var0) {
        StringBuffer var1 = new StringBuffer();

        for (int var2 = 0; var2 < var0.length; ++var2) {
            int var3;
            if ((var3 = var0[var2]) < 0) {
                var3 += 256;
            }

            int var4 = var3 / 16;
            var3 %= 16;
            var1.append(md5String[var4] + md5String[var3]);
        }

        return var1.toString();
    }

    /*获取一个文件的md5码 */
    public static String getFileMD5String(File file) throws Exception {
        messagedigest = MessageDigest.getInstance("MD5");
        FileInputStream in = new FileInputStream(file);
        FileChannel ch = in.getChannel();
        MappedByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, 0,
                file.length());
        messagedigest.update(byteBuffer);
        return bufferToHex(messagedigest.digest());
    }

    /*获取一个字符串的md5码 */
    public static String getStringMD5String(String str) throws Exception {
        messagedigest = MessageDigest.getInstance("MD5");
        messagedigest.update(str.getBytes());
        return bufferToHex(messagedigest.digest());
    }

    /*验证一个字符串和一个MD5码是否相等 */
    public static boolean check(String str, String md5) throws Exception {
        if (getStringMD5String(str).equals(md5)) {
            return true;
        } else {
            return false;
        }
    }

    /*验证一个文件和一个MD5码是否相等 */
    public static boolean check(File f, String md5) {
        String s = getMd5ByFile(f);
        if (!TextUtils.isEmpty(s)) {
            if (s.equals(md5)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private static String bufferToHex(byte bytes[]) {
        return bufferToHex(bytes, 0, bytes.length);
    }

    private static String bufferToHex(byte bytes[], int m, int n) {
        StringBuffer stringbuffer = new StringBuffer(2 * n);
        int k = m + n;
        for (int l = m; l < k; l++) {
            appendHexPair(bytes[l], stringbuffer);
        }
        return stringbuffer.toString();
    }

    private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
        char c0 = md5Chars[(bt & 0xf0) >> 4];
        char c1 = md5Chars[bt & 0xf];
        stringbuffer.append(c0);
        stringbuffer.append(c1);
    }

    /**
     * RandomAccessFile 获取文件的MD5值
     * 性能比FileInputStream高
     *
     * @param file 文件路径
     * @return md5
     */
    public static String getFileMd5(File file) {
        MessageDigest messageDigest;
        RandomAccessFile randomAccessFile = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            if (file == null) {
                return "";
            }
            if (!file.exists()) {
                return "";
            }
            randomAccessFile = new RandomAccessFile(file, "r");
            byte[] bytes = new byte[1024 * 1024 * 10];
            int len = 0;
            while ((len = randomAccessFile.read(bytes)) != -1) {
                messageDigest.update(bytes, 0, len);
            }
            BigInteger bigInt = new BigInteger(1, messageDigest.digest());
            StringBuilder md5 = new StringBuilder(bigInt.toString(16));
            while (md5.length() < 32) {
                md5.insert(0, "0");
            }
            return md5.toString();
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                if (randomAccessFile != null) {
                    randomAccessFile.close();
                    randomAccessFile = null;
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return "";
    }

    private static String getMd5ByFile(File file) {
        InputStream fis;
        byte[] buffer = new byte[2048];
        int numRead = 0;
        MessageDigest md5;

        try {
            fis = new FileInputStream(file);
            md5 = MessageDigest.getInstance("MD5");
            while ((numRead = fis.read(buffer)) > 0) {
                md5.update(buffer, 0, numRead);
            }
            fis.close();
            return md5ToString(md5.digest());
        } catch (Exception e) {
            System.out.println("error");
            return null;
        }
    }

    private static String md5ToString(byte[] md5Bytes) {
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }
}
