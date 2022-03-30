/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.framework.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

/**
 *
 *	加密工具
 * @author zjp
 * @version v1.0
 * @since v6.4.0
 * 2017年11月23日 上午10:13:45
 */
public class EncodeUtil {
	/**
     * 默认构造方法，使用默认密钥
     */
    public EncodeUtil() throws Exception {
        this(strDefaultKey);
    }

    /**
     * 指定密钥构造方法
     * @param strKey  指定的密钥
     * @throws Exception
     */
    public EncodeUtil(String strKey) throws Exception {
        // Security.addProvider(new com.sun.crypto.provider.SunJCE());

        SecretKeySpec key = new SecretKeySpec(getKey(strKey), "DES");
        encryptCipher = Cipher.getInstance("DES/ECB/NoPadding");
        encryptCipher.init(Cipher.ENCRYPT_MODE, key);
        decryptCipher = Cipher.getInstance("DES/ECB/NoPadding");
        decryptCipher.init(Cipher.DECRYPT_MODE, key);
    }
    /** 字符串默认键值 */
    private static String strDefaultKey ="shopflix8864";
    /** 加密工具 */
    private Cipher encryptCipher = null;
    /** 解密工具 */
    private Cipher decryptCipher = null;
    /**
     * 将byte数组转换为表示16进制值的字符串， 如：byte[]{8,18}转换为：0813， 和public static byte[]
     * hexStr2ByteArr(String strIn) 互为可逆的转换过程
     * @param arrB  需要转换的byte数组
     * @return 转换后的字符串
     * @throws Exception 本方法不处理任何异常，所有异常全部抛出
     */
    public static String byteArr2HexStr(byte[] arrB) throws Exception {
        int iLen = arrB.length;
        // 每个byte用两个字符才能表示，所以字符串的长度是数组长度的两倍
        StringBuffer sb = new StringBuffer(iLen * 2);
        for (int i = 0; i < iLen; i++) {
            int intTmp = arrB[i];
            // 把负数转换为正数
            while (intTmp < 0) {
                intTmp = intTmp + 256;
            }
            // 小于0F的数需要在前面补0
            if (intTmp < 16) {
                sb.append("0");
            }
            sb.append(Integer.toString(intTmp, 16));
        }
        return sb.toString();
    }
    /**
     * 将表示16进制值的字符串转换为byte数组， 和public static String byteArr2HexStr(byte[] arrB)
     * 互为可逆的转换过程
     * @param strIn 需要转换的字符串
     * @return 转换后的byte数组
     */
    public static byte[] hexStr2ByteArr(String strIn) throws Exception {
        byte[] arrB = strIn.getBytes();
        int iLen = arrB.length;
        // 两个字符表示一个字节，所以字节数组长度是字符串长度除以2
        byte[] arrOut = new byte[iLen / 2];
        for (int i = 0; i < iLen; i = i + 2) {
            String strTmp = new String(arrB, i, 2);
            arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
        }
        return arrOut;
    }
    /**
     * 加密字节数组
     * @param arrB  需加密的字节数组
     * @return 加密后的字节数组
     */
    public byte[] encrypt(byte[] arrB) throws Exception {
    	int count = arrB.length % 8;
    	if(count>0){
    		StringBuffer str = new StringBuffer(new String(arrB));
    		for (int i = 0; i < (8 - count); i++) {
				str.append(" ");
			}
    		return encryptCipher.doFinal(str.toString().getBytes());
    	}
        return encryptCipher.doFinal(arrB);
    }
    /**
     * 加密字符串
     * @param strIn  需加密的字符串
     * @return 加密后的字符串
     */
    public String encrypt(String strIn) throws Exception {
        return byteArr2HexStr(encrypt(strIn.getBytes()));
    }
    /**
     * 解密字节数组
     * @param arrB  需解密的字节数组
     * @return 解密后的字节数组
     */
    public byte[] decrypt(byte[] arrB) throws Exception {
        return decryptCipher.doFinal(arrB);
    }
    /**
     * 解密字符串
     * @param strIn  需解密的字符串
     * @return 解密后的字符串
     */
    public String decrypt(String strIn) throws Exception {
        return new String(decrypt(hexStr2ByteArr(strIn)));
    }
    /**
     * 从指定字符串生成密钥，密钥所需的字节数组长度为8位 不足8位时后面补0，超出8位只取前8位
     * @param keyRule  构成该字符串的字节数组
     * @return 生成的密钥
     */
    private byte[]  getKey(String keyRule) throws Exception {
    	 Key key = null;
         byte[] keyByte = keyRule.getBytes();
         // 创建一个空的八位数组,默认情况下为0
         byte[] byteTemp = new byte[8];
         // 将用户指定的规则转换成八位数组
         for (int i = 0; i < byteTemp.length && i < keyByte.length; i++) {
             byteTemp[i] = keyByte[i];
         }
         key = new SecretKeySpec(byteTemp, "DES");
         return key.getEncoded();
    }
    /**
     * 加密
     * @param string
     * @return
     */
    public static String encryptCode(String string) {
    		try {
            EncodeUtil des1 = new EncodeUtil();
            return  des1.encrypt(string);
        } catch (Exception e) {

            return "";
        }
    }
    /**
     * 解密
     * @param string
     * @return
     */
    public static String decryptCode(String string) {
    		try {
                EncodeUtil des1 = new EncodeUtil();
            return  des1.decrypt(string).trim();
        } catch (Exception e) {
            return "";

        }
    }


}
