/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.framework.util;

import java.io.UnsupportedEncodingException;


/**
 * To change this template use File | Settings | File Templates.
 * @author Dawei
 * @version v1.0
 * @since v7.0.0
 * 2018年3月23日 上午10:26:41
 */
public class HexUtils {

    private static final char[] HEX_LOOK_UP = {'0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * 将字节数组转换为Hex字符串
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
     * 将Hex字符串转换为字节数组
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
