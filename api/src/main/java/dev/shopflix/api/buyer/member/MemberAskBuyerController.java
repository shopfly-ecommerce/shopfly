/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.api.buyer.member;

import dev.shopflix.core.member.model.dos.MemberAsk;
import dev.shopflix.core.member.model.dto.CommentQueryParam;
import dev.shopflix.core.member.model.vo.CommentVO;
import dev.shopflix.core.member.service.MemberAskManager;
import dev.shopflix.framework.context.UserContext;
import dev.shopflix.framework.database.Page;
import dev.shopflix.framework.security.model.Buyer;
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