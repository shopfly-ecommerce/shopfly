/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.statistics.service.impl;

import com.enation.app.javashop.core.statistics.model.dto.RefundData;
import com.enation.app.javashop.core.statistics.service.RefundDataManager;
import com.enation.app.javashop.framework.database.DaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * 退款变化业务实现类
 *
 * @author mengyuanming
 * @version 2.0
 * @since 7.0
 * 2018/6/4 9:59
 */
@Service
public class RefundDataManagerImpl implements RefundDataManager {

    @Autowired
    @Qualifier("sssDaoSupport")
    private DaoSupport daoSupport;


    @Override
    public void put(RefundData refundData) {
        //审核通过
        this.daoSupport.insert("es_sss_refund_data", refundData);

    }

}
