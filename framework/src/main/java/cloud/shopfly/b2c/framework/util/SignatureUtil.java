/*
 *  Copyright 2008-2022 Shopfly.cloud Group.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
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
 * Signature tool
 * @author kingapex
 * @version 1.0
 * @since 6.4.0
 * 18/02/2018
 */
public class SignatureUtil {


    /**
     * Sign the string
     * @param hexPrivateKey The private key
     * @param str The string to sign
     * @return A string after the signature, returned if the signature failsnull
     */
    public static  String signature(String hexPrivateKey, String str) {
        try {
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(HexUtils.hexStrToBytes(hexPrivateKey));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);

            // Generate a digital signature for the information with the private key
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
            // This is the digital signature output by SignatureData
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
