/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.api.seller.member;

import dev.shopflix.core.member.model.dos.Member;
import dev.shopflix.core.member.model.dto.MemberEditDTO;
import dev.shopflix.core.member.model.dto.MemberQueryParam;
import dev.shopflix.core.member.service.MemberManager;
import dev.shopflix.framework.database.Page;
import dev.shopflix.framework.exception.ResourceNotFoundException;
import dev.shopflix.framework.util.BeanUtil;
import dev.shopflix.framework.util.StringUtil;
import dev.shopflix.framework.validation.annotation.Mobile;
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
 * 会员控制器
 *
 * @author zh
 * @version v2.0
 * @since v7.0.0
 * 2018-03-16 11:33:56
 */
@RestController
@RequestMapping("/seller/members")
@Validated
@Api(description = "会员管理API")
public class MemberSellerController {

    @Autowired
    private MemberManager memberManager;

    @ApiOperation(value = "注销会员登录")
    @PostMapping(value = "/logout")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uid", value = "会员id", dataType = "int", paramType = "query", required = true)
    })
    public String loginOut(@NotNull(message = "会员id不能为空") Integer uid) {
        this.memberManager.logout(uid);
        return null;
    }


    @ApiOperation(value = "查询会员列表", response = Member.class)
    @GetMapping
    public Page list(@Valid MemberQueryParam memberQueryParam, @ApiIgnore Integer pageNo,
                     @ApiIgnore Integer pageSize) {
        memberQueryParam.setPageNo(pageNo);
        memberQueryParam.setPageSize(pageSize);
        return this.memberManager.list(memberQueryParam);
    }

    @PutMapping(value = "/{id}")
    @ApiOperation(value = "修改会员", response = Member.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "password", value = "会员密码", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "mobile", value = "手机号码", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "remark", value = "会员备注", required = false, dataType = "String", paramType = "query")
    })
    public Member edit(@Valid MemberEditDTO memberEditDTO, @PathVariable Integer id, String password, @Mobile String mobile, String remark) {
        Member member = memberManager.getModel(id);
        if (member == null) {
            throw new ResourceNotFoundException("当前会员不存在");
        }
        //如果密码不为空的话 修改密码
        if (!StringUtil.isEmpty(password)) {
            //退出会员信息
            memberManager.memberLoginout(id);
            //组织会员的新密码
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
    @ApiOperation(value = "删除会员")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "要删除的会员主键", required = true, dataType = "int", paramType = "path")
    })
    public String delete(@PathVariable Integer id) {
        Member member = memberManager.getModel(id);
        if (member == null) {
            throw new ResourceNotFoundException("当前会员不存在");
        }
        member.setDisabled(-1);
        this.memberManager.edit(member, id);
        return "";
    }


    @PostMapping(value = "/{id}")
    @ApiOperation(value = "恢复会员")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "要恢复的会员主键", required = true, dataType = "int", paramType = "path")
    })
    public Member recovery(@PathVariable Integer id) {
        Member member = memberManager.getModel(id);
        if (member == null) {
            throw new ResourceNotFoundException("当前会员不存在");
        }
        if (member.getDisabled().equals(-1)) {
            member.setDisabled(0);
            this.memberManager.edit(member, id);
        }
        return member;
    }


    @GetMapping(value = "/{id}")
    @ApiOperation(value = "查询一个会员")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "要查询的会员主键", required = true, dataType = "int", paramType = "path")
    })
    public Member get(@PathVariable Integer id) {
        return this.memberManager.getModel(id);
    }


    @PostMapping
    @ApiOperation(value = "平台添加会员")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "password", value = "会员密码", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "uname", value = "会员用户名", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "mobile", value = "手机号码", required = true, dataType = "String", paramType = "query")
    })
    public Member addMember(@Valid MemberEditDTO memberEditDTO, @NotEmpty(message = "会员密码不能为空") String password, @Length(min = 2, max = 20, message = "用户名长度必须在2到20位之间") String uname, @Mobile String mobile) {
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
    @ApiOperation(value = "查询多个会员的基本信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "member_ids", value = "要查询的会员的主键", required = true, dataType = "int", paramType = "path", allowMultiple = true)})
    public List<Member> getGoodsDetail(@PathVariable("member_ids") Integer[] memberIds) {
        return this.memberManager.getMemberByIds(memberIds);

    }

}
