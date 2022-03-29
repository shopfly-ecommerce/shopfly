/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.framework.logs;

/**
 * 日志工厂，实际上用的还是 slf4j,封装了一层
 * @author kingapex
 * @version 1.0
 * @since 7.1.0
 * 2019-04-17
 */

public abstract class LoggerFactory {

    /**
     * 获取logger
     * @param claz 调用类
     * @return
     */
    public  static  Logger getLogger(Class<?> claz) {
        org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(claz);
        return new DefaultLoggerImpl(logger);
    }
}
