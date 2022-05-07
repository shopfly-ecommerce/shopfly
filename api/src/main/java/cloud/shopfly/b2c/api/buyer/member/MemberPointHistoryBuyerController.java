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

import cloud.shopfly.b2c.core.member.model.dos.MemberPointHistory;
import cloud.shopfly.b2c.core.member.model.vo.MemberPointVO;
import cloud.shopfly.b2c.core.member.service.MemberManager;
import cloud.shopfly.b2c.core.member.service.MemberPointHistoryManager;
import cloud.shopfly.b2c.framework.context.UserContext;
import cloud.shopfly.b2c.framework.database.Page;
import cloud.shopfly.b2c.framework.security.model.Buyer;
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
 * Member points table controller
 *
 * @author zh
 * @version v7.0.0
 * @since v7.0.0
 * 2018-04-03 15:44:12
 */
@RestController
@RequestMapping("/members")
@Api(description = "Member point correlationAPI")
public class MemberPointHistoryBuyerController {

    @Autowired
    private MemberPointHistoryManager memberPointHistoryManager;
    @Autowired
    private MemberManager memberManager;


    @ApiOperation(value = "Query membership points list", response = MemberPointHistory.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page_no", value = "The page number", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "page_size", value = "Display quantity per page", required = true, dataType = "int", paramType = "query")
    })
    @GetMapping("/points")
    public Page list(@ApiIgnore @NotEmpty(message = "The page number cannot be blank") Integer pageNo, @ApiIgnore @NotEmpty(message = "The number of pages cannot be empty") Integer pageSize) {
        Buyer buyer = UserContext.getBuyer();
        return this.memberPointHistoryManager.list(pageNo, pageSize, buyer.getUid());
    }


    @ApiOperation(value = "Query current members points")
    @GetMapping("/points/current")
    public MemberPointVO getPoint() {
        return memberManager.getMemberPoint();

    }

}
