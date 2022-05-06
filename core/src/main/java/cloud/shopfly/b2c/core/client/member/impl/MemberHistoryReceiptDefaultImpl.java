/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.client.member.impl;

import cloud.shopfly.b2c.core.client.member.MemberHistoryReceiptClient;
import cloud.shopfly.b2c.core.member.model.dos.ReceiptHistory;
import cloud.shopfly.b2c.core.member.service.ReceiptHistoryManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

/**
 * 会员历史默认发票实现
 *
 * @author zh
 * @version v7.0
 * @date 18/7/27 下午2:57
 * @since v7.0
 */

@Service
@ConditionalOnProperty(value = "shopfly.product", havingValue = "stand")
public class MemberHistoryReceiptDefaultImpl implements MemberHistoryReceiptClient {

    @Autowired
    private ReceiptHistoryManager receiptHistoryManager;

    @Override
    public ReceiptHistory getReceiptHistory(String orderSn) {
        return receiptHistoryManager.getReceiptHistory(orderSn);
    }

    @Override
    public ReceiptHistory add(ReceiptHistory receiptHistory) {
        return receiptHistoryManager.add(receiptHistory);
    }
}
