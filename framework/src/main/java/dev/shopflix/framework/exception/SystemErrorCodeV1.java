/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.framework.exception;

/**
 * 系统级别异常码
 *
 * @author kingapex
 * @version v1.0.0
 * @since v1.0.0
 * 2017年3月27日 下午6:56:35
 */
public class SystemErrorCodeV1 {

    /**
     * 无权限异常
     */
    public final static String NO_PERMISSION = "002";
    /**
     * 资源未能找到
     */
    public final static String RESOURCE_NOT_FOUND = "003";
    /**
     * 错误的请求参数
     */
    public final static String INVALID_REQUEST_PARAMETER = "004";
    /**
     * 错误的配置参数
     */
    public final static String INVALID_CONFIG_PARAMETER = "005";
    /**
     * 错误的配置参数
     */
    public final static String INVALID_COTENT = "006";
}
