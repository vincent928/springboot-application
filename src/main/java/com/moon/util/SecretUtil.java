package com.moon.util;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;

/**
 * Author : moon
 * Date  : 2018/12/10 11:37
 * Description : Class for 3DES对称加解密工具类
 */
public class SecretUtil {

    //加密算法 可指定DES,DESede(3DES),Blowfish
    private static final String ALGORITHM = "DESede";
    //加密密钥 24字节数组
    private static final String CRYPT_PRIVATE_KEY = "";

    /**
     * 加密方法
     *
     * @param src 源数据的字节数组
     * @return
     */
    public static byte[] encrypt3DES(byte[] src) {
        try {
            //生成密钥
            SecretKey secretKey = new SecretKeySpec(build3DESKey(CRYPT_PRIVATE_KEY), ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return cipher.doFinal(src);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e1) {
            e1.printStackTrace();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return null;
    }

    /**
     * 解密方法
     *
     * @param src 密文字节数组
     * @return
     */
    public static byte[] decrypt3DES(byte[] src) {
        try {
            SecretKey decryptKey = new SecretKeySpec(build3DESKey(CRYPT_PRIVATE_KEY), ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, decryptKey);
            return cipher.doFinal(src);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e1) {
            e1.printStackTrace();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return null;
    }


    /**
     * 根据字符串生成密钥字节数组
     * 密钥要求长度为24位字节数组
     *
     * @param keyStr 密钥字符串
     * @return
     * @throws UnsupportedEncodingException
     */
    private static byte[] build3DESKey(String keyStr) throws UnsupportedEncodingException {
        byte[] key = new byte[24];
        byte[] temp = keyStr.getBytes("UTF-8");
        /**
         * 执行数组拷贝
         * System.arraycopy(源数组,开始拷贝位置,目标数组,拷贝多少位)
         */
        if (key.length > temp.length) {
            //如果temp长度不够24位，则拷贝temp数组整个长度的内容到key数组中去
            System.arraycopy(temp, 0, key, 0, temp.length);
        } else {
            //如果temp长度大于24位，则拷贝temp数组24个长度到key数组中去
            System.arraycopy(temp, 0, key, 0, key.length);
        }
        return key;
    }

}
