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
import java.security.Key;

/**
 * 
 *	Encryption tools
 * @author zjp
 * @version v1.0
 * @since v6.4.0
 * 2017years11month23The morning of10:13:45
 */
@SuppressWarnings("AlibabaUndefineMagicConstant")
public class JiaMiUtil {
	/**  
     * Default constructor, using the default key
     */    
    public JiaMiUtil() throws Exception {
        this(strDefaultKey);    
    }    
    
    /**  
     * Specifies the key constructor
     * @param strKey  Specified key
     * @throws Exception  
     */    
    public JiaMiUtil(String strKey) throws Exception {
        // Security.addProvider(new com.sun.crypto.provider.SunJCE());    

        SecretKeySpec key = new SecretKeySpec(getKey(strKey), "DES");  
        encryptCipher = Cipher.getInstance("DES/ECB/NoPadding");    
        encryptCipher.init(Cipher.ENCRYPT_MODE, key);    
        decryptCipher = Cipher.getInstance("DES/ECB/NoPadding");    
        decryptCipher.init(Cipher.DECRYPT_MODE, key);    
    }    
    /** String default key value*/    
    private static String strDefaultKey ="shopfly8864";
    /** Encryption tools*/    
    private Cipher encryptCipher = null;    
    /** Decryption tool*/    
    private Cipher decryptCipher = null;    
    /**  
     * willbyteArrays are converted to representations16A string of base values, Such as：byte[]{8,18}convert：0813， andpublic static byte[]  
     * hexStr2ByteArr(String strIn) Reversible transformations
     * @param arrB  transformablebyteAn array of
     * @return Converted string
     * @throws Exception This method does not handle any exceptions; all exceptions are thrown
     */    
    public static String byteArr2HexStr(byte[] arrB) throws Exception {    
        int iLen = arrB.length;    
        // Each byte takes two characters, so the string is twice the length of the array
        StringBuffer sb = new StringBuffer(iLen * 2);    
        for (int i = 0; i < iLen; i++) {    
            int intTmp = arrB[i];    
            // Convert negative numbers into positive numbers
            while (intTmp < 0) {    
                intTmp = intTmp + 256;    
            }    
            // Anything less than 0F needs to be added with a 0
            if (intTmp < 16) {    
                sb.append("0");    
            }    
            sb.append(Integer.toString(intTmp, 16));    
        }    
        return sb.toString();    
    }    
    /**  
     * Will say16The string of the base value is converted tobyteArray, andpublic static String byteArr2HexStr(byte[] arrB)  
     * Reversible transformations
     * @param strIn The string to be converted
     * @return The transformedbyteAn array of
     */    
    public static byte[] hexStr2ByteArr(String strIn) throws Exception {    
        byte[] arrB = strIn.getBytes();    
        int iLen = arrB.length;    
        // Two characters represent a byte, so the length of the byte array is the length of the string divided by 2
        byte[] arrOut = new byte[iLen / 2];    
        for (int i = 0; i < iLen; i = i + 2) {    
            String strTmp = new String(arrB, i, 2);    
            arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);    
        }    
        return arrOut;    
    }    
    /**  
     * Encrypted byte array
     * @param arrB  The byte array to be encrypted
     * @return An encrypted array of bytes
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
     * Encrypted string
     * @param strIn  String to be encrypted
     * @return Encrypted string
     */    
    public String encrypt(String strIn) throws Exception {    
        return byteArr2HexStr(encrypt(strIn.getBytes()));    
    }    
    /**  
     * Decrypted byte array
     * @param arrB  An array of bytes to decrypt
     * @return A decrypted byte array
     */    
    public byte[] decrypt(byte[] arrB) throws Exception {    
        return decryptCipher.doFinal(arrB);    
    }    
    /**  
     * Decryption string
     * @param strIn  String to decrypt
     * @return The decrypted string
     */    
    public String decrypt(String strIn) throws Exception {    
        return new String(decrypt(hexStr2ByteArr(strIn)));    
    }    
    /**  
     * Generates a key from the specified string. The required byte array length is8A lack of8Bit when to complement0And beyond8Only before8position 
     * @param keyRule  An array of bytes that make up the string
     * @return Generated key
     */    
    private byte[]  getKey(String keyRule) throws Exception {    
    	 Key key = null;  
         byte[] keyByte = keyRule.getBytes();  
         // Creates an empty eight-bit array, 0 by default
         byte[] byteTemp = new byte[8];  
         // Converts a user-specified rule to an eight-bit array
         for (int i = 0; i < byteTemp.length && i < keyByte.length; i++) {  
             byteTemp[i] = keyByte[i];  
         }  
         key = new SecretKeySpec(byteTemp, "DES");  
         return key.getEncoded();   
    }    
    /**
     * encryption
     * @param string
     * @return
     */
    public static  String encryptCode(String string) {
    		try {    
            JiaMiUtil des1 = new JiaMiUtil();
            return  des1.encrypt(string);  
        } catch (Exception e) {    

            return "";
        }  
    }
    /**
     * decryption
     * @param string
     * @return
     */
    public static  String decryptCode(String string) {
    		try {
                JiaMiUtil des1 = new JiaMiUtil();
            return  des1.decrypt(string).trim();
        } catch (Exception e) {    
            return "";

        }
    }


    public static void main(String[] args) {
        String str= "http://record.javamall.com.cn/api/base/record";
//        str = encryptCode(str);
        str ="4a0a2e796ef27271bac6f45d8f38fb67df2f490babd7a6af4a06dc1a2eb0f20e69f527cc4872d7805db39b1afd9c1a7b5be1b3b2c6601035fc4cd591ee66b58d";
        System.out.println(str);
        str = decryptCode(str);
        System.out.println(str.trim()+"!");

    }
    
}
