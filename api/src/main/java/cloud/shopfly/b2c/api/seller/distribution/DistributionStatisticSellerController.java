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
package cloud.shopfly.b2c.api.seller.distribution;

import cloud.shopfly.b2c.core.distribution.exception.DistributionErrorCode;
import cloud.shopfly.b2c.core.distribution.exception.DistributionException;
import cloud.shopfly.b2c.core.distribution.service.DistributionStatisticManager;
import cloud.shopfly.b2c.core.statistics.model.vo.SimpleChart;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * statistical
 *
 * @author Chopper
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/5/24 In the afternoon3:15
 */
@RestController
@RequestMapping("/seller/distribution/statistic")
@Api(description = "statistical")
public class DistributionStatisticSellerController {
    protected final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private DistributionStatisticManager distributionStatisticManager;

    @ApiOperation("Order amount statistics")
    @GetMapping("/order")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "circle", value = "Search types：YEAR/MONTH", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "member_id", value = "membersid", paramType = "query", required = true, dataType = "int"),
            @ApiImplicitParam(name = "year", value = "year", paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "month", value = "in", paramType = "query", dataType = "int")
    })
    public SimpleChart order(String circle, Integer memberId, Integer year, Integer month) {
        try {
            if (memberId == null) {
                throw new DistributionException(DistributionErrorCode.E1011.code(), DistributionErrorCode.E1011.des());
            }
            return distributionStatisticManager.getOrderMoney(circle, memberId, year, month);
        } catch (DistributionException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Abnormal statistical amount：", e);
            throw new DistributionException(DistributionErrorCode.E1000.code(), DistributionErrorCode.E1000.des());
        }
    }

    @ApiOperation("Order quantity statistics")
    @GetMapping("/count")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "circle", value = "Search types：YEAR/MONTH", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "member_id", value = "membersid", paramType = "query", required = true, dataType = "int"),
            @ApiImplicitParam(name = "year", value = "year", paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "month", value = "in", paramType = "query", dataType = "int")
    })
    public SimpleChart count(String circle, Integer memberId, Integer year, Integer month) {
        try {
            if (memberId == null) {
                throw new DistributionException(DistributionErrorCode.E1011.code(), DistributionErrorCode.E1011.des());
            }
            return distributionStatisticManager.getOrderCount(circle, memberId, year, month);
        } catch (DistributionException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Abnormal statistical amount：", e);
            throw new DistributionException(DistributionErrorCode.E1000.code(), DistributionErrorCode.E1000.des());
        }
    }

    @ApiOperation("Order cashback statistics")
    @GetMapping("/push")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "circle", value = "Search types：YEAR/MONTH", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "member_id", value = "membersid", paramType = "query", required = true, dataType = "int"),
            @ApiImplicitParam(name = "year", value = "year", paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "month", value = "in", paramType = "query", dataType = "int")
    })
    public SimpleChart push(String circle, Integer memberId, Integer year, Integer month) {
        try {
            if (memberId == null) {
                throw new DistributionException(DistributionErrorCode.E1011.code(), DistributionErrorCode.E1011.des());
            }
            return distributionStatisticManager.getPushMoney(circle, memberId, year, month);
        } catch (DistributionException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Abnormal statistical amount：", e);
            throw new DistributionException(DistributionErrorCode.E1000.code(), DistributionErrorCode.E1000.des());
        }
    }

}
