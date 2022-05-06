/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.member.service.impl;

import cloud.shopfly.b2c.core.client.trade.OrderClient;
import cloud.shopfly.b2c.core.member.model.dos.ReceiptHistory;
import cloud.shopfly.b2c.core.member.model.vo.ReceiptHistoryVO;
import cloud.shopfly.b2c.core.member.service.ReceiptHistoryManager;
import cloud.shopfly.b2c.core.trade.sdk.model.OrderDetailDTO;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import cloud.shopfly.b2c.framework.database.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 发票历史业务类
 *
 * @author zh
 * @version v7.0.0
 * @since v7.0.0
 * 2018-07-20 20:48:09
 */
@Service
public class ReceiptHistoryManagerImpl implements ReceiptHistoryManager {

    @Autowired

    private DaoSupport memberDaoSupport;

    @Autowired
    private OrderClient orderClient;

    @Override
    public Page list(int page, int pageSize) {
        //金额为0的发票不显示
        StringBuffer sqlBuffer = new StringBuffer("select * from es_receipt_history where receipt_amount != 0 ");
        sqlBuffer.append(" order by add_time desc");
        Page webPage = this.memberDaoSupport.queryForPage(sqlBuffer.toString(), page, pageSize, ReceiptHistory.class);
        return webPage;
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ReceiptHistory add(ReceiptHistory receiptHistory) {
        this.memberDaoSupport.insert(receiptHistory);
        receiptHistory.setHistoryId(memberDaoSupport.getLastId("es_history_receipt"));
        return receiptHistory;
    }

    @Override
    public ReceiptHistory getReceiptHistory(String orderSn) {
        ReceiptHistory receiptHistory = this.memberDaoSupport.queryForObject("select * from es_receipt_history where order_sn = ?", ReceiptHistory.class, orderSn);
        if (receiptHistory != null) {
            return receiptHistory;
        }
        return new ReceiptHistory();
    }

    @Override
    public ReceiptHistoryVO getReceiptDetail(Integer historyId) {
        //获取发票详细信息
        StringBuffer sqlBuffer = new StringBuffer("select * from es_receipt_history where history_id = ?");
        ReceiptHistory receiptHistory = this.memberDaoSupport.queryForObject(sqlBuffer.toString(), ReceiptHistory.class, historyId);
        if (receiptHistory == null) {
            return new ReceiptHistoryVO();
        }
        //查询订单信息
        OrderDetailDTO orderDetailDTO = orderClient.getModel(receiptHistory.getOrderSn());
        ReceiptHistoryVO receiptHistoryVO = new ReceiptHistoryVO(receiptHistory, orderDetailDTO);

        return receiptHistoryVO;
    }
}
