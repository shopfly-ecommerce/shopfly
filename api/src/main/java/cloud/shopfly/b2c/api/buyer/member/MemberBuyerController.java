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


import cloud.shopfly.b2c.core.base.rabbitmq.AmqpExchange;
import cloud.shopfly.b2c.core.member.model.dos.Member;
import cloud.shopfly.b2c.core.member.model.dto.MemberEditDTO;
import cloud.shopfly.b2c.core.member.model.dto.MemberStatisticsDTO;
import cloud.shopfly.b2c.core.member.service.MemberManager;
import cloud.shopfly.b2c.framework.context.UserContext;
import cloud.shopfly.b2c.framework.exception.ResourceNotFoundException;
import cloud.shopfly.b2c.framework.security.model.Buyer;
import cloud.shopfly.b2c.framework.util.BeanUtil;
import cloud.shopfly.b2c.framework.util.EmojiCharacterUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import cloud.shopfly.b2c.framework.rabbitmq.MessageSender;
import cloud.shopfly.b2c.framework.rabbitmq.MqMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;


/**
 * Member controller
 *
 * @author zh
 * @version v2.0
 * @since v7.0.0
 * 2018-03-16 11:33:56
 */
@RestController
@RequestMapping("/members")
@Api(description = "Members relatedAPI")
public class MemberBuyerController {

    @Autowired
    private MemberManager memberManager;
    @Autowired
    private MessageSender messageSender;


    @PutMapping
    @ApiOperation(value = "Perfect member details", response = Member.class)
    public Member perfectInfo(@Valid MemberEditDTO memberEditDTO) {
        Buyer buyer = UserContext.getBuyer();
        Member member = memberManager.getModel(buyer.getUid());
        // Determine whether this member exists in the database
        if (member == null) {
            throw new ResourceNotFoundException("This member does not exist");
        }
        String str =EmojiCharacterUtil.encode(memberEditDTO.getNickname());
        memberEditDTO.setNickname(str);
        BeanUtil.copyProperties(memberEditDTO, member);
        // Judge whether members are revising or perfecting their data
        if (member.getInfoFull() != null && !member.getInfoFull().equals(1)) {
            member.setInfoFull(1);
            this.messageSender.send(new MqMessage(AmqpExchange.MEMBER_INFO_COMPLETE, "member-info-complete-routingkey", member.getMemberId()));
        }
        member.setFace(memberEditDTO.getFace());
        member.setTel(memberEditDTO.getTel());
        this.memberManager.edit(member, buyer.getUid());
        // Send membership information change messages
        this.messageSender.send(new MqMessage(AmqpExchange.MEMBER_INFO_CHANGE, AmqpExchange.MEMBER_INFO_CHANGE + "_ROUTING", member.getMemberId()));
        return member;
    }


    @GetMapping
    @ApiOperation(value = "Query the current member information")
    public Member get() {
        return this.memberManager.getModel(UserContext.getBuyer().getUid());
    }

    @ApiOperation(value = "Log out member login")
    @PostMapping(value = "/logout")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uid", value = "membersid", dataType = "int", paramType = "query", required = true)
    })
    public String loginOut(@NotNull(message = "membersidCant be empty") Integer uid) {
        this.memberManager.logout(uid);
        return null;
    }

    @GetMapping("/statistics")
    @ApiOperation(value = "Collect some data about the current membership")
    public MemberStatisticsDTO getMemberStatistics() {
        return this.memberManager.getMemberStatistics();
    }

}
