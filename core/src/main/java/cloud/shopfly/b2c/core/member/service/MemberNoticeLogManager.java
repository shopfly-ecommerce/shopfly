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
package cloud.shopfly.b2c.core.member.service;

import cloud.shopfly.b2c.core.member.model.dos.MemberNoticeLog;
import cloud.shopfly.b2c.framework.database.Page;

/**
 * 会员站内消息历史业务层
 *
 * @author zh
 * @version v7.0.0
 * @since v7.0.0
 * 2018-07-05 14:10:16
 */
public interface MemberNoticeLogManager {

    /**
     * 查询会员站内消息历史列表
     *
     * @param page     页码
     * @param pageSize 每页数量
     * @param read     是否已读,1已读 0未读
     * @return Page
     */
    Page list(int page, int pageSize, Integer read);

    /**
     * 添加会员站内消息历史
     *
     * @param content  消息内容
     * @param sendTime 发送时间
     * @param memberId 会员id
     * @param title    标题
     * @return 历史消息
     */
    MemberNoticeLog add(String content, long sendTime, Integer memberId, String title);

    /**
     * 设置已读
     *
     * @param ids 消息id
     */
    void read(Integer[] ids);

    /**
     * 删除历史消息
     *
     * @param ids
     */
    void delete(Integer[] ids);

}