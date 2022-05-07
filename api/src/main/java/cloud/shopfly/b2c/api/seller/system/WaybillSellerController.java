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
import cloud.shopfly.b2c.core.system.model.dos.WayBillDO;
import cloud.shopfly.b2c.core.system.model.vo.WayBillVO;
import cloud.shopfly.b2c.core.system.service.WaybillManager;
import cloud.shopfly.b2c.framework.database.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Electron plane single controller
 *
 * @author zh
 * @version v7.0.0
 * @since v7.0.0
 * 2018-06-08 16:26:05
 */
@RestController
@RequestMapping("/seller/systems/waybills")
@Api(description = "Electron plane dependenceAPI")
public class WaybillSellerController {

    @Autowired
    private WaybillManager waybillManager;


    @ApiOperation(value = "Query the electronic plane single column list", response = WayBillDO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page_no", value = "The page number", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "page_size", value = "Display quantity per page", required = true, dataType = "int", paramType = "query")
    })
    @GetMapping
    public Page list(@ApiIgnore Integer pageNo, @ApiIgnore Integer pageSize) {
        return this.waybillManager.list(pageNo, pageSize);
    }

    @ApiOperation(value = "Modify the electronic sheet", response = UploaderDO.class)
    @PutMapping(value = "/{bean}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bean", value = "Electronic surface singlebean id", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "wayBillVO", value = "Electron plane single object", required = true, dataType = "WayBillVO", paramType = "body")
    })
    public WayBillVO edit(@PathVariable String bean, @RequestBody @ApiIgnore WayBillVO wayBillVO) {
        wayBillVO.setBean(bean);
        return this.waybillManager.edit(wayBillVO);
    }


    @PutMapping(value = "/{bean}/open")
    @ApiOperation(value = "Turn on the electronic plane", response = WayBillDO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bean", value = "bean", required = true, dataType = "String", paramType = "path")
    })
    public String open(@PathVariable String bean) {
        this.waybillManager.open(bean);
        return null;
    }

    @ApiOperation(value = "Get the electronic surface sheet configuration", response = String.class)
    @GetMapping("/{bean}")
    @ApiImplicitParam(name = "bean", value = "Electronic surface singlebean id", required = true, dataType = "String", paramType = "path")
    public WayBillVO getWaybillSetting(@PathVariable String bean) {
        return waybillManager.getWaybillConfig(bean);
    }

}
