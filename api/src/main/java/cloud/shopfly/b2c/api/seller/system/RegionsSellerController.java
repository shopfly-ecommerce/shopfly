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

import cloud.shopfly.b2c.core.system.model.dos.Regions;
import cloud.shopfly.b2c.core.system.model.vo.RegionsVO;
import cloud.shopfly.b2c.core.system.service.RegionsManager;
import cloud.shopfly.b2c.framework.exception.ResourceNotFoundException;
import cloud.shopfly.b2c.framework.util.BeanUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


/**
 * Area controller
 *
 * @author zh
 * @version v7.0.0
 * @since v7.0.0
 * 2018-05-18 11:45:03
 */
@RestController
@RequestMapping("/seller/systems/regions")
@Api(description = "Areas related toAPI")
public class RegionsSellerController {

    @Autowired
    private RegionsManager regionsManager;

    @ApiOperation(value = "Add region", response = Regions.class)
    @PostMapping
    public Regions add(@Valid RegionsVO regionsVO) {
        return this.regionsManager.add(regionsVO);

    }

    @PutMapping(value = "/{id}")
    @ApiOperation(value = "Modify the area", response = Regions.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "A primary key", required = true, dataType = "int", paramType = "path")
    })
    public Regions edit(@Valid RegionsVO regionsVO, @PathVariable Integer id) {
        Regions regions = regionsManager.getModel(id);
        if (regions == null) {
            throw new ResourceNotFoundException("The current region does not exist");
        }
        BeanUtil.copyProperties(regionsVO, regions);
        this.regionsManager.edit(regions, id);
        return regions;
    }


    @DeleteMapping(value = "/{id}")
    @ApiOperation(value = "Delete the region")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "The primary key of the locale to be deleted", required = true, dataType = "int", paramType = "path")
    })
    public String delete(@PathVariable Integer id) {
        this.regionsManager.delete(id);
        return "";
    }


    @GetMapping(value = "/{id}")
    @ApiOperation(value = "Querying a region")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "Primary key of the region to be queried", required = true, dataType = "int", paramType = "path")
    })
    public Regions get(@PathVariable Integer id) {

        Regions regions = this.regionsManager.getModel(id);

        return regions;
    }


    @GetMapping(value = "/{id}/children")
    @ApiOperation(value = "Gets the children of a region")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "regionid", required = true, dataType = "int", paramType = "path")
    })
    public List<Regions> getChildrenById(@PathVariable Integer id) {

        return regionsManager.getRegionsChildren(id);
    }

}
