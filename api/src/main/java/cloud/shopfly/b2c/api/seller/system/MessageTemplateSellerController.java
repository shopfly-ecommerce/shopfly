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

import cloud.shopfly.b2c.core.system.model.dos.MessageTemplateDO;
import cloud.shopfly.b2c.core.system.model.dto.MessageTemplateDTO;
import cloud.shopfly.b2c.core.system.service.MessageTemplateManager;
import cloud.shopfly.b2c.framework.database.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Message template controller
 *
 * @author zjp
 * @version v7.0.0
 * @since v7.0.0
 * 2018-07-05 16:38:43
 */
@RestController
@RequestMapping("/seller/systems/message-templates")
@Api(description = "Message template correlationAPI")
@Validated
public class MessageTemplateSellerController {

    @Autowired
    private MessageTemplateManager messageTemplateManager;


    @ApiOperation(value = "Example Query the message template list", response = MessageTemplateDO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page_no", value = "The page number", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "page_size", value = "Display quantity per page", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "type", value = "Message type", required = true, dataType = "String", paramType = "query", allowableValues = "SHOP,MEMBER")
    })
    @GetMapping
    public Page list(@ApiIgnore @NotNull(message = "The page number cannot be blank") Integer pageNo, @ApiIgnore @NotNull(message = "The number of pages cannot be empty") Integer pageSize, @NotEmpty(message = "Template type This parameter is mandatory") String type) {

        return this.messageTemplateManager.list(pageNo, pageSize, type);
    }


    @PutMapping(value = "/{id}")
    @ApiOperation(value = "Modify the message template", response = MessageTemplateDO.class)
    @ApiImplicitParam(name = "id", value = "A primary key", required = true, dataType = "int", paramType = "path")
    public MessageTemplateDO edit(@Valid MessageTemplateDTO messageTemplate, @PathVariable("id") Integer id) {
        return this.messageTemplateManager.edit(messageTemplate, id);
    }

}
