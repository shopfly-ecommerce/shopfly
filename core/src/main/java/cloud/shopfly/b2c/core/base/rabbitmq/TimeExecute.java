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
package cloud.shopfly.b2c.core.base.rabbitmq;

/**
 * Lazy loading task executor
 *
 * @author zh
 * @version v7.0
 * @date 19/3/1 In the afternoon2:13
 * @since v7.0
 */
public class TimeExecute {

    /**
     * Promotion lazy load actuator
     */
    public final static String PROMOTION_EXECUTER = "promotionTimeTriggerExecuter";
    /**
     * Group lazy load actuators
     */
    public final static String PINTUAN_EXECUTER = "pintuanTimeTriggerExecute";
}
