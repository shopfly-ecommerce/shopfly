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


import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author zjp
 * @version v7.0
 * @Description AESEncryption tools
 * @ClassName AESUtil
 * @since v7.0 In the afternoon10:53 2018/6/26
 */
public class AESUtil {

    /**
     * encryption
     *
     * @param sSrc
     * @param sKey
     * @return
     * @throws Exception
     */
    public static String encrypt(String sSrc, String sKey) throws Exception {
        if (sKey == null) {
            System.out.print("KeyIs emptynull");
            return null;
        }
        // Check whether the Key is 16 bits
        if (sKey.length() != 16) {
            System.out.print("KeyThe length is not16position");
            return null;
        }
        byte[] raw = sKey.getBytes("utf-8");
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        // "Algorithm/mode/complement mode"
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));

        // BASE64 is used as the transcoding function, which can also play the role of two-time encryption.
        return Base64.encode(encrypted);
    }

    /**
     * decryption
     *
     * @param sSrc
     * @param sKey
     * @return
     * @throws Exception
     */
    public static String decrypt(String sSrc, String sKey) throws Exception {
        try {
            // Check whether the Key is correct
            if (sKey == null) {
                System.out.print("KeyIs emptynull");
                return null;
            }
            // Check whether the Key is 16 bits
            if (sKey.length() != 16) {
                System.out.print("KeyThe length is not16position");
                return null;
            }
            byte[] raw = sKey.getBytes("utf-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            // Decrypt it in Base64 first
            byte[] encrypted1 = Base64.decode(sSrc);
            try {
                byte[] original = cipher.doFinal(encrypted1);
                String originalString = new String(original, "utf-8");
                return originalString;
            } catch (Exception e) {
                System.out.println(e.toString());
                return null;
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }
}
