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

import cloud.shopfly.b2c.core.system.model.dos.NoticeLogDO;
import cloud.shopfly.b2c.framework.database.Page;

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