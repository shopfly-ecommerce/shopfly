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

import cloud.shopfly.b2c.core.system.model.vo.ExpressDetailVO;
import cloud.shopfly.b2c.core.system.service.ExpressPlatformManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Logistics query interface
 *
 * @author zh
 * @version v7.0
 * @date 18/7/12 In the morning10:30
 * @since v7.0
 */
@Api(description = "Logistics query interface")
@RestController
@RequestMapping("/express")
@Validated
public class ExpressDetailBuyerController {

    @Autowired
    private ExpressPlatformManager expressPlatformManager;

    @ApiOperation(value = "Query logistics details")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "Logistics companyid", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "num", value = "Courier number", dataType = "String", paramType = "query"),
    })
    @GetMapping
    public ExpressDetailVO list(@ApiIgnore Integer id, @ApiIgnore String num) {
        return this.expressPlatformManager.getExpressDetail(id, num);
    }
}
