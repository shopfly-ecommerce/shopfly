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
import cloud.shopfly.b2c.core.statistics.service.PageViewStatisticManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

/**
 * Business center traffic analysis
 *
 * @author mengyuanming
 * @version 2.0
 * @since 7.0
 * 2018years3month19The morning of8:35:47
 */

@Api(description = "Merchant statistics traffic analysis")
@RestController
@RequestMapping("/seller/statistics/page_view")
public class PageViewStatisticSellerController {

    @Autowired
    private PageViewStatisticManager pageViewStatisticManager;

    @ApiOperation(value = "To obtain product access data, only take the first30", response = SimpleChart.class)
    @GetMapping("/goods")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cycle_type", value = "Cycle type", dataType = "String", paramType = "query", required = true, allowableValues = "YEAR,MONTH"),
            @ApiImplicitParam(name = "year", value = "Year, default current year", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "month", value = "Month, default current month", dataType = "int", paramType = "query")})
    public SimpleChart getGoods(@Valid @ApiIgnore SearchCriteria searchCriteria) {
        return this.pageViewStatisticManager.countGoods(searchCriteria);
    }

}
