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
 * Member Points ManagementAPI
 *
 * @author zh
 * @version v7.0
 * @date 18/7/14 In the afternoon2:05
 * @since v7.0
 */
@RestController
@RequestMapping("/seller/members/point")
@Validated
@Api(description = "Member Points backgroundAPI")
public class MemberPointManagerController {

    @Autowired
    private MemberPointManager memberPointManager;
    @Autowired
    private MemberManager memberManager;

    @Autowired
    MemberPointHistoryManager memberPointHistoryManager;

    @PutMapping(value = "/{member_id}")
    @ApiOperation(value = "Modifications cost points", response = Member.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "member_id", value = "membersid", required = true, dataType = "int", paramType = "path"),
            @ApiImplicitParam(name = "point", value = "Adjusted member consumption points", required = true, dataType = "int", paramType = "query")
    })
    public void editPoint(@PathVariable("member_id") Integer memberId, @Min(value = 0, message = "Consumption points cannot be less than0") Integer point) {
        // If the current member points are greater than the adjusted points, it is consumption, otherwise, it is new
        Member member = memberManager.getModel(memberId);
        Integer currentPoint = member.getConsumPoint();
        // The number of points added or spent
        Integer operationPoint = point - currentPoint;
        // Operation type 1 is plus integral and operation type 0 is minus integral or no operation
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
        memberPointHistory.setReason("Manually Modified by the administrator");
        memberPointHistory.setOperator(admin.getUsername());
        memberPointManager.pointOperation(memberPointHistory);

    }

    @ApiOperation(value = "Query membership points list", response = MemberPointHistory.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page_no", value = "The page number", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "page_size", value = "Display quantity per page", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "member_id", value = "membersid", required = true, dataType = "int", paramType = "path")
    })
    @GetMapping("/{member_id}")
    public Page list(@PathVariable("member_id") Integer memberId, @ApiIgnore @NotNull(message = "The page number cannot be blank") Integer pageNo, @ApiIgnore @NotNull(message = "The number of pages cannot be empty") Integer pageSize) {
        return this.memberPointHistoryManager.list(pageNo, pageSize, memberId);
    }


}
