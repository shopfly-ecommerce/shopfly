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

import cloud.shopfly.b2c.core.system.model.dos.UploaderDO;
import cloud.shopfly.b2c.core.system.model.vo.UploaderVO;
import cloud.shopfly.b2c.core.system.service.UploaderManager;
import cloud.shopfly.b2c.framework.database.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.constraints.NotEmpty;

/**
 * Storage scheme controller
 *
 * @author zh
 * @version v7.0.0
 * @since v7.0.0
 * 2018-03-22 09:31:56
 */
@RestController
@RequestMapping("/seller/systems/uploaders")
@Api(description = "Storage Scheme correlationAPI")
public class UploaderSellerController {

    @Autowired
    private UploaderManager uploaderManager;


    @ApiOperation(value = "Example Query the storage scheme list", response = UploaderDO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page_no", value = "The page number", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "page_size", value = "Display quantity per page", required = true, dataType = "int", paramType = "query")
    })
    @GetMapping
    public Page list(@ApiIgnore @NotEmpty(message = "The page number cannot be blank") Integer pageNo, @ApiIgnore @NotEmpty(message = "The number of pages cannot be empty") Integer pageSize) {
        return this.uploaderManager.list(pageNo, pageSize);
    }

    @ApiOperation(value = "Modify storage scheme parameters", response = UploaderDO.class)
    @PutMapping(value = "/{bean}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bean", value = "Storage solutionbean id", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "uploader", value = "Store the object", required = true, dataType = "UploaderVO", paramType = "body")
    })
    public UploaderVO edit(@PathVariable String bean, @RequestBody @ApiIgnore UploaderVO uploader) {
        uploader.setBean(bean);
        return this.uploaderManager.edit(uploader);
    }

    @ApiOperation(value = "Enable a storage scheme", response = String.class)
    @PutMapping("/{bean}/open")
    @ApiImplicitParam(name = "bean", value = "bean", required = true, dataType = "String", paramType = "path")
    public String open(@PathVariable String bean) {
        this.uploaderManager.openUploader(bean);
        return null;
    }


    @ApiOperation(value = "Obtain the configuration of the storage scheme", response = String.class)
    @GetMapping("/{bean}")
    @ApiImplicitParam(name = "bean", value = "Storage solutionbean id", required = true, dataType = "String", paramType = "path")
    public UploaderVO getUploadSetting(@PathVariable String bean) {
        return this.uploaderManager.getUploadConfig(bean);
    }


}
