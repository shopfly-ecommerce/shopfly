/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.framework.logs;

/**
 * 默认的logger实现
 * @author kingapex
 * @version 1.0
 * @since 7.1.0
 * 2019-04-17
 */

public class DefaultLoggerImpl implements Logger  {

    /**
     * 构造器，必须用slf4j loggger来初始化
     * @param logger
     */
    public DefaultLoggerImpl(org.slf4j.Logger logger) {
        this.logger = logger;
    }

    private  org.slf4j.Logger logger;

    @Override
    public void info(String log) {
        if (logger.isInfoEnabled()) {
            logger.info(log);
        }
    }

    @Override
    public void debug(String log) {
        if (logger.isDebugEnabled()) {
            logger.debug(log);
        }


    }

    @Override
    public void error(String log) {
        logger.error(log);

    }

    @Override
    public void error(String log, Throwable throwable) {
        logger.error(log,throwable);
    }
}
