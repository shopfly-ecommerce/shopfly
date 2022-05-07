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

import cloud.shopfly.b2c.core.goods.GoodsErrorCode;
import cloud.shopfly.b2c.core.goods.model.dto.GoodsSkuQuantityDTO;
import cloud.shopfly.b2c.core.goods.model.enums.QuantityType;
import cloud.shopfly.b2c.core.goods.model.vo.CacheGoods;
import cloud.shopfly.b2c.core.goods.model.vo.GoodsQuantityVO;
import cloud.shopfly.b2c.core.goods.model.vo.GoodsSkuVO;
import cloud.shopfly.b2c.core.goods.service.GoodsQuantityManager;
import cloud.shopfly.b2c.core.goods.service.GoodsQueryManager;
import cloud.shopfly.b2c.framework.ShopflyConfig;
import cloud.shopfly.b2c.framework.exception.ServiceException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Commodity inventory maintenance
 *
 * @author fk
 * @version v2.0
 * @since v7.0.0
 * 2018years3month23The morning of11:23:05
 */
@Api(description = "Store center merchandise inventory is maintained separatelyapi")
@RestController
@RequestMapping("/seller/goods/{goods_id}/quantity")
@Validated
public class GoodsQuantitySellerController {

    @Autowired
    private GoodsQueryManager goodsQueryManager;
    @Autowired
    private GoodsQuantityManager goodsQuantityManager;
    @Autowired
    private ShopflyConfig shopflyConfig;

    @ApiOperation(value = "The merchant maintains the inventory interface separately", notes = "The merchant maintains the inventory interface separately时使用")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "goods_id", value = "productid", required = true, dataType = "int", paramType = "path"),
            @ApiImplicitParam(name = "sku_quantity_list", value = "Inventory collection, its an array", required = true, dataType = "GoodsSkuQuantityDTO", paramType = "body", allowMultiple = true),
    })
    @PutMapping
    public void updateQuantity(@ApiIgnore @Valid @RequestBody List<GoodsSkuQuantityDTO> skuQuantityList, @PathVariable("goods_id") Integer goodsId) {

        CacheGoods goods = goodsQueryManager.getFromCache(goodsId);

        if (goods == null) {
            throw new ServiceException(GoodsErrorCode.E307.code(), "No operation permission");
        }

        // The original collection of SKUs
        List<GoodsSkuVO> skuList = goods.getSkuList();
        Map<Integer, GoodsSkuVO> skuMap = new HashMap<>(skuList.size());
        for (GoodsSkuVO sku : skuList) {
            skuMap.put(sku.getSkuId(), sku);
        }


        // Inventory list to update
        List<GoodsQuantityVO> stockList = new ArrayList<>();

        for (GoodsSkuQuantityDTO quantity : skuQuantityList) {

            if (quantity.getQuantityCount() == null || quantity.getQuantityCount() < 0) {
                throw new ServiceException(GoodsErrorCode.E307.code(), "skuTotal inventory cannot be empty or negative");
            }

            GoodsSkuVO sku = skuMap.get(quantity.getSkuId());
            if (sku == null) {
                throw new ServiceException(GoodsErrorCode.E307.code(), "productskuThere is no");
            }
            // Pending Shipment
            Integer waitRogCount = sku.getQuantity() - sku.getEnableQuantity();
            // Determine whether the inventory is less than the number of goods to be shipped
            if (quantity.getQuantityCount() < waitRogCount) {
                throw new ServiceException(GoodsErrorCode.E307.code(), "skuThe number of inventory should not be less than the number of goods to be shipped");
            }

            // The actual inventory
            GoodsQuantityVO actualQuantityVo = new GoodsQuantityVO();
            // So if you take the number of passes -- the number of passes now, its changing, so if you pass 2000, it was 200, then its plus 1800, and if you pass 100, it was 200 then its minus 100
            int stockNum = quantity.getQuantityCount() - sku.getQuantity();
            actualQuantityVo.setQuantity(stockNum);
            actualQuantityVo.setGoodsId(goodsId);
            actualQuantityVo.setQuantityType(QuantityType.actual);
            actualQuantityVo.setSkuId(quantity.getSkuId());

            stockList.add(actualQuantityVo);

            // Clone a quantity VO set to update available inventory
            try {
                GoodsQuantityVO enableVo = (GoodsQuantityVO) actualQuantityVo.clone();
                enableVo.setQuantityType(QuantityType.enable);
                stockList.add(enableVo);
            } catch (CloneNotSupportedException e) {
                throw new ServiceException(GoodsErrorCode.E307.code(), "goodsQuantityVo clone error");
            }

        }

        // Update the inventory
        this.goodsQuantityManager.updateSkuQuantity(stockList);

        // If the item inventory buffer pool is enabled, the item inventory in the database needs to be synchronized immediately to ensure that the item inventory displays properly
        if (shopflyConfig.isStock()) {
            // Synchronize the database inventory immediately
            goodsQuantityManager.syncDataBase();
        }
    }

}
