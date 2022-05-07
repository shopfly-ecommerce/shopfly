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
 * Member station message history controller
 *
 * @author zh
 * @version v7.0.0
 * @since v7.0.0
 * 2018-07-05 14:10:16
 */
@RestController
@RequestMapping("/members/member-nocice-logs")
@Api(description = "Member site message history relatedAPI")
public class MemberNoticeLogBuyerController {

    @Autowired
    private MemberNoticeLogManager memberNociceLogManager;


    @ApiOperation(value = "Query the message history list of member sites", response = MemberNoticeLog.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page_no", value = "The page number", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "page_size", value = "Display quantity per page", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "read", value = "Has it been read,1Have read,0unread", allowableValues = "0,1", required = false, dataType = "int", paramType = "query")
    })
    @GetMapping
    public Page list(@ApiIgnore Integer pageNo, @ApiIgnore Integer pageSize, Integer read) {
        return this.memberNociceLogManager.list(pageNo, pageSize, read);
    }


    @PutMapping(value = "/{ids}/read")
    @ApiOperation(value = "Set the message to read", response = MemberNoticeLog.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "To be set to read messagesid", required = true, dataType = "int", paramType = "path", allowMultiple = true)
    })
    public String read(@PathVariable Integer[] ids) {
        this.memberNociceLogManager.read(ids);
        return null;
    }


    @DeleteMapping(value = "/{ids}")
    @ApiOperation(value = "Delete message history in member site")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "Primary key of the message to delete", required = true, dataType = "int", paramType = "path", allowMultiple = true)
    })
    public String delete(@PathVariable Integer[] ids) {
        this.memberNociceLogManager.delete(ids);
        return null;
    }

}
