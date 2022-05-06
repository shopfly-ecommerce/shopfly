/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.client.statistics.impl;

import cloud.shopfly.b2c.core.client.statistics.RefundDataClient;
import cloud.shopfly.b2c.core.statistics.model.dto.RefundData;
import cloud.shopfly.b2c.core.statistics.service.RefundDataManager;
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
@ConditionalOnProperty(value="shopfly.product", havingValue="stand")
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
