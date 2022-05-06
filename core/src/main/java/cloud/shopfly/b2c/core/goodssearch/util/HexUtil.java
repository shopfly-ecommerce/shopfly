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
package cloud.shopfly.b2c.core.goodssearch.util;

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
