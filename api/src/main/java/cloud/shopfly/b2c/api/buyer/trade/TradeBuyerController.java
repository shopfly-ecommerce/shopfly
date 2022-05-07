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
package cloud.shopfly.b2c.api.buyer.trade;

import cloud.shopfly.b2c.core.trade.order.model.vo.TradeVO;
import cloud.shopfly.b2c.core.trade.order.service.TradeManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Transaction controller
 *
 * @author Snow create in 2018/5/8
 * @version v2.0
 * @since v7.0.0
 */
@Api(description = "Transaction interface module")
@RestController
@RequestMapping("/trade")
@Validated
public class TradeBuyerController {

    @Autowired
    @Qualifier("tradeManagerImpl")
    private TradeManager tradeManager;


    @ApiOperation(value = "Create a trading")
    @PostMapping(value = "/create")
    @ApiImplicitParam(name = "client", value = "Client type", required = false, dataType = "String", paramType = "query", allowableValues = "PC,WAP,NATIVE,REACT")
    public TradeVO create(String client) {
        return this.tradeManager.createTrade(client);
    }


}
