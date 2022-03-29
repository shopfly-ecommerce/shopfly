/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.distribution.service;

import com.enation.app.javashop.core.distribution.model.dos.UpgradeLogDO;
import com.enation.app.javashop.core.distribution.model.enums.UpgradeTypeEnum;
import com.enation.app.javashop.framework.database.Page;


/**
 * 升级日志管理类
 *
 * @author Chopper
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/5/22 下午12:56
 */

public interface UpgradeLogManager {

    /**
     * 搜索
     *
     * @param page       分页
     * @param pageSize   分页每页数量
     * @param memberName 会员名
     * @return Page
     */
    Page<UpgradeLogDO> page(int page, int pageSize, String memberName);

    /**
     * 新增一个模板升级日志
     *
     * @param upgradeLog
     * @return do
     */
    UpgradeLogDO add(UpgradeLogDO upgradeLog);

    /**
     * 新增日志,一定要再修改之前【因为旧的模板id是根据用户id现查的】
     *
     * @param memberId        会员id
     * @param newTplId        新的模板id
     * @param upgradeTypeEnum 模版操作类型
     */
    void addUpgradeLog(int memberId, int newTplId, UpgradeTypeEnum upgradeTypeEnum);
}
