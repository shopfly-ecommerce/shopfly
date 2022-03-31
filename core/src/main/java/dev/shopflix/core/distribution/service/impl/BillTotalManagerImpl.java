/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.distribution.service.impl;

import dev.shopflix.core.distribution.model.dos.BillTotalDO;
import dev.shopflix.core.distribution.service.BillTotalManager;
import dev.shopflix.framework.database.DaoSupport;
import dev.shopflix.framework.database.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * 总结算单处理
 *
 * @author Chopper
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/6/14 上午7:13
 */

@Service
public class BillTotalManagerImpl implements BillTotalManager {

    @Autowired
    
    private DaoSupport daoSupport;

    @Override
    public Page page(int page, int pageSize) {
        return this.daoSupport.queryForPage("select * from es_bill_total", page, pageSize);
    }


    @Override
    public BillTotalDO add(BillTotalDO billTotal) {
        daoSupport.insert("es_bill_total", billTotal);
        return billTotal;

    }


    @Override
    public BillTotalDO getTotalByStart(Long startTime) {
        return this.daoSupport.queryForObject("select * from es_bill_total where start_time = ? ", BillTotalDO.class, startTime);
    }


}
