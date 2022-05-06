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
package cloud.shopfly.b2c.core.trade.order.model.enums;

/**
 * 申请售后的状态
 *
 * @author Snow
 * @version v2.0
 * @since v6.4
 * 2017年9月6日 下午4:16:53
 */
public enum ServiceStatusEnum {

    /**
     * 未申请
     */
    NOT_APPLY("未申请"),

    /**
     * 已申请
     */
    APPLY("已申请"),

    /**
     * 审核通过
     */
    PASS("审核通过"),

    /**
     * 审核未通过
     */
    REFUSE("审核未通过"),

    /**
     * 已失效或不允许申请售后
     */
    EXPIRED("已失效不允许申请售后");


    private String description;

    ServiceStatusEnum(String description) {
        this.description = description;
    }

    public String description() {
        return this.description;
    }

    public String value() {
        return this.name();
    }
}
