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
package cloud.shopfly.b2c.api.base;

import cloud.shopfly.b2c.core.member.model.vo.RegionVO;
import cloud.shopfly.b2c.core.system.model.dos.Regions;
import cloud.shopfly.b2c.core.system.service.RegionsManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * regionapi
 *
 * @author zh
 * @version v7.0
 * @date 18/5/28 In the afternoon7:49
 * @since v7.0
 */
@RestController
@RequestMapping("/regions")
@Api(description = "regionAPI")
public class RegionsBaseController {

    @Autowired
    private RegionsManager regionsManager;


    @GetMapping(value = "/{id}/children")
    @ApiOperation(value = "Gets the children of a region")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "regionid", required = true, dataType = "int", paramType = "path")
    })
    public List<Regions> getChildrenById(@PathVariable Integer id) {

        return regionsManager.getRegionsChildren(id);
    }


    @GetMapping(value = "/depth/{depth}")
    @ApiOperation(value = "Regions that organize the region data structure according to the region deep query")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "depth", value = "The depth of the", required = true, dataType = "int", paramType = "path")
    })
    public List<RegionVO> getRegionByDepth(@PathVariable Integer depth) {
        return regionsManager.getRegionByDepth(depth);
    }


}
