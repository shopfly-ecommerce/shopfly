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
import cloud.shopfly.b2c.core.member.model.dto.MemberEditDTO;
import cloud.shopfly.b2c.core.member.model.dto.MemberQueryParam;
import cloud.shopfly.b2c.core.member.service.MemberManager;
import cloud.shopfly.b2c.framework.database.Page;
import cloud.shopfly.b2c.framework.exception.ResourceNotFoundException;
import cloud.shopfly.b2c.framework.util.BeanUtil;
import cloud.shopfly.b2c.framework.util.StringUtil;
import cloud.shopfly.b2c.framework.validation.annotation.Mobile;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;


/**
 * Member controller
 *
 * @author zh
 * @version v2.0
 * @since v7.0.0
 * 2018-03-16 11:33:56
 */
@RestController
@RequestMapping("/seller/members")
@Validated
@Api(description = "Member managementAPI")
public class MemberSellerController {

    @Autowired
    private MemberManager memberManager;

    @ApiOperation(value = "Log out member login")
    @PostMapping(value = "/logout")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uid", value = "membersid", dataType = "int", paramType = "query", required = true)
    })
    public String loginOut(@NotNull(message = "membersidCant be empty") Integer uid) {
        this.memberManager.logout(uid);
        return null;
    }


    @ApiOperation(value = "Query membership list", response = Member.class)
    @GetMapping
    public Page list(@Valid MemberQueryParam memberQueryParam, @ApiIgnore Integer pageNo,
                     @ApiIgnore Integer pageSize) {
        memberQueryParam.setPageNo(pageNo);
        memberQueryParam.setPageSize(pageSize);
        return this.memberManager.list(memberQueryParam);
    }

    @PutMapping(value = "/{id}")
    @ApiOperation(value = "Modify the member", response = Member.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "password", value = "The member password", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "mobile", value = "Mobile phone number", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "remark", value = "Member of the note", required = false, dataType = "String", paramType = "query")
    })
    public Member edit(@Valid MemberEditDTO memberEditDTO, @PathVariable Integer id, String password, @Mobile String mobile, String remark) {
        Member member = memberManager.getModel(id);
        if (member == null) {
            throw new ResourceNotFoundException("Current member does not exist");
        }
        // If the password is not empty, change the password
        if (!StringUtil.isEmpty(password)) {
            // Membership Withdrawal Information
            memberManager.memberLoginout(id);
            // Organization members new password
            member.setPassword(StringUtil.md5(password + member.getUname().toLowerCase()));
        }
        member.setRemark(remark);
        member.setUname(member.getUname());
        member.setMobile(mobile);
        member.setTel(memberEditDTO.getTel());
        BeanUtil.copyProperties(memberEditDTO, member);
        this.memberManager.edit(member, id);
        return member;
    }


    @DeleteMapping(value = "/{id}")
    @ApiOperation(value = "Delete members")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "The member primary key to delete", required = true, dataType = "int", paramType = "path")
    })
    public String delete(@PathVariable Integer id) {
        Member member = memberManager.getModel(id);
        if (member == null) {
            throw new ResourceNotFoundException("Current member does not exist");
        }
        member.setDisabled(-1);
        this.memberManager.edit(member, id);
        return "";
    }


    @PostMapping(value = "/{id}")
    @ApiOperation(value = "To restore the member")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "The member primary key to restore", required = true, dataType = "int", paramType = "path")
    })
    public Member recovery(@PathVariable Integer id) {
        Member member = memberManager.getModel(id);
        if (member == null) {
            throw new ResourceNotFoundException("Current member does not exist");
        }
        if (member.getDisabled().equals(-1)) {
            member.setDisabled(0);
            this.memberManager.edit(member, id);
        }
        return member;
    }


    @GetMapping(value = "/{id}")
    @ApiOperation(value = "Query a member")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "The member primary key to query", required = true, dataType = "int", paramType = "path")
    })
    public Member get(@PathVariable Integer id) {
        return this.memberManager.getModel(id);
    }


    @PostMapping
    @ApiOperation(value = "Platform membership")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "password", value = "The member password", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "uname", value = "Member user name", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "mobile", value = "Mobile phone number", required = true, dataType = "String", paramType = "query")
    })
    public Member addMember(@Valid MemberEditDTO memberEditDTO, @NotEmpty(message = "Member password cannot be empty") String password, @Length(min = 2, max = 20, message = "The length of the username must be within2to20Between a") String uname, @Mobile String mobile) {
        Member member = new Member();
        member.setUname(uname);
        member.setPassword(password);
        member.setNickname(memberEditDTO.getNickname());
        member.setMobile(mobile);
        member.setTel(memberEditDTO.getTel());
        BeanUtil.copyProperties(memberEditDTO, member);
        memberManager.register(member);
        return member;

    }


    @GetMapping(value = "/{member_ids}/list")
    @ApiOperation(value = "Query the basic information of multiple members")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "member_ids", value = "Primary key of the member to query", required = true, dataType = "int", paramType = "path", allowMultiple = true)})
    public List<Member> getGoodsDetail(@PathVariable("member_ids") Integer[] memberIds) {
        return this.memberManager.getMemberByIds(memberIds);

    }

}
