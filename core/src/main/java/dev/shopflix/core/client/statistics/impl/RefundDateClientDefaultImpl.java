/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.client.statistics.impl;

import dev.shopflix.core.client.statistics.RefundDataClient;
import dev.shopflix.core.statistics.model.dto.RefundData;
import dev.shopflix.core.statistics.service.RefundDataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

/**
 * RefundDateClientDefaultImpl
 *
 * @author Chopper
 * @version v1.0
 * @since v7.0
 * 2018-08-14 下午2:42
 */
@Service
@ConditionalOnProperty(value="shopflix.product", havingValue="stand")
public class RefundDateClientDefaultImpl implements RefundDataClient {

    @Autowired
    private RefundDataManager refundDataManager;
    /**
     * 退款消息写入
     *
     * @param refundData
     */
    @Override
    public void put(RefundData refundData) {
        refundDataManager.put(refundData);
    }
}
