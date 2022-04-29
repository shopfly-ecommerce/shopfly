/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.api.buyer.member;


import dev.shopflix.core.base.rabbitmq.AmqpExchange;
import dev.shopflix.core.member.MemberErrorCode;
import dev.shopflix.core.member.model.dos.Member;
import dev.shopflix.core.member.model.dto.MemberEditDTO;
import dev.shopflix.core.member.model.dto.MemberStatisticsDTO;
import dev.shopflix.core.member.service.MemberManager;
import dev.shopflix.framework.context.UserContext;
import dev.shopflix.framework.exception.ResourceNotFoundException;
import dev.shopflix.framework.exception.ServiceException;
import dev.shopflix.framework.security.model.Buyer;
import dev.shopflix.framework.util.BeanUtil;
import dev.shopflix.framework.util.EmojiCharacterUtil;
import dev.shopflix.framework.util.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import dev.shopflix.framework.rabbitmq.MessageSender;
import dev.shopflix.framework.rabbitmq.MqMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;


/**
 * 会员控制器
 *
 * @author zh
 * @version v2.0
 * @since v7.0.0
 * 2018-03-16 11:33:56
 */
@RestController
@RequestMapping("/members")
@Api(description = "会员相关API")
public class MemberBuyerController {

    @Autowired
    private MemberManager memberManager;
    @Autowired
    private MessageSender messageSender;


    @PutMapping
    @ApiOperation(value = "完善会员细信息", response = Member.class)
    public Member perfectInfo(@Valid MemberEditDTO memberEditDTO) {
        Buyer buyer = UserContext.getBuyer();
        Member member = memberManager.getModel(buyer.getUid());
        //判断数据库是否存在此会员
        if (member == null) {
            throw new ResourceNotFoundException("此会员不存在");
        }
        String str =EmojiCharacterUtil.encode(memberEditDTO.getNickname());
        memberEditDTO.setNickname(str);
        BeanUtil.copyProperties(memberEditDTO, member);
        //判断会员是修改资料还是完善资料
        if (member.getInfoFull() != null && !member.getInfoFull().equals(1)) {
            member.setInfoFull(1);
            this.messageSender.send(new MqMessage(AmqpExchange.MEMBER_INFO_COMPLETE, "member-info-complete-routingkey", member.getMemberId()));
        }
        member.setFace(memberEditDTO.getFace());
        member.setTel(memberEditDTO.getTel());
        this.memberManager.edit(member, buyer.getUid());
        //发送会员资料变化消息
        this.messageSender.send(new MqMessage(AmqpExchange.MEMBER_INFO_CHANGE, AmqpExchange.MEMBER_INFO_CHANGE + "_ROUTING", member.getMemberId()));
        return member;
    }


    @GetMapping
    @ApiOperation(value = "查询当前会员信息")
    public Member get() {
        return this.memberManager.getModel(UserContext.getBuyer().getUid());
    }

    @ApiOperation(value = "注销会员登录")
    @PostMapping(value = "/logout")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uid", value = "会员id", dataType = "int", paramType = "query", required = true)
    })
    public String loginOut(@NotNull(message = "会员id不能为空") Integer uid) {
        this.memberManager.logout(uid);
        return null;
    }

    @GetMapping("/statistics")
    @ApiOperation(value = "统计当前会员的一些数据")
    public MemberStatisticsDTO getMemberStatistics() {
        return this.memberManager.getMemberStatistics();
    }

}