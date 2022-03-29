/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.base.rabbitmq;

/**
 * 延迟加载任务执行器
 *
 * @author zh
 * @version v7.0
 * @date 19/3/1 下午2:13
 * @since v7.0
 */
public class TimeExecute {

    /**
     * 促销延迟加载执行器
     */
    public final static String PROMOTION_EXECUTER = "promotionTimeTriggerExecuter";
    /**
     * 拼团延迟加载执行器
     */
    public final static String PINTUAN_EXECUTER = "pintuanTimeTriggerExecute";
}
