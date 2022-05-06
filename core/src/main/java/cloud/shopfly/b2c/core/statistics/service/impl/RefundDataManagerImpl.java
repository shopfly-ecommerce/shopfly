/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.statistics.service.impl;

import cloud.shopfly.b2c.core.statistics.model.dto.RefundData;
import cloud.shopfly.b2c.core.statistics.service.RefundDataManager;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
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
    
    private DaoSupport daoSupport;


    @Override
    public void put(RefundData refundData) {
        //审核通过
        this.daoSupport.insert("es_sss_refund_data", refundData);

    }

}
