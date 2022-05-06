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

import cloud.shopfly.b2c.core.member.model.dos.MemberAsk;
import cloud.shopfly.b2c.core.member.model.dto.CommentQueryParam;
import cloud.shopfly.b2c.core.member.model.vo.CommentVO;
import cloud.shopfly.b2c.core.member.service.MemberAskManager;
import cloud.shopfly.b2c.framework.context.UserContext;
import cloud.shopfly.b2c.framework.database.Page;
import cloud.shopfly.b2c.framework.security.model.Buyer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 咨询控制器
 *
 * @author fk
 * @version v1.0
 * @since v7.0.0
 * 2018-05-04 17:41:18
 */
@RestController
@RequestMapping("/members/asks")
@Api(description = "咨询相关API")
@Validated
public class MemberAskBuyerController {

    @Autowired
    private MemberAskManager memberAskManager;


    @ApiOperation(value = "查询我的咨询列表", response = MemberAsk.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page_no", value = "页码", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "page_size", value = "每页显示数量", dataType = "int", paramType = "query"),
    })
    @GetMapping
    public Page list(@ApiIgnore Integer pageNo, @ApiIgnore Integer pageSize, CommentQueryParam param) {

        param.setPageNo(pageNo);
        param.setPageSize(pageSize);
        Buyer member = UserContext.getBuyer();
        param.setMemberId(member.getUid());

        return this.memberAskManager.list(param);
    }


    @ApiOperation(value = "添加咨询", response = MemberAsk.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ask_content", value = "咨询内容", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "goods_id", value = "咨询商品id", dataType = "int", paramType = "query")
    })
    @PostMapping
    public MemberAsk add(@NotEmpty(message = "请输入咨询内容")@ApiIgnore String askContent,@NotNull(message = "咨询商品不能为空") @ApiIgnore Integer goodsId) {

        MemberAsk memberAsk = this.memberAskManager.add(askContent,goodsId);

        return memberAsk;
    }

    @ApiOperation(value	= "查询某商品的咨询", response = CommentVO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page_no", value = "页码", required = false, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "page_size", value = "每页显示数量", required = false, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name="goods_id",value="商品ID",required=true,paramType="path",dataType="int")
    })
    @GetMapping("/goods/{goods_id}")
    public Page listAsks(@ApiIgnore Integer pageNo, @ApiIgnore Integer pageSize, @PathVariable("goods_id") Integer goodsId)	{

        CommentQueryParam param = new CommentQueryParam();
        param.setGoodsId(goodsId);
        param.setPageNo(pageNo);
        param.setPageSize(pageSize);
        param.setReplyStatus(1);

        return	this.memberAskManager.list(param);
    }
}