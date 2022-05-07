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
package cloud.shopfly.b2c.framework.trigger.rabbitmq.model;

import java.io.Serializable;

/**
 * Delayed task message
 *
 * @author Chopper
 * @version v1.0
 * @since v7.0
 * 2019-02-12 In the afternoon5:46
 */
public class TimeTriggerMsg implements Serializable {


    private static final long serialVersionUID = 8897917127201859535L;
    /**
     * actuatorbeanid
     */
    private String triggerExecuter;

    /**
     * Execution time of the actuator
     */
    private Long triggerTime;


    /**
     * Actuator parameter
     */
    private Object param;

    /**
     * The onlyKEY
     */
    private String uniqueKey;


    public TimeTriggerMsg() {
    }

    public TimeTriggerMsg(String triggerExecuter, Object param, Long triggerTime, String uniqueKey) {
        this.triggerExecuter = triggerExecuter;
        this.triggerTime = triggerTime;
        this.param = param;
        this.uniqueKey = uniqueKey;
    }

    public Long getTriggerTime() {
        return triggerTime;
    }

    public void setTriggerTime(Long triggerTime) {
        this.triggerTime = triggerTime;
    }

    public String getTriggerExecuter() {
        return triggerExecuter;
    }

    public void setTriggerExecuter(String triggerExecuter) {
        this.triggerExecuter = triggerExecuter;
    }

    public Object getParam() {
        return param;
    }

    public void setParam(Object param) {
        this.param = param;
    }

    public String getUniqueKey() {
        return uniqueKey;
    }

    public void setUniqueKey(String uniqueKey) {
        this.uniqueKey = uniqueKey;
    }
}
