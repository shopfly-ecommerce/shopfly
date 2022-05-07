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

import cloud.shopfly.b2c.core.goods.model.dos.DraftGoodsDO;
import cloud.shopfly.b2c.core.goods.model.dos.GoodsDO;
import cloud.shopfly.b2c.core.goods.model.dto.GoodsDTO;
import cloud.shopfly.b2c.core.goods.model.vo.DraftGoodsVO;
import cloud.shopfly.b2c.core.goods.model.vo.GoodsParamsGroupVO;
import cloud.shopfly.b2c.core.goods.model.vo.GoodsSkuVO;
import cloud.shopfly.b2c.core.goods.service.DraftGoodsManager;
import cloud.shopfly.b2c.core.goods.service.DraftGoodsParamsManager;
import cloud.shopfly.b2c.core.goods.service.DraftGoodsSkuManager;
import cloud.shopfly.b2c.framework.database.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * Draft commodity controller
 *
 * @author fk
 * @version v2.0
 * @since v7.0.0
 * 2018-03-26 10:40:34
 */
@RestController
@RequestMapping("/seller/goods/draft-goods")
@Api(description = "Draft commodity correlationAPI")
public class DraftGoodsSellerController {

    @Autowired
    private DraftGoodsManager draftGoodsManager;
    @Autowired
    private DraftGoodsParamsManager draftGoodsParamsManager;
    @Autowired
    private DraftGoodsSkuManager draftGoodsSkuManager;


    @ApiOperation(value = "Query the draft commodity list", response = DraftGoodsDO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page_no", value = "The page number", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "page_size", value = "Display quantity per page", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "keyword", value = "Keyword query", required = false, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "shop_cat_path", value = "Store classificationpath, such as0|10|", required = false, dataType = "int", paramType = "query"),
    })
    @GetMapping
    public Page list(@ApiIgnore @NotEmpty(message = "The page number cannot be blank") Integer pageNo,
                     @ApiIgnore @NotEmpty(message = "The number of pages cannot be empty") Integer pageSize,
                     String keyword, @ApiIgnore String shopCatPath) {

        return this.draftGoodsManager.list(pageNo, pageSize, keyword, shopCatPath);
    }

    @ApiOperation(value = "Add the goods", response = DraftGoodsDO.class)
    @ApiImplicitParam(name = "goods", value = "Product information", required = true, dataType = "GoodsDTO", paramType = "body")
    @PostMapping
    public DraftGoodsDO add(@ApiIgnore @RequestBody GoodsDTO goods) {

        DraftGoodsDO draftGoods = this.draftGoodsManager.add(goods);

        return draftGoods;
    }

    @PutMapping(value = "/{draft_goods_id}")
    @ApiOperation(value = "Revised draft goods", response = DraftGoodsDO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "draft_goods_id", value = "A primary key", required = true, dataType = "int", paramType = "path"),
            @ApiImplicitParam(name = "goods", value = "Product information", required = true, dataType = "GoodsDTO", paramType = "body")
    })
    public DraftGoodsDO edit(@RequestBody GoodsDTO goods, @PathVariable("draft_goods_id") Integer draftGoodsId) {

        DraftGoodsDO draftGoods = this.draftGoodsManager.edit(goods, draftGoodsId);

        return draftGoods;
    }


    @DeleteMapping(value = "/{draft_goods_ids}")
    @ApiOperation(value = "Delete draft goods")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "draft_goods_ids", value = "Primary key to delete draft goods", required = true, dataType = "int", paramType = "path")
    })
    public String delete(@PathVariable("draft_goods_ids") Integer[] draftGoodsIds) {

        this.draftGoodsManager.delete(draftGoodsIds);

        return "";
    }


    @GetMapping(value = "/{id}")
    @ApiOperation(value = "Query a draft item,Merchants edit draft goods for use")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "Primary key of draft goods to query", required = true, dataType = "int", paramType = "path")
    })
    public DraftGoodsVO get(@PathVariable Integer id) {
        return this.draftGoodsManager.getVO(id);
    }

    @GetMapping(value = "/{draft_goods_id}/params")
    @ApiOperation(value = "Query parameters associated with a draft commodity, including parameters not added")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "draft_goods_id", value = "Primary key to delete draft goods", required = true, dataType = "int", paramType = "path")
    })
    public List<GoodsParamsGroupVO> queryDraftParam(@PathVariable("draft_goods_id") Integer draftGoodsId) {

        DraftGoodsDO draftGoods = this.draftGoodsManager.getModel(draftGoodsId);

        List<GoodsParamsGroupVO> list = draftGoodsParamsManager.getParamByCatAndDraft(draftGoods.getCategoryId(), draftGoodsId);

        return list;

    }

    @ApiOperation(value = "Query draft box merchandiseskuinformation", notes = "Obtained when the merchant center edits the draft box itemskuinformation")
    @ApiImplicitParam(name = "draft_goods_id", value = "The draft of goodsid", required = true, dataType = "int", paramType = "path")
    @GetMapping(value = "/{draft_goods_id}/skus")
    public List<GoodsSkuVO> queryByDraftGoodsid(@PathVariable("draft_goods_id") Integer draftGoodsId) throws Exception {

        List<GoodsSkuVO> list = draftGoodsSkuManager.getSkuList(draftGoodsId);

        return list;
    }

    @ApiOperation(value = "Draft box goods shelf interface", notes = "Draft box is used when goods are put on shelves")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "draft_goods_id", value = "The draft of goodsid", required = true, dataType = "int", paramType = "path"),
            @ApiImplicitParam(name = "goods", value = "Product information", required = true, dataType = "GoodsDTO", paramType = "body")
    })
    @PutMapping(value = "/{draft_goods_id}/market")
    public GoodsDO addMarket(@ApiIgnore @Valid @RequestBody GoodsDTO goodsVO, @PathVariable("draft_goods_id") Integer draftGoodsId) {

        GoodsDO goods = draftGoodsManager.addMarket(goodsVO, draftGoodsId);

        return goods;
    }


}
