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
package cloud.shopfly.b2c.framework.trigger.util;

/**
 * Delay taskmqImplements the content, providing encryption algorithms and task prefix parameters
 *
 * @author Chopper
 * @version v1.0
 * @since v7.0
 * 2019-02-25 In the morning10:59
 */
public class RabbitmqTriggerUtil {

    /**
     * The prefix
     */
    private static final String PREFIX = "{rabbitmq_trigger}_";

    /**
     * Generates a delayed task idkey
     *
     * @param executerName actuatorbeanid
     * @param triggerTime  The execution time
     * @param uniqueKey    Custom representation
     * @return
     */
    public static String generate(String executerName, Long triggerTime, String uniqueKey) {
        return PREFIX + (executerName + triggerTime + uniqueKey).hashCode();
    }


}
