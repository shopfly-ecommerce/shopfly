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
package cloud.shopfly.b2c.core.client.member;

import cloud.shopfly.b2c.core.system.model.vo.ShipTemplateVO;

/**
 * @version v7.0
 * @Description: 店铺运费模版Client默认实现
 * @Author: zjp
 * @Date: 2018/7/25 16:20
 */
public interface ShipTemplateClient {
    /**
     * 获取运费模版
     * @param id 运费模版主键
     * @return ShipTemplate  运费模版
     */
    ShipTemplateVO get(Integer id);

}
