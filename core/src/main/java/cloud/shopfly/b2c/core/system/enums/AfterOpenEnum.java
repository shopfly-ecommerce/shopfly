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
 * 操作类型 枚举
 *
 * @author zh
 * @version v7.0
 * @since v7.0 2017年10月17日 上午10:49:25
 */
public enum AfterOpenEnum {

    /**
     * 打开应用
     */
    go_app,
    /**
     * 跳转到URL
     */
    go_url,
    /**
     * 打开特定的activity
     */
    go_activity,
    /**
     * 用户自定义内容
     */
    go_custom


}
