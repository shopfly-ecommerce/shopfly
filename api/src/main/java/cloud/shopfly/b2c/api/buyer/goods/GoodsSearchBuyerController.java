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
package cloud.shopfly.b2c.api.buyer.goods;

import cloud.shopfly.b2c.core.goodssearch.model.GoodsSearchDTO;
import cloud.shopfly.b2c.core.goodssearch.model.GoodsWords;
import cloud.shopfly.b2c.core.goodssearch.service.GoodsSearchManager;
import cloud.shopfly.b2c.framework.database.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Map;

/**
 * @author fk
 * @version v2.0
 * @Description: Full text product search
 * @date 2018/6/1915:55
 * @since v7.0.0
 */
@RestController
@RequestMapping("/goods/search")
@Api(description = "Product retrieval correlationAPI")
public class GoodsSearchBuyerController {

    @Autowired
    private GoodsSearchManager goodsSearchManager;

    @ApiOperation(value = "Querying commodity list")
    @GetMapping
    public Page searchGoods(@ApiIgnore Integer pageNo, @ApiIgnore Integer pageSize, GoodsSearchDTO goodsSearch){

        goodsSearch.setPageNo(pageNo);
        goodsSearch.setPageSize(pageSize);

        return goodsSearchManager.search(goodsSearch);
    }

    @ApiOperation(value = "Query item selectors")
    @GetMapping("/selector")
    public Map searchGoodsSelector(GoodsSearchDTO goodsSearch){

        return goodsSearchManager.getSelector(goodsSearch);
    }

    @ApiOperation(value = "Query the quantity of a commodity participle")
    @ApiImplicitParam(name = "keyword", value = "Search keywords", required = true, dataType = "string", paramType = "query")
    @GetMapping("/words")
    public List<GoodsWords> searchGoodsWords(String keyword){

        return goodsSearchManager.getGoodsWords(keyword);
    }

    @ApiOperation(value = "To obtain'Recommend to you'Products")
    @GetMapping("/recommend")
    public Page recommendGoodsList(@ApiIgnore Integer pageNo, @ApiIgnore Integer pageSize){
        GoodsSearchDTO goodsSearch = new GoodsSearchDTO();
        goodsSearch.setPageNo(pageNo);
        goodsSearch.setPageSize(pageSize);

        return goodsSearchManager.recommendGoodsList(goodsSearch);
    }
}
