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

import cloud.shopfly.b2c.core.member.model.dos.MemberNoticeLog;
import cloud.shopfly.b2c.core.member.service.MemberNoticeLogManager;
import cloud.shopfly.b2c.framework.database.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;


/**
 * 会员站内消息历史控制器
 *
 * @author zh
 * @version v7.0.0
 * @since v7.0.0
 * 2018-07-05 14:10:16
 */
@RestController
@RequestMapping("/members/member-nocice-logs")
@Api(description = "会员站内消息历史相关API")
public class MemberNoticeLogBuyerController {

    @Autowired
    private MemberNoticeLogManager memberNociceLogManager;


    @ApiOperation(value = "查询会员站内消息历史列表", response = MemberNoticeLog.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page_no", value = "页码", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "page_size", value = "每页显示数量", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "read", value = "是否已读，1已读，0未读", allowableValues = "0,1", required = false, dataType = "int", paramType = "query")
    })
    @GetMapping
    public Page list(@ApiIgnore Integer pageNo, @ApiIgnore Integer pageSize, Integer read) {
        return this.memberNociceLogManager.list(pageNo, pageSize, read);
    }


    @PutMapping(value = "/{ids}/read")
    @ApiOperation(value = "将消息设置为已读", response = MemberNoticeLog.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "要设置为已读消息的id", required = true, dataType = "int", paramType = "path", allowMultiple = true)
    })
    public String read(@PathVariable Integer[] ids) {
        this.memberNociceLogManager.read(ids);
        return null;
    }


    @DeleteMapping(value = "/{ids}")
    @ApiOperation(value = "删除会员站内消息历史")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "要删除的消息主键", required = true, dataType = "int", paramType = "path", allowMultiple = true)
    })
    public String delete(@PathVariable Integer[] ids) {
        this.memberNociceLogManager.delete(ids);
        return null;
    }

}