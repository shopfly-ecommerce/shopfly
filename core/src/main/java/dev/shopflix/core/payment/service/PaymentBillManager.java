/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.payment.service;

import dev.shopflix.core.payment.model.dos.PaymentBillDO;
import dev.shopflix.core.payment.model.enums.TradeType;
import dev.shopflix.framework.database.Page;

import java.util.List;

/**
 * 支付帐单业务层
 *
 * @author fk
 * @version v2.0
 * @since v7.0.0
 * 2018-04-16 17:28:07
 */
public interface PaymentBillManager {

    /**
     * 查询支付帐单列表
     *
     * @param page     页码
     * @param pageSize 每页数量
     * @return Page
     */
    Page list(int page, int pageSize);

    /**
     * 添加支付帐单
     *
     * @param paymentBill 支付帐单
     * @return PaymentBill 支付帐单
     */
    PaymentBillDO add(PaymentBillDO paymentBill);

    /**
     * 支付成功调用
     *
     * @param billSn        支付账单号
     * @param returnTradeNo 第三方平台回传单号（第三方平台的支付单号）
     * @param tradeType     交易类型
     * @param payPrice      支付金额
     */
    void paySuccess(String billSn, String returnTradeNo, TradeType tradeType, double payPrice);


    /**
     * 使用单号和交易类型查询对应的支付流水，最后一条
     *
     * @param sn
     * @param tradeType
     * @return
     */
    PaymentBillDO getBillBySnAndTradeType(String sn, String tradeType);

    /**
     * 使用第三方单号查询流水
     *
     * @param returnTradeNo
     * @return
     */
    PaymentBillDO getBillByReturnTradeNo(String returnTradeNo);


    /**
     * 根据billSn修改第三方交易流水号
     * @param billSn
     * @param returnTradeNo
     */
    void updateTradeNoByBillSn(String billSn,String returnTradeNo);

    /**
     * 根据billSn读取数据
     * @param billSn
     * @return
     */
    PaymentBillDO getBillByBillSn(String billSn);


    List<PaymentBillDO> getWaitPay();

}