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
package cloud.shopfly.b2c.api.seller.system;


import cloud.shopfly.b2c.core.system.service.NoticeLogManager;
import cloud.shopfly.b2c.framework.database.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Store station message controller
 *
 * @author zjp
 * @version v7.0.0
 * @since v7.0.0
 * 2018-07-10 10:21:45
 */
@RestController
@RequestMapping("/seller/systems/notice-logs")
@Api(description = "Store site message relatedAPI")
public class SystemNoticeLogSellerController {

    @Autowired
    private NoticeLogManager noticeLogManager;


    @ApiOperation(value = "Query the message list in the store")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page_no", value = "The page number", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "page_size", value = "Display quantity per page", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "is_read", value = "Have read1read0unread", required = false, dataType = "int", paramType = "query", allowableValues = "0,1"),
            @ApiImplicitParam(name = "type", value = "Message type", required = false, dataType = "String", paramType = "query", allowableValues = ",ORDER,GOODS,AFTERSALE")
    })
    @GetMapping
    public Page list(@ApiIgnore Integer pageNo, @ApiIgnore Integer pageSize, @ApiIgnore Integer isRead, String type) {

        return this.noticeLogManager.list(pageNo, pageSize, type, isRead);
    }

    @DeleteMapping(value = "/{ids}")
    @ApiOperation(value = "Delete messages in the shop site")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "Primary key of the message to delete", required = true, dataType = "int", paramType = "path", allowMultiple = true)
    })
    public void delete(@PathVariable("ids") Integer[] ids) {

        this.noticeLogManager.delete(ids);
 
    }

    @PutMapping(value = "/{ids}/read")
    @ApiOperation(value = "Set the message to read")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "To be set to read messagesid", required = true, dataType = "int", paramType = "path", allowMultiple = true)
    })
    public String read(@PathVariable Integer[] ids) {
        this.noticeLogManager.read(ids);
        return null;
    }

}
