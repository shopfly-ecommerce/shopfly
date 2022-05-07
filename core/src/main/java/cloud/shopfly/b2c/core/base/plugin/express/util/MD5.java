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
package cloud.shopfly.b2c.core.base.plugin.express.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5 encryption
 *
 * @author zh
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/7/5 In the afternoon2:40
 */
public class MD5 {
    private static MessageDigest mdInst = null;
    private static char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    private static MessageDigest getMdInst() {
        if (mdInst == null) {
            try {
                mdInst = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        return mdInst;
    }

    public final static String encode(String s) {
        try {
            byte[] btInput = s.getBytes();
            // Updates the digest with the specified byte
            getMdInst().update(btInput);
            // Obtain ciphertext
            byte[] md = getMdInst().digest();
            // Converts the ciphertext to a hexadecimal string
            int j = md.length;
            char[] str = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] arg) {

//        String password = MD5.encode("123456") + "20160315120530";
//        System.out.println(password);
//        System.out.println(password.toLowerCase().equals("e10adc3949ba59abbe56e057f20f883e20160315120530"));
//
//        password = MD5.encode(password.toLowerCase());
//
//        System.out.print(password);
//        System.out.print(password.toLowerCase().equals("ea8b8077f748b2357ce635b9f49b7abe"));
    }
}
