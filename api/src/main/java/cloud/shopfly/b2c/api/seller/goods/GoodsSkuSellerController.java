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

import cloud.shopfly.b2c.core.goods.model.dto.GoodsQueryParam;
import cloud.shopfly.b2c.core.goods.model.vo.CacheGoods;
import cloud.shopfly.b2c.core.goods.model.vo.GoodsSkuVO;
import cloud.shopfly.b2c.core.goods.service.GoodsQueryManager;
import cloud.shopfly.b2c.core.goods.service.GoodsSkuManager;
import cloud.shopfly.b2c.framework.database.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * productskuThe controller
 *
 * @author fk
 * @version v2.0
 * @since v7.0.0 2018-03-21 11:48:40
 */
@RestController
@RequestMapping("/seller/goods")
@Api(description = "productskurelatedAPI")
public class GoodsSkuSellerController {

    @Autowired
    private GoodsQueryManager goodsQueryManager;

    @Autowired
    private GoodsSkuManager goodsSkuManager;

    @ApiOperation(value = "productskuInformation Acquisitionapi")
    @ApiImplicitParam(name = "goods_id", value = "productid", required = true, dataType = "int", paramType = "path")
    @GetMapping("/{goods_id}/skus")
    public List<GoodsSkuVO> queryByGoodsId(@PathVariable(name = "goods_id") Integer goodsId) {

        CacheGoods goods = goodsQueryManager.getFromCache(goodsId);

        return goods.getSkuList();
    }


    @GetMapping(value = "/skus/{sku_ids}/details")
    @ApiOperation(value = "Example Query basic information about multiple commodities")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sku_ids", value = "To query theSKUA primary key", required = true, dataType = "int", paramType = "path", allowMultiple = true)})
    public List<GoodsSkuVO> getGoodsDetail(@PathVariable("sku_ids") Integer[] skuIds) {

        return this.goodsSkuManager.query(skuIds);
    }


    @ApiOperation(value = "The querySKUThe list of", response = GoodsSkuVO.class)
    @GetMapping("/skus")
    public Page list(GoodsQueryParam param, @ApiIgnore Integer pageNo, @ApiIgnore Integer pageSize) {

        param.setPageNo(pageNo);
        param.setPageSize(pageSize);

        return this.goodsSkuManager.list(param);
    }

}
