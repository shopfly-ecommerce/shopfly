/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.pagedata.service;

import cloud.shopfly.b2c.core.pagedata.model.SiteNavigation;
import cloud.shopfly.b2c.framework.database.Page;

import java.util.List;

/**
 * 导航栏业务层
 *
 * @author fk
 * @version v1.0
 * @since v7.0.0
 * 2018-06-12 17:07:22
 */
public interface SiteNavigationManager {

    /**
     * 查询导航栏列表
     *
     * @param page     页码
     * @param pageSize 每页数量
     * @param clientType
     * @return Page
     */
    Page list(int page, int pageSize, String clientType);

    /**
     * 添加导航栏
     *
     * @param siteNavigation 导航栏
     * @return SiteNavigation 导航栏
     */
    SiteNavigation add(SiteNavigation siteNavigation);

    /**
     * 修改导航栏
     *
     * @param siteNavigation 导航栏
     * @param id             导航栏主键
     * @return SiteNavigation 导航栏
     */
    SiteNavigation edit(SiteNavigation siteNavigation, Integer id);

    /**
     * 删除导航栏
     *
     * @param id 导航栏主键
     */
    void delete(Integer id);

    /**
     * 获取导航栏
     *
     * @param id 导航栏主键
     * @return SiteNavigation  导航栏
     */
    SiteNavigation getModel(Integer id);

    /**
     * 更新排序
     * @param id 菜单id
     * @param sort	上移和下移
     * @return 导航菜单
     */
    SiteNavigation updateSort(Integer id, String sort);

    /**
     * 客户端查询列表
     * @param clientType
     * @return
     */
    List<SiteNavigation> listByClientType(String clientType);
}