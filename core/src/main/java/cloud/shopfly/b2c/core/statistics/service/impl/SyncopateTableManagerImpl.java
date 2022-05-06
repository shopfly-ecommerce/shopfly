/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.statistics.service.impl;

import cloud.shopfly.b2c.core.statistics.service.SyncopateTableManager;
import cloud.shopfly.b2c.core.statistics.util.SyncopateUtil;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * SyncopateTableManagerImpl
 *
 * @author Chopper
 * @version v1.0
 * @since v7.0
 * 2018-08-14 上午9:59
 */
@Service
public class SyncopateTableManagerImpl implements SyncopateTableManager {

    @Autowired
    
    private DaoSupport daoSupport;

    /**
     * 每日填充数据
     */
    @Override
    public void everyDay() {
        SyncopateUtil.syncopateTable(LocalDate.now().getYear(), daoSupport);
    }

}