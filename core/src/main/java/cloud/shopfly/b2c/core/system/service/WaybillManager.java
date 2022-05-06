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
package cloud.shopfly.b2c.core.system.service;

import cloud.shopfly.b2c.core.system.model.dos.WayBillDO;
import cloud.shopfly.b2c.core.system.model.vo.WayBillVO;
import cloud.shopfly.b2c.framework.database.Page;

/**
 * 电子面单业务层
 *
 * @author zh
 * @version v7.0.0
 * @since v7.0.0
 * 2018-06-08 16:26:05
 */
public interface WaybillManager {

    /**
     * 查询电子面单列表
     *
     * @param page     页码
     * @param pageSize 每页数量
     * @return Page
     */
    Page list(int page, int pageSize);

    /**
     * 添加电子面单
     *
     * @param wayBill 电子面单
     * @return WayBillDO 电子面单
     */
    WayBillDO add(WayBillVO wayBill);

    /**
     * 修改电子面单
     *
     * @param wayBill 电子面单
     * @return WayBillDO 电子面单
     */
    WayBillVO edit(WayBillVO wayBill);

    /**
     * 获取电子面单
     *
     * @param id 电子面单主键
     * @return WayBillDO  电子面单
     */
    WayBillDO getModel(Integer id);

    /**
     * 开启电子面单
     *
     * @param bean beanid
     */
    void open(String bean);


    /**
     * 根据beanid获取电子面单方案
     *
     * @param bean
     * @return
     */
    WayBillDO getWayBillByBean(String bean);


    /**
     * 根据beanid获取电子面单方案
     *
     * @param bean beanid
     * @return 电子面单vo
     */
    WayBillVO getWaybillConfig(String bean);

    /**
     * 生成电子面单
     *
     * @param orderSn 订单编号
     * @param logiId  物流公司id
     * @return
     */
    String createPrintData(String orderSn, Integer logiId);

}