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
package cloud.shopfly.b2c.api.seller.goods;

import cloud.shopfly.b2c.core.base.SettingGroup;
import cloud.shopfly.b2c.core.client.system.SettingClient;
import cloud.shopfly.b2c.core.goods.model.dto.GoodsSettingVO;
import cloud.shopfly.b2c.framework.util.JsonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author fk
 * @version v1.0
 * @Description: Commodity setting controller
 * @date 2018/5/25 10:31
 * @since v7.0.0
 */
@RestController
@RequestMapping("/seller/goods/settings")
@Api(description = "ProductAPI")
public class GoodsSettingSellerController {

    @Autowired
    private SettingClient settingClient;

    @GetMapping
    @ApiOperation(value = "Obtain product audit setting information")
    public GoodsSettingVO getGoodsSetting(){

        String json = this.settingClient.get(SettingGroup.GOODS);
        return JsonUtil.jsonToObject(json, GoodsSettingVO.class);
    }

    @PostMapping
    @ApiOperation(value = "Save product audit setting information")
    public GoodsSettingVO save(@Valid GoodsSettingVO goodsSetting){

        this.settingClient.save(SettingGroup.GOODS,goodsSetting);

        return goodsSetting;
    }

}
