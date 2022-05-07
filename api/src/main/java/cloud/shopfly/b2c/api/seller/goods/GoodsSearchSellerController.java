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
package cloud.shopfly.b2c.api.seller.goods;

import cloud.shopfly.b2c.core.base.rabbitmq.AmqpExchange;
import cloud.shopfly.b2c.core.system.model.TaskProgressConstant;
import cloud.shopfly.b2c.core.system.service.ProgressManager;
import cloud.shopfly.b2c.framework.exception.ResourceNotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import cloud.shopfly.b2c.framework.rabbitmq.MessageSender;
import cloud.shopfly.b2c.framework.rabbitmq.MqMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fk
 * @version v2.0
 * @Description: Full text product search
 * @date 2018/6/1915:55
 * @since v7.0.0
 */
@RestController
@RequestMapping("/seller/goods/search")
@Api(description = "Product retrieval correlationAPI")
public class GoodsSearchSellerController {

    @Autowired
    private ProgressManager progressManager;
    @Autowired
    private MessageSender messageSender;

    @GetMapping
    @ApiOperation(value = "The commodity index is initialized")
    public String create(){

        if (progressManager.getProgress(TaskProgressConstant.GOODS_INDEX) != null) {
            throw new ResourceNotFoundException("An index task is in progress and can be generated only after the task is complete.");
        }
        /** Send index generation message*/
        this.messageSender.send(new MqMessage(AmqpExchange.INDEX_CREATE, AmqpExchange.INDEX_CREATE+"_ROUTING","1"));

        return TaskProgressConstant.GOODS_INDEX;
    }
}
