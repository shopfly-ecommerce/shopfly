/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.pagecreate.service.impl;

import dev.shopflix.core.base.rabbitmq.AmqpExchange;
import dev.shopflix.core.pagecreate.service.PageCreateManager;
import dev.shopflix.core.system.model.TaskProgressConstant;
import dev.shopflix.core.system.model.enums.ProgressEnum;
import dev.shopflix.core.system.model.vo.TaskProgress;
import dev.shopflix.core.system.service.ProgressManager;
import org.apache.commons.logging.Log;
import dev.shopflix.framework.rabbitmq.MessageSender;
import dev.shopflix.framework.rabbitmq.MqMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 静态页生成实现
 *
 * @author zh
 * @version v1.0
 * @since v6.4.0
 * 2017年9月1日 上午11:51:09
 */
@Component
public class PageCreateManagerImpl implements PageCreateManager {

    protected final Log logger = org.apache.commons.logging.LogFactory.getLog(this.getClass());

    @Autowired
    private MessageSender messageSender;

    @Autowired
    private ProgressManager progressManager;

    @Override
    public boolean startCreate(String[] choosePages) {

        TaskProgress taskProgress =  progressManager.getProgress(TaskProgressConstant.PAGE_CREATE);
        if ( taskProgress!= null) {
            //如果任务已经完成，返回可以执行
            return taskProgress.getTaskStatus().equals(ProgressEnum.SUCCESS.getStatus());
        }
        this.sendPageCreateMessage(choosePages);
        return true;
    }


    /**
     * 发送页面生成消息
     *
     * @param choosePages 要发送的对象 要生成的页面
     */
    public void sendPageCreateMessage(String[] choosePages) {
        try {
            this.messageSender.send(new MqMessage(AmqpExchange.PAGE_CREATE, AmqpExchange.PAGE_CREATE+"_ROUTING", choosePages));
        } catch (Exception e) {
            logger.error(e);
        }
    }


}
