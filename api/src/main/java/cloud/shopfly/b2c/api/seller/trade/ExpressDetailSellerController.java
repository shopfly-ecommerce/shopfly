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
package cloud.shopfly.b2c.api.seller.trade;

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
 * 物流查询接口
 *
 * @author zh
 * @version v7.0
 * @date 18/7/12 上午10:30
 * @since v7.0
 */
@Api(description = "物流查询接口")
@RestController
@RequestMapping("/seller/express")
@Validated
public class ExpressDetailSellerController {

    @Autowired
    private ExpressPlatformManager expressPlatformManager;

    @ApiOperation(value = "查询物流详细")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "物流公司id", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "num", value = "快递单号", dataType = "String", paramType = "query"),
    })
    @GetMapping
    public ExpressDetailVO list(@ApiIgnore Integer id, @ApiIgnore String num) {
        return this.expressPlatformManager.getExpressDetail(id, num);
    }
}
