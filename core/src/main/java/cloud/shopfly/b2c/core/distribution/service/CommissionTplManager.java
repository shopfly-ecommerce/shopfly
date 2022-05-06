/*
 *  Copyright 2008-2022 Shopfly.cloud Group.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package cloud.shopfly.b2c.core.distribution.service;

import cloud.shopfly.b2c.core.distribution.model.dos.CommissionTpl;
import cloud.shopfly.b2c.framework.database.Page;

/**
 * 提成模板manager接口
 *
 * @author Chopper
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/5/22 上午10:52
 */

public interface CommissionTplManager {


    /**
     * 获取某个会员使用的模版
     *
     * @param memberId 会员id
     * @return CommissionTplDO
     */
    CommissionTpl getCommissionTplByMember(int memberId);

    /**
     * page
     *
     * @param page     页码
     * @param pageSize 分页大小
     * @return page
     */
    Page page(int page, int pageSize);

    /**
     * 通过id获得CommissionTpl
     *
     * @param id
     * @return CommissionTpl
     */
    CommissionTpl getModel(int id);


    /**
     * 添加一个commissionTpl
     *
     * @param commissionTpl 模版
     * @return CommissionTplDO
     */
    CommissionTpl add(CommissionTpl commissionTpl);


    /**
     * 修改一个CommissionTpl
     *
     * @param commissionTpl
     * @return CommissionTplDO
     */
    CommissionTpl edit(CommissionTpl commissionTpl);

    /**
     * 删除一个CommissionTpl
     *
     * @param tplId
     */
    void delete(Integer tplId);

    /**
     * 得到一个默认的模版
     *
     * @return DO
     */
    CommissionTpl getDefaultCommission();
}
