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

import cloud.shopfly.b2c.core.distribution.model.dos.BillTotalDO;
import cloud.shopfly.b2c.core.distribution.service.BillTotalManager;
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
 * Distributor general statement
 *
 * @author Chopper
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/5/24 In the afternoon2:47
 */
@Api(description = "Distributor general statement")
@RestController
@RequestMapping("/seller/distribution/bill/total")
public class BillTotalSellerController {

    @Autowired
    private BillTotalManager billTotalManager;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "page_size", value = "Page size", required = false, paramType = "query", dataType = "int", allowMultiple = false),
            @ApiImplicitParam(name = "page_no", value = "The page number", required = false, paramType = "query", dataType = "int", allowMultiple = false),
    })
    @GetMapping
    @ApiOperation("Balance sheet paging")
    public Page<BillTotalDO> page(@ApiIgnore Integer pageNo, @ApiIgnore Integer pageSize) {
        return billTotalManager.page(pageNo, pageSize);
    }



}
