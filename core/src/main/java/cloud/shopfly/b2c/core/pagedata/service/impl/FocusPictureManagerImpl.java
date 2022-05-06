/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.pagedata.service.impl;

import cloud.shopfly.b2c.core.pagedata.model.FocusPicture;
import cloud.shopfly.b2c.core.pagedata.service.FocusPictureManager;
import cloud.shopfly.b2c.core.system.SystemErrorCode;
import cloud.shopfly.b2c.core.system.model.enums.ClientType;
import cloud.shopfly.b2c.core.base.message.CmsManageMsg;
import cloud.shopfly.b2c.core.base.rabbitmq.AmqpExchange;
import cloud.shopfly.b2c.framework.database.DaoSupport;
import cloud.shopfly.b2c.framework.exception.ServiceException;
import cloud.shopfly.b2c.framework.validation.annotation.Operation;
import cloud.shopfly.b2c.framework.rabbitmq.MessageSender;
import cloud.shopfly.b2c.framework.rabbitmq.MqMessage;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
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
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public FocusPicture edit(FocusPicture cmsFocusPicture, Integer id) {

        this.daoSupport.update(cmsFocusPicture, id);
        //发送消息
        sendFocusChangeMessage(cmsFocusPicture.getClientType());

        return cmsFocusPicture;
    }

    @Override
    @Operation
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
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
