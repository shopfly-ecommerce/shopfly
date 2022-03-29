/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.system.service;

import dev.shopflix.core.system.model.dos.AdminUser;
import dev.shopflix.core.system.model.vo.AdminLoginVO;
import dev.shopflix.core.system.model.vo.AdminUserVO;
import dev.shopflix.framework.database.Page;

/**
 * 平台管理员业务层
 *
 * @author zh
 * @version v7.0
 * @since v7.0.0
 * 2018-06-20 20:38:26
 */
public interface AdminUserManager {

    /**
     * 查询平台管理员列表
     *
     * @param page     页码
     * @param pageSize 每页数量
     * @return Page
     */
    Page list(int page, int pageSize);

    /**
     * 添加平台管理员
     *
     * @param adminUserVO 平台管理员
     * @return AdminUser 平台管理员
     */
    AdminUser add(AdminUserVO adminUserVO);

    /**
     * 修改平台管理员
     *
     * @param adminUserVO 平台管理员
     * @param id          平台管理员主键
     * @return AdminUser 平台管理员
     */
    AdminUser edit(AdminUserVO adminUserVO, Integer id);

    /**
     * 删除平台管理员
     *
     * @param id 平台管理员主键
     */
    void delete(Integer id);

    /**
     * 获取平台管理员
     *
     * @param id 平台管理员主键
     * @return AdminUser  平台管理员
     */
    AdminUser getModel(Integer id);

    /**
     * 管理员登录
     *
     * @param name     管理员名称
     * @param password 管理员密码
     * @return
     */
    AdminLoginVO login(String name, String password);

    /**
     * 通过refreshToken重新获取accessToken
     *
     * @param refreshToken
     * @return
     */
    String exchangeToken(String refreshToken);

    /**
     * 管理员注销
     *
     * @param uid 会员id
     */
    void logout(Integer uid);


}