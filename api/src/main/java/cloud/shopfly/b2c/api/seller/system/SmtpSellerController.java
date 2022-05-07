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

import cloud.shopfly.b2c.core.system.model.dos.SmtpDO;
import cloud.shopfly.b2c.core.system.service.SmtpManager;
import cloud.shopfly.b2c.framework.database.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * Mail controller
 *
 * @author zh
 * @version v7.0.0
 * @since v7.0.0
 * 2018-03-25 16:16:53
 */
@RestController
@RequestMapping("/seller/systems/smtps")
@Api(description = "Email relatedAPI")
public class SmtpSellerController {

    @Autowired
    private SmtpManager smtpManager;


    @ApiOperation(value = "Querying mailing lists", response = SmtpDO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page_no", value = "The page number", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "page_size", value = "Display quantity per page", required = true, dataType = "int", paramType = "query")
    })
    @GetMapping
    public Page list(@ApiIgnore @NotEmpty(message = "The page number cannot be blank") Integer pageNo, @ApiIgnore @NotEmpty(message = "The number of pages cannot be empty") Integer pageSize) {
        return this.smtpManager.list(pageNo, pageSize);
    }

    @PutMapping(value = "/{id}")
    @ApiOperation(value = "editsmtp", response = SmtpDO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "A primary key", required = true, dataType = "int", paramType = "path")
    })
    public SmtpDO edit(@Valid SmtpDO smtp, @PathVariable Integer id) {

        this.smtpManager.edit(smtp, id);

        return smtp;
    }

    @GetMapping(value = "/{id}")
    @ApiOperation(value = "Query asmtp")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "To query thesmtpA primary key", required = true, dataType = "int", paramType = "path")
    })
    public SmtpDO get(@PathVariable Integer id) {
        SmtpDO smtp = this.smtpManager.getModel(id);
        return smtp;
    }

    @ApiOperation(value = "addsmtp", response = SmtpDO.class)
    @PostMapping
    public SmtpDO add(@Valid SmtpDO smtp) {
        this.smtpManager.add(smtp);
        return smtp;
    }


    @DeleteMapping(value = "/{id}")
    @ApiOperation(value = "deletesmtp")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "Want to delete thesmtpA primary key", required = true, dataType = "int", paramType = "path")
    })
    public String delete(@PathVariable Integer id) {
        this.smtpManager.delete(id);
        return null;
    }

    @PostMapping(value = "/send")
    @ApiOperation(value = "Test sending emails")
    @ApiImplicitParam(name = "email", value = "Email address to send", required = true, dataType = "String", paramType = "query")
    public String send(@Valid String email, SmtpDO smtp) {
        this.smtpManager.send(email, smtp);
        return null;
    }

}
