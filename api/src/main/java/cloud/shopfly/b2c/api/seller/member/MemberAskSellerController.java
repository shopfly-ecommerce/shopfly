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

import cloud.shopfly.b2c.core.member.model.dos.MemberAsk;
import cloud.shopfly.b2c.core.member.model.dto.CommentQueryParam;
import cloud.shopfly.b2c.core.member.service.MemberAskManager;
import cloud.shopfly.b2c.framework.database.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.constraints.NotEmpty;

/**
 * 咨询控制器
 *
 * @author fk
 * @version v1.0
 * @since v7.0.0
 * 2018-05-04 17:41:18
 */
@RestController
@RequestMapping("/seller/members/asks")
@Api(description = "咨询相关API")
@Validated
public class MemberAskSellerController {

    @Autowired
    private MemberAskManager memberAskManager;


    @ApiOperation(value = "查询咨询列表", response = MemberAsk.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page_no", value = "页码", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "page_size", value = "每页显示数量", dataType = "int", paramType = "query")
    })
    @GetMapping
    public Page list(@ApiIgnore Integer pageNo, @ApiIgnore Integer pageSize, CommentQueryParam param) {
        param.setPageNo(pageNo);
        param.setPageSize(pageSize);
        return this.memberAskManager.list(param);
    }


    @ApiOperation(value = "回复咨询", response = MemberAsk.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "reply_content", value = "回复内容", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "ask_id", value = "咨询id", dataType = "int", paramType = "path")
    })
    @PutMapping("/{ask_id}/reply")
    public MemberAsk reply(@ApiIgnore @NotEmpty(message = "请输入回复内容") String replyContent, @ApiIgnore @PathVariable("ask_id") Integer askId) {

        MemberAsk memberAsk = this.memberAskManager.reply(replyContent, askId);

        return memberAsk;
    }

    @ApiOperation(value = "删除咨询", response = MemberAsk.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ask_id", value = "咨询id", dataType = "int", paramType = "path"),
    })
    @DeleteMapping("/{ask_id}")
    public String reply(@PathVariable("ask_id") Integer askId) {

        this.memberAskManager.delete(askId);

        return "";
    }

    @ApiOperation(value = "审核咨询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ask_id", value = "咨询id", dataType = "int", paramType = "path"),
            @ApiImplicitParam(name = "auth_status", value = "审核状态:PASS_AUDIT(审核通过),REFUSE_AUDIT(审核拒绝)",
                    allowableValues = "PASS_AUDIT,REFUSE_AUDIT", dataType = "string", paramType = "query")
    })
    @PostMapping("/auth/{ask_id}")
    public String authAsk(@PathVariable("ask_id") Integer askId,@ApiIgnore String authStatus) {

        this.memberAskManager.auth(askId,authStatus);

        return "";
    }
}
