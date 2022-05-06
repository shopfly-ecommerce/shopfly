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

import cloud.shopfly.b2c.core.system.model.dos.Message;
import cloud.shopfly.b2c.core.system.model.vo.MessageVO;
import cloud.shopfly.b2c.framework.database.Page;

/**
 * 站内消息业务层
 *
 * @author zh
 * @version v7.0.0
 * @since v7.0.0
 * 2018-07-04 21:50:52
 */
public interface MessageManager {

    /**
     * 查询站内消息列表
     *
     * @param page     页码
     * @param pageSize 每页数量
     * @return Page
     */
    Page list(int page, int pageSize);

    /**
     * 添加站内消息
     *
     * @param messageVO 站内消息
     * @return Message 站内消息
     */
    Message add(MessageVO messageVO);

    /**
     * 通过id查询站内消息
     *
     * @param id 消息id
     * @return 站内消息对象
     */
    Message get(Integer id);
}