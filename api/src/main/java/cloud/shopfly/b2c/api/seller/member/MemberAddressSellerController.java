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

import cloud.shopfly.b2c.core.member.model.dos.MemberAddress;
import cloud.shopfly.b2c.core.member.service.MemberAddressManager;
import cloud.shopfly.b2c.framework.database.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 会员管理地址api
 *
 * @author zh
 * @version v2.0
 * @since v7.0.0
 * 2018-03-18 15:37:00
 */
@RestController
@RequestMapping("/seller/members")
@Api(description = "会员地址相关API")
public class MemberAddressSellerController {

    @Autowired
    private MemberAddressManager memberAddressManager;

    @ApiOperation(value = "查询指定会员的地址列表", response = MemberAddress.class)
    @GetMapping(value = "/addresses/{member_id}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page_no", value = "页码", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "page_size", value = "每页显示数量", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "member_id", value = "会员id", required = true, dataType = "int", paramType = "path")
    })
    public Page list(@ApiIgnore @PathVariable("member_id") Integer memberId, @ApiIgnore Integer pageNo, @ApiIgnore Integer pageSize) {
        return this.memberAddressManager.list(pageNo, pageSize, memberId);
    }
}
