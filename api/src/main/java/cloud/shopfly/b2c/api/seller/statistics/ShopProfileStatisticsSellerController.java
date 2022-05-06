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

import cloud.shopfly.b2c.core.statistics.model.vo.ShopProfileVO;
import cloud.shopfly.b2c.core.statistics.model.vo.SimpleChart;
import cloud.shopfly.b2c.core.statistics.service.ShopProfileStatisticsManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商家中心统计，店铺概况
 *
 * @author mengyuanming
 * @version 2.0
 * @since 7.0
 * 2018年4月18日上午11:30:50
 */
@Api(description = "商家统计 店铺概况")
@RestController
@RequestMapping("/seller/statistics/shop_profile")
public class ShopProfileStatisticsSellerController {

    @Autowired
    private ShopProfileStatisticsManager shopProfileStatisticsManager;

    @ApiOperation(value = "获取30天店铺概况展示数据", response = ShopProfileVO.class)
    @GetMapping("/data")
    public ShopProfileVO getLast30dayStatus() {
        return this.shopProfileStatisticsManager.data();
    }

    @ApiOperation(value = "获取30天店铺下单金额统计图数据", response = SimpleChart.class)
    @GetMapping("/chart")
    public SimpleChart getLast30dayLineChart() {
        return this.shopProfileStatisticsManager.chart();
    }

}
