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

import cloud.shopfly.b2c.core.member.model.vo.SalesVO;
import cloud.shopfly.b2c.core.member.service.MemberSalesManager;
import cloud.shopfly.b2c.framework.database.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author fk
 * @version v2.0
 * @Description: Commodity tradingcontroller
 * @date 2018/8/15 15:51
 * @since v7.0.0
 */
@Api(description = "Commodity transaction module")
@RestController
@RequestMapping("/trade/goods")
public class GoodsSalesBuyerController {

    @Autowired
    private MemberSalesManager memberSalesManager;

    @ApiOperation(value = "Query sales records of an item", response = SalesVO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page_no", value = "The page number", required = false, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "page_size", value = "Display quantity per page", required = false, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "goods_id", value = "productID", required = true, paramType = "path", dataType = "int")
    })
    @GetMapping("/{goods_id}/sales")
    public Page salesList(@ApiIgnore Integer pageNo, @ApiIgnore Integer pageSize, @PathVariable("goods_id") Integer goodsId) {
        return this.memberSalesManager.list(pageSize, pageNo, goodsId);
    }


}
