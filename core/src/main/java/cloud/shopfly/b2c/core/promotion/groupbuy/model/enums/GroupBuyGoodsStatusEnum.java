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
 * 团购商品审核状态
 *
 * @author Snow create in 2018/4/25
 * @version v2.0
 * @since v7.0.0
 */
public enum GroupBuyGoodsStatusEnum {

    /**
     * 待审核
     */
    PENDING(0),

    /**
     * 通过审核
     */
    APPROVED(1),

    /**
     * 未通过审核
     */
    NOT_APPROVED(2);

    private Integer status;


    GroupBuyGoodsStatusEnum(Integer status) {
        this.status = status;
    }

    public Integer status() {
        return this.status;
    }

    public String value() {
        return this.name();
    }


}
