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

import cloud.shopfly.b2c.core.statistics.model.vo.ShopDashboardVO;
import cloud.shopfly.b2c.core.statistics.service.DashboardStatisticManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Business center, home page data
 *
 * @author mengyuanming
 * @version 2.0
 * @since 7.0
 * 2018/6/25 15:13
 */
@RestController
@RequestMapping("/seller/statistics/dashboard")
@Api(description = "Manage home page data")
public class DashboardStatisticsSellerController {

    @Autowired
    private DashboardStatisticManager dashboardStatisticManager;

    @ApiOperation(value = "Home page data", response = ShopDashboardVO.class)
    @GetMapping
    public ShopDashboardVO shop() {
        return this.dashboardStatisticManager.getData();
    }

}
