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
package cloud.shopfly.b2c.core.goods.model.enums;

/**
 * Created by kingapex on 2019-01-17.
 * 库存数类型
 * @author kingapex
 * @version 1.0
 * @since 7.0.0
 * 2019-01-17
 */
public enum QuantityType {

    /**
     * 实际的库存，包含了待发货的
     */
    actual,

    /**
     * 可以售的库存，不包含待发货的
     */
    enable

}
