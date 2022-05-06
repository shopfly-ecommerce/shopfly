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
package cloud.shopfly.b2c.core.trade.cart.model.dos;


/**
 * 订单权限
 *
 * @author kingapex
 * @version 1.0
 * @since v7.0.0
 * 2017年4月12日下午1:36:38
 */
public enum OrderPermission {

    /**
     * 卖家权限
     */
    seller,


    /**
     * 买家权限
     */
    buyer,

    /**
     * 管理员权限
     */
    admin,

    /**
     * 客户端权限
     */
    client


}
