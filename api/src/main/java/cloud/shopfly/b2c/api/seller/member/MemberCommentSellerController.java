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
package cloud.shopfly.b2c.api.seller.member;

import cloud.shopfly.b2c.core.goods.model.enums.Permission;
import cloud.shopfly.b2c.core.member.model.dos.CommentReply;
import cloud.shopfly.b2c.core.member.model.dto.CommentQueryParam;
import cloud.shopfly.b2c.core.member.model.vo.CommentVO;
import cloud.shopfly.b2c.core.member.service.CommentReplyManager;
import cloud.shopfly.b2c.core.member.service.MemberCommentManager;
import cloud.shopfly.b2c.framework.database.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.constraints.NotEmpty;

/**
 * 评论控制器
 *
 * @author fk
 * @version v1.0
 * @since v7.0.0
 * 2018-05-03 10:19:14
 */
@RestController
@RequestMapping("/seller/members/comments")
@Api(description = "评论相关API")
@Validated
public class MemberCommentSellerController {

    @Autowired
    private MemberCommentManager memberCommentManager;
    @Autowired
    private CommentReplyManager commentReplyManager;


    @ApiOperation(value = "查询评论列表", response = CommentVO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page_no", value = "页码", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "page_size", value = "每页显示数量", required = true, dataType = "int", paramType = "query")
    })
    @GetMapping
    public Page list(@ApiIgnore Integer pageNo, @ApiIgnore Integer pageSize, CommentQueryParam param) {
        param.setPageNo(pageNo);
        param.setPageSize(pageSize);
        return this.memberCommentManager.list(param);
    }

    @ApiOperation(value = "回复评论", notes = "商家回复")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "comment_id", value = "评论id", required = true, dataType = "int", paramType = "path"),
            @ApiImplicitParam(name = "reply", value = "商家回复内容", required = true, dataType = "String", paramType = "query")})
    @PostMapping(value = "/{comment_id}/reply")
    public CommentReply replyComment(@PathVariable(name = "comment_id") Integer commentId, @NotEmpty(message = "回复内容不能为空") String reply) {

        CommentReply commentReply = this.commentReplyManager.replyComment(commentId, reply, Permission.ADMIN);

        return commentReply;
    }

    @ApiOperation(value = "删除评论")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "comment_id", value = "评论id", required = true, dataType = "int", paramType = "path"),
    })
    @DeleteMapping(value = "/{comment_id}")
    public String deleteComment(@PathVariable(name = "comment_id") Integer commentId) {

        this.memberCommentManager.delete(commentId);

        return "";
    }


}
