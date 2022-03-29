/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.system.service;

import com.enation.app.javashop.core.system.model.dos.ExpressPlatformDO;
import com.enation.app.javashop.core.system.model.vo.ExpressDetailVO;
import com.enation.app.javashop.core.system.model.vo.ExpressPlatformVO;
import com.enation.app.javashop.framework.database.Page;

/**
 * 快递平台业务层
 *
 * @author zh
 * @version v7.0.0
 * @since v7.0.0
 * 2018-07-11 14:42:50
 */
public interface ExpressPlatformManager {

    /**
     * 查询快递平台列表
     *
     * @param page     页码
     * @param pageSize 每页数量
     * @return Page
     */
    Page list(int page, int pageSize);

    /**
     * 添加快递平台
     *
     * @param expressPlatformVO 快递平台
     * @return expressPlatformVO 快递平台
     */
    ExpressPlatformVO add(ExpressPlatformVO expressPlatformVO);

    /**
     * 修改快递平台
     *
     * @param expressPlatformVO 快递平台
     * @return ExpressPlatformDO 快递平台
     */
    ExpressPlatformVO edit(ExpressPlatformVO expressPlatformVO);

    /**
     * 根据beanid获取快递平台
     *
     * @param bean beanid
     * @return
     */
    ExpressPlatformDO getExpressPlatform(String bean);

    /**
     * 根据快递平台的beanid 获取快递平台的配置项
     *
     * @param bean 快递平台beanid
     * @return 快递平台
     */
    ExpressPlatformVO getExoressConfig(String bean);

    /**
     * 开启某个快递平台
     *
     * @param bean
     */
    void open(String bean);

    /**
     * 查询物流信息
     *
     * @param id 物流公司id
     * @param nu  物流单号
     * @return 物流详细
     */
    ExpressDetailVO getExpressDetail(Integer id, String nu);
}