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
package cloud.shopfly.b2c.core.promotion.pintuan.model;

/**
 * Created by kingapex on 2019-01-24.
 * 拼团订单状态
 *
 * @author kingapex
 * @version 1.0
 * @since 7.1.0
 * 2019-01-24
 */
public enum PintuanOrderStatus {

    /**
     * 新订单
     */
    new_order,

    /**
     * 待成团
     */
    wait,

    /**
     * 已经支付
     */
    pay_off,

    /**
     * 已成团
     */
    formed,

    /**
     * 已取消
     */
    cancel
}
