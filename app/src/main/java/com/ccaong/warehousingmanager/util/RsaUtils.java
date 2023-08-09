package com.ccaong.warehousingmanager.util;

import android.util.Base64;
import android.util.Log;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;


/**
 * RSA加密解密
 *
 * @author ruoyi
 **/
public class RsaUtils {

//    public static String encryptByPublicKey = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAKoR8mX0rGKLqzcWmOzbfj64K8ZIgOdH"
//            + "nzkXSOVOZbFu/TJhZ7rFAN+eaGkl3C4buccQd/EjEsj9ir7ijT7h96MCAwEAAQ==";

    public static String encryptByPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAsuF2EZjwubCtfzzF2RcZhlkl9LDhySzGLPS" +
            "mb2Zfz+CAKT8EWaapXFnZGc1PTLgiqBoK45S02jRMkfz3PSWWptc19QXBwY50/BtTAM+Eys37xLIMNcle/YPHQIY+aU/aMdSFhtBfAgPDeRpejr/4y" +
            "e0WzddA0TtbBAZvLe7d7ndH876mPNdLUbO6Fi2eUnNwrbInTgd1kb7xeSZdiIjcqbCS5PhpIpvx2UtbOGFbkhYL8es6LFqR3I" +
            "itqAfgbeTe8aUvOE3QDoKmKjqtItHQwLOmvmtpaQZDbJj3NT6wfHDsxQIjlqmuk63iUsKmtmMUP1flarfI0hXMPzLYM7apdQIDAQAB";
//     Rsa 私钥
//    public static String privateKey = "MIIBVAIBADANBgkqhkiG9w0BAQEFAASCAT4wggE6AgEAAkEAqhHyZfSsYourNxaY"
//            + "7Nt+PrgrxkiA50efORdI5U5lsW79MmFnusUA355oaSXcLhu5xxB38SMSyP2KvuKN"
//            + "PuH3owIDAQABAkAfoiLyL+Z4lf4Myxk6xUDgLaWGximj20CUf+5BKKnlrK+Ed8gA"
//            + "kM0HqoTt2UZwA5E2MzS4EI2gjfQhz5X28uqxAiEA3wNFxfrCZlSZHb0gn2zDpWow"
//            + "cSxQAgiCstxGUoOqlW8CIQDDOerGKH5OmCJ4Z21v+F25WaHYPxCFMvwxpcw99Ecv"
//            + "DQIgIdhDTIqD2jfYjPTY8Jj3EDGPbH2HHuffvflECt3Ek60CIQCFRlCkHpi7hthh"
//            + "YhovyloRYsM+IS9h/0BzlEAuO0ktMQIgSPT3aFAgJYwKpqRYKlLDVcflZFCKY7u3" + "UP8iWi1Qw0Y=";
//

    public static String privateKey = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCy4XYRmPC5sK1/PMXZFxmGWSX0sOHJLMYs9KZvZ" +
            "l/P4IApPwRZpqlcWdkZzU9MuCKoGgrjlLTaNEyR/Pc9JZam1zX1BcHBjnT8G1MAz4TKzfvEsgw1yV79g8dAhj5pT9ox1IWG0F8CA8N5Gl6Ov/jJ7RbN10" +
            "DRO1sEBm8t7t3ud0fzvqY810tRs7oWLZ5Sc3CtsidOB3WRvvF5Jl2IiNypsJLk+Gkim/HZS1s4YVuSFgvx6zosWpHciK2oB+Bt5N7xpS84TdAOgqYqOq" +
            "0i0dDAs6a+a2lpBkNsmPc1PrB8cOzFAiOWqa6TreJSwqa2YxQ/V+Vqt8jSFcw/Mtgztql1AgMBAAECggEBAJs7j0OcLqNmUFnPun43Tp5JNOgQrA/x/B" +
            "S9RsX72H/EelPQ7QyD1gtfqUh75CpWzTSGTRbMUNnfcudp2IDG4uLsdlWo5kj7QEgbyj801y9L7AX44SG4G+fy+PHkzbqEYnzFyg6BO0G7VGH+4ToOHG/" +
            "XgOemuggY81Yd41PAK/YpN+aypIJtB6r3ewoXQ+Hr+9XxWj3bulq1SRYelxE0VkRmL7uq/2SRh720p1AfnbtH87fkOvWtdXz4GrGd6SXsKyNOGPifKWVKK" +
            "oNIlJonvEWGaOGw4sgC9Am0gW9ntjunjKnwWFOWzwZjlB1JjmnBWobSgDwK750zmHB3QViOa6ECgYEA2Gzt9glSVDofw7vh52eDem7Vjyn2hhpu1z8aPJ" +
            "Kpi44jFp0u3l926kNbevlrJVXw4VKVnOwroDEbcd+qMUbgkEmQ57oGE9fxpxKxNV8PWVH5Gm2VI8mB9OYv242QvahcoOfGo5OnTd6Ar1cXmtaNkVlQ30E" +
            "InHEaiW1NjKj5NH0CgYEA05cGuZYIDzSR5W4PNXAvJaqoLkr5o/ScjFhldJJ8vLdDO21NTXUFa6AEn0aa756jfDscE0rHBvoGZRLvgOOlJDLVeyIKM/Qc" +
            "nzDNV8HnUibmKdiHSCKhcXKEXxtTO1nd21EqsO+r2OrbGA17KdHfRxyvouvRdyl/gCUOywOPMlkCgYEAqvV5D6YTY7c6SkqKy1locXlFtz5Fn28X6W3OUtT" +
            "jmvqfWwi08jHk5G5qv/xwF+EIU2UCDCA+4d6IuG4eqag8UZ3bGkFG9bvaBsjsNKWvoTXwmWDAs7FEt+hxG3R7RmXNLcmIjsRfH1LIwjkAy236DDvKd2CO5c0" +
            "uUiqGXLtJh50CgYBRolIo5haj7y9e6lCZ2HeIO8h5W9nnBOglSbGTATV5Bee9lNUfTkfSF0HRRcdfLcB7nL6fPtl8+pffBSg8fRfo2Gf/AsoW81bboFOJST" +
            "l7O6DPgkfnb90DzR//Jaa+HlT3WeBztXSC01HGfOUI5H0VjZ7B5+O/6tXTVfjYIL5tuQKBgCIFrXHm9pKDIza06QsTHmioxsryXFD4kKtOQGsmKc89PqEds9p" +
            "4Xi/60mTyl7yk1BYqYt51Ujkut6ca2l/vLWqr+lv1b4fTWYYq+7Wg4O5Q13KQVnk32zH9aw7OpedtpWcskIfuCSQr8j7KQhmZES3BNQ0yYuOWQTeMPdo63hYZ";

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


    public static final String TRANSFORMATION = "RSA/None/PKCS1Padding";

    /**
     * 私钥解密
     *
     * @param text 待解密的文本
     * @return 解密后的文本
     */
    public static String decryptByPrivateKey(String text) throws Exception {
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec5 = new PKCS8EncodedKeySpec(Base64.decode(privateKey.getBytes(), Base64.NO_WRAP));


        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec5);
//        Cipher cipher = Cipher.getInstance("RSA");
        Cipher cipher = Cipher.getInstance("RSA/None/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] result = cipher.doFinal(Base64.decode(text.getBytes(), Base64.NO_WRAP));
        return new String(result);
    }
}
