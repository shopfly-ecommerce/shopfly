/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.pagedata.service.impl;

import com.enation.app.javashop.core.base.message.CmsManageMsg;
import com.enation.app.javashop.core.base.rabbitmq.AmqpExchange;
import com.enation.app.javashop.core.pagedata.model.FocusPicture;
import com.enation.app.javashop.core.pagedata.service.FocusPictureManager;
import com.enation.app.javashop.core.system.SystemErrorCode;
import com.enation.app.javashop.core.system.model.enums.ClientType;
import com.enation.app.javashop.framework.database.DaoSupport;
import com.enation.app.javashop.framework.exception.ServiceException;
import com.enation.app.javashop.framework.validation.annotation.Operation;
import com.enation.app.javashop.framework.rabbitmq.MessageSender;
import com.enation.app.javashop.framework.rabbitmq.MqMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 焦点图业务类
 *
 * @author fk
 * @version v1.0
 * @since v7.0.0
 * 2018-05-21 15:23:23
 */
@Service
public class FocusPictureManagerImpl implements FocusPictureManager {

    @Autowired
    @Qualifier("systemDaoSupport")
    private DaoSupport daoSupport;

    @Autowired
    private MessageSender messageSender;


    @Override
    public List list(String clientType) {
        String sql = "select * from es_focus_picture  where client_type = ? order by id asc";
        return this.daoSupport.queryForList(sql, FocusPicture.class, clientType);
    }

    @Override
    @Operation
    @Transactional(value = "systemTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public FocusPicture add(FocusPicture cmsFocusPicture) {
        //焦点图不能超过5个
        String sql = "select count(0) from es_focus_picture where client_type=?";
        Integer count = this.daoSupport.queryForInt(sql, cmsFocusPicture.getClientType());
        if (count >= 5) {
            throw new ServiceException(SystemErrorCode.E956.code(), "焦点图数量不能超过五张");
        }

        this.daoSupport.insert(cmsFocusPicture);

        cmsFocusPicture.setId(this.daoSupport.getLastId("es_focus_picture"));

        //发送消息
        sendFocusChangeMessage(cmsFocusPicture.getClientType());

        return cmsFocusPicture;
    }

    /**
     * 发送首页变化消息
     *
     * @param clientType
     */
    private void sendFocusChangeMessage(String clientType) {

        CmsManageMsg cmsManageMsg = new CmsManageMsg();

        if (ClientType.PC.name().equals(clientType)) {
            this.messageSender.send(new MqMessage(AmqpExchange.PC_INDEX_CHANGE, AmqpExchange.PC_INDEX_CHANGE + "_ROUTING", cmsManageMsg));
        } else {
            this.messageSender.send(new MqMessage(AmqpExchange.MOBILE_INDEX_CHANGE, AmqpExchange.MOBILE_INDEX_CHANGE + "_ROUTING", cmsManageMsg));
        }
    }

    @Override
    @Transactional(value = "systemTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public FocusPicture edit(FocusPicture cmsFocusPicture, Integer id) {

        this.daoSupport.update(cmsFocusPicture, id);
        //发送消息
        sendFocusChangeMessage(cmsFocusPicture.getClientType());

        return cmsFocusPicture;
    }

    @Override
    @Operation
    @Transactional(value = "systemTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void delete(Integer id) {
        FocusPicture cmsFocusPicture = this.getModel(id);

        this.daoSupport.delete(FocusPicture.class, id);

        //发送消息
        sendFocusChangeMessage(cmsFocusPicture.getClientType());
    }

    @Override
    public FocusPicture getModel(Integer id) {
        return this.daoSupport.queryForObject(FocusPicture.class, id);
    }
}
