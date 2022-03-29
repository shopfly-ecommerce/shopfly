/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.system.service;

import com.enation.app.javashop.core.system.model.dos.SmtpDO;
import com.enation.app.javashop.framework.database.Page;

/**
 * 邮件业务层
 *
 * @author zh
 * @version v7.0.0
 * @since v7.0.0
 * 2018-03-25 16:16:53
 */
public interface SmtpManager {

    /**
     * 查询邮件列表
     *
     * @param page     页码
     * @param pageSize 每页数量
     * @return Page
     */
    Page list(int page, int pageSize);

    /**
     * 修改邮件
     *
     * @param smtp 邮件
     * @param id   邮件主键
     * @return Smtp 邮件
     */
    SmtpDO edit(SmtpDO smtp, Integer id);

    /**
     * 获取邮件
     *
     * @param id 邮件主键
     * @return Smtp  邮件
     */
    SmtpDO getModel(Integer id);

    /**
     * 添加邮件
     *
     * @param smtp 邮件
     * @return Smtp 邮件
     */
    SmtpDO add(SmtpDO smtp);

    /**
     * 删除邮件
     *
     * @param id 邮件主键
     */
    void delete(Integer id);

    /**
     * 发送测试邮件
     *
     * @param send 发送邮件地址
     * @param smtp smtp设置
     */
    void send(String send, SmtpDO smtp);

    /**
     * 获取当前使用的smtp方案
     *
     * @return smtp
     */
    SmtpDO getCurrentSmtp();
}