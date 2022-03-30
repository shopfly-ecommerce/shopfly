/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.consumer.shop.trigger;

import com.enation.app.javashop.core.base.message.PintuanChangeMsg;
import com.enation.app.javashop.core.base.rabbitmq.TimeExecute;
import com.enation.app.javashop.core.promotion.pintuan.model.Pintuan;
import com.enation.app.javashop.core.promotion.pintuan.service.PintuanManager;
import com.enation.app.javashop.core.promotion.tool.model.enums.PromotionStatusEnum;
import com.enation.app.javashop.framework.trigger.Interface.TimeTrigger;
import com.enation.app.javashop.framework.trigger.Interface.TimeTriggerExecuter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 拼团定时开启关闭活动 延时任务执行器
 *
 * @author Chopper
 * @version v1.0
 * @since v7.0
 * 2019-02-13 下午5:34
 */
@Component("pintuanTimeTriggerExecute")
public class PintuanTimeTriggerExecuter implements TimeTriggerExecuter {

    @Autowired
    private TimeTrigger timeTrigger;

    @Autowired
    private PintuanManager pintuanManager;

    protected final Log logger = LogFactory.getLog(this.getClass());

    /**
     * 执行任务
     *
     * @param object 任务参数
     */
    @Override
    public void execute(Object object) {
        PintuanChangeMsg pintuanChangeMsg = (PintuanChangeMsg) object;

        //如果是要开启活动
        if (pintuanChangeMsg.getOptionType() == 1) {
            Pintuan pintuan = pintuanManager.getModel(pintuanChangeMsg.getPintuanId());
            if (pintuan.getStatus().equals(PromotionStatusEnum.WAIT.name())) {
                pintuanManager.openPromotion(pintuanChangeMsg.getPintuanId());
                //开启活动后，立马设置一个关闭的流程
                pintuanChangeMsg.setOptionType(0);
                timeTrigger.add(TimeExecute.PINTUAN_EXECUTER, pintuanChangeMsg, pintuan.getEndTime(), "TIME_TRIGGER_" + pintuan.getPromotionId());
                if (logger.isDebugEnabled()) {
                    this.logger.debug("活动[" + pintuan.getPromotionName() + "]开始,id=[" + pintuan.getPromotionId() + "]");
                }
            }
        } else {
            //拼团活动结束
            Pintuan pintuan = pintuanManager.getModel(pintuanChangeMsg.getPintuanId());
            if (pintuan.getStatus().equals(PromotionStatusEnum.UNDERWAY.name())) {
                pintuanManager.closePromotion(pintuanChangeMsg.getPintuanId());
            }
            if (logger.isDebugEnabled()) {
                this.logger.debug("活动[" + pintuan.getPromotionName() + "]结束,id=[" + pintuan.getPromotionId() + "]");
            }
        }
    }
}
