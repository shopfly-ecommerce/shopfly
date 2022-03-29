/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.trade.order.service;

import com.enation.app.javashop.core.trade.order.model.dos.TransactionRecord;

import java.util.List;

/**
 * 交易记录表业务层
 *
 * @author Snow
 * @version v7.0.0
 * @since v7.0.0
 * 2018-05-25 15:37:56
 */
public interface TransactionRecordManager {

    /**
     * 查询交易记录表列表
     *
     * @param orderSn 页码
     * @return List
     */
    List<TransactionRecord> listAll(String orderSn);

    /**
     * 添加交易记录表
     *
     * @param transactionRecord 交易记录表
     * @return TransactionRecord 交易记录表
     */
    TransactionRecord add(TransactionRecord transactionRecord);

    /**
     * 修改交易记录表
     *
     * @param transactionRecord 交易记录表
     * @param id                交易记录表主键
     * @return TransactionRecord 交易记录表
     */
    TransactionRecord edit(TransactionRecord transactionRecord, Integer id);

    /**
     * 删除交易记录表
     *
     * @param id 交易记录表主键
     */
    void delete(Integer id);

    /**
     * 获取交易记录表
     *
     * @param id 交易记录表主键
     * @return TransactionRecord  交易记录表
     */
    TransactionRecord getModel(Integer id);

}
