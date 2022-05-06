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
package cloud.shopfly.b2c.core.system.enums;

/**
 * 通知类型 枚举
 *
 * @author zh
 * @version v7.0
 * @since v7.0 2017年10月17日 上午10:49:25
 */
public enum DisplayTypeEnum {
    /**
     * 通知:消息送达到用户设备后，由友盟SDK接管处理并在通知栏上显示通知内容。
     */
    NOTIFICATION {
        @Override
        public String getValue() {
            return "notification";
        }
    },
    /**
     * 消息:消息送达到用户设备后，消息内容透传给应用自身进行解析处理。
     */
    MESSAGE {
        @Override
        public String getValue() {
            return "message";
        }
    };

    public abstract String getValue();


}
