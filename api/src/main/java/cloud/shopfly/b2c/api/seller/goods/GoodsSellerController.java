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

import cloud.shopfly.b2c.core.goods.model.dos.GoodsDO;
import cloud.shopfly.b2c.core.goods.model.dto.GoodsDTO;
import cloud.shopfly.b2c.core.goods.model.dto.GoodsQueryParam;
import cloud.shopfly.b2c.core.goods.model.vo.GoodsSelectLine;
import cloud.shopfly.b2c.core.goods.model.vo.GoodsVO;
import cloud.shopfly.b2c.core.goods.service.GoodsManager;
import cloud.shopfly.b2c.core.goods.service.GoodsQueryManager;
import cloud.shopfly.b2c.framework.database.Page;
import cloud.shopfly.b2c.framework.util.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;

/**
 * Merchandise controller
 *
 * @author fk
 * @version v2.0
 * @since v7.0.0 2018-03-21 11:23:10
 */
@RestController
@RequestMapping("/seller/goods")
@Api(description = "commodity-relatedAPI")
@Validated
@Scope("request")
public class GoodsSellerController {

    @Autowired
    private GoodsQueryManager goodsQueryManager;

    @Autowired
    private GoodsManager goodsManager;


    @ApiOperation(value = "Querying commodity list", response = GoodsDO.class)
    @GetMapping
    public Page list(GoodsQueryParam param, @ApiIgnore Integer pageNo, @ApiIgnore Integer pageSize) {

        param.setPageNo(pageNo);
        param.setPageSize(pageSize);

        return this.goodsQueryManager.list(param);
    }

    @ApiOperation(value = "Example Query the list of warning products", response = GoodsDO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page_no", value = "The page number", required = false, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "page_size", value = "Number each page", required = false, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "keyword", value = "Query keyword", required = false, dataType = "string", paramType = "query")
    })
    @GetMapping("/warning")
    public Page warningList(@ApiIgnore Integer pageNo, @ApiIgnore Integer pageSize, String keyword) {

        return this.goodsQueryManager.warningGoodsList(pageNo, pageSize, keyword);

    }

    @ApiOperation(value = "Add the goods", response = GoodsDO.class)
    @ApiImplicitParam(name = "goods", value = "Product information", required = true, dataType = "GoodsDTO", paramType = "body")
    @PostMapping
    public GoodsDO add(@ApiIgnore @Valid @RequestBody GoodsDTO goods) {

        GoodsDO goodsDO = this.goodsManager.add(goods);

        return goodsDO;
    }

    @PutMapping(value = "/{id}")
    @ApiOperation(value = "Modify the goods", response = GoodsDO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "A primary key", required = true, dataType = "int", paramType = "path"),
            @ApiImplicitParam(name = "goods", value = "Product information", required = true, dataType = "GoodsDTO", paramType = "body")
    })
    public GoodsDO edit(@Valid @RequestBody GoodsDTO goods, @PathVariable Integer id) {

        GoodsDO goodsDO = this.goodsManager.edit(goods, id);

        return goodsDO;
    }

    @GetMapping(value = "/{id}")
    @ApiOperation(value = "Querying a product,Use in editing")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "Primary key of the item to query", required = true, dataType = "int", paramType = "path")})
    public GoodsVO get(@PathVariable Integer id) {

        GoodsVO goods = this.goodsQueryManager.queryGoods(id);

        return goods;
    }

    @ApiOperation(value = "Merchants took their goods off the shelves", notes = "Merchants took their goods off the shelves时使用")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "goods_ids", value = "productIDA collection of", required = true, paramType = "path", dataType = "int", allowMultiple = true)
    })
    @PutMapping(value = "/{goods_ids}/under")
    public String underGoods(@PathVariable("goods_ids") Integer[] goodsIds, String reason) {

        if (StringUtil.isEmpty(reason)) {
            reason = "Self-removal, no reason";
        }
        this.goodsManager.under(goodsIds, reason);

        return null;
    }

    @ApiOperation(value = "Businesses put goods in the recycling bin", notes = "Only goods removed from the shelves can be put into the recycling bin")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "goods_ids", value = "productIDA collection of", required = true, paramType = "path", dataType = "int", allowMultiple = true)
    })
    @PutMapping(value = "/{goods_ids}/recycle")
    public String deleteGoods(@PathVariable("goods_ids") Integer[] goodsIds) {

        this.goodsManager.inRecycle(goodsIds);

        return null;
    }

    @ApiOperation(value = "Merchant restore goods", notes = "Used when recycling goods at the merchants recycling station")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "goods_ids", value = "productIDA collection of", required = true, paramType = "path", dataType = "int", allowMultiple = true)
    })
    @PutMapping(value = "/{goods_ids}/revert")
    public String revertGoods(@PathVariable("goods_ids") Integer[] goodsIds) {

        this.goodsManager.revert(goodsIds);

        return null;
    }

    @ApiOperation(value = "Merchants completely delete goods", notes = "Used when deleting an item from the merchants recycle bin")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "goods_ids", value = "productIDA collection of", required = true, paramType = "path", dataType = "int", allowMultiple = true)
    })
    @DeleteMapping(value = "/{goods_ids}")
    public String cleanGoods(@PathVariable("goods_ids") Integer[] goodsIds) {

        this.goodsManager.delete(goodsIds);

        return "";
    }


    @GetMapping(value = "/{goods_ids}/details")
    @ApiOperation(value = "Example Query basic information about multiple commodities")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "goods_ids", value = "Primary key of the item to query", required = true, dataType = "int", paramType = "path", allowMultiple = true)})
    public List<GoodsSelectLine> getGoodsDetail(@PathVariable("goods_ids") Integer[] goodsIds) {

        return this.goodsQueryManager.query(goodsIds);
    }

}
