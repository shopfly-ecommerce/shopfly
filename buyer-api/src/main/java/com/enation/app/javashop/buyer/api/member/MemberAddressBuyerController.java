/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.buyer.api.member;

import com.enation.app.javashop.core.member.model.dos.MemberAddress;
import com.enation.app.javashop.core.member.service.MemberAddressManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 会员地址控制器
 *
 * @author zh
 * @version v2.0
 * @since v7.0.0
 * 2018-03-18 15:37:00
 */
@RestController
@RequestMapping("/members")
@Api(description = "会员地址相关API")
public class MemberAddressBuyerController {

    @Autowired
    private MemberAddressManager memberAddressManager;

    @ApiOperation(value = "查询当前会员地址列表", response = MemberAddress.class)
    @GetMapping(value = "/addresses")
    public List<MemberAddress> list() {
        return this.memberAddressManager.list();
    }

    @ApiOperation(value = "添加会员地址", response = MemberAddress.class)
    @PostMapping(value = "/address")
    public MemberAddress add(@Valid MemberAddress memberAddress) {
        this.memberAddressManager.add(memberAddress);
        return memberAddress;
    }

    @PutMapping(value = "/address/{id}")
    @ApiOperation(value = "修改会员地址", response = MemberAddress.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", required = true, dataType = "int", paramType = "path")
    })
    public MemberAddress edit(@Valid MemberAddress memberAddress, @PathVariable Integer id) {

        return this.memberAddressManager.edit(memberAddress, id);
    }


    @PutMapping(value = "/address/{id}/default")
    @ApiOperation(value = "设置地址为默认", response = MemberAddress.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", required = true, dataType = "int", paramType = "path")
    })
    public String editDefault(@PathVariable Integer id) {
        this.memberAddressManager.editDefault(id);
        return null;
    }


    @DeleteMapping(value = "/address/{id}")
    @ApiOperation(value = "删除会员地址")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "要删除的会员地址id", required = true, dataType = "int", paramType = "path")
    })
    public String delete(@PathVariable Integer id) {

        this.memberAddressManager.delete(id);

        return "";
    }

    @GetMapping(value = "/address/{id}")
    @ApiOperation(value = "查询当前会员的某个地址")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "要查询的地址id", required = true, dataType = "int", paramType = "path")
    })
    public MemberAddress get(@PathVariable Integer id) {

        MemberAddress memberAddress = this.memberAddressManager.getModel(id);

        return memberAddress;
    }


}
