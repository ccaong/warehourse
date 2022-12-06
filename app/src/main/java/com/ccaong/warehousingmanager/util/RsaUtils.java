package com.ccaong.warehousingmanager.util;

import android.util.Base64;
import android.util.Log;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;


/**
 * RSA加密解密
 *
 * @author ruoyi
 **/
public class RsaUtils {

    public static String encryptByPublicKey = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAKoR8mX0rGKLqzcWmOzbfj64K8ZIgOdH"
            + "nzkXSOVOZbFu/TJhZ7rFAN+eaGkl3C4buccQd/EjEsj9ir7ijT7h96MCAwEAAQ==";


    /**
     * 公钥加密
     *
     * @param text 待加密的文本
     * @return 加密后的文本
     */
    public static String encryptByPublicKey(String text) throws
            Exception {
        X509EncodedKeySpec x509EncodedKeySpec2 = null;
        try {
            x509EncodedKeySpec2 = new X509EncodedKeySpec(Base64.decode(encryptByPublicKey.getBytes(), Base64.NO_WRAP));
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("错误", e.toString());
        }
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec2);
        Cipher cipher = Cipher.getInstance("RSA/None/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] result = cipher.doFinal(text.getBytes());
        return Base64Utils.encode(result);
    }
}
