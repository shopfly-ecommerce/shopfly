/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.system.service.impl;

import dev.shopflix.core.base.rabbitmq.AmqpExchange;
import dev.shopflix.core.member.MemberErrorCode;
import dev.shopflix.core.system.model.dos.Message;
import dev.shopflix.core.system.model.dto.MessageDTO;
import dev.shopflix.core.system.model.vo.MessageVO;
import dev.shopflix.core.system.service.MessageManager;
import dev.shopflix.framework.context.AdminUserContext;
import dev.shopflix.framework.database.DaoSupport;
import dev.shopflix.framework.database.Page;
import dev.shopflix.framework.exception.ServiceException;
import dev.shopflix.framework.util.BeanUtil;
import dev.shopflix.framework.util.DateUtil;
import dev.shopflix.framework.util.StringUtil;
import dev.shopflix.framework.rabbitmq.MessageSender;
import dev.shopflix.framework.rabbitmq.MqMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 站内消息业务类
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
    @Transactional(value = "systemTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Message add(MessageVO messageVO) {
        if (messageVO.getSendType().equals(1)) {
            if (StringUtil.isEmpty(messageVO.getMemberIds())) {
                throw new ServiceException(MemberErrorCode.E122.code(), "请指定发送会员");
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
