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

import cloud.shopfly.b2c.core.system.model.dos.ShipTemplateDO;
import cloud.shopfly.b2c.core.system.model.vo.ShipTemplateSellerVO;
import cloud.shopfly.b2c.core.system.model.vo.ShipTemplateVO;
import cloud.shopfly.b2c.core.system.service.ShipTemplateManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;

/**
 * Freight template controller
 *
 * @author zjp
 * @version v7.0.0
 * @since v7.0.0
 * 2018-03-28 21:44:49
 */
@RestController
@RequestMapping("/seller/shops/ship-templates")
@Api(description = "Freight template relatedAPI")
@Validated
public class ShipTemplateSellerController {

    @Autowired
    private ShipTemplateManager shipTemplateManager;


    @ApiOperation(value = "Query the shipping template list", response = ShipTemplateVO.class)
    @GetMapping
    public List<ShipTemplateSellerVO> list() {
        return this.shipTemplateManager.getStoreTemplate();
    }


    @ApiOperation(value = "Add the shipping template", response = ShipTemplateDO.class)
    @PostMapping
    public ShipTemplateDO add(@Valid @RequestBody ShipTemplateSellerVO shipTemplate) {

        return this.shipTemplateManager.save(shipTemplate);
    }

    @PutMapping(value = "/{template_id}")
    @ApiOperation(value = "Modify the freight template", response = ShipTemplateDO.class)
    @ApiImplicitParam(name = "template_id", value = "templateid", required = true, dataType = "int", paramType = "path")
    public ShipTemplateDO edit(@Valid @RequestBody ShipTemplateSellerVO shipTemplate, @ApiIgnore @PathVariable("template_id") Integer templateId) {
        shipTemplate.setId(templateId);
        return this.shipTemplateManager.edit(shipTemplate);
    }


    @DeleteMapping(value = "/{template_id}")
    @ApiOperation(value = "Delete the shipping template")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "template_id", value = "The shipping template primary key to remove", required = true, dataType = "int", paramType = "path")
    })
    public String delete(@ApiIgnore @PathVariable("template_id") Integer templateId) {
        this.shipTemplateManager.delete(templateId);
        return null;
    }

    @GetMapping(value = "/{template_id}")
    @ApiOperation(value = "Query a shipping template")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "template_id", value = "Primary key of the freight template to be queried", required = true, dataType = "int", paramType = "path")
    })
    public ShipTemplateSellerVO get(@ApiIgnore @PathVariable("template_id") Integer templateId) {

        ShipTemplateSellerVO shipTemplate = this.shipTemplateManager.getFromDB(templateId);

        return shipTemplate;
    }

}
