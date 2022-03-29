/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.payment.service.impl;

import com.enation.app.javashop.core.payment.PaymentErrorCode;
import com.enation.app.javashop.core.payment.model.dos.PaymentBillDO;
import com.enation.app.javashop.core.payment.model.enums.TradeType;
import com.enation.app.javashop.core.payment.service.PaymentBillManager;
import com.enation.app.javashop.core.payment.service.PaymentCallbackDevice;
import com.enation.app.javashop.framework.database.DaoSupport;
import com.enation.app.javashop.framework.database.Page;
import com.enation.app.javashop.framework.exception.ServiceException;
import com.enation.app.javashop.framework.logs.Debugger;
import com.enation.app.javashop.framework.logs.Logger;
import com.enation.app.javashop.framework.logs.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 支付帐单业务类
 *
 * @author fk
 * @version v2.0
 * @since v7.0.0
 * 2018-04-16 17:28:07
 */
@Service
public class PaymentBillManagerImpl implements PaymentBillManager {

    @Autowired
    @Qualifier("tradeDaoSupport")
    private DaoSupport daoSupport;

    @Autowired
    private Debugger debugger;

    @Autowired
    private List<PaymentCallbackDevice> callbackDeviceList;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public Page list(int page, int pageSize) {

        String sql = "select * from es_payment_bill  ";
        Page webPage = this.daoSupport.queryForPage(sql, page, pageSize, PaymentBillDO.class);

        return webPage;
    }

    @Override
    @Transactional(value = "tradeTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public PaymentBillDO add(PaymentBillDO paymentBill) {
        this.daoSupport.insert(paymentBill);

        return paymentBill;
    }

    @Override
    @Transactional(value = "tradeTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void paySuccess(String billSn, String returnTradeNo, TradeType tradeType, double payPrice) {

        if (callbackDeviceList == null && callbackDeviceList.isEmpty()) {
            logger.error("支付回调失败，原因为：【没有任何回调器声明】");
            throw new ServiceException(PaymentErrorCode.E507.code(), "支付回调失败，原因为：【没有任何回调器声明】");
        } else {

            PaymentCallbackDevice device = findDevice(tradeType);
            if (device == null) {
                logger.error("支付回调失败，原因为：【" + tradeType.name() + "没有适配回调器】");
                debugger.log("支付回调失败，原因为：【" + tradeType.name() + "没有适配回调器】");
                throw new ServiceException(PaymentErrorCode.E507.code(), "支付回调失败，原因为：【" + tradeType.name() + "没有适配回调器】");
            }

            //根据支付单号找到交易单号
            PaymentBillDO bill = this.getByBillSn(billSn);

            if (bill == null) {
                debugger.log("支付回调失败，原因为：【" + tradeType.name() + "类型的交易,编号为：" + billSn + "没有找到相应的账单】");
                logger.error("支付回调失败，原因为：【" + tradeType.name() + "类型的交易,编号为：" + billSn + "没有找到相应的账单】");
                throw new RuntimeException("支付回调失败，原因为：【" + tradeType.name() + "类型的交易编号为：" + billSn + "没有找到相应的账单】");
            }

            logger.debug("找到账单：");
            debugger.log("找到账单：");
            logger.debug(bill.toString());
            debugger.log(bill.toString());

            String tradeSn = bill.getSn();

            //调用回调器完成交易状态的变更
            device.paySuccess(tradeSn, returnTradeNo, payPrice);
            logger.debug("调用：" + device + "成功");
            debugger.log("调用：" + device + "成功");

            //修改支付账单的状态
            daoSupport.execute("update es_payment_bill set is_pay=1,return_trade_no=? where bill_id=?", returnTradeNo, bill.getBillId());

            logger.debug("更改支付账单状态成功");
            debugger.log("更改支付账单状态成功");
        }

    }

    private PaymentBillDO getByBillSn(String billSn) {
        String sql = "select * from es_payment_bill where out_trade_no = ?";
        return this.daoSupport.queryForObject(sql, PaymentBillDO.class, billSn);

    }

    /**
     * 在回调器列表中到合适的回调器
     *
     * @param tradeType 交易类型
     * @return
     */
    private PaymentCallbackDevice findDevice(TradeType tradeType) {
        for (PaymentCallbackDevice device : callbackDeviceList) {
            if (tradeType.equals(device.tradeType())) {
                return device;
            }
        }
        return null;
    }


    @Override
    public PaymentBillDO getBillBySnAndTradeType(String sn, String tradeType) {

        String sql = "select * from es_payment_bill where sn = ? and trade_type = ? order by bill_id desc limit 0,1";

        return this.daoSupport.queryForObject(sql, PaymentBillDO.class, sn, tradeType);
    }

    @Override
    public PaymentBillDO getBillByReturnTradeNo(String returnTradeNo) {

        String sql = "select * from es_payment_bill where return_trade_no = ? ";

        return this.daoSupport.queryForObject(sql, PaymentBillDO.class, returnTradeNo);
    }
}
