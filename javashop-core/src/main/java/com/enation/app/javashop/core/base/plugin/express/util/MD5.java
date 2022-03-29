/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.base.plugin.express.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5 加密
 *
 * @author zh
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/7/5 下午2:40
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
            // 使用指定的字节更新摘要
            getMdInst().update(btInput);
            // 获得密文
            byte[] md = getMdInst().digest();
            // 把密文转换成十六进制的字符串形式
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
