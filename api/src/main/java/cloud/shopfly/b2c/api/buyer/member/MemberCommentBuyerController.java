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

import cloud.shopfly.b2c.core.goods.model.enums.Permission;
import cloud.shopfly.b2c.core.member.model.dos.MemberComment;
import cloud.shopfly.b2c.core.member.model.dto.AdditionalCommentDTO;
import cloud.shopfly.b2c.core.member.model.dto.CommentQueryParam;
import cloud.shopfly.b2c.core.member.model.dto.CommentScoreDTO;
import cloud.shopfly.b2c.core.member.model.vo.CommentVO;
import cloud.shopfly.b2c.core.member.model.vo.MemberCommentCount;
import cloud.shopfly.b2c.core.member.service.MemberCommentManager;
import cloud.shopfly.b2c.framework.context.UserContext;
import cloud.shopfly.b2c.framework.database.Page;
import cloud.shopfly.b2c.framework.security.model.Buyer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;

/**
 * Comment controller
 *
 * @author fk
 * @version v1.0
 * @since v7.0.0
 * 2018-05-03 10:19:14
 */
@RestController
@RequestMapping("/members/comments")
@Api(description = "Review relatedAPI")
public class MemberCommentBuyerController {

    @Autowired
    private MemberCommentManager memberCommentManager;


    @ApiOperation(value = "Check my list of comments", response = CommentVO.class)
    @GetMapping
    public Page list(@ApiIgnore Integer pageNo, @ApiIgnore Integer pageSize, CommentQueryParam param) {

        Buyer buyer = UserContext.getBuyer();
        param.setMemberId(buyer.getUid());
        param.setPageNo(pageNo);
        param.setPageSize(pageSize);

        return this.memberCommentManager.list(param);
    }


    @ApiOperation(value = "Submit comments")
    @PostMapping
    public MemberComment addComments(@Valid @RequestBody CommentScoreDTO comment) {

        return memberCommentManager.add(comment, Permission.BUYER);
    }

    @ApiOperation(value = "Query a review of an item", response = CommentVO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page_no", value = "The page number", required = false, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "page_size", value = "Display quantity per page", required = false, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "goods_id", value = "productID", required = true, paramType = "path", dataType = "int")
    })
    @GetMapping("/goods/{goods_id}")
    public Page list(@ApiIgnore Integer pageNo, @ApiIgnore Integer pageSize, @PathVariable("goods_id") Integer goodsId, CommentQueryParam param) {

        param.setGoodsId(goodsId);
        param.setPageNo(pageNo);
        param.setPageSize(pageSize);

        return this.memberCommentManager.list(param);
    }

    @ApiOperation(value = "Query the number of reviews for an item", response = MemberCommentCount.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "goods_id", value = "productID", required = true, paramType = "path", dataType = "int")
    })
    @GetMapping("/goods/{goods_id}/count")
    public MemberCommentCount count(@PathVariable("goods_id") Integer goodsId) {

        return this.memberCommentManager.count(goodsId);
    }

    @ApiOperation(value = "Additional comments from members", response = AdditionalCommentDTO.class)
    @PostMapping("/additional")
    public List<AdditionalCommentDTO> additionalComments(@Valid @RequestBody List<AdditionalCommentDTO> comments) {

        return memberCommentManager.additionalComments(comments, Permission.BUYER);
    }

}
