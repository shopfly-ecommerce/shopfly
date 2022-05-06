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
package cloud.shopfly.b2c.api.buyer.promotion;

import cloud.shopfly.b2c.core.promotion.tool.model.vo.PromotionVO;
import cloud.shopfly.b2c.core.promotion.tool.service.PromotionGoodsManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * 促销活动控制器
 *
 * @author Snow create in 2018/7/10
 * @version v2.0
 * @since v7.0.0
 */

@RestController
@RequestMapping("/promotions")
@Api(description = "促销活动相关API")
@Validated
public class PromotionBuyerController {

    @Autowired
    private PromotionGoodsManager promotionGoodsManager;

    @ApiOperation(value = "根据商品读取参与的所有活动")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "goods_id", value = "商品ID", required = true, dataType = "int", paramType = "path")
    })
    @GetMapping("/{goods_id}")
    public List<PromotionVO> getGoods(@ApiIgnore @PathVariable("goods_id") Integer goodsId){
        List<PromotionVO> promotionVOList = this.promotionGoodsManager.getPromotion(goodsId);
        return promotionVOList;
    }
}
