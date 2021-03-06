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
import cloud.shopfly.b2c.core.statistics.model.vo.MapChartData;
import cloud.shopfly.b2c.core.statistics.model.vo.MultipleChart;
import cloud.shopfly.b2c.core.statistics.model.vo.SalesTotal;
import cloud.shopfly.b2c.core.statistics.model.vo.SimpleChart;
import cloud.shopfly.b2c.core.statistics.service.OrderStatisticManager;
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

/**
 * Order statistics
 *
 * @author chopper
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/5/8 In the afternoon6:47
 */


@RestController
@Api(description = "The background of statistical=》Other statistics")
@RequestMapping("/seller/statistics/order")
public class OrderStatisticSellerController {

    @Autowired
    private OrderStatisticManager orderAnalysisManager;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "cycle_type", value = "The date type", dataType = "String", paramType = "query", required = true, allowableValues = "YEAR,MONTH"),
            @ApiImplicitParam(name = "year", value = "year", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "month", value = "in", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "order_status", value = "Status", required = false, dataType = "String", paramType = "query")
    })
    @ApiOperation("Other statistics=》Order statistics=》Place the order amount")
    @GetMapping(value = "/order/money")
    public MultipleChart getOrderMoney(@ApiIgnore SearchCriteria searchCriteria, @ApiIgnore String orderStatus) {
        return this.orderAnalysisManager.getOrderMoney(searchCriteria, orderStatus);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "cycle_type", value = "The date type", dataType = "String", paramType = "query", required = true, allowableValues = "YEAR,MONTH"),
            @ApiImplicitParam(name = "year", value = "year", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "month", value = "in", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "order_status", value = "Status", required = false, dataType = "String", paramType = "query")
    })
    @ApiOperation("Other statistics=》Order statistics=》Order quantity")
    @GetMapping(value = "/order/num")
    public MultipleChart getOrderNum(@ApiIgnore SearchCriteria searchCriteria, @ApiIgnore String orderStatus) {
        return this.orderAnalysisManager.getOrderNum(searchCriteria, orderStatus);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "cycle_type", value = "The date type", dataType = "String", paramType = "query", required = true, allowableValues = "YEAR,MONTH"),
            @ApiImplicitParam(name = "year", value = "year", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "month", value = "in", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "order_status", value = "Status", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "page_no", value = "The page number", required = false, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "page_size", value = "Page size", required = false, dataType = "int", paramType = "query")
    })
    @ApiOperation("Other statistics=》Order statistics=》Order quantity")
    @GetMapping(value = "/order/page")
    public Page getOrderPage(@ApiIgnore SearchCriteria searchCriteria, @ApiIgnore String orderStatus, @ApiIgnore Integer pageNo, @ApiIgnore Integer pageSize) {
        return this.orderAnalysisManager.getOrderPage(searchCriteria, orderStatus, pageNo, pageSize);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "cycle_type", value = "The date type", dataType = "String", paramType = "query", required = true, allowableValues = "YEAR,MONTH"),
            @ApiImplicitParam(name = "year", value = "year", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "month", value = "in", dataType = "int", paramType = "query"),
    })
    @ApiOperation("Other statistics=》Sales revenue statisticspage")
    @GetMapping(value = "/sales/money")
    public Page getSalesMoney(@ApiIgnore SearchCriteria searchCriteria, @ApiIgnore Integer pageNo, @ApiIgnore Integer pageSize) {
        return this.orderAnalysisManager.getSalesMoney(searchCriteria,  pageNo,  pageSize);
    }


    @ApiOperation("Other statistics=》Sales revenue refund statisticspage")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cycle_type", value = "The date type", dataType = "String", paramType = "query", required = true, allowableValues = "YEAR,MONTH"),
            @ApiImplicitParam(name = "year", value = "year", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "month", value = "in", dataType = "int", paramType = "query"),
    })
    @GetMapping(value = "/aftersales/money")
    public Page getAfterSalesMoney(@ApiIgnore SearchCriteria searchCriteria, @ApiIgnore Integer pageNo, @ApiIgnore Integer pageSize) {
        return this.orderAnalysisManager.getAfterSalesMoney(searchCriteria,  pageNo,  pageSize);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "cycle_type", value = "The date type", dataType = "String", paramType = "query", required = true, allowableValues = "YEAR,MONTH"),
            @ApiImplicitParam(name = "year", value = "year", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "month", value = "in", dataType = "int", paramType = "query"),
    })
    @ApiOperation("Other statistics=》An overview of sales revenue")
    @GetMapping(value = "/sales/total")
    public SalesTotal getSalesMoneyTotal(@ApiIgnore SearchCriteria searchCriteria) {
        return this.orderAnalysisManager.getSalesMoneyTotal(searchCriteria);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "cycle_type", value = "The date type", dataType = "String", paramType = "query", required = true, allowableValues = "YEAR,MONTH"),
            @ApiImplicitParam(name = "year", value = "year", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "month", value = "in", dataType = "int", paramType = "query"),
    })
    @ApiOperation("Regional analysis=>Number of single members")
    @GetMapping(value = "/region/member")
    public MapChartData getOrderRegionMember(@ApiIgnore SearchCriteria searchCriteria) {
        return this.orderAnalysisManager.getOrderRegionMember(searchCriteria);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "cycle_type", value = "The date type", dataType = "String", paramType = "query", required = true, allowableValues = "YEAR,MONTH"),
            @ApiImplicitParam(name = "year", value = "year", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "month", value = "in", dataType = "int", paramType = "query"),
    })
    @ApiOperation("Regional analysis=>Order quantity")
    @GetMapping(value = "/region/num")
    public MapChartData getOrderRegionNum(@ApiIgnore SearchCriteria searchCriteria) {
        return this.orderAnalysisManager.getOrderRegionNum(searchCriteria);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "cycle_type", value = "The date type", dataType = "String", paramType = "query", required = true, allowableValues = "YEAR,MONTH"),
            @ApiImplicitParam(name = "year", value = "year", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "month", value = "in", dataType = "int", paramType = "query"),
    })
    @ApiOperation("Regional analysis=>Place the order amount")
    @GetMapping(value = "/region/money")
    public MapChartData getOrderRegionMoney(@ApiIgnore SearchCriteria searchCriteria) {
        return this.orderAnalysisManager.getOrderRegionMoney(searchCriteria);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "cycle_type", value = "The date type", dataType = "String", paramType = "query", required = true, allowableValues = "YEAR,MONTH"),
            @ApiImplicitParam(name = "year", value = "year", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "month", value = "in", dataType = "int", paramType = "query"),
    })
    @ApiOperation("Area analysis table=>page")
    @GetMapping(value = "/region/form")
    public Page getOrderRegionForm(@ApiIgnore SearchCriteria searchCriteria) {
        return this.orderAnalysisManager.getOrderRegionForm(searchCriteria);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "cycle_type", value = "The date type", dataType = "String", paramType = "query", required = true, allowableValues = "YEAR,MONTH"),
            @ApiImplicitParam(name = "year", value = "year", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "month", value = "in", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "prices", value = "A price range", required = false, paramType = "query", dataType = "int", allowMultiple = true)
    })
    @ApiOperation("Customer unit price distribution=>Customer unit price distribution")
    @GetMapping(value = "/unit/price")
    public SimpleChart getUnitPrice(@ApiIgnore SearchCriteria searchCriteria, @RequestParam(required = false) Integer[] prices) {
        return this.orderAnalysisManager.getUnitPrice(searchCriteria, prices);
    }

    @ApiOperation("Customer unit price distribution=>Purchase frequency analysis")
    @GetMapping(value = "/unit/num")
    public Page getUnitNum() {
        return this.orderAnalysisManager.getUnitNum();
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "cycle_type", value = "The date type", dataType = "String", paramType = "query", required = true, allowableValues = "YEAR,MONTH"),
            @ApiImplicitParam(name = "year", value = "year", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "month", value = "in", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "prices", value = "A price range", required = false, paramType = "query", dataType = "int", allowMultiple = true)
    })
    @ApiOperation("Customer unit price distribution=>Purchase period analysis")
    @GetMapping(value = "/unit/time")
    public SimpleChart getUnitTime(@ApiIgnore SearchCriteria searchCriteria) {
        return this.orderAnalysisManager.getUnitTime(searchCriteria);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "cycle_type", value = "The date type", dataType = "String", paramType = "query", required = true, allowableValues = "YEAR,MONTH"),
            @ApiImplicitParam(name = "year", value = "year", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "month", value = "in", dataType = "int", paramType = "query"),
    })
    @ApiOperation("Refund statistics")
    @GetMapping(value = "/return/money")
    public SimpleChart getReturnMoney(@ApiIgnore SearchCriteria searchCriteria) {
        return this.orderAnalysisManager.getReturnMoney(searchCriteria);
    }

}
