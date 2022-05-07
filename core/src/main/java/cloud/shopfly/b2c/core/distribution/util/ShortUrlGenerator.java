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


/**
 * Short link generator
 *
 * @author Sylow
 * @version v1.0, 2015-11-18
 * @since v1.0
 */
public class ShortUrlGenerator {

    /**
     * Generating short links
     *
     * @param url Long links
     * @return Four short address arrays, any one of them
     */
    public static String[] getShortUrl(String url) {

        // You can customize the mixed KEY used to generate MD5 encryption characters
        String key = "shopfly";

        // To use the character that generated the URL
        String[] chars = new String[]{"a", "b", "c", "d", "e", "f", "g", "h",
                "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t",
                "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
                "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H",
                "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
                "U", "V", "W", "X", "Y", "Z"
        };

        // MD5 encryption for the incoming url
        String sMD5EncryptResult = ShopflyEncrypt.md5(key + url);

        String hex = sMD5EncryptResult;
        String[] resUrl = new String[4];

        // Generate four short addresses in a group of 8 bits
        for (int i = 0; i < 4; i++) {

            // Bitwise sums the encrypted characters in hexadecimal 8 bits with 0x3FFFFFFF
            String sTempSubString = hex.substring(i * 8, i * 8 + 8);

            // You need to use long here because inteper.parseint () can only handle 31 bits, with the first sign bit, if not
            // Long is going to cross the line
            long lHexLong = 0x3FFFFFFF & Long.parseLong(sTempSubString, 16);

            String outChars = "";

            // Generate 6 times -6 bit short address
            for (int j = 0; j < 6; j++) {

                // The resulting value is bitwise and 0x0000003D to obtain the character array chars index
                long index = 0x0000003D & lHexLong;

                // Add the obtained characters
                outChars += chars[(int) index];

                // Each cycle moves 5 bits to the right
                lHexLong = lHexLong >> 5;

            }

            // Stores the string into the output array of the corresponding index
            resUrl[i] = outChars;

        }

        return resUrl;

    }

    /**
     * Follow the short linkkeyvalue
     *
     * @param shortUrl Short link
     * @return keyvalueString
     */
    public static String getShortKey(String shortUrl) {
        String key = shortUrl.substring(shortUrl.lastIndexOf("/") + 1, shortUrl.length());
        return key;
    }

    /**
     * Test functions
     *
     * @param args
     */
    public static void main(String[] args) {

        // Long connection: http://www.javamall.com.cn
        // The parsed short link is http://**/Afeyae

        String sLongUrl = "http://www.javamall.com.cn";
        String[] aResult = getShortUrl(sLongUrl);

        // Print out the result
        for (int i = 0; i < aResult.length; i++) {
            System.out.println(aResult[i]);
        }

        //String key = getShortKey("http://www:8080/su/asfasf");
        //System.out.println(key);

    }
}
