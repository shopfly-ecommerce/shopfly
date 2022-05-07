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
package cloud.shopfly.b2c.api.seller.distribution;

import cloud.shopfly.b2c.core.base.SettingGroup;
import cloud.shopfly.b2c.core.base.model.vo.SuccessMessage;
import cloud.shopfly.b2c.core.client.distribution.DistributionGoodsClient;
import cloud.shopfly.b2c.core.client.system.SettingClient;
import cloud.shopfly.b2c.core.distribution.model.dos.DistributionGoods;
import cloud.shopfly.b2c.core.distribution.model.dos.DistributionSetting;
import cloud.shopfly.b2c.framework.util.JsonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Distribution of goods
 *
 * @author liushuai
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/8/30 In the morning9:42
 */
@RestController
@RequestMapping("/seller/distribution")
@Api(description = "Distribution of goodsAPI")
public class DistributionGoodsSellerController {


    @Autowired
    private DistributionGoodsClient distributionGoodsClient;


    @Autowired
    private SettingClient settingClient;

    @ApiOperation(value = "Distribution of goods rebate to obtain")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "goods_id", value = "productid", required = true, dataType = "int", paramType = "path"),
    })
    @GetMapping(value = "/goods/{goods_id}")
    public DistributionGoods querySetting(@PathVariable("goods_id") Integer goodsId) {
        return distributionGoodsClient.getModel(goodsId);
    }


    @ApiOperation(value = "Distribution of goods rebate setup")
    @PutMapping(value = "/goods")
    public DistributionGoods settingGoods(DistributionGoods distributionGoods) {
        return distributionGoodsClient.edit(distributionGoods);
    }


    @ApiOperation(value = "Get distribution Settings:1open/0close")
    @GetMapping(value = "/setting")
    public SuccessMessage setting() {
        DistributionSetting ds = JsonUtil.jsonToObject(settingClient.get(SettingGroup.DISTRIBUTION), DistributionSetting.class);
        return new SuccessMessage(ds.getGoodsModel());
    }


}
