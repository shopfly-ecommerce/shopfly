/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.api.buyer.member;

import dev.shopflix.core.member.model.dos.MemberPointHistory;
import dev.shopflix.core.member.model.vo.MemberPointVO;
import dev.shopflix.core.member.service.MemberManager;
import dev.shopflix.core.member.service.MemberPointHistoryManager;
import dev.shopflix.framework.context.UserContext;
import dev.shopflix.framework.database.Page;
import dev.shopflix.framework.security.model.Buyer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.constraints.NotEmpty;

/**
 * 会员积分表控制器
 *
 * @author zh
 * @version v7.0.0
 * @since v7.0.0
 * 2018-04-03 15:44:12
 */
@RestController
@RequestMapping("/members")
@Api(description = "会员积分相关API")
public class MemberPointHistoryBuyerController {

    @Autowired
    private MemberPointHistoryManager memberPointHistoryManager;
    @Autowired
    private MemberManager memberManager;


    @ApiOperation(value = "查询会员积分列表", response = MemberPointHistory.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page_no", value = "页码", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "page_size", value = "每页显示数量", required = true, dataType = "int", paramType = "query")
    })
    @GetMapping("/points")
    public Page list(@ApiIgnore @NotEmpty(message = "页码不能为空") Integer pageNo, @ApiIgnore @NotEmpty(message = "每页数量不能为空") Integer pageSize) {
        Buyer buyer = UserContext.getBuyer();
        return this.memberPointHistoryManager.list(pageNo, pageSize, buyer.getUid());
    }


    @ApiOperation(value = "查询当前会员的积分")
    @GetMapping("/points/current")
    public MemberPointVO getPoint() {
        return memberManager.getMemberPoint();

    }

}