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
