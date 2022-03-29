/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.framework.auth;

/**
 * Token 解析器
 * @author kingapex
 * @version 1.0
 * @since 7.1.0
 * 2019-06-21
 */
public interface TokenParser {

    /**
     * 解析token
     * @param token
     * @return 用户对象
     */
    <T>  T parse(Class<T> clz, String token) throws TokenParseException;


}
