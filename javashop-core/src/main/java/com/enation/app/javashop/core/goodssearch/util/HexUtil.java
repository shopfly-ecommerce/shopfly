/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.goodssearch.util;

import org.apache.commons.codec.binary.Hex;

/**
 * 
 * 对字符串进行hex
 * @author zh
 * @version v1.0
 * @since v1.0
 * 2017年4月19日 下午5:41:32
 */
public class HexUtil {
	
	/**
	 * 将字符串进行hex
	 * @param str
	 * @return
	 */
	public static String encode(String str){
		try{
			
			
//			return new Hex().encodeHexString(str.getBytes("UTF-8"));
			return new String(Hex.encodeHex(str.getBytes("UTF-8")));
		}catch(Exception ex){
			return str;
		}
	}
	
	/**
	 * 将hex后的字符串解密
	 * @param encodedStr
	 * @return
	 */
	public static String decode(String encodedStr){
		try{
			return new String(new Hex().decode(encodedStr.getBytes()), "UTF-8");
		}catch(Exception ex){
			return encodedStr;
		}
	}
	
}
