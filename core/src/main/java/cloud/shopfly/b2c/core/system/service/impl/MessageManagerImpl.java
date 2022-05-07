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
package cloud.shopfly.b2c.core.system.service.impl;

import cloud.shopfly.b2c.core.member.MemberErrorCode;
import cloud.shopfly.b2c.core.system.model.dos.Message;
import cloud.shopfly.b2c.core.system.model.dto.MessageDTO;
import cloud.shopfly.b2c.core.system.model.vo.MessageVO;
import cloud.shopfly.b2c.core.system.service.MessageManager;
import cloud.shopfly.b2c.core.base.rabbitmq.AmqpExchange;
import cloud.shopfly.b2c.framework.context.AdminUserContext;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import cloud.shopfly.b2c.framework.database.Page;
import cloud.shopfly.b2c.framework.exception.ServiceException;
import cloud.shopfly.b2c.framework.util.BeanUtil;
import cloud.shopfly.b2c.framework.util.DateUtil;
import cloud.shopfly.b2c.framework.util.StringUtil;
import cloud.shopfly.b2c.framework.rabbitmq.MessageSender;
import cloud.shopfly.b2c.framework.rabbitmq.MqMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Intra-station message business class
 *
 * @author zh
 * @version v7.0.0
 * @since v7.0.0
 * 2018-07-04 21:50:52
 */
@Service
public class MessageManagerImpl implements MessageManager {

    @Autowired
    private MessageSender messageSender;

    @Autowired
    
    private DaoSupport systemDaoSupport;

    @Override
    public Page list(int page, int pageSize) {
        String sql = "select * from es_message order by send_time desc ";
        Page webPage = this.systemDaoSupport.queryForPage(sql, page, pageSize, MessageDTO.class);
        return webPage;
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Message add(MessageVO messageVO) {
        if (messageVO.getSendType().equals(1)) {
            if (StringUtil.isEmpty(messageVO.getMemberIds())) {
                throw new ServiceException(MemberErrorCode.E122.code(), "Please specify sending member");
            }
        }
        Message message = new Message();
        BeanUtil.copyProperties(messageVO, message);
        message.setAdminId(AdminUserContext.getAdmin().getUid());
        message.setAdminName(AdminUserContext.getAdmin().getUsername());
        message.setSendTime(DateUtil.getDateline());
        this.systemDaoSupport.insert(message);
        Integer id = this.systemDaoSupport.getLastId("es_message");
        message.setId(id);
        this.messageSender.send(new MqMessage(AmqpExchange.MEMBER_MESSAGE, "member-message-routingkey", id));
        return message;
    }

    @Override
    public Message get(Integer id) {
        String sql = "select * from es_message where id = ?";
        return this.systemDaoSupport.queryForObject(sql, Message.class, id);
    }
}
