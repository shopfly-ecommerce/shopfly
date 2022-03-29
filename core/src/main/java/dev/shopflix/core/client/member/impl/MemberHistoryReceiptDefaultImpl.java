/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.client.member.impl;

import dev.shopflix.core.client.member.MemberHistoryReceiptClient;
import dev.shopflix.core.member.model.dos.ReceiptHistory;
import dev.shopflix.core.member.service.ReceiptHistoryManager;
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
@ConditionalOnProperty(value = "javashop.product", havingValue = "stand")
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
