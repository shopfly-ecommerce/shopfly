/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.distribution.util;

import java.security.MessageDigest;

/**
 * JavashopMd5加密类
 *
 * @author Sylow
 * @version v1.0, 2015-11-18
 * @since v1.0
 */
public class JavashopEncrypt {

    /**
     * 十六进制下数字到字符的映射数组
     */
    private final static String[] HEX_DIGITS = {"0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"};

    /**
     * 把inputString加密
     *
     * @param inputStr 字符串
     * @return 加密后的字符串
     */
    public static String md5(String inputStr) {
        return encodeByMD5(inputStr);
    }

    /**
     * 验证输入的密码是否正确
     *
     * @param password    真正的密码（加密后的真密码）
     * @param inputString 输入的字符串
     * @return 验证结果，boolean类型
     */
    public static boolean authenticatePassword(String password,
                                               String inputString) {
        return password.equals(encodeByMD5(inputString));
    }

    /**
     * 对字符串进行MD5编码
     *
     * @param originString 字符串
     * @return 编码后的字符串
     */
    private static String encodeByMD5(String originString) {
        if (originString != null) {
            try {
                // 创建具有指定算法名称的信息摘要
                MessageDigest md5 = MessageDigest.getInstance("MD5");
                // 使用指定的字节数组对摘要进行最后更新，然后完成摘要计算
                byte[] results = md5.digest(originString.getBytes());
                // 将得到的字节数组变成字符串返回
                String result = byteArrayToHexString(results);
                return result;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 轮换字节数组为十六进制字符串
     *
     * @param b 字节数组
     * @return 十六进制字符串
     */
    private static String byteArrayToHexString(byte[] b) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            resultSb.append(byteToHexString(b[i]));
        }
        return resultSb.toString();
    }

    /**
     * 将一个字节转化成十六进制形式的字符串
     *
     * @param b 字节码
     * @return 十六进制的字符串
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
     * 测试函数
     *
     * @param args
     */
    public static void main(String[] args) {

        //加密
        String md5Str = JavashopEncrypt.md5("http://www.javamall.com.cn");
        System.out.println(md5Str);

        //验证
        boolean result = JavashopEncrypt.authenticatePassword(md5Str, "http://www.javamall.com.cn");
        System.out.println(result);
    }

}
