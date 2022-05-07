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
import cloud.shopfly.b2c.core.statistics.model.vo.MultipleChart;
import cloud.shopfly.b2c.core.statistics.model.vo.SimpleChart;
import cloud.shopfly.b2c.core.statistics.service.ReportsStatisticsManager;
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
import java.util.*;

/**
 * Business statistics operation report
 *
 * @author mengyuanming
 * @version 2.0
 * @since 7.0
 * 2018years4month18On the afternoon4:28:23
 */
@Api(description = "Business statistics operation report")
@RestController
@RequestMapping("/seller/statistics/reports")
public class ReportsStatisticsSellerController {

    @Autowired
    private ReportsStatisticsManager reportsStatisticsManager;

    @ApiOperation(value = "Sales statistics order amount chart data", response = MultipleChart.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cycle_type", value = "The date type", dataType = "String", paramType = "query", required = true, allowableValues = "YEAR,MONTH"),
            @ApiImplicitParam(name = "year", value = "year", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "month", value = "in", dataType = "int", paramType = "query")})
    @GetMapping("/sales_money")
    public MultipleChart getSalesMoney(@Valid @ApiIgnore SearchCriteria searchCriteria) {
        return this.reportsStatisticsManager.getSalesMoney(searchCriteria);
    }

    @ApiOperation(value = "Sales statistics order volume chart data", response = MultipleChart.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cycle_type", value = "Cycle type", dataType = "String", paramType = "query", required = true, allowableValues = "YEAR,MONTH"),
            @ApiImplicitParam(name = "year", value = "year", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "month", value = "in", dataType = "int", paramType = "query")})
    @GetMapping("/sales_num")
    public MultipleChart getSalesNum(@Valid @ApiIgnore SearchCriteria searchCriteria) {
        return this.reportsStatisticsManager.getSalesNum(searchCriteria);
    }

    @ApiOperation(value = "Sales statistics table data", response = Page.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cycle_type", value = "Cycle type", dataType = "String", paramType = "query", required = true, allowableValues = "YEAR,MONTH"),
            @ApiImplicitParam(name = "year", value = "year", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "month", value = "in", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "page_no", value = "The current page number", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "page_size", value = "Page data capacity", dataType = "int", paramType = "query")})
    @GetMapping("/sales_page")
    public Page getSalesPage(@Valid @ApiIgnore SearchCriteria searchCriteria, @ApiIgnore Integer pageNo, @ApiIgnore Integer pageSize) {
        return this.reportsStatisticsManager.getSalesPage(searchCriteria, pageNo, pageSize);
    }

    @ApiOperation(value = "Summary of sales statistics", response = HashMap.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cycle_type", value = "Cycle type", dataType = "String", paramType = "query", required = true, allowableValues = "YEAR,MONTH"),
            @ApiImplicitParam(name = "year", value = "year", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "month", value = "in", dataType = "int", paramType = "query")})
    @GetMapping("/sales_summary")
    public Map getSalesSummary(@Valid @ApiIgnore SearchCriteria searchCriteria) {
        return this.reportsStatisticsManager.getSalesSummary(searchCriteria);
    }

    @ApiOperation(value = "Regional analysis", response = ArrayList.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cycle_type", value = "Cycle type", dataType = "String", paramType = "query", required = true, allowableValues = "YEAR,MONTH"),
            @ApiImplicitParam(name = "year", value = "year", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "month", value = "in", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "type", value = "The data type", dataType = "String", paramType = "query",required = true,allowableValues = "ORDER_MEMBER_NUM,ORDER_PRICE,ORDER_NUM")
    })
    @GetMapping("/regions/data")
    public List regionMemberMap(@Valid @ApiIgnore SearchCriteria searchCriteria,@ApiIgnore String type) {
        if (null == type || "".equals(type)){
            throw new StatisticsException(StatisticsErrorCode.E801.code(), "Specify the data you want");
        }
        return this.reportsStatisticsManager.regionsMap(searchCriteria, type);
    }

    @ApiOperation(value = "Purchase analysis customer unit price distribution", response = SimpleChart.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cycle_type", value = "Cycle type", dataType = "String", paramType = "query", required = true, allowableValues = "YEAR,MONTH"),
            @ApiImplicitParam(name = "year", value = "year", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "month", value = "in", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "ranges", value = "Price range, no default values are passed", dataType = "int", paramType = "query", allowMultiple = true)})
    @GetMapping("/purchase/ranges")
    public SimpleChart orderDistribution(@Valid @ApiIgnore SearchCriteria searchCriteria, @ApiIgnore @RequestParam(value = "ranges", required = false) Integer[] ranges) {

        // If it is null, set the default value
        if (null == ranges || ranges.length == 0) {
            ranges = new Integer[5];
            ranges[0] = 0;
            ranges[1] = 500;
            ranges[2] = 1000;
            ranges[3] = 1500;
            ranges[4] = 2000;
        }

        // Convert to list, get rid of NULL, the set of array conversions is limited, so thats the only stupid way to do it
        List<Integer> list = Arrays.asList(ranges);
        List<Integer> newList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) != null) {
                newList.add(list.get(i));
            }
        }
        Integer[] newRanges = newList.toArray(new Integer[newList.size()]);

        if (newRanges.length < 2) {
            throw new StatisticsException(StatisticsErrorCode.E801.code(), "At least two numbers should be uploaded to form a price range");
        }
        return this.reportsStatisticsManager.orderDistribution(searchCriteria, newRanges);
    }

    @ApiOperation(value = "Purchase analysis Purchase period distribution", response = SimpleChart.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cycle_type", value = "Cycle type", dataType = "String", paramType = "query", required = true, allowableValues = "YEAR,MONTH"),
            @ApiImplicitParam(name = "year", value = "year", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "month", value = "in", dataType = "int", paramType = "query")})
    @GetMapping("/purchase/period")
    public SimpleChart purchasePeriod(@Valid @ApiIgnore SearchCriteria searchCriteria) {
        return this.reportsStatisticsManager.purchasePeriod(searchCriteria);
    }


}
