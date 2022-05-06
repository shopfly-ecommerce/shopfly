/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.consumer.shop.trigger;

import cloud.shopfly.b2c.core.promotion.pintuan.service.PintuanOrderManager;
import cloud.shopfly.b2c.framework.trigger.Interface.TimeTriggerExecuter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 评团订单延时处理
 *
 * @author Chopper
 * @version v1.0
 * @since v7.0
 * 2019-03-07 下午5:38
 */
@Component("pintuanOrderHandlerTriggerExecuter")
public class PintuanOrderHandlerTriggerExecuter implements TimeTriggerExecuter {


    @Autowired
    private PintuanOrderManager pintuanOrderManager;


    /**
     * 执行任务
     *
     * @param object 任务参数
     */
    @Override
    public void execute(Object object) {

        //pintuanOrderManager.handle((int) object);


    }
}
