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

import cloud.shopfly.b2c.core.member.model.dos.MemberAddress;
import cloud.shopfly.b2c.core.member.service.MemberAddressManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * API related to the user's shipping address
 *
 * @author dmy
 * @version v2.0
 * @since v7.0.0
 * 2018-03-18 15:37:00
 */
@RestController
@RequestMapping("/members")
@Api(description = "API related to the user's shipping address")
public class MemberAddressBuyerController {

    @Autowired
    private MemberAddressManager memberAddressManager;

    @ApiOperation(value = "Query the collection of user shipping address information", response = MemberAddress.class)
    @GetMapping(value = "/addresses")
    public List<MemberAddress> list() {
        return this.memberAddressManager.list();
    }

    @ApiOperation(value = "Add user shipping address", response = MemberAddress.class)
    @PostMapping(value = "/address")
    public MemberAddress add(@Valid MemberAddress memberAddress) {
        this.memberAddressManager.add(memberAddress);
        return memberAddress;
    }

    @PutMapping(value = "/address/{id}")
    @ApiOperation(value = "Update user shipping address", response = MemberAddress.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "primary key ID", required = true, dataType = "int", paramType = "path")
    })
    public MemberAddress edit(@Valid MemberAddress memberAddress, @PathVariable Integer id) {
        return this.memberAddressManager.edit(memberAddress, id);
    }

    @PutMapping(value = "/address/{id}/default")
    @ApiOperation(value = "Set as default shipping address", response = MemberAddress.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "primary key ID", required = true, dataType = "int", paramType = "path")
    })
    public String editDefault(@PathVariable Integer id) {
        this.memberAddressManager.editDefault(id);
        return null;
    }

    @DeleteMapping(value = "/address/{id}")
    @ApiOperation(value = "Delete user shipping address")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "primary key ID", required = true, dataType = "int", paramType = "path")
    })
    public void delete(@PathVariable Integer id) {
        this.memberAddressManager.delete(id);
    }

    @GetMapping(value = "/address/{id}")
    @ApiOperation(value = "Query the details of a user's shipping address")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "primary key ID", required = true, dataType = "int", paramType = "path")
    })
    public MemberAddress get(@PathVariable Integer id) {
        MemberAddress memberAddress = this.memberAddressManager.getModel(id);
        return memberAddress;
    }
}
