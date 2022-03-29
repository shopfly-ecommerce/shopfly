/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.client.statistics.impl;

import dev.shopflix.core.client.statistics.SyncopateTableClient;
import dev.shopflix.core.statistics.service.SyncopateTableManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

/**
 * 统计数据填充
 *
 * @author chopper
 * @version v1.0
 * @since v7.0
 * 2018-06-22 上午8:41
 */
@Service
@ConditionalOnProperty(value = "javashop.product", havingValue = "stand")
public class SyncopateTableDefaultImpl implements SyncopateTableClient {
    @Autowired
    private SyncopateTableManager syncopateTableManager;

    /**
     * 每日填充数据
     */
    @Override
    public void everyDay() {
        syncopateTableManager.everyDay();
    }

}
