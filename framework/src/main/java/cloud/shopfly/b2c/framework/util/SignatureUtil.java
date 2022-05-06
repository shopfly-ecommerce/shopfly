/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.framework.util;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * Created by kingapex on 18/02/2018.
 * 签名工具
 * @author kingapex
 * @version 1.0
 * @since 6.4.0
 * 18/02/2018
 */
public class SignatureUtil {


    /**
     * 对字串进行签名
     * @param hexPrivateKey 私钥
     * @param str 要签名的字串
     * @return 签名后的字串，如果签名失败返回null
     */
    public static  String signature(String hexPrivateKey, String str) {
        try {
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(HexUtils.hexStrToBytes(hexPrivateKey));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);

            // 用私钥对信息生成数字签名
            Signature signature = Signature.getInstance("MD5withRSA");
            signature.initSign(privateKey);
            signature.update(str.getBytes("UTF-8"));
            return HexUtils.bytesToHexStr(signature.sign());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean verifySign(String hexPublicKey, String signedStr, String str) {
        try {
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(HexUtils.hexStrToBytes(hexPublicKey));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
            //这是SignatureData输出的数字签名
            byte[] signed = HexUtils.hexStrToBytes(signedStr);

            java.security.Signature signature = java.security.Signature.getInstance("MD5withRSA");
            signature.initVerify(publicKey);
            signature.update(str.getBytes());
            return signature.verify(signed);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


}
