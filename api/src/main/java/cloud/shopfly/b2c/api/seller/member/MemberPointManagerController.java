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

import cloud.shopfly.b2c.core.member.model.dos.Member;
import cloud.shopfly.b2c.core.member.model.dos.MemberPointHistory;
import cloud.shopfly.b2c.core.member.service.MemberManager;
import cloud.shopfly.b2c.core.member.service.MemberPointHistoryManager;
import cloud.shopfly.b2c.core.member.service.MemberPointManager;
import cloud.shopfly.b2c.framework.context.AdminUserContext;
import cloud.shopfly.b2c.framework.database.Page;
import cloud.shopfly.b2c.framework.security.model.Admin;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 会员积分管理API
 *
 * @author zh
 * @version v7.0
 * @date 18/7/14 下午2:05
 * @since v7.0
 */
@RestController
@RequestMapping("/seller/members/point")
@Validated
@Api(description = "会员积分后台API")
public class MemberPointManagerController {

    @Autowired
    private MemberPointManager memberPointManager;
    @Autowired
    private MemberManager memberManager;

    @Autowired
    MemberPointHistoryManager memberPointHistoryManager;

    @PutMapping(value = "/{member_id}")
    @ApiOperation(value = "修改会消费积分", response = Member.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "member_id", value = "会员id", required = true, dataType = "int", paramType = "path"),
            @ApiImplicitParam(name = "point", value = "调整后的会员消费积分", required = true, dataType = "int", paramType = "query")
    })
    public void editPoint(@PathVariable("member_id") Integer memberId, @Min(value = 0, message = "消费积分不能小于0") Integer point) {
        //获取当前会员的积分 如果当前会员积分大于调整后的积分 则为消费，反之则为新增
        Member member = memberManager.getModel(memberId);
        Integer currentPoint = member.getConsumPoint();
        //增加或者消费的积分数
        Integer operationPoint = point - currentPoint;
        //操作类型  1为加积分 0为减积分或无操作
        Integer type = 0;
        if (operationPoint > 0) {
            type = 1;
        }
        Admin admin = AdminUserContext.getAdmin();
        MemberPointHistory memberPointHistory = new MemberPointHistory();
        memberPointHistory.setMemberId(memberId);
        memberPointHistory.setGradePointType(0);
        memberPointHistory.setGradePoint(0);
        memberPointHistory.setConsumPoint(Math.abs(operationPoint));
        memberPointHistory.setConsumPointType(type);
        memberPointHistory.setReason("管理员手工修改");
        memberPointHistory.setOperator(admin.getUsername());
        memberPointManager.pointOperation(memberPointHistory);

    }

    @ApiOperation(value = "查询会员积分列表", response = MemberPointHistory.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page_no", value = "页码", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "page_size", value = "每页显示数量", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "member_id", value = "会员id", required = true, dataType = "int", paramType = "path")
    })
    @GetMapping("/{member_id}")
    public Page list(@PathVariable("member_id") Integer memberId, @ApiIgnore @NotNull(message = "页码不能为空") Integer pageNo, @ApiIgnore @NotNull(message = "每页数量不能为空") Integer pageSize) {
        return this.memberPointHistoryManager.list(pageNo, pageSize, memberId);
    }


}
