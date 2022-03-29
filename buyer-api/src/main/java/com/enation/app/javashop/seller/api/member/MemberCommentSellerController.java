/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.seller.api.member;

import com.enation.app.javashop.core.goods.model.enums.Permission;
import com.enation.app.javashop.core.member.model.dos.CommentReply;
import com.enation.app.javashop.core.member.model.dto.CommentQueryParam;
import com.enation.app.javashop.core.member.model.vo.CommentVO;
import com.enation.app.javashop.core.member.service.CommentReplyManager;
import com.enation.app.javashop.core.member.service.MemberCommentManager;
import com.enation.app.javashop.framework.context.UserContext;
import com.enation.app.javashop.framework.database.Page;
import com.enation.app.javashop.framework.security.model.Seller;
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
