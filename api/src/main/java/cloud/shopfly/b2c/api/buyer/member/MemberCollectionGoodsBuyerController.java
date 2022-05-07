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
package cloud.shopfly.b2c.api.buyer.member;

import cloud.shopfly.b2c.core.base.model.vo.SuccessMessage;
import cloud.shopfly.b2c.core.member.model.dos.MemberCollectionGoods;
import cloud.shopfly.b2c.core.member.service.MemberCollectionGoodsManager;
import cloud.shopfly.b2c.framework.database.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.constraints.NotNull;

/**
 * Member commodity collection table controller
 *
 * @author zh
 * @version v7.0.0
 * @since v7.0.0
 * 2018-04-02 10:13:41
 */
@RestController
@RequestMapping("/members")
@Api(description = "Member merchandise collection table relatedAPI")
@Validated
public class MemberCollectionGoodsBuyerController {

    @Autowired
    private MemberCollectionGoodsManager memberCollectionGoodsManager;


    @ApiOperation(value = "Check the list of members collections", response = MemberCollectionGoods.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page_no", value = "The page number", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "page_size", value = "Display quantity per page", required = true, dataType = "int", paramType = "query")
    })
    @GetMapping("/collection/goods")
    public Page list(@ApiIgnore Integer pageNo, @ApiIgnore Integer pageSize) {

        return this.memberCollectionGoodsManager.list(pageNo, pageSize);
    }


    @ApiOperation(value = "Add member merchandise collection", response = MemberCollectionGoods.class)
    @PostMapping("/collection/goods")
    @ApiImplicitParam(name = "goods_id", value = "productid", required = true, dataType = "int", paramType = "query")
    public MemberCollectionGoods add(@NotNull(message = "productidCant be empty") @ApiIgnore Integer goodsId) {
        MemberCollectionGoods memberCollectionGoods = new MemberCollectionGoods();
        memberCollectionGoods.setGoodsId(goodsId);
        return this.memberCollectionGoodsManager.add(memberCollectionGoods);
    }

    @DeleteMapping(value = "/collection/goods/{goods_id}")
    @ApiOperation(value = "Delete member merchandise collection")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "goods_id", value = "productid", required = true, dataType = "int", paramType = "path")
    })
    public String delete(@PathVariable("goods_id") Integer goodsId) {
        this.memberCollectionGoodsManager.delete(goodsId);
        return "";
    }

    @GetMapping(value = "/collection/goods/{id}")
    @ApiOperation(value = "Check whether members collect goods")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "productid", required = true, dataType = "int", paramType = "path")
    })
    public SuccessMessage isCollection(@PathVariable Integer id) {
        return new SuccessMessage(this.memberCollectionGoodsManager.isCollection(id));
    }


}
