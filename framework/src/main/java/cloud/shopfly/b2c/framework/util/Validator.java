/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.framework.util;

import java.util.regex.Pattern;

/**
 * 校验器：利用正则表达式校验邮箱、手机号等
 * @author Sylow
 * @version v1.1,2016年7月7日
 * @since v6.1
 */
public class Validator {

	/**
	 * 正则表达式：验证用户名
	 */
	public static final String REGEX_USERNAME = "^[\\u4E00-\\u9FA5\\uf900-\\ufa2d\\w\\-]{4,20}$";

	/**
	 * 正则表达式：验证密码
	 */
	public static final String REGEX_PASSWORD = "^[a-zA-Z0-9]{6,16}$";

	/**
	 * 正则表达式：验证手机号
	 */
	public static final String REGEX_MOBILE = "^0?(13[0-9]|14[0-9]|15[0-9]|16[0-9]|17[0-9]|18[0-9]|19[0-9])[0-9]{8}$";

	/**
	 * 正则表达式：验证邮箱
	 */
	public static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

	/**
	 * 正则表达式：验证汉字
	 */
	public static final String REGEX_CHINESE = "^[\u4e00-\u9fa5],{0,}$";

	/**
	 * 正则表达式：验证身份证
	 */
	public static final String REGEX_ID_CARD ="(^\\d{17}(?:\\d|X|x)$)|(^\\d{15}$)";

	/**
	 * 正则表达式：验证URL
	 */
	public static final String REGEX_URL = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";

	/**
	 * 正则表达式：验证IP地址
	 */
	public static final String REGEX_IP_ADDR = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";

	/**
	 * 正则表达式：正整数
	 */
	public static final String REGEX_POSITIVE_INT ="\\-?[1-9]\\d+?";


	/**
	 * 校验用户名
	 *
	 * @param username
	 * @return 校验通过返回true，否则返回false
	 */
	public static boolean isUsername(String username) {

		// 非空校验
		if (username == null) {
			return false;
		}

		return Pattern.matches(REGEX_USERNAME, username);
	}

	/**
	 * 校验密码
	 *
	 * @param password
	 * @return 校验通过返回true，否则返回false
	 */
	public static boolean isPassword(String password) {

		// 非空校验
		if (password == null) {
			return false;
		}


		return Pattern.matches(REGEX_PASSWORD, password);
	}

	/**
	 * 校验手机号
	 *
	 * @param mobile
	 * @return 校验通过返回true，否则返回false
	 */
	public static boolean isMobile(String mobile) {

		// 非空校验
		if (mobile == null) {
			return false;
		}

		return Pattern.matches(REGEX_MOBILE, mobile);
	}

	/**
	 * 校验邮箱
	 *
	 * @param email
	 * @return 校验通过返回true，否则返回false
	 */
	public static boolean isEmail(String email) {

		// 非空校验
		if (email == null) {
			return false;
		}

		return Pattern.matches(REGEX_EMAIL, email);
	}

	/**
	 * 校验汉字
	 *
	 * @param chinese
	 * @return 校验通过返回true，否则返回false
	 */
	public static boolean isChinese(String chinese) {


		// 非空校验
		if (chinese == null) {
			return false;
		}


		return Pattern.matches(REGEX_CHINESE, chinese);
	}

	/**
	 * 校验身份证
	 *
	 * @param idCard
	 * @return 校验通过返回true，否则返回false
	 */
	public static boolean isIDCard(String idCard) {

		// 非空校验
		if (idCard == null) {
			return false;
		}



		return Pattern.matches(REGEX_ID_CARD, idCard);
	}

	/**
	 * 校验URL
	 *
	 * @param url
	 * @return 校验通过返回true，否则返回false
	 */
	public static boolean isUrl(String url) {

		// 非空校验
		if (url == null) {
			return false;
		}


		return Pattern.matches(REGEX_URL, url);
	}

	/**
	 * 校验IP地址
	 *
	 * @param ipAddr
	 * @return 校验通过返回true，否则返回false
	 */
	public static boolean isIPAddr(String ipAddr) {

		// 非空校验
		if (ipAddr == null) {
			return false;
		}

		return Pattern.matches(REGEX_IP_ADDR, ipAddr);
	}

	/**
	 * 校验正整数
	 *
	 * @param ipAddr
	 * @return 校验通过返回true，否则返回false
	 */
	public static boolean isPositiveInt(Integer num) {

		// 非空校验
		if (num == null) {
			return false;
		}

		return Pattern.matches(REGEX_POSITIVE_INT, num+"");
	}

	public static void main(String[] args) {
		//String username = "30003001155115454x";
		Integer num = 12;
		System.out.println(Validator.isPositiveInt(num));
	}
}
