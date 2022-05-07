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
package cloud.shopfly.b2c.framework.trigger.Interface;

/**
 * Delayed execution interface
 *
 * @author liushuai
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2019/2/13 In the afternoon8:13
 */
public interface TimeTrigger {

    /**
     * Adding a Delayed Task
     *
     * @param executerName actuatorbeanid
     * @param param        Perform parameter
     * @param triggerTime  Execution time Timestamp The unit is seconds
     * @param uniqueKey    If it is a need to have a modification/Cancel the delayed task of the delayed task function,<br/>
     *                     Please fill in this parameter for subsequent deletion and modification as the unique certificate<br/>
     *                     Recommended parameters are：PINTUAZN_{ACTIVITY_ID} For example,pintuan_123<br/>
     *                     Globally unique within a service
     */
    void add(String executerName, Object param, Long triggerTime, String uniqueKey);

    /**
     * Modifying a Delayed Task
     *
     * @param executerName   actuatorbeanid
     * @param param          Perform parameter
     * @param triggerTime    Execution time Timestamp The unit is seconds
     * @param oldTriggerTime Old task execution time
     * @param uniqueKey      Unique credentials when adding tasks
     */
    void edit(String executerName, Object param, Long oldTriggerTime, Long triggerTime, String uniqueKey);

    /**
     * Deleting a Delayed Task
     *
     * @param executerName actuator
     * @param triggerTime  The execution time
     * @param uniqueKey    Unique credentials when adding tasks
     */
    void delete(String executerName, Long triggerTime, String uniqueKey);
}
