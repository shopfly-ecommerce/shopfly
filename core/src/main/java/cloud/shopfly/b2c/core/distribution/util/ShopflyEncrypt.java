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
package cloud.shopfly.b2c.core.distribution.util;

import java.security.MessageDigest;

/**
 * shopfly Md5Encryption class
 *
 * @author Sylow
 * @version v1.0, 2015-11-18
 * @since v1.0
 */
public class ShopflyEncrypt {

    /**
     * A hexadecimal array of numeral-to-character mappings
     */
    private final static String[] HEX_DIGITS = {"0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"};

    /**
     * theinputStringencryption
     *
     * @param inputStr string
     * @return Encrypted string
     */
    public static String md5(String inputStr) {
        return encodeByMD5(inputStr);
    }

    /**
     * Verify that the entered password is correct
     *
     * @param password    The real password（The encrypted real password）
     * @param inputString Input string
     * @return Verify the results,booleantype
     */
    public static boolean authenticatePassword(String password,
                                               String inputString) {
        return password.equals(encodeByMD5(inputString));
    }

    /**
     * On a stringMD5coding
     *
     * @param originString string
     * @return The encoded string
     */
    private static String encodeByMD5(String originString) {
        if (originString != null) {
            try {
                // Creates a summary of information with the specified algorithm name
                MessageDigest md5 = MessageDigest.getInstance("MD5");
                // The digest is last updated with the specified byte array, and then the digest calculation is completed
                byte[] results = md5.digest(originString.getBytes());
                // Returns the resulting byte array as a string
                String result = byteArrayToHexString(results);
                return result;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * The rotation byte array is a hexadecimal string
     *
     * @param b An array of bytes
     * @return A hexadecimal character string
     */
    private static String byteArrayToHexString(byte[] b) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            resultSb.append(byteToHexString(b[i]));
        }
        return resultSb.toString();
    }

    /**
     * Converts a byte to a hexadecimal string
     *
     * @param b The bytecode
     * @return The value is a hexadecimal string
     */
    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0) {
            n = 256 + n;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return HEX_DIGITS[d1] + HEX_DIGITS[d2];
    }

    /**
     * Test functions
     *
     * @param args
     */
    public static void main(String[] args) {

        // encryption
        String md5Str = ShopflyEncrypt.md5("http://www.javamall.com.cn");
        System.out.println(md5Str);

        // validation
        boolean result = ShopflyEncrypt.authenticatePassword(md5Str, "http://www.javamall.com.cn");
        System.out.println(result);
    }

}
