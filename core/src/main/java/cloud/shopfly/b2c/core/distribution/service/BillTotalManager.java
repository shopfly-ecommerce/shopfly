/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.distribution.service;

import cloud.shopfly.b2c.core.distribution.model.dos.BillTotalDO;
import cloud.shopfly.b2c.framework.database.Page;

/**
 * 用户结算单
 *
 * @author Chopper
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/5/22 上午9:30
 */

public interface BillTotalManager {
    /**
     * 获取结算page
     *
     * @param page     页码
     * @param pageSize 分页大小
     * @return
     */
    Page page(int page, int pageSize);

    /**
     * 新增一个总结算单
     *
     * @param billTotal
     * @return
     */
    BillTotalDO add(BillTotalDO billTotal);

    /**
     * 获取
     *
     * @param startTime
     * @return
     */
    BillTotalDO getTotalByStart(Long startTime);
}
