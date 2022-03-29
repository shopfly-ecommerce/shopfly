/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.system.service;

import dev.shopflix.core.system.model.dos.NoticeLogDO;
import dev.shopflix.framework.database.Page;

/**
 * 店铺站内消息业务层
 *
 * @author zjp
 * @version v7.0.0
 * @since v7.0.0
 * 2018-07-10 10:21:45
 */
public interface NoticeLogManager {

    /**
     * 查询店铺站内消息列表
     *
     * @param page     页码
     * @param pageSize 每页数量
     * @param type     类型
     * @param isRead   1 已读，0 未读
     * @return Page
     */
    Page list(int page, int pageSize, String type, Integer isRead);

    /**
     * 添加店铺站内消息
     *
     * @param shopNoticeLog 店铺站内消息
     * @return ShopNoticeLog 店铺站内消息
     */
    NoticeLogDO add(NoticeLogDO shopNoticeLog);

    /**
     * 删除历史消息
     *
     * @param ids
     */
    void delete(Integer[] ids);

    /**
     * 设置已读
     *
     * @param ids 消息id
     */
    void read(Integer[] ids);

}