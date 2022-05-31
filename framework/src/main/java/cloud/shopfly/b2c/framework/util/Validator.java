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

import java.util.regex.Pattern;

/**
 * validators：Verify mailboxes with regular expressions、Phone number etc.
 * @author Sylow
 * @version v1.1,2016years7month7day
 * @since v6.1
 */
public class Validator {

	/**
	 * Regular expression：Verify user name
	 */
	public static final String REGEX_USERNAME = "^[\\u4E00-\\u9FA5\\uf900-\\ufa2d\\w\\-]{4,20}$";

	/**
	 * Regular expression：Verify password
	 */
	public static final String REGEX_PASSWORD = "^[a-zA-Z0-9]{6,16}$";

	/**
	 * Regular expression：Verify the phone number
	 */
	public static final String REGEX_MOBILE = "^[\\d+]+$";

	/**
	 * Regular expression：Validation email
	 */
	public static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

	/**
	 * Regular expression：Verify the characters
	 */
	public static final String REGEX_CHINESE = "^[\u4e00-\u9fa5],{0,}$";

	/**
	 * Regular expression：Verify ID card
	 */
	public static final String REGEX_ID_CARD ="(^\\d{17}(?:\\d|X|x)$)|(^\\d{15}$)";

	/**
	 * Regular expression：validationURL
	 */
	public static final String REGEX_URL = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";

	/**
	 * Regular expression：validationIPaddress
	 */
	public static final String REGEX_IP_ADDR = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";

	/**
	 * Regular expression：Positive integer
	 */
	public static final String REGEX_POSITIVE_INT ="\\-?[1-9]\\d+?";


	/**
	 * Verify user name
	 *
	 * @param username
	 * @return Check pass returntrueOtherwise returnfalse
	 */
	public static boolean isUsername(String username) {

		// Not empty check
		if (username == null) {
			return false;
		}

		return Pattern.matches(REGEX_USERNAME, username);
	}

	/**
	 * Check the password
	 *
	 * @param password
	 * @return Check pass returntrueOtherwise returnfalse
	 */
	public static boolean isPassword(String password) {

		// Not empty check
		if (password == null) {
			return false;
		}


		return Pattern.matches(REGEX_PASSWORD, password);
	}

	/**
	 * Check mobile phone number
	 *
	 * @param mobile
	 * @return Check pass returntrueOtherwise returnfalse
	 */
	public static boolean isMobile(String mobile) {

		// Not empty check
		if (mobile == null) {
			return false;
		}

		return Pattern.matches(REGEX_MOBILE, mobile);
	}

	/**
	 * Check your email
	 *
	 * @param email
	 * @return Check pass returntrueOtherwise returnfalse
	 */
	public static boolean isEmail(String email) {

		// Not empty check
		if (email == null) {
			return false;
		}

		return Pattern.matches(REGEX_EMAIL, email);
	}

	/**
	 * Check the Chinese characters
	 *
	 * @param chinese
	 * @return Check pass returntrueOtherwise returnfalse
	 */
	public static boolean isChinese(String chinese) {


		// Not empty check
		if (chinese == null) {
			return false;
		}


		return Pattern.matches(REGEX_CHINESE, chinese);
	}

	/**
	 * Verification of identity card
	 *
	 * @param idCard
	 * @return Check pass returntrueOtherwise returnfalse
	 */
	public static boolean isIDCard(String idCard) {

		// Not empty check
		if (idCard == null) {
			return false;
		}



		return Pattern.matches(REGEX_ID_CARD, idCard);
	}

	/**
	 * checkURL
	 *
	 * @param url
	 * @return Check pass returntrueOtherwise returnfalse
	 */
	public static boolean isUrl(String url) {

		// Not empty check
		if (url == null) {
			return false;
		}


		return Pattern.matches(REGEX_URL, url);
	}

	/**
	 * checkIPaddress
	 *
	 * @param ipAddr
	 * @return Check pass returntrueOtherwise returnfalse
	 */
	public static boolean isIPAddr(String ipAddr) {

		// Not empty check
		if (ipAddr == null) {
			return false;
		}

		return Pattern.matches(REGEX_IP_ADDR, ipAddr);
	}

	/**
	 * Check positive integer
	 *
	 * @param ipAddr
	 * @return Check pass returntrueOtherwise returnfalse
	 */
	public static boolean isPositiveInt(Integer num) {

		// Not empty check
		if (num == null) {
			return false;
		}

		return Pattern.matches(REGEX_POSITIVE_INT, num+"");
	}
}
