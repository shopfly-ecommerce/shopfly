/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
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
 * 评论控制器
 *
 * @author fk
 * @version v1.0
 * @since v7.0.0
 * 2018-05-03 10:19:14
 */
@RestController
@RequestMapping("/members/comments")
@Api(description = "评论相关API")
public class MemberCommentBuyerController {

    @Autowired
    private MemberCommentManager memberCommentManager;


    @ApiOperation(value = "查询我的评论列表", response = CommentVO.class)
    @GetMapping
    public Page list(@ApiIgnore Integer pageNo, @ApiIgnore Integer pageSize, CommentQueryParam param) {

        Buyer buyer = UserContext.getBuyer();
        param.setMemberId(buyer.getUid());
        param.setPageNo(pageNo);
        param.setPageSize(pageSize);

        return this.memberCommentManager.list(param);
    }


    @ApiOperation(value = "提交评论")
    @PostMapping
    public MemberComment addComments(@Valid @RequestBody CommentScoreDTO comment) {

        return memberCommentManager.add(comment, Permission.BUYER);
    }

    @ApiOperation(value = "查询某商品的评论", response = CommentVO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page_no", value = "页码", required = false, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "page_size", value = "每页显示数量", required = false, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "goods_id", value = "商品ID", required = true, paramType = "path", dataType = "int")
    })
    @GetMapping("/goods/{goods_id}")
    public Page list(@ApiIgnore Integer pageNo, @ApiIgnore Integer pageSize, @PathVariable("goods_id") Integer goodsId, CommentQueryParam param) {

        param.setGoodsId(goodsId);
        param.setPageNo(pageNo);
        param.setPageSize(pageSize);

        return this.memberCommentManager.list(param);
    }

    @ApiOperation(value = "查询某商品的评论数量", response = MemberCommentCount.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "goods_id", value = "商品ID", required = true, paramType = "path", dataType = "int")
    })
    @GetMapping("/goods/{goods_id}/count")
    public MemberCommentCount count(@PathVariable("goods_id") Integer goodsId) {

        return this.memberCommentManager.count(goodsId);
    }

    @ApiOperation(value = "会员追加评论", response = AdditionalCommentDTO.class)
    @PostMapping("/additional")
    public List<AdditionalCommentDTO> additionalComments(@Valid @RequestBody List<AdditionalCommentDTO> comments) {

        return memberCommentManager.additionalComments(comments, Permission.BUYER);
    }

}