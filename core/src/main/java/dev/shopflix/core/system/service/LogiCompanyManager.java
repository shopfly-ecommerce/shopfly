/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.system.service;

import dev.shopflix.core.system.model.dos.LogiCompanyDO;
import dev.shopflix.framework.database.Page;

import java.util.List;

/**
 * 物流公司业务层
 *
 * @author zjp
 * @version v7.0.0
 * @since v7.0.0
 * 2018-03-29 15:10:38
 */
public interface LogiCompanyManager {

    /**
     * 查询物流公司列表
     *
     * @param page     页码
     * @param pageSize 每页数量
     * @return Page
     */
    Page list(int page, int pageSize);

    /**
     * 添加物流公司
     *
     * @param logi 物流公司
     * @return Logi 物流公司
     */
    LogiCompanyDO add(LogiCompanyDO logi);

    /**
     * 修改物流公司
     *
     * @param logi 物流公司
     * @param id   物流公司主键
     * @return Logi 物流公司
     */
    LogiCompanyDO edit(LogiCompanyDO logi, Integer id);

    /**
     * 删除物流公司
     *
     * @param id 物流公司主键
     */
    void delete(Integer[] id);

    /**
     * 获取物流公司
     *
     * @param id 物流公司主键
     * @return Logi  物流公司
     */
    LogiCompanyDO getModel(Integer id);

    /**
     * 通过code获取物流公司
     *
     * @param code 物流公司code
     * @return 物流公司
     */
    LogiCompanyDO getLogiByCode(String code);

    /**
     * 通过快递鸟物流code获取物流公司
     *
     * @param kdcode 快递鸟公司code
     * @return 物流公司
     */
    LogiCompanyDO getLogiBykdCode(String kdcode);

    /**
     * 根据物流名称查询物流信息
     *
     * @param name 物流名称
     * @return 物流公司
     */
    LogiCompanyDO getLogiByName(String name);

    /**
     * 查询物流公司列表(不分页)
     *
     * @return Page
     */
    List<LogiCompanyDO> list();

}