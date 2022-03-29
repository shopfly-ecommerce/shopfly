/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.framework.logs;

/**
 * 日志接口
 * @author kingapex
 * @version 1.0
 * @since 7.1.0
 * 2019-04-17
 */
public interface Logger {

    /**
     * info
     * @param log
     */
    void info(String log);

    /**
     * 调试日志
     * @param log
     */
    void debug(String log);

    /**
     * 记录错误日志
     * @param log
     */
    void error(String log);

    /**
     * 错误日志
     * @param log
     * @param throwable
     */
    void error(String log, Throwable throwable);

}
