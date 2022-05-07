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
import cloud.shopfly.b2c.core.statistics.model.vo.SimpleChart;
import cloud.shopfly.b2c.core.statistics.service.IndustryStatisticManager;
import cloud.shopfly.b2c.framework.database.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;


/**
 * Industry analysis
 *
 * @author chopper
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/5/8 In the afternoon6:47
 */

@RestController
@Api(description = "Statistical industry analysis")
@RequestMapping("/seller/statistics/industry")
public class IndustryStatisticSellerController {

    @Autowired
    private IndustryStatisticManager industryAnalysisManager;

    @ApiOperation("Order quantity is counted by classification")
    @GetMapping(value = "/order/quantity")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cycle_type", value = "The date type", dataType = "String", paramType = "query", required = true, allowableValues = "YEAR,MONTH"),
            @ApiImplicitParam(name = "year", value = "year", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "month", value = "in", dataType = "int", paramType = "query"),
    })
    public SimpleChart getOrderQuantity(@ApiIgnore SearchCriteria searchCriteria) {
        return this.industryAnalysisManager.getOrderQuantity(searchCriteria);
    }

    @ApiOperation("Count the quantity of goods ordered by category")
    @GetMapping(value = "/goods/num")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cycle_type", value = "The date type", dataType = "String", paramType = "query", required = true, allowableValues = "YEAR,MONTH"),
            @ApiImplicitParam(name = "year", value = "year", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "month", value = "in", dataType = "int", paramType = "query"),
    })
    public SimpleChart getGoodsNum(@ApiIgnore SearchCriteria searchCriteria) {
        return this.industryAnalysisManager.getGoodsNum(searchCriteria);
    }

    @ApiOperation("According to the classification statistics order amount")
    @GetMapping(value = "/order/money")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cycle_type", value = "The date type", dataType = "String", paramType = "query", required = true, allowableValues = "YEAR,MONTH"),
            @ApiImplicitParam(name = "year", value = "year", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "month", value = "in", dataType = "int", paramType = "query"),
    })
    public SimpleChart getOrderMoney(@ApiIgnore SearchCriteria searchCriteria) {
        return this.industryAnalysisManager.getOrderMoney(searchCriteria);
    }

    @ApiOperation("Summarize the overview")
    @GetMapping(value = "/overview")
    public Page getGeneralOverview(SearchCriteria searchCriteria) {
        return this.industryAnalysisManager.getGeneralOverview(searchCriteria);
    }
}
