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
package cloud.shopfly.b2c.core.promotion.groupbuy.model.enums;

/**
 * GroupbuyQuantityLogEnum
 *
 * @author Chopper
 * @version v1.0
 * @since v7.0
 * 2018-09-21 上午9:01
 */
public enum GroupbuyQuantityLogEnum {

    /** 已结束*/
    BUY("售出"),

    /** 进行中*/
    CANCEL("取消");

    private String status;


    GroupbuyQuantityLogEnum(String status) {
        this.status=status;
    }

    public String getStatus() {
        return this.status;
    }
}
