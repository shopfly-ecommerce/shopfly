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
package cloud.shopfly.b2c.core.promotion.tool.support;

import cloud.shopfly.b2c.core.goods.model.vo.SpecValueVO;
import cloud.shopfly.b2c.framework.util.JsonUtil;
import cloud.shopfly.b2c.framework.util.StringUtil;

import java.util.List;

/**
 * sku Name generation tool
 * @author kingapex
 * @version 1.0
 * @since 7.1.0
 * 2019-03-05
 */

public class SkuNameUtil {

    public  static  String createSkuName(String specs) {
        if (StringUtil.isEmpty(specs)) {
            return "";
        }
        List<SpecValueVO> specList  = JsonUtil.jsonToList(specs, SpecValueVO.class);

        StringBuffer skuName = new StringBuffer();
        specList.forEach(specValueVO -> {
            skuName.append(" ");
            skuName.append(specValueVO.getSpecValue());
        });
        return skuName.toString();
    }

}
