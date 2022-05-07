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

import java.io.UnsupportedEncodingException;


/**
 * To change this template use File | Settings | File Templates.
 * @author Dawei
 * @version v1.0
 * @since v7.0.0
 * 2018years3month23The morning of10:26:41
 */
public class HexUtils {

    private static final char[] HEX_LOOK_UP = {'0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * Converts a byte array toHexstring
     * @param bytes
     * @return
     */
    public static String bytesToHexStr(byte[] bytes) {
        StringBuffer stringBuffer = new StringBuffer(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++) {
            stringBuffer.append(HEX_LOOK_UP[(bytes[i] >>> 4) & 0x0f]);
            stringBuffer.append(HEX_LOOK_UP[bytes[i] & 0x0f]);
        }
        return stringBuffer.toString();
    }

    /**
     * willHexA string is converted to a byte array
     * @param str
     * @return
     */
    public static byte[] hexStrToBytes(String str) {
        byte[] bytes = new byte[str.length() / 2];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) Integer.parseInt(str.substring(2 * i, 2 * i + 2),
                    16);
        }
        return bytes;
    }
}
