/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.consumer.job.execute.impl;

import dev.shopflix.consumer.job.execute.EveryHourExecute;
import dev.shopflix.core.aftersale.service.AfterSaleManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 每小时执行
 *
 * @author Chopper
 * @version v1.0
 * @since v7.0
 * 2018-07-25 上午10:21
 */
@Component
public class RefundStatusJob implements EveryHourExecute {

    @Autowired
    private AfterSaleManager afterSaleManager;
    /**
     * 每小时执行
     */
    @Override
    public void everyHour() {
        afterSaleManager.queryRefundStatus();
    }
}
