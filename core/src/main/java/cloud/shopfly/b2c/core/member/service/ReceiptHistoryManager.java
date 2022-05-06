/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.member.service;

import cloud.shopfly.b2c.core.member.model.dos.ReceiptHistory;
import cloud.shopfly.b2c.core.member.model.vo.ReceiptHistoryVO;
import cloud.shopfly.b2c.framework.database.Page;

/**
 * 发票历史业务层
 *
 * @author zh
 * @version v7.0.0
 * @since v7.0.0
 * 2018-07-20 20:48:09
 */
public interface ReceiptHistoryManager {

    /**
     * 查询发票历史列表
     *
     * @param page     页码
     * @param pageSize 每页数量
     * @return Page
     */
    Page list(int page, int pageSize);

    /**
     * 添加发票历史
     *
     * @param receiptHistory 发票历史
     * @return ReceiptHistory 发票历史
     */
    ReceiptHistory add(ReceiptHistory receiptHistory);

    /**
     * 根据订单sn查询历史发票信息
     *
     * @param orderSn 订单sn
     * @return 历史发票信息
     */
    ReceiptHistory getReceiptHistory(String orderSn);


    /**
     * 获取发票历史详细信息，包括收货地址已经商品sku相关信息
     *
     * @param historyId 发票历史的id
     * @return 发票详细VO
     */
    ReceiptHistoryVO getReceiptDetail(Integer historyId);

}