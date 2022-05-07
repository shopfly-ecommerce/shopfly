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
package cloud.shopfly.b2c.api.seller.pagedata;

import cloud.shopfly.b2c.core.pagedata.model.FocusPicture;
import cloud.shopfly.b2c.core.pagedata.service.FocusPictureManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;

/**
 * Focus graph controller
 *
 * @author fk
 * @version v1.0
 * @since v7.0.0
 * 2018-05-21 15:23:23
 */
@RestController
@RequestMapping("/seller/focus-pictures")
@Api(description = "Focal graph correlationAPI")
public class FocusPictureSellerController {

    @Autowired
    private FocusPictureManager focusPictureManager;


    @ApiOperation(value = "Query the list of focus graphs", response = FocusPicture.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "client_type", value = "Client typeAPP/WAP/PC", dataType = "string", paramType = "query"),
    })
    @GetMapping
    public List list(@ApiIgnore String clientType) {

        return this.focusPictureManager.list(clientType);
    }


    @ApiOperation(value = "Add focus diagram", response = FocusPicture.class)
    @PostMapping
    public FocusPicture add(@Valid FocusPicture cmsFocusPicture) {

        this.focusPictureManager.add(cmsFocusPicture);

        return cmsFocusPicture;
    }

    @PutMapping(value = "/{id}")
    @ApiOperation(value = "Modify focus diagram", response = FocusPicture.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "A primary key", required = true, dataType = "int", paramType = "path")
    })
    public FocusPicture edit(@Valid FocusPicture cmsFocusPicture, @PathVariable Integer id) {

        this.focusPictureManager.edit(cmsFocusPicture, id);

        return cmsFocusPicture;
    }


    @DeleteMapping(value = "/{id}")
    @ApiOperation(value = "Delete focus diagram")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "The focus graph primary key to delete", required = true, dataType = "int", paramType = "path")
    })
    public String delete(@PathVariable Integer id) {

        this.focusPictureManager.delete(id);

        return "";
    }


    @GetMapping(value = "/{id}")
    @ApiOperation(value = "Query a focus graph")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "Primary key of the focus graph to be queried", required = true, dataType = "int", paramType = "path")
    })
    public FocusPicture get(@PathVariable Integer id) {

        FocusPicture cmsFocusPicture = this.focusPictureManager.getModel(id);

        return cmsFocusPicture;
    }

}
