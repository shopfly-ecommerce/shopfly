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
package cloud.shopfly.b2c.api.seller.statistics;

import cloud.shopfly.b2c.core.base.SearchCriteria;
import cloud.shopfly.b2c.core.statistics.StatisticsErrorCode;
import cloud.shopfly.b2c.core.statistics.StatisticsException;
import cloud.shopfly.b2c.core.statistics.model.vo.SimpleChart;
import cloud.shopfly.b2c.core.statistics.service.GoodsFrontStatisticsManager;
import cloud.shopfly.b2c.framework.database.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Merchant center, commodity analysis
 *
 * @author mengyuanming
 * @version 2.0
 * @since 7.0
 * 2018years4month18The morning of11:57:48
 */
@Api(description = "Merchant statistical analysis of goods")
@RestController
@RequestMapping("/seller/statistics/goods")
public class GoodsStatisticsSellerController {

    @Autowired
    private GoodsFrontStatisticsManager goodsFrontStatisticsManager;

    @ApiOperation(value = "Product details, get near30Daily sales data", response = Page.class)
    @GetMapping(value = "/goods_detail")
    @ApiImplicitParams({@ApiImplicitParam(name = "page_no", value = "The current page number", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "page_size", value = "The page size", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "category_id", value = "Categoryid", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "goods_name", value = "Name", dataType = "String", paramType = "query")})
    public Page getGoodsSalesDetail(@ApiIgnore Integer pageNo, @ApiIgnore Integer pageSize, @ApiIgnore Integer categoryId, @ApiIgnore String goodsName) {

        if (null == categoryId) {
            throw new StatisticsException(StatisticsErrorCode.E801.code(), "CategoryidDo not empty");
        }
        return this.goodsFrontStatisticsManager.getGoodsDetail(pageNo, pageSize, categoryId, goodsName);
    }

    @ApiOperation(value = "Sales price", response = SimpleChart.class)
    @GetMapping(value = "/price_sales")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sections", value = "Price range, no default values are passed", dataType = "int", paramType = "query", required = true, allowMultiple = true),
            @ApiImplicitParam(name = "cycle_type", value = "Cycle type", dataType = "String", paramType = "query", required = true, allowableValues = "YEAR,MONTH"),
            @ApiImplicitParam(name = "year", value = "year", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "month", value = "in", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "category_id", value = "Categoryid", dataType = "int", paramType = "query")
    })
    public SimpleChart getGoodsPriceSales(@ApiIgnore @Valid SearchCriteria searchCriteria, @ApiIgnore @RequestParam(value = "sections", required = false) ArrayList<Integer> sections) {

        // If the price space is empty, add the default value
        if (null == sections || sections.size() == 0) {
            sections = new ArrayList<>();
            sections.add(0);
            sections.add(500);
            sections.add(500);
            sections.add(1000);
            sections.add(1000);
            sections.add(1500);
            sections.add(1500);
            sections.add(2000);
        }

        // Remove the null values
        sections.removeIf(Objects::isNull);
        if (sections.size() < 2) {
            throw new StatisticsException(StatisticsErrorCode.E801.code(), "At least two numbers should be uploaded to form a price range");
        }
        return this.goodsFrontStatisticsManager.getGoodsPriceSales(sections, searchCriteria);
    }

    @ApiOperation(value = "Ranked first in terms of order amount30Commodity, paging data", response = Page.class)
    @GetMapping(value = "/order_price_page")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cycle_type", value = "Cycle type", dataType = "String", paramType = "query", required = true, allowableValues = "YEAR,MONTH"),
            @ApiImplicitParam(name = "year", value = "year", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "month", value = "in", dataType = "int", paramType = "query")})
    public Page getGoodsOrderPriceTopPage(@ApiIgnore SearchCriteria searchCriteria) {
        // The ranking is 30 by default
        Integer topNum = 30;
        // To get the data
        return this.goodsFrontStatisticsManager.getGoodsOrderPriceTopPage(topNum, searchCriteria);
    }

    @ApiOperation(value = "Top order quantity30, paging data", response = Page.class)
    @GetMapping(value = "/order_num_page")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cycle_type", value = "Cycle type", dataType = "String", paramType = "query", required = true, allowableValues = "YEAR,MONTH"),
            @ApiImplicitParam(name = "year", value = "year", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "month", value = "in", dataType = "int", paramType = "query")})
    public Page getGoodsNumTop(@ApiIgnore SearchCriteria searchCriteria) {
        return this.goodsFrontStatisticsManager.getGoodsNumTopPage(30, searchCriteria);
    }

    @ApiOperation(value = "Ranked first in terms of order amount30Commodities, charts and data", response = SimpleChart.class)
    @GetMapping(value = "/order_price")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cycle_type", value = "Cycle type", dataType = "String", paramType = "query", required = true, allowableValues = "YEAR,MONTH"),
            @ApiImplicitParam(name = "year", value = "year", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "month", value = "in", dataType = "int", paramType = "query")})
    public SimpleChart getGoodsOrderPriceTopChart(@ApiIgnore SearchCriteria searchCriteria) {

        // The ranking is 30 by default
        Integer topNum = 30;

        // 2. Obtain data
        return this.goodsFrontStatisticsManager.getGoodsOrderPriceTop(topNum, searchCriteria);
    }

    @ApiOperation(value = "Top order quantity30, chart data", response = SimpleChart.class)
    @GetMapping(value = "/order_num")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cycle_type", value = "Cycle type", dataType = "String", paramType = "query", required = true, allowableValues = "YEAR,MONTH"),
            @ApiImplicitParam(name = "year", value = "year", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "month", value = "in", dataType = "int", paramType = "query")})
    public SimpleChart getGoodsNumTopChart(@ApiIgnore SearchCriteria searchCriteria) {
        // To get the data
        return this.goodsFrontStatisticsManager.getGoodsNumTop(30, searchCriteria);
    }

}
